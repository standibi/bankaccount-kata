package com.audensiel.banking.domain.account;

import com.audensiel.banking.domain.operation.Operation;
import com.audensiel.banking.domain.operation.OperationType;

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
       processOperation(OperationType.DEPOSIT, amount);
    }

    /**
     * Withdaw money from bank account
     *
     * @param amount
     */

    public void withdraw(BigDecimal amount) {
        processOperation(OperationType.WITHDRAWAL, amount);
    }

    private void processOperation(OperationType operationType, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalOperationException("This operation requires a positive amount");
        }
        if (operationType == OperationType.WITHDRAWAL && balance.compareTo(amount) < 0) {
            throw new InsufficientFundException("Cannot withdraw more than account balance");
        }

        balance = operationType == OperationType.WITHDRAWAL ? balance.subtract(amount) : balance.add(amount);

    }

    public BigDecimal getBalance() {
        return this.balance;
    }

}
