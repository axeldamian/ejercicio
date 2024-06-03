package com.mercadolibre.mongo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.mercadolibre.dtos.Request;

@Repository
public interface ItemRepository extends MongoRepository<Request, String> {

    @Query("{dna:'?0'}")
    boolean findItemByDna(String[] dna);
    
    public long count();

    List<Request> findByDna(String[] dna);

    long countByResult(boolean result);
}