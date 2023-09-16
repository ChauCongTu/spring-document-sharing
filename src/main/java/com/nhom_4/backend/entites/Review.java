package com.nhom_4.backend.entites;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "auto_id")
    @SequenceGenerator(name = "auto_id", sequenceName = "auto_id", allocationSize = 1)
    private Long id;

    private Long document_id;
    private String name;
    private String email;
    private String content;
    private Date created_at;
}
