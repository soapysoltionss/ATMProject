package com.atm;

import org.testng.ITest;
import org.testng.annotations.Test;

import java.lang.Math;
import java.security.NoSuchAlgorithmException;

public class testCases {

    // Test cases for Account.java
    // Account acc1 = new Account("current", "8133389705", "7008076141", "user");
    // Account acc2 = new Account("current", "5044891741", "8622489873", "user");

    // @Test
    // public void testDeposit() {
    //     acc1.deposit(100);
    //     equals(acc1.getBalance() > 100);
    // }

    // @Test
    // public void testWithdraw() {
    //     acc1.withdraw(100)
    //     equals(acc1.getBalance() < 100);
    // }

    // @Test
    // public void testTransfer() {
    //     acc1.transfer(acc1, "Hi", 100 );
    //     equals(acc1.getBalance() < 100);
    // }

    // @Test
    // public void testOtherTransfer() {
    //     acc1.otherTransfer("7008076141", "Hi" , 10);
    //     equals(acc1.getBalance() < 100);
    // }

    // @Test
    // public void testDodifyBalance() {
    //     acc1.modifyBalance(100);
    //     equals(acc1.getBalance() > 100);
    // }

    // ------------------------------------------------------------------------------------------

    // Test cases for Bank.java

    Bank bank = new Bank("Bank");

    @Test
    public void testGetNewUserUUID() {
        String uuid = bank.getNewUserUUID();
        equals(uuid.length() == 10);
    }

    @Test
    public void testGetNewAccountUUID() {
        String uuid = bank.getNewAccountUUID();
        equals(uuid.length() == 10);
    }

    // @Test
    // public void testAddUser() {
    //     String user = bank.addUser("Test", "User", "1234", "Singapore");
    //     equals(user.length() == 10);
    //     System.out.println(user);
    // }

    // @Test
    // public void testGetUsers() {
    //     ArrayList<User> users = bank.getUsers();
    //     equals(users.size() > 0);
    // }

    @Test
    public void testUserLogin() {
        User testUser = bank.userLogin("5044891741", "1111");
        equals(testUser != null);
    }

    //------------------------------------------------------------------------------------------

    // Test cases for User.java
    
    

    //------------------------------------------------------------------------------------------

    // Test cases for transaction.java
    Transaction transaction = new Transaction(100, "Test Memo", "5044891741");

    @Test
    public void testTransaction(){
        equals(transaction.getHolder().equals("5044891741");
        equals(transaction.getAmount() == 100);
        equals(transaction.getMemo().equals("Hi"));
    }

    //------------------------------------------------------------------------------------------

    // Test cases for ATM.java
    ATM atm = new ATM();

    // @Test
    // public void testDisplayATMMenu() {
        
    // }

    // @Test
    // public void testMainMenuPrompt(){

    // }

    // @Test
    // public void testWithdrawalFunds() {

    // }

    // @Test
    // public void testDepositFunds() {
        
    // }

    // @Test
    // public void testTransferFunds() {

    // }

    // @Test
    // public void testPrintUserMenu() {

    // }

}
