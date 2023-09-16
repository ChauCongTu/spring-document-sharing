package com.nhom_4.backend.repositories;

import com.nhom_4.backend.entites.Collection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollectionRepository extends JpaRepository<Collection, Long> {
}
