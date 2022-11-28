package com.audensiel.banking.domain.account;

import com.audensiel.banking.domain.operation.Operation;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class BankAccount {

    private long id;
    private BigDecimal balance;
    private List<Operation> operations;

    public BankAccount(long id, BigDecimal balance, List operations) {
        this.balance = Objects.requireNonNull(balance, "Account balance cannot be null");
        this.operations = operations;
    }

    /**
     * Make a deposit to account
     *
     * @param amount
     */
    public void deposit(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalOperationException("Cannot deposit negative amount");
        }
        balance = balance.add(amount);
    }

    public void withdraw(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalOperationException("Cannot withdraw negative amount");
        }
        if (balance.compareTo(amount) < 0) {
            throw new InsufficientFundException(String.format("Account balance is less than ", amount));
        }
        balance = balance.subtract(amount);
    }

    public BigDecimal getBalance() {
        return this.balance;
    }

}
