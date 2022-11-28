package com.audensiel.banking;

import com.audensiel.banking.domain.account.BankAccount;
import com.audensiel.banking.domain.operation.Operation;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static com.audensiel.banking.Config.STATEMENT_LINE_FORMAT;

/**
 * Hello world!
 *
 */
public class BankingApp
{
    public static void main( String[] args )
    {
        BankAccount bankAccount = new BankAccount(BigDecimal.ZERO, new ArrayList<>());
        bankAccount.deposit(new BigDecimal(600), LocalDateTime.parse("2022-11-11T10:10:10"));
        bankAccount.withdraw(new BigDecimal(50), LocalDateTime.now());
        bankAccount.deposit(new BigDecimal(200), LocalDateTime.now());
        bankAccount.withdraw(new BigDecimal(250), LocalDateTime.now());

        System.out.printf(STATEMENT_LINE_FORMAT, "Operation", "Date" , "Amount" , "Balance");
        bankAccount.statement().forEach(Operation::printOperation);
    }
}
