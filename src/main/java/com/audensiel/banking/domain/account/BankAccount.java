package com.audensiel.banking.domain.account;

import com.audensiel.banking.domain.operation.Operation;
import com.audensiel.banking.domain.operation.OperationType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class BankAccount {

    private BigDecimal balance;
    private List<Operation> operations;

    public BankAccount(BigDecimal balance, List<Operation> operations) {
        this.balance = Objects.requireNonNull(balance, "Account balance cannot be null");
        this.operations = operations;
    }

    /**
     * Make a deposit to account
     *
     * @param amount
     * @param dateTime
     */
    public void deposit(BigDecimal amount, LocalDateTime dateTime) {
        processOperation(OperationType.DEPOSIT, amount, dateTime);
    }

    /**
     * Withdaw money from bank account
     *
     * @param amount
     */

    public void withdraw(BigDecimal amount, LocalDateTime dateTime) {
        processOperation(OperationType.WITHDRAWAL, amount, dateTime);
    }

    private void processOperation(OperationType operationType, BigDecimal amount, LocalDateTime dateTime) {
        final Operation operation = new Operation(operationType, dateTime, amount);
        balance = operation.process(balance);
        operations.add(operation);
    }

    public BigDecimal getBalance() {
        return this.balance;
    }

    public List<Operation> statement() {
        return List.copyOf(operations);
    }
}
