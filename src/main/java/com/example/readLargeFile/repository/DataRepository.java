package com.example.readLargeFile.repository;

import com.example.readLargeFile.model.Data;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Optional;

@Repository
public interface DataRepository extends JpaRepository<Data, BigInteger> {
    Optional<Data> getDataById(BigInteger id);
}
