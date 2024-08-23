package com.example.skuCampusAlarm.controller;

import com.example.skuCampusAlarm.domain.Comment;
import com.example.skuCampusAlarm.domain.CommentForm;
import com.example.skuCampusAlarm.domain.Member;
import com.example.skuCampusAlarm.service.CommentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/posts/{postId}/comment")
    public ResponseEntity<String> createComment(@PathVariable("postId") Long postId, @RequestBody CommentForm form, HttpSession session) {
        Member loggedInMember = (Member) session.getAttribute("loggedInMember");
        if (loggedInMember == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }
        try {
            Comment comment = commentService.createComment(postId, loggedInMember.getId(), form.getContent(), form.getParentId());
            return ResponseEntity.status(HttpStatus.CREATED).body("댓글이 성공적으로 작성되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/posts/{postId}/comment/{commentId}")
    public ResponseEntity<String> updateComment(@PathVariable("postId") Long postId, @PathVariable("commentId") Long commentId, @RequestBody CommentForm form, HttpSession session) {
        Member loggedInMember = (Member) session.getAttribute("loggedInMember");
        if (loggedInMember == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }

        try {
            commentService.updateComment(postId, commentId, loggedInMember.getId(), form.getContent());
            return ResponseEntity.ok("댓글이 성공적으로 수정되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    @DeleteMapping("/posts/{postId}/comment/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable("postId") Long postId, @PathVariable("commentId") Long commentId, HttpSession session) {
        Member loggedInMember = (Member) session.getAttribute("loggedInMember");
        if (loggedInMember == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }

        try {
            commentService.deleteComment(postId, commentId, loggedInMember.getId());
            return ResponseEntity.ok("댓글이 성공적으로 삭제되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<Comment>> getCommentsByPostId(@PathVariable("postId") Long postId) {
        List<Comment> comments = commentService.getCommentsByPostId(postId);
        return ResponseEntity.ok(comments);
    }

    @PostMapping("/reply/{parentId}")
    public ResponseEntity<String> addReply(@PathVariable("parentId") Long parentId, @RequestBody CommentForm form, HttpSession session) {
        Member loggedInMember = (Member) session.getAttribute("loggedInMember");
        if (loggedInMember == null) {
            return ResponseEntity.status(401).body("로그인이 필요합니다.");
        }

        Comment parentComment = commentService.getCommentById(parentId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid parent comment ID"));

        if (parentComment.isDeleted()) {
            return ResponseEntity.status(403).body("삭제된 댓글에는 답글을 작성할 수 없습니다.");
        }

        Comment comment = new Comment();
        comment.setParent(parentComment);
        comment.setAuthor(loggedInMember);
        comment.setCreatedAt(LocalDateTime.now());
        comment.setPost(parentComment.getPost());
        comment.setDeleted(false);
        comment.setContent(form.getContent());

        commentService.saveComment(comment);
        return ResponseEntity.ok("답글이 성공적으로 추가되었습니다.");
    }
    @GetMapping("/comments/{parentId}/replies")
    public ResponseEntity<List<Comment>> getReplies(@PathVariable("parentId") Long parentId) {
        List<Comment> replies = commentService.getReplies(parentId);
        return ResponseEntity.ok(replies);
    }
}