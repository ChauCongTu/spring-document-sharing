package com.nhom_4.backend.dtos;

import org.springframework.web.multipart.MultipartFile;

public class CollectionRequest {
    private String name;
    private MultipartFile thumb;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MultipartFile getThumb() {
        return thumb;
    }

    public void setThumb(MultipartFile thumb) {
        this.thumb = thumb;
    }
}
