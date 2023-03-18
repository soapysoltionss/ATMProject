package com.atm;

import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.testng.ITest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TestFile {

    //Test Objects
    Bank testBank = new Bank("TestBank");
    User testUser = testBank.userLogin("5044891741", "1111");
    User testUser2 = testBank.userLogin("8133389705", "6969");

    //DEPOSIT CASES 
    //Regular use case
    @Test
    void testDeposit() throws InvalidAmountException {
        double startBal = testUser.getAccount(0).getBalance();
        testUser.getAccount(0).deposit(100);
        //assertTrue(testUser.getAccount(0).getBalance() == startBal+100);
        assertEquals(testUser.getAccount(0).getBalance(), startBal+100);
    }

    //Zero value
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testDepositValue0() throws InvalidAmountException {
        thrown.expect(InvalidAmountException.class);
        thrown.expectMessage("Amount must be greater than zero.");
        testUser.getAccount(0).deposit(0);
    }

    //Invalid value
    @Test
    public void testInvalidValue() throws InvalidAmountException {
        thrown.expect(InvalidAmountException.class);
        thrown.expectMessage("Amount must be greater than zero.");
        testUser.getAccount(0).deposit(-10);
    }

    //Exception cases
    @Test
    public void testExceptionCases() throws InvalidAmountException {
        thrown.expect(InvalidAmountException.class);
        thrown.expectMessage("Amount must be greater than zero.");
        testUser.getAccount(0).deposit(1.6969696969696969);
    }

    // ------------------------------------------------------------------------------------------

    //WITHDRAWAL CASES
    
    //DEPOSIT CASES 
    //Regular use case
    @Test
    public void testWithdrawNormalAmount() throws InvalidWithdrawAmountException {
        double startBal = testUser.getAccount(0).getBalance();
        testUser.getAccount(0).withdraw(1);
        //assertTrue(testUser.getAccount(0).getBalance() == startBal+100);
        assertEquals(testUser.getAccount(0).getBalance(), startBal-1);
    }

    //Zero value
    @Test
    public void testWithdrawValue0() throws InvalidWithdrawAmountException {
        thrown.expect(InvalidWithdrawAmountException.class);
        thrown.expectMessage("Amount must be greater than zero.");
        testUser.getAccount(0).withdraw(-10);
    }

    //Invalid value
    @Test
    public void testWithdrawInvalidValue() throws InvalidWithdrawAmountException {
        thrown.expect(InvalidWithdrawAmountException.class);
        thrown.expectMessage("Amount to withdraw can't be 0 and must be greater than 0");
        testUser.getAccount(0).withdraw(-0);
    }

    //------------------------------------------------------------------------------------------

    //TRANSFER CASES

    //Regular use case
    @Test
    public void testTransfer() {
        double startBal = testUser2.getAccount(0).getBalance();
        testUser.getAccount(0).transfer(testUser2.getAccount(0), "testCase", 1);
        assertEquals(testUser2.getAccount(0).getBalance(), startBal+1);
    }

    //Zero value
    @Test
    public void testTransferValue0() throws InvalidTransferFundsException {
        thrown.expect(InvalidTransferFundsException.class);
        thrown.expectMessage("Amount to withdraw can't be 0");
        testUser.getAccount(0).transfer(testUser2.getAccount(0), "testing transfer 0", 0);
    }

    //Invalid value
    @Test
    public void testTransferInvalidValue() throws InvalidTransferFundsException {
        thrown.expect(InvalidTransferFundsException.class);
        thrown.expectMessage("Amount must be greater than zero.");
        testUser.getAccount(0).transfer(testUser2.getAccount(0), "testing transfer 0", -10);
    }

    @Test
    public void testTransferAmountMoreThanBalance() throws InvalidTransferFundsException {
        // double balance = testUser.getAccount(0).getBalance();
        double withdrawAmount = testUser.getAccount(0).getBalance() + 10;
        thrown.expect(InvalidTransferFundsException.class);
        thrown.expectMessage("Amount must not be greater than\n" + "balance of $%.02f.\n");
        testUser.getAccount(0).transfer(testUser2.getAccount(0), "testing transfer 0", withdrawAmount);
    }

    // ------------------------------------------------------------------------------------------

    // Test cases for Bank.java

    @Test
    public void testGetNewUserUUID() {
        String uuid = testBank.getNewUserUUID();
        equals(uuid.length() == 10);
    }

    @Test
    public void testGetNewAccountUUID() {
        String uuid = testBank.getNewAccountUUID();
        equals(uuid.length() == 10);
    }

    @Test
    public void testUserLogin() {
        User testUser = testBank.userLogin("5044891741", "1111");
        equals(testUser != null);
    }

    //------------------------------------------------------------------------------------------

    // Test cases for ATM.java
    ATM testAtm = new ATM();

    @Test
    void withdrawalFunds() {
        String uuid = "5044891741";
        String pin = "1111";
        int fromAcct = 0;
        double amount = 100;
        // create user object
        User testUser = testBank.userLogin(uuid, pin);
        testAtm.withdrawFundsHelper(testUser, fromAcct, amount);
    }

    @Test
    void depositFunds() {
        String uuid = "5044891741";
        String pin = "1111";
        int fromAcct = 0;
        double amount = 100;
        // create user object
        User testUser = testBank.userLogin(uuid, pin);
        testAtm.deposiFundstHelper(testUser, fromAcct, amount);
    }

    @Test
    void transferFunds() {
        String uuid = "8133389705";
        String pin = "6969";
        int fromAcct = 0;
        int toAcct = 1;
        String memo = "test";
        double amount = 100;
        // create user object
        User testUser = testBank.userLogin(uuid, pin);
        testAtm.transferFundsHelper(testUser, fromAcct, toAcct, amount, memo);

    }

    @Test
    void otherTransferFunds() {
        String uuid = "5044891741";
        String pin = "1111";
        int fromAcct = 0;
        String toAcctOther = "8133389705";
        String memo = "test";
        double amount = 10;
        // create user object
        User testUser = testBank.userLogin(uuid, pin);
        testAtm.otherTransferFundsHelper(testUser, fromAcct, toAcctOther, amount, memo);
    }

    @Test
    void testLocalTransferLimit() {
        int fromAcct = 0;
        int newLimit = 100;
        testAtm.localTransferLimitHelper(testUser, fromAcct, 100);
        assertEquals(newLimit, testUser.getAccount(fromAcct).getLocalTransferLimit());
    }

    @Test
    void testLocalWithdrawLimit() {
        int fromAcct = 0;
        int newLimit = 100;
        testAtm.localWithdrawLimitHelper(testUser, fromAcct, 100);
        assertEquals(newLimit, testUser.getAccount(fromAcct).getLocalWithdrawLimit());
    }

    @Test
    void testOverseasTransferLimit() {
        int fromAcct = 0;
        int newLimit = 100;
        testAtm.overseasTransferLimitHelper(testUser, fromAcct, 100);
        assertEquals(newLimit, testUser.getAccount(fromAcct).getOverseasTransferLimit());
    }

    @Test
    void testOverseasWithdrawLimit() {
        int fromAcct = 0;
        int newLimit = 100;
        testAtm.overseasWithdrawLimitHelper(testUser, fromAcct, 100);
        assertEquals(newLimit, testUser.getAccount(fromAcct).getOverseasWithdrawLimit());
    }

}