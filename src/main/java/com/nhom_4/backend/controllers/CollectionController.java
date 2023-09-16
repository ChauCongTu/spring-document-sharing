package com.nhom_4.backend.controllers;

import com.nhom_4.backend.entites.Collection;
import com.nhom_4.backend.dtos.CollectionRequest;
import com.nhom_4.backend.repositories.CollectionRepository;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/collection")
public class CollectionController {

    private final CollectionRepository collectionRepository;

    private final Path root = Paths.get("public/collection/thumb");

    public CollectionController(CollectionRepository collectionRepository) {
        this.collectionRepository = collectionRepository;
    }

    @GetMapping("/")
    public List<Collection> all() { return collectionRepository.findAll(); }

    @PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Collection> createCollection(@ModelAttribute CollectionRequest collectionRequest) throws IOException {
        Collection collection = new Collection();
        collection.setName(collectionRequest.getName());

        // Lấy tên file phương tiện
        MultipartFile thumb = collectionRequest.getThumb();
        String fileName = thumb.getOriginalFilename();

        try {
            Files.copy(thumb.getInputStream(), this.root.resolve(thumb.getOriginalFilename()));
        } catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {
                throw new RuntimeException("A file of that name already exists.");
            }

            throw new RuntimeException(e.getMessage());
        }

        // Gán tên file phương tiện vào trường thumb của collection
        collection.setThumb("/public/collection/thumb/" + fileName);

        // Lưu collection vào cơ sở dữ liệu
        collectionRepository.save(collection);

        return ResponseEntity.ok(collection);
    }

    @GetMapping("/{id}")
    public Optional<Collection> one(@PathVariable Long id) {
        return collectionRepository.findById(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Optional<Collection> update(@ModelAttribute CollectionRequest collectionRequest, @PathVariable Long id) throws IOException {
         return collectionRepository.findById(id)
                .map(collection -> {
                    collection.setName(collectionRequest.getName());

                    if (!collectionRequest.getThumb().isEmpty()) {
                        // Lấy tên file phương tiện
                        MultipartFile thumb = collectionRequest.getThumb();
                        String fileName = thumb.getOriginalFilename();

                        try {
                            Files.copy(thumb.getInputStream(), this.root.resolve(thumb.getOriginalFilename()));
                        } catch (Exception e) {
                            if (e instanceof FileAlreadyExistsException) {
                                throw new RuntimeException("A file of that name already exists.");
                            }

                            throw new RuntimeException(e.getMessage());
                        }

                        // Gán tên file phương tiện vào trường thumb của collection
                        collection.setThumb("/public/collection/thumb/" + fileName);
                    }
                    return collectionRepository.save(collection);
                });
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable Long id) {
        Optional<Collection> collection = collectionRepository.findById(id);
        collection.map(
                cl -> {
                    File file = new File("/public/collection/thumb/" + cl.getThumb());
                    file.delete();
                    return true;
                }
        );
        collectionRepository.deleteById(id);
    }
}
