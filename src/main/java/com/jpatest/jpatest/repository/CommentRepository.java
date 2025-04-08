package com.jpatest.jpatest.repository;

import com.jpatest.jpatest.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    // select * from comment where board_id=?
    public List<Comment> findByBoardId(int boardId);
}
