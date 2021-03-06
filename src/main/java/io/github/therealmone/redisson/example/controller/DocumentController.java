package io.github.therealmone.redisson.example.controller;

import io.github.therealmone.redisson.example.dto.UpdateDocumentRequest;
import io.github.therealmone.redisson.example.model.Document;
import io.github.therealmone.redisson.example.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/document")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    @GetMapping("/{documentId}")
    public Document getById(@PathVariable("documentId") UUID documentId) {
        return documentService.getDocumentById(documentId);
    }

    @GetMapping("/")
    public List<Document> getAllDocuments() {
        return documentService.getAllDocuments();
    }

    @PostMapping("/")
    public Document createDocument() {
        return documentService.createNewDocument();
    }

    @PutMapping("/{documentId}")
    public ResponseEntity<?> updateDocument(@PathVariable("documentId") UUID documentId,
                                            @RequestBody UpdateDocumentRequest updateDocumentRequest) {

        documentService.updateDocument(documentId, updateDocumentRequest);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
