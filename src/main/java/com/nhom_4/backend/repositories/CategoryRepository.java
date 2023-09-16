package com.nhom_4.backend.repositories;

import com.nhom_4.backend.entites.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
