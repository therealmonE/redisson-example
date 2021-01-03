package io.github.therealmone.redisson.example.controller;

import io.github.therealmone.redisson.example.service.LockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/document")
@RequiredArgsConstructor
public class LockController {

    private final LockService lockService;

    @PutMapping("/{documentId}/lock")
    public ResponseEntity<?> lockDocument(@PathVariable("documentId") UUID documentId,
                                          @RequestParam("seconds") Integer seconds) {

        lockService.lockDocumentAsync(documentId, document -> {
            try {
                TimeUnit.SECONDS.sleep(seconds);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
