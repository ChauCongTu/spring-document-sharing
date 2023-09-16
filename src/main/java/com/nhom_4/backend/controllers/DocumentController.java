package com.nhom_4.backend.controllers;

import com.nhom_4.backend.dtos.DocumentRequest;
import com.nhom_4.backend.entites.Document;
import com.nhom_4.backend.repositories.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/document")
public class DocumentController {

    @Autowired
    private final DocumentRepository repository;

    private final Path image = Paths.get("public/document/thumb");
    private final Path file = Paths.get("public/document/file");

    public DocumentController(DocumentRepository repository) {
        this.repository = repository;
    }

    // Get all categories
    @GetMapping("/")
    public List<Document> all () {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Document> one (@PathVariable Long id) {
        Optional<Document> document = repository.findById(id);
        document.map(doc -> {
            doc.setViewCount(doc.getViewCount() + 1);
            return repository.save(doc);
        });
        return repository.findById(id);
    }
    @PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Document add (@ModelAttribute DocumentRequest documentRequest) {
        Document newDocument = new Document();

        // Xử lý tải lên tập tin

        try {
            Files.copy(documentRequest.getThumb().getInputStream(), this.image.resolve(documentRequest.getThumb().getOriginalFilename()));
        } catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {
                throw new RuntimeException("A thumb of that name already exists.");
            }

            throw new RuntimeException(e.getMessage());
        }
        try {
            Files.copy(documentRequest.getFilePath().getInputStream(), this.file.resolve(documentRequest.getFilePath().getOriginalFilename()));
        } catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {
                throw new RuntimeException("A file of that name already exists.");
            }

            throw new RuntimeException(e.getMessage());
        }
        String thumb_path = "/public/document/thumb/" + documentRequest.getThumb().getOriginalFilename();
        String file_path = "/public/document/file/" + documentRequest.getFilePath().getOriginalFilename();
        String type = documentRequest.getFilePath().getContentType();
        newDocument.setType(type);
        newDocument.setThumb(thumb_path);
        newDocument.setFilePath(file_path);
        newDocument.setTitle(documentRequest.getTitle());
        newDocument.setSummary(documentRequest.getSummary());
        newDocument.setContent(documentRequest.getContent());
        newDocument.setUser_id(documentRequest.getUser_id());
        newDocument.setCategory_id(documentRequest.getCategory_id());
        newDocument.setViewCount(0L);
        newDocument.setDownloadCount(0L);
        newDocument.setIsApproved(1L);
        return repository.save(newDocument);
    }

    @PutMapping(value = "/approve/{id}")
    public Optional<Document> approve (@PathVariable Long id) {
        Optional<Document> document = repository.findById(id);
        document.map(doc -> {
            doc.setIsApproved(1L);
            return repository.save(doc);
        });
        return repository.findById(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    Optional<Document> update(@ModelAttribute DocumentRequest documentRequest, @PathVariable Long id) {
        return repository.findById(id)
                .map(documentItem -> {
                    if (!documentRequest.getFilePath().isEmpty()) {
                        String file_path = "/public/document/file/" + documentRequest.getFilePath().getOriginalFilename();
                        String type = documentRequest.getFilePath().getContentType();
                        documentItem.setType(type);
                        documentItem.setFilePath(file_path);
                    }
                    if (!documentRequest.getThumb().isEmpty()) {
                        String thumb_path = "/public/document/thumb/" + documentRequest.getThumb().getOriginalFilename();
                        documentItem.setThumb(thumb_path);
                    }
                    documentItem.setTitle(documentRequest.getTitle());
                    documentItem.setSummary(documentRequest.getSummary());
                    documentItem.setContent(documentRequest.getContent());
                    documentItem.setUser_id(documentRequest.getUser_id());
                    documentItem.setCategory_id(documentRequest.getCategory_id());
                    return repository.save(documentItem);
                });
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }

    @GetMapping("/search")
    public List<Document> search (@RequestParam("s") String string) {
        List<Document> documents = repository.findByTitleContaining(string);
        return documents;
    }

    @GetMapping("/{id}/related")
    public List<Document> related (@PathVariable Long id) {
        List<Document> documents = repository.findRelatedDocument(id);
        return documents;
    }
}
