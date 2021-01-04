package io.github.therealmone.redisson.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.therealmone.redisson.example.AbstractIntegrationTest;
import io.github.therealmone.redisson.example.action.DocumentControllerAction;
import io.github.therealmone.redisson.example.dto.UpdateDocumentRequest;
import io.github.therealmone.redisson.example.model.Document;
import io.github.therealmone.redisson.example.repository.DocumentRepository;
import lombok.SneakyThrows;
import lombok.val;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@AutoConfigureMockMvc
public class DocumentControllerTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DocumentRepository repository;

    @Autowired
    private ObjectMapper mapper;

    private DocumentControllerAction action;

    @Before
    public void setUp() {
        action = new DocumentControllerAction(mockMvc, mapper);
    }

    @After
    public void cleanUp() {
        repository.deleteAll();
    }

    @Test
    public void updateDocument() {
        val documentId = UUID.randomUUID();

        val document = new Document();
        document.setId(documentId);
        document.setLocked(false);

        repository.save(document);

        action.updateDocument(documentId, new UpdateDocumentRequest("data"), HttpStatus.OK);

        val updatedDocument = repository.findById(documentId).orElseThrow();

        assertEquals("data", updatedDocument.getData());
    }

    @SneakyThrows
    @Test
    public void updateDocumentMultithreaded() {
        val documentId = UUID.randomUUID();

        val document = new Document();
        document.setId(documentId);
        document.setLocked(false);

        repository.save(document);

        val threadCount = 15;

        val monitor = new Object();
        val countDownFotNotify = new CountDownLatch(threadCount);
        val countDownForAssert = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            val data = "data" + i;

            new Thread(() -> {
                synchronized (monitor) {
                    try {
                        countDownFotNotify.countDown();
                        monitor.wait();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    action.updateDocument(documentId, new UpdateDocumentRequest(data), HttpStatus.OK);
                    countDownForAssert.countDown();
                }
            }).start();
        }

        countDownFotNotify.await();
        synchronized (monitor) {
            monitor.notifyAll();
        }
        countDownForAssert.await();

        val updatedDocument = repository.findById(documentId).orElseThrow();

        assertNotNull(updatedDocument.getData());
    }

}
