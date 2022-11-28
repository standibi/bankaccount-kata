package com.audensiel.banking.domain.operation;

import com.audensiel.banking.exception.IllegalOperationException;
import com.audensiel.banking.exception.InsufficientFundException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static com.audensiel.banking.Config.STATEMENT_LINE_FORMAT;

public class Operation {
    private BigDecimal amount;
    private OperationType type;
    private LocalDateTime date;
    private BigDecimal balance = BigDecimal.ZERO;

    public Operation(OperationType type, LocalDateTime date, BigDecimal amount) {
        this.amount = amount;
        this.type = type;
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Operation operation = (Operation) o;

        return amount.compareTo(operation.amount) == 0 && type == operation.type && date.isEqual(operation.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, type, date, balance);
    }

    /**
     * Process un operation
     * @param initialBalance - Account balance before transaction
     * @return Account balance after transaction
     */

    public BigDecimal process(BigDecimal initialBalance) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalOperationException("This operation requires a positive amount");
        }
        if (type == OperationType.WITHDRAWAL && initialBalance.compareTo(amount) < 0) {
            throw new InsufficientFundException();
        }
        balance = type == OperationType.WITHDRAWAL ? initialBalance.subtract(amount) : initialBalance.add(amount);
        return balance;
    }

    @Override
    public String toString() {
        return type + " | "+ date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")) +
                " | " + amount + " | "+balance;
    }

    public void printOperation() {
        System.out.printf(STATEMENT_LINE_FORMAT, type, date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")), amount, balance);
    }
}