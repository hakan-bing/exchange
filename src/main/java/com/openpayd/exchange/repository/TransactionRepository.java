package com.openpayd.exchange.repository;

import com.openpayd.exchange.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository("transactionRepository")
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query(value = "SELECT * FROM TRANSACTION WHERE (:transactionId is null or ID = :transactionId) AND (:date is null or DATE = :date) \n-- #pageable\n"
            , countQuery = "SELECT count(*) FROM TRANSACTION WHERE (:transactionId is null or ID = :transactionId) AND (:date is null or DATE = :date)", nativeQuery = true)
    Page<Transaction> findByTransactionIdAndDate(@Param("transactionId") Long transactionId, @Param("date") String date, Pageable pageable);
}
