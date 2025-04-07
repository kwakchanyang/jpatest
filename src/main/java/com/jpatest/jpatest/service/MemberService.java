package com.jpatest.jpatest.service;

import com.jpatest.jpatest.dto.MemberDto;
import com.jpatest.jpatest.entity.Member;
import com.jpatest.jpatest.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;


    public void insert(MemberDto memberDto) {



        // dto -> entity
        Member member = memberDto.createMember();
        memberRepository.save(member);

    }
// findByMemberIdAndPassword == select * from member where member_id=#{} and password=#{}
    public boolean select(MemberDto memberDto) {

        Member member = memberRepository.findByMemberIdAndPassword(
                memberDto.getMemberId(),memberDto.getPassword()
        );
        if(member == null)
            return false;
        return true;
    }
}
