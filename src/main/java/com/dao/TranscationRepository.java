package com.dao;

import com.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TranscationRepository extends JpaRepository<Transaction,Long> {
}
