package com.example.skuCampusAlarm.repository;

import com.example.skuCampusAlarm.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    // 게시글 저장 메서드 추가
    Post save(Post post);

    List<Post> findByAuthorId(Long authorId);


}



