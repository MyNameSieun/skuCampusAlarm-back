package com.example.skuCampusAlarm.service;

import com.example.skuCampusAlarm.domain.Comment;
import com.example.skuCampusAlarm.domain.Member;
import com.example.skuCampusAlarm.domain.Post;
import com.example.skuCampusAlarm.repository.CommentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostService postService;
    private final MemberService memberService;

    public CommentService(CommentRepository commentRepository, PostService postService, MemberService memberService) {
        this.commentRepository = commentRepository;
        this.postService = postService;
        this.memberService = memberService;
    }

    public Comment createComment(Long postId, Long authorId, String content, Long parentId) {
        Post post = postService.getPostById(postId).orElseThrow(() -> new IllegalArgumentException("Invalid post ID"));
        Member author = memberService.findOne(authorId).orElseThrow(() -> new IllegalArgumentException("Invalid author ID"));



        Comment parent = null;
        if (parentId != null) {
            parent = commentRepository.findById(parentId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid parent comment ID"));
        }

        Comment comment = new Comment();
        comment.setPost(post);
        comment.setAuthor(author);
        comment.setContent(content);
        comment.setCreatedAt(LocalDateTime.now());
        comment.setParent(parent); // parent를 null로 설정하는 부분 수정

        return commentRepository.save(comment);
    }

    public void updateComment(Long postId, Long commentId, Long authorId, String content) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글이 존재 하지 않습니다."));

        if (!comment.getPost().getId().equals(postId)) {
            throw new IllegalArgumentException("Comment does not belong to the post");
        }
        if (comment.isDeleted()) {
            throw new IllegalArgumentException("삭제된 댓글은 수정할 수 없습니다.");
        }
        if (!comment.getAuthor().getId().equals(authorId)) {
            throw new IllegalArgumentException("댓글 작성자가 아닙니다.");
        }

        comment.setContent(content);
        commentRepository.save(comment);
    }

    public void deleteComment(Long postId, Long commentId, Long authorId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid comment ID"));

        if (!comment.getPost().getId().equals(postId)) {
            throw new IllegalArgumentException("Comment does not belong to the post");
        }

        if (!comment.getAuthor().getId().equals(authorId)) {
            throw new IllegalArgumentException("댓글 작성자가 아닙니다.");
        }

        if (comment.getReplies().isEmpty()) { // 대댓글이 없는 경우
            commentRepository.delete(comment);
        } else { // 대댓글이 있는 경우
            comment.setDeleted(true);
            comment.setContent("삭제된 댓글입니다.");
            commentRepository.save(comment);
        }
    }

    public List<Comment> getCommentsByPostId(Long postId) {
        return commentRepository.findByPostId(postId);
    }

    public List<Comment> getReplies(Long parentId) {
        return commentRepository.findByParentId(parentId);
    }
    public Optional<Comment> getCommentById(Long commentId) {
        return commentRepository.findById(commentId);
    }

    public void saveComment(Comment comment) {
        commentRepository.save(comment);
    }
}