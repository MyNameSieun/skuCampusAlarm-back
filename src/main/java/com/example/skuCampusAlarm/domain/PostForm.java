package com.example.skuCampusAlarm.domain;


import org.springframework.web.multipart.MultipartFile;

import java.beans.ConstructorProperties;
import java.util.List;

public class PostForm {
    private String title;
    private String content;



    // 생성자, 게터, 세터
    @ConstructorProperties({"title", "content"})
    public PostForm(String title, String content) {
        this.title = title;
        this.content = content;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

