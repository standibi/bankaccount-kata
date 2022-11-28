package com.audensiel.banking.domain.account;

import com.audensiel.banking.exception.IllegalOperationException;
import com.audensiel.banking.exception.InsufficientFundException;
import com.audensiel.banking.domain.operation.Operation;
import com.audensiel.banking.domain.operation.OperationType;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.*;

public class BankAccountTest {
    @Mock
    private List<Operation> operations;
    private BankAccount bankAccount;

    @Before
    public void setUp() {
        openMocks(this);
        bankAccount = new BankAccount(BigDecimal.ZERO, operations);
    }

    @Test
    public void depositShouldIncreasesBalanceByDepositAmount() {
        final BigDecimal balanceBeforeDeposit = bankAccount.getBalance();
        final BigDecimal amountToDeposit = new BigDecimal(100);
        final LocalDateTime dateTime = LocalDateTime.parse("2022-11-11T10:10:10");
        bankAccount.deposit(amountToDeposit, dateTime);

        final BigDecimal balanceAfterDeposit = bankAccount.getBalance();
        assertThat(balanceAfterDeposit, equalTo(balanceBeforeDeposit.add(amountToDeposit)));
    }

    @Test(expected = IllegalOperationException.class)
    public void depositOfNegativeAmountShouldThrowException() {
        final LocalDateTime dateTime = LocalDateTime.parse("2022-11-11T10:10:10");
        bankAccount.deposit(new BigDecimal(-100), dateTime);
    }

    @Test
    public void withdrawalShouldDecreaseBalanceByAmountWithdrawn() {
        final BigDecimal initialAmount = new BigDecimal(100);
        final LocalDateTime dateTime = LocalDateTime.parse("2022-11-11T10:10:10");
        bankAccount.deposit(initialAmount, dateTime);
        final BigDecimal amountToWithdraw = new BigDecimal(70);

        bankAccount.withdraw(amountToWithdraw, dateTime);

        final BigDecimal balanceAfterWithdawal = bankAccount.getBalance();
        assertThat(balanceAfterWithdawal, equalTo(initialAmount.subtract(amountToWithdraw)));
    }

    @Test(expected = InsufficientFundException.class)
    public void withdrawalOfNegativeAmountShouldThrowException() {
        final LocalDateTime dateTime = LocalDateTime.parse("2022-11-11T10:10:10");
        bankAccount.withdraw(new BigDecimal(100), dateTime);
    }

    @Test
    public void depositShouldAddItemToAccountOperations() {
        final BigDecimal amount = new BigDecimal(100);
        final LocalDateTime dateTime = LocalDateTime.parse("2022-11-11T10:10:10");
        bankAccount.deposit(amount, dateTime);
        verify(operations).add(new Operation(OperationType.DEPOSIT, dateTime, amount));
    }

    @Test
    public void withdrawalShouldAddItemToAccountOperations() {
        final LocalDateTime dateTime = LocalDateTime.parse("2022-11-11T10:10:10");
        bankAccount.deposit(new BigDecimal(200), dateTime);
        final BigDecimal amount = new BigDecimal(100);
        bankAccount.withdraw(amount, dateTime);
        verify(operations).add(new Operation(OperationType.WITHDRAWAL, dateTime, amount));
    }

    @Test
    public void shouldReturnAccountStatement() {
        bankAccount = new BankAccount(BigDecimal.ZERO, new ArrayList<>());
        bankAccount.deposit(new BigDecimal(100), LocalDateTime.parse("2022-11-11T10:10:10"));
        bankAccount.withdraw(new BigDecimal(50), LocalDateTime.parse("2022-11-11T10:10:10"));
        bankAccount.deposit(new BigDecimal(200), LocalDateTime.parse("2022-11-11T10:10:10"));

        final List<Operation> statement = bankAccount.statement();

        assertThat(statement.size(), equalTo(3));

    }

}
