package com.example.skuCampusAlarm.domain;

public class CommentForm {
    private String content;
    private Long parentId; // 추가된 필드: 부모 댓글 ID

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}