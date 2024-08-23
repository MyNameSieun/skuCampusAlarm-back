package com.example.skuCampusAlarm.controller;

import com.example.skuCampusAlarm.domain.Member;
import com.example.skuCampusAlarm.domain.Post;
import com.example.skuCampusAlarm.domain.PostForm;
import com.example.skuCampusAlarm.repository.MemberRepository;
import com.example.skuCampusAlarm.repository.PostRepository;
import com.example.skuCampusAlarm.service.PostService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@Slf4j
public class PostController {

    private final PostService postService;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;


    @Autowired
    public PostController(PostService postService,
                          MemberRepository memberRepository, PostRepository postRepository) {
        this.postService = postService;
        this.memberRepository = memberRepository;
        this.postRepository = postRepository;
    }
    @PostMapping("/posts/new")
    public ResponseEntity<String> createPost(@RequestParam("title") String title,
                                             @RequestParam("content") String content,
                                             HttpSession session) throws IOException {
        Member loggedInMember = (Member) session.getAttribute("loggedInMember");
        if (loggedInMember == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }


        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        post.setCreatedAt(LocalDateTime.now());
        post.setAuthor(loggedInMember);

        postService.createPost(post);

        return ResponseEntity.status(HttpStatus.CREATED).body("게시글이 성공적으로 작성되었습니다.");
    }






    @GetMapping("/posts")
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }


    @GetMapping("/posts/{id}")
    public ResponseEntity<Post> showPost(@PathVariable("id") Long postId) {
        return postService.getPostById(postId)
                .map(post -> {
                    post.getComments().size();

                    return ResponseEntity.ok(post);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<String> deletePost(@PathVariable("id") Long postId, HttpSession session) {
        Member loggedInMember = (Member) session.getAttribute("loggedInMember");
        if (loggedInMember == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }

        Optional<Post> postOptional = postRepository.findById(postId);
        if (postOptional.isPresent()) {
            Post post = postOptional.get();
            if (post.getAuthor().getId().equals(loggedInMember.getId())) {
                postRepository.deleteById(postId);
                return ResponseEntity.ok("게시글이 성공적으로 삭제되었습니다.");
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("게시글 작성자만 삭제할 수 있습니다.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 ID의 게시물을 찾을 수 없습니다.");
        }
    }

    @PutMapping("/posts/{id}")
    public ResponseEntity<String> updatePost(@PathVariable("id") Long postId,
                                             HttpSession session,
                                             @RequestParam("title") String title,
                                             @RequestParam("content") String content,
                                             @RequestPart(value = "images", required = false) List<MultipartFile> images) throws IOException {
        Member loggedInMember = (Member) session.getAttribute("loggedInMember");
        if (loggedInMember == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }

        Optional<Post> postOptional = postRepository.findById(postId);
        if (postOptional.isPresent()) {
            Post post = postOptional.get();
            if (post.getAuthor().getId().equals(loggedInMember.getId())) {
                if (title != null) {
                    post.setTitle(title);
                }
                if (content != null) {
                    post.setContent(content);
                }

                postRepository.save(post);
                return ResponseEntity.ok("게시글이 성공적으로 수정되었습니다.");
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("게시글 작성자만 수정할 수 있습니다.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 ID의 게시물을 찾을 수 없습니다.");
        }

    }

}
