package com.btvn.ss7ex5.service;

import org.springframework.stereotype.Service;

@Service
public class TransactionService {
    public String performTransaction(String walletAddress, double amount) {
        return "Giao dịch thành công! Đã chuyển " + amount + " USD đến ví " + walletAddress;
    }
}