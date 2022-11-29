package com.openclassrooms.paymybuddy.repository;

import com.openclassrooms.paymybuddy.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {


    @Query(value = "SELECT * FROM transactions t WHERE t.sender_id = :id OR t.beneficiary_id = :id",
            countQuery = "SELECT COUNT(*) FROM transactions t WHERE t.sender_id = :id OR t.beneficiary_id = :id",
            nativeQuery = true)
    Page<Transaction> findAllTransactionsById(@Param("id") int id, Pageable pageRequest);

}
