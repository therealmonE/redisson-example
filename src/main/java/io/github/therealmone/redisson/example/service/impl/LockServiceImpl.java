package io.github.therealmone.redisson.example.service.impl;

import io.github.therealmone.redisson.example.model.Document;
import io.github.therealmone.redisson.example.repository.DocumentRepository;
import io.github.therealmone.redisson.example.service.LockService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.val;
import org.redisson.api.RedissonClient;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class LockServiceImpl implements LockService {

    private final RedissonClient redissonClient;
    private final DocumentRepository documentRepository;

    @Override
    @SneakyThrows
    public void lockDocument(UUID documentId, Consumer<Document> documentConsumer) {
        if (!documentRepository.existsById(documentId)) {
            throw new NoSuchElementException();
        }

        val lock = redissonClient.getFairLock(documentId.toString());

        if (lock.tryLock(5, TimeUnit.SECONDS)) {
            try {
                setDocumentLocked(documentId);

                val document = documentRepository.findById(documentId).orElseThrow();
                documentConsumer.accept(document);

                documentRepository.save(document);
            } finally {
                setDocumentUnlocked(documentId);
                lock.unlock();
            }
        } else {
            throw new RuntimeException("Lock is unavailable");
        }
    }

    @Override
    @Async
    public void lockDocumentAsync(UUID documentId, Consumer<Document> documentConsumer) {
        lockDocument(documentId, documentConsumer);
    }

    private void setDocumentUnlocked(UUID documentId) {
        val document = documentRepository.findById(documentId).orElseThrow();
        document.setLocked(false);
        documentRepository.save(document);
    }

    private void setDocumentLocked(UUID documentId) {
        val document = documentRepository.findById(documentId).orElseThrow();
        document.setLocked(true);
        documentRepository.save(document);
    }
}
