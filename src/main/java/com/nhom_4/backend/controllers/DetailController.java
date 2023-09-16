package com.nhom_4.backend.controllers;

import com.nhom_4.backend.dtos.DetailRequest;
import com.nhom_4.backend.entites.CollectionDetail;
import com.nhom_4.backend.repositories.CollectionRepository;
import com.nhom_4.backend.repositories.DetailRepository;
import com.nhom_4.backend.repositories.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/detail")
public class DetailController {

    @Autowired
    private final DetailRepository detailRepository;
    @Autowired
    private final CollectionRepository collectionRepository;
    @Autowired
    private final DocumentRepository documentRepository;

    public DetailController(DetailRepository detailRepository,
                            CollectionRepository collectionRepository,
                            DocumentRepository documentRepository) {
        this.detailRepository = detailRepository;
        this.collectionRepository = collectionRepository;
        this.documentRepository = documentRepository;
    }

    @PostMapping("/{collection_id}")
    CollectionDetail add (@RequestBody DetailRequest detailRequest, @PathVariable Long collection_id) {
        CollectionDetail detail = new CollectionDetail();
        detail.setCollection_id(collection_id);
        detail.setDocument(documentRepository.findById(detailRequest.getDocument_id()).get());

        return detailRepository.save(detail);
    }
    @DeleteMapping("/{id}")
    void delete (@PathVariable Long id) {
        detailRepository.deleteById(id);
    }
}
