package com.jpatest.jpatest.service;

import com.jpatest.jpatest.dto.BoardDto;
import com.jpatest.jpatest.dto.BoardListDto;
import com.jpatest.jpatest.dto.CommentDto;
import com.jpatest.jpatest.entity.Board;
import com.jpatest.jpatest.entity.Comment;
import com.jpatest.jpatest.repository.BoardRepository;
import com.jpatest.jpatest.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    private final CommentRepository commentRepository;

    // @Autowired 또는 lombok의 @RequiredArgsConstructor을 사용해 주입한다. (final붙은 애의 생성자를 만들어줌)
    // 주입하기 전에 반드시 빈(@Service,@Repository, @Bean)등록 되어야한다.

    public BoardDto getBoard(int boardId){
        //  findById는 기본있는메서드임         ┌primay key로 지정한 컬럼
        Board board = boardRepository.findById(boardId).get();

        List<Comment> commentList = commentRepository.findByBoardId(boardId);

        List<CommentDto> commentDtos = new ArrayList<>();
        for(Comment comment : commentList){
            CommentDto commentDto = CommentDto.from(comment);
            commentDtos.add(commentDto);
        }

        BoardDto boardDto = BoardDto.of(board,commentDtos);
        return boardDto;
    }

    // 게시글 10개 가져와서 목록 출력하기 위한 메서드
    public Page<BoardListDto> getBoardList(Pageable pageable){
        // 결과값을 담을 DTO리스트 생성 > 나중에 Entity
        List<BoardListDto> boardListDtos = new ArrayList<>();

        // 레퍼지토리를 통해 테이블에서 조회하기 - pageable을 이용하여 10개 가져오기
        // peageable 값설정은 control에서 한다.
        List<Board> boards = boardRepository.findAllByOrderByIdDesc(pageable);

        // 전체 게시글 갯수
        Long total = boardRepository.count(); // long기본데이터타입 Long 클래스타입

        // boards리스트를 하나씩 순회하면서 entity(Board) ->  dto(BoardListDto) -> 변환된 boardListDto를 boardListDtos에 추가
        for(Board board : boards){
            BoardListDto boardListDto = BoardListDto.from(board);
            boardListDtos.add(boardListDto); // boardListDto로 썼을때는 Dto안에는 add메서드가 존재하지 않는다 > 그래서 게시글리스트가 비어있는 상태임
        }

        return new PageImpl<>(boardListDtos, pageable, total);
    }
}
