package com.btvn.ss7ex5.aspect;


import com.btvn.ss7ex5.exception.HighRiskException;
import com.btvn.ss7ex5.exception.InvalidTransactionException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TransactionMonitorAspect {
    @Before("execution(* com.btvn.ss7ex5.service.TransactionService.performTransaction(..))")
    public void inspectTransaction(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        String walletAddress = (String) args[0];
        double amount = (Double) args[1];

        if (amount <= 0) {
            throw new InvalidTransactionException("Số lượng tiền gửi phải lớn hơn 0!");
        }

        if (walletAddress == null || walletAddress.trim().length() < 5) {
            throw new InvalidTransactionException("Địa chỉ ví nhận không hợp lệ (Phải từ 5 ký tự trở lên)!");
        }

        if (amount > 10000) {
            throw new HighRiskException("Giao dịch vượt hạn mức ( " + amount + " USD). Yêu cầu duyệt thủ công!");
        }
    }
}
