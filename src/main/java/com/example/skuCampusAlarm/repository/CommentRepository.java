package com.example.skuCampusAlarm.repository;

import com.example.skuCampusAlarm.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostId(Long postId);
    List<Comment> findByParentId(Long parentId); // 부모 댓글 ID로 검색
}