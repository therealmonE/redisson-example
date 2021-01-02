package io.github.therealmone.redisson.example.repository;

import io.github.therealmone.redisson.example.model.Document;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DocumentRepository extends MongoRepository<Document, UUID> {

}
