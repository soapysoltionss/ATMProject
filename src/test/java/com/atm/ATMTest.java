package com.atm;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
class ATMTest {

    // Test cases for ATM.java
    ATM atm = new ATM();
    Bank testBank = new Bank("testBank");
    //User testUser = new User();
    Scanner input = new Scanner(System.in);

    @Test
    void withdrawalFunds() {
        String uuid = "5044891741";
        String pin = "1111";
        int fromAcct = 0;
        double amount = 100;
        /*
        ByteArrayInputStream uuid_input = new ByteArrayInputStream(uuid.getBytes());
        System.setIn(uuid_input);
        ByteArrayOutputStream uuid_output = new ByteArrayOutputStream();
        PrintStream uuid_print = new PrintStream(uuid_output);

        ByteArrayInputStream pin_input = new ByteArrayInputStream(pin.getBytes());
        System.setIn(pin_input);
        ByteArrayOutputStream pin_output = new ByteArrayOutputStream();
        PrintStream pin_print = new PrintStream(pin_output);

        ByteArrayInputStream amount_input = new ByteArrayInputStream(ByteBuffer.allocate(4).putFloat(amount).array());
        System.setIn(amount_input);
        ByteArrayOutputStream amount_output = new ByteArrayOutputStream();
        PrintStream amount_print = new PrintStream(amount_output);
        */

        // create user object
        User testUser = testBank.userLogin(uuid, pin);
        atm.withdrawFundsHelper(testUser, fromAcct, amount);
    }

    @Test
    void depositFunds() {
        String uuid = "5044891741";
        String pin = "1111";
        int fromAcct = 0;
        double amount = 100;
        // create user object
        User testUser = testBank.userLogin(uuid, pin);
        atm.deposiFundstHelper(testUser, fromAcct, amount);
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
        atm.transferFundsHelper(testUser, fromAcct, toAcct, amount, memo);
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
        atm.otherTransferFundsHelper(testUser, fromAcct, toAcctOther, amount, memo);
    }

    @Test
    void testLocalTransfer() {
        String uuid = "5044891741";
        String pin = "1111";
        int fromAcct = 0;
        int newLimit = 100;
        // create user object
        User testUser = testBank.userLogin(uuid, pin);
        atm.localTransferLimitHelper(testUser, fromAcct, newLimit);
    }

    @Test
    void testLocalWithdraw() {
        String uuid = "5044891741";
        String pin = "1111";
        int fromAcct = 0;
        int newLimit = 10;
        // create user object
        User testUser = testBank.userLogin(uuid, pin);
        atm.localWithdrawLimitHelper(testUser, fromAcct, newLimit);
    }

    @Test
    void testOverseasTransfer() {
        String uuid = "5044891741";
        String pin = "1111";
        int fromAcct = 0;
        int newLimit = 10;
        // create user object
        User testUser = testBank.userLogin(uuid, pin);
        atm.overseasTransferLimitHelper(testUser, fromAcct, newLimit);
    }

    @Test
    void testOverseasWithdraw() {
        String uuid = "5044891741";
        String pin = "1111";
        int fromAcct = 0;
        int newLimit = 10;
        // create user object
        User testUser = testBank.userLogin(uuid, pin);
        atm.overseasWithdrawLimitHelper(testUser, fromAcct, newLimit);
    }

}