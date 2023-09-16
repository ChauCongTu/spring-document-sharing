package com.nhom_4.backend.entites;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "documents")
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "auto_id")
    @SequenceGenerator(name = "auto_id", sequenceName = "auto_id", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String summary;

    @Column(name = "file_path", nullable = false)
    private String filePath;

    @Column(name = "view_count")
    private Long viewCount;

    @Column(name = "download_count")
    private Long downloadCount;

    @Column()
    private String type;

    @Column()
    private Date created_at;

    @Column()
    private String thumb;

    @Column(name = "isapproved")
    private Long isApproved;

    @Column()
    private String content;

    @Column()
    private Long user_id;

    @Column
    private Long category_id;

    @OneToMany(targetEntity = Review.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "document_id", referencedColumnName = "id")
    private List<Review> reviews;
}
