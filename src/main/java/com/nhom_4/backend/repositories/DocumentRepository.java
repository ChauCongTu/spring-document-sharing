package com.nhom_4.backend.repositories;

import com.nhom_4.backend.entites.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    @Query("SELECT d FROM Document d WHERE UPPER(d.title) LIKE %?1%")
    List<Document> findByTitleContaining(String query);

    @Query("SELECT d FROM Document d WHERE d.id <> ?1 AND d.category_id = (SELECT d2.category_id FROM Document d2 WHERE d2.id = ?1)")
    List<Document> findRelatedDocument(Long id);
}
