package com.lazyprogrammer.payment.repositories;

import com.lazyprogrammer.payment.models.AccountsDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BankRepository {
    private static final Map<String, Long> ACCOUNTS = new HashMap<>();
    private static long MINIMUM_BALANCE_REQUIRED = 100;

    static {
        populateAccounts();
    }

    public static boolean checkBalanceAndPay(String userId,
                                             long paymentAmount) {
        long availableBalance = ACCOUNTS.get(userId);
        if ((availableBalance - MINIMUM_BALANCE_REQUIRED) < paymentAmount) {
            return Boolean.FALSE;
        } else {
            ACCOUNTS.put(userId, (availableBalance - paymentAmount));
            return Boolean.TRUE;
        }
    }


    public static List<AccountsDto> getAllAccounts() {
        return ACCOUNTS.entrySet().stream()
                .map(entry -> new AccountsDto(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    public static void populateAccounts() {
        ACCOUNTS.put("101", 3000L);
        ACCOUNTS.put("102", 6000L);
        ACCOUNTS.put("103", 9000L);
    }

}
