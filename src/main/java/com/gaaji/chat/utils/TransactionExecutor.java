package com.gaaji.chat.utils;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Supplier;

@Component
public class TransactionExecutor {
    @Transactional
    public <T> T execute(Supplier<T> supplier) {
        return supplier.get();
    }
}
