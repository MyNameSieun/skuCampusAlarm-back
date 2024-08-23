package com.example.skuCampusAlarm.service;


import com.example.skuCampusAlarm.domain.Member;
import com.example.skuCampusAlarm.domain.Post;
import com.example.skuCampusAlarm.repository.MemberRepository;
import com.example.skuCampusAlarm.repository.PostRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    @Autowired
    public PostService(PostRepository postRepository, MemberRepository memberRepository) {
        this.postRepository = postRepository;
        this.memberRepository = memberRepository;
    }

    public void createPost(Post post) {


        postRepository.save(post);
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Optional<Post> getPostById(Long postId) {
        return postRepository.findById(postId);
    }


    public void deletePost(Long postId, String nickname, String password) {
        Optional<Member> author = memberRepository.findByNickname(nickname);
        if (author.isPresent() && author.get().getPassword1().equals(password)) {
            postRepository.deleteById(postId);
        } else {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }

    public void updatePost(Long postId, String title, String content) {
        Optional<Post> postOptional = postRepository.findById(postId);
        if (postOptional.isPresent()) {
            Post post = postOptional.get();
            post.setTitle(title);
            post.setContent(content);
            postRepository.save(post);
        } else {
            throw new IllegalArgumentException("해당 ID의 게시물을 찾을 수 없습니다.");
        }
    }


    public List<Post> findPostsByAuthorId(Long authorId) {
        return postRepository.findByAuthorId(authorId);
    }





}
