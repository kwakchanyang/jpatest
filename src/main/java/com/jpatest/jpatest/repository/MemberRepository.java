package com.jpatest.jpatest.repository;

import com.jpatest.jpatest.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {


    public Member findByMemberIdAndPassword(String memberId, String password);
}
