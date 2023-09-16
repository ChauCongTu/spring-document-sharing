package com.nhom_4.backend.repositories;

import com.nhom_4.backend.entites.CollectionDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetailRepository extends JpaRepository<CollectionDetail, Long> {
}
