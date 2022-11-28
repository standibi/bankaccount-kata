package com.audensiel.banking.domain.operation;

import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class OperationTest {
    private Operation depositOperation;
    private Operation withdrawalOperation;
    @Test
    public void shouldComputeBalanceAfterDepositOperation(){
        final LocalDateTime dateTime = LocalDateTime.parse("2022-10-10T10:10:10");
        final BigDecimal operationAmount = new BigDecimal(100);
        depositOperation = new Operation(OperationType.DEPOSIT, dateTime,  operationAmount);

        final BigDecimal initialBalance = new BigDecimal(500);
        final BigDecimal newBalance = depositOperation.process(initialBalance);

        assertThat(newBalance, equalTo(initialBalance.add(operationAmount)));
    }

    @Test
    public void shouldComputeBalanceAfterWithDrawalOperation(){
        final LocalDateTime dateTime = LocalDateTime.parse("2022-10-10T10:10:10");
        final BigDecimal operationAmount = new BigDecimal(100);
        depositOperation = new Operation(OperationType.WITHDRAWAL, dateTime,  operationAmount);

        final BigDecimal initialBalance = new BigDecimal(500);
        final BigDecimal newBalance = depositOperation.process(initialBalance);

        assertThat(newBalance, equalTo(initialBalance.subtract(operationAmount)));
    }
}
