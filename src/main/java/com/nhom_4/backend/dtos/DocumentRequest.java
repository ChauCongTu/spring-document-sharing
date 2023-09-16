package com.nhom_4.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DocumentRequest {
    private Long id;
    private String title;
    private String summary;
    private MultipartFile filePath;
    private MultipartFile thumb;
    private String content;
    private Long user_id;
    private Long category_id;
}
