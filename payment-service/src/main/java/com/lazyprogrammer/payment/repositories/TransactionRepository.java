package com.lazyprogrammer.payment.repositories;

import com.lazyprogrammer.payment.models.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class TransactionRepository {
    private static final Map<String, Transaction> DB = new HashMap<>();
    private static final Logger log =
            LoggerFactory.getLogger(TransactionRepository.class.getName());

    public static void save(Transaction transaction) {
        log.info("Saving transaction {}", transaction);
        DB.put(transaction.transactionId(), transaction);
        log.info("Saved transaction {}", transaction);
    }

    public static Transaction get(String transactionId) {
        return DB.get(transactionId);
    }

    public static Collection<Transaction> getAll() {
        return DB.values();
    }

}
