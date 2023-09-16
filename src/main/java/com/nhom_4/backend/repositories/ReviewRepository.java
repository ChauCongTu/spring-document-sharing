package com.nhom_4.backend.repositories;

import com.nhom_4.backend.entites.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("SELECT r FROM Review r WHERE r.document_id = ?1")
    List<Review> findByDocumentId(Long documentId);
}
