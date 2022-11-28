package com.audensiel.banking.domain.account;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;

public class BankAccountTest {
    private BankAccount bankAccount;
    @Before
    public void setUp() {
        bankAccount = new BankAccount(1L, BigDecimal.ZERO, new ArrayList<>());
    }

    @Test
    public void depositShouldIncreasesBalanceByDepositAmount() {
        final BigDecimal balanceBeforeDeposit = bankAccount.getBalance();
        final BigDecimal amountToDeposit = new BigDecimal(100);

        bankAccount.deposit(amountToDeposit);

        final BigDecimal balanceAfterDeposit = bankAccount.getBalance();
        assertThat(balanceAfterDeposit, equalTo(balanceBeforeDeposit.add(amountToDeposit)));
    }

    @Test(expected = IllegalOperationException.class)
    public void depositOfNegativeAmountShouldThrowException(){
        bankAccount.deposit(new BigDecimal(-100));
    }

    @Test
    public void withdrawalShouldDecreaseBalanceByAmountWithdrawn(){
        final BigDecimal initialAmount = new BigDecimal(100);
        bankAccount.deposit(initialAmount);
        final BigDecimal amountToWithdraw = new BigDecimal(70);

        bankAccount.withdraw(amountToWithdraw);

        final BigDecimal balanceAfterWithdawal = bankAccount.getBalance();
        assertThat(balanceAfterWithdawal, equalTo(initialAmount.subtract(amountToWithdraw)));
    }

    @Test(expected = InsufficientFundException.class)
    public void withdrawalOfNegativeAmountShouldThrowException(){
        bankAccount.withdraw(new BigDecimal(100));
    }




}
