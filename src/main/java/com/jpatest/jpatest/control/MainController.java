package com.jpatest.jpatest.control;

import com.jpatest.jpatest.dto.BoardDto;
import com.jpatest.jpatest.dto.BoardListDto;
import com.jpatest.jpatest.dto.CommentDto;
import com.jpatest.jpatest.dto.MemberDto;
import com.jpatest.jpatest.service.BoardService;
import com.jpatest.jpatest.service.MemberService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private BoardService boardService;

    @GetMapping("/")
    public String home(){

        return "index";
    }

    @GetMapping("/member/signUp")
    public String signUp(Model model) {

        //회원가입 페이지에서 작성한 내용을 Dto에 담는다.
        model.addAttribute("memberDto",new MemberDto());

        return "signUp";
    }

    @PostMapping("/member/signUp")
    public String signUp(MemberDto memberDto, Model model){

        memberService.insert(memberDto);

        return "redirect:/";
    }

    @GetMapping("/member/signIn")
    public String signIn(Model model){

        model.addAttribute("memberDto", new MemberDto());
        return "signIn";
    }

    @PostMapping("/member/signIn")
    public String signIn(MemberDto memberDto, HttpSession session){

        boolean isSuccess = memberService.select(memberDto);
        if( isSuccess ) // 아이디 비번 일치하면 true
            session.setAttribute("user" , memberDto.getMemberId());

        return "redirect:/";
    }

    // 게시판 목록 페이지 띄우기
    @GetMapping("/board/index/{page}")
    public String boardHome(@PathVariable("page") Integer pageNum, Model model){
        // BoardService 클래스의 메서드를 호출하여 10개의 게시글을 가져온다.
        // 한 페이지에 10개씩 출력할 것이다.
        // 페이징을 위해 필요한 값도 가져올것이다.(전체 게시글)
        //  Optional - NulPinterException방지를 위해
        // 주의 - 매개변수 사용안하기 , 변수(필드)에서 사용안하기
        // 어디에 사용하나? : 메서드의 리턴 타입으로만 사용하는 것이 좋다.
        // 권장이에요~~ 무조건 지키라는거 아니고~!!
        // Optional을 사용해서 만든다면
        // @PathVariable("page") Optional<Integer> pageNum,

        // -> int currentPage = pageNum.orElse(0);
        int currentPage = (pageNum != null) ? pageNum : 0;
        Pageable pageable = PageRequest.of(currentPage,10);
        // 한 페이지에 10개씩 보여주기 위해 10을 넣었고, 0인덱스투버 limit들어가야 한다.

        Page<BoardListDto> boardListDtos = boardService.getBoardList(pageable);

        model.addAttribute("boardListDtos", boardListDtos);
        model.addAttribute("page", pageable.getPageNumber());
        model.addAttribute("maxPage", 10);

        return "boardList";
    }

    // 게시판 상세 보기
    @GetMapping("/board/detail")
    public String detail(@RequestParam("id") int boardId, Model model) {

        BoardDto boardDto = boardService.getBoard(boardId);
        model.addAttribute("boardDetailDto", boardDto);
        model.addAttribute("commentDto", new CommentDto());

        return "detail";
    }
}

// entity, dto -> repository -> service -> control