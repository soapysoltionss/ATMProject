package com.atm;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.http.ParseException;

public class Main {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        Scanner input = new Scanner(System.in);
        Bank theBank = new Bank("Bank of Kek");
        System.out.println("Testing: Current users = " + theBank.countUsers());
        User curUser;
        while (true) {
            curUser = Main.mainMenuPrompt(theBank, input);
            Main.printUserMenu(curUser, input);
        }

        // Bank theBank = new Bank("Bank of Kek");
        // System.out.println(theBank.countUsers());
        // theBank.printInfo();
        // User currentUser = theBank.userLogin("8133389705", "6969");
        // Account newAcc = currentUser.addAccount("savings", theBank);

        // Account currentAccount = currentUser.getAccount(0);

        // currentAccount.transfer(currentUser.getAccount(1), 15);

        // currentAccount.deposit(100);
        //User firstUser = theBank.addUser("Kek", "asdasd", "1111", "Singapore");
        // Account savingsAccountForFirstUser = firstUser.addAccount("current",
        // theBank);
        // System.out.println(firstUser);
        // System.out.println(savingsAccountForFirstUser);
    }

    public static User mainMenuPrompt(Bank theBank, Scanner input) {
        String userID;
        String pin;
        User authUser;

        do {
            System.out.printf("\n\nWelcome to %s\n\n", theBank.getName());
            System.out.print("Enter User ID: ");
            userID = input.nextLine();
            System.out.print("Enter Pin: ");
            pin = input.nextLine();
            theBank.refreshUsers();
            authUser = theBank.userLogin(userID, pin);
            if (authUser == null) {
                System.out.println("Incorrect user ID/Pin combination! Please try again. ");
            }

        } while (authUser == null);
        return authUser;
    }

    public static int accSelect(User theUser, Scanner input, String msg){
        int acc;
        do {
            theUser.printAccountSummary();
            System.out.printf("Enter the number(1-%d) of the account " + msg, theUser.numOfAccounts());
            acc = input.nextInt() - 1;
            if (acc < 0 || acc >= theUser.numOfAccounts()) {
                System.out.println("Invalid account. Please try again.");
            }
        } while (acc < 0 || acc >= theUser.numOfAccounts());
        return acc;
    }
    public static void showTransactionHistory(User theUser, Scanner input) {
        int theAccount;
        theAccount = accSelect(theUser, input, "whose transaction you want to see: ");
        theUser.printTransactionHistory(theAccount);
    }

    public static void withdrawalFunds(User theUser, Scanner input) {
        int fromAcct;
        double amount;
        double acctBal;
        String memo;

        fromAcct = accSelect(theUser, input, "to withdraw from: ");
        acctBal = theUser.getAccountBalance(fromAcct);

        do{
            System.out.printf("Enter the amount to withdraw (max $%.02f): $", acctBal);
            amount = input.nextDouble();
            if(amount < 0){
                System.out.println("Amount must be greater than zero.");
            } else if (amount > acctBal) {
                System.out.printf("Amount must not be greater than\n" + "balance of $%.02f.\n", acctBal);
            } else if ((BigDecimal.valueOf(amount).scale() > 2)){
                System.out.println("Amount must not have more than 2dp.");
            } else if (amount == 0) {
                System.out.printf("Amount to withdraw can't be 0");
            }
        }while(amount < 0 || amount > acctBal || (BigDecimal.valueOf(amount).scale() > 2));

        // takes rest of input
        input.nextLine();
        
        theUser.getAccount(fromAcct).withdraw(amount);

    }
    
    public static void depositFunds(User theUser, Scanner input) {
        int fromAcct;
        double acctBal;
        String memo;

        fromAcct = accSelect(theUser, input, "to deposit in: ");
        acctBal = theUser.getAccountBalance(fromAcct);
        double amount;
        do{
            System.out.printf("Enter the amount to deposit: $");
            amount = input.nextDouble();
            if(amount < 0){
                System.out.println("Amount must be greater than zero.");
            } else if ((BigDecimal.valueOf(amount).scale() > 2)){
                System.out.println("Amount must not have more than 2dp.");
            } else if (amount == 0) {
                System.out.printf("Amount to deposit can't be 0");
            }
        }while(amount < 0 || (BigDecimal.valueOf(amount).scale() > 2));

        // takes rest of input
        input.nextLine();
        
        theUser.getAccount(fromAcct).deposit(amount);

    }


    public static void transferFunds(User theUser, Scanner input) {
        int fromAcct;
        double acctBal;
        String memo;
        int toAcct;
        fromAcct = accSelect(theUser, input, "to transfer from: ");
        acctBal = theUser.getAccountBalance(fromAcct);
        boolean correctAcc = false;
        do {
            theUser.printAccountSummary();
            System.out.printf("%d: Other Accounts",theUser.numOfAccounts()+1);
            System.out.printf("\nEnter the number (1-%d) of the account" + " to transfer to: ", theUser.numOfAccounts()+1);
            toAcct = input.nextInt()-1;
            if (toAcct < 0 || toAcct >= theUser.numOfAccounts()+1) {
                System.out.println("Invalid account. Please try again.");
            }
            else if (toAcct==fromAcct){
                System.out.println("Invalid selection, select a different account. Please try again.");
            } else {
                correctAcc = true;
            }
        } while (toAcct < 0 || toAcct >= theUser.numOfAccounts()+1 || correctAcc == false);

        
        if (toAcct == theUser.numOfAccounts()){
            input.nextLine();
            System.out.printf("Enter the account number to transfer to: ");
            String toAcctOthr = (String)input.nextLine();
            double checkDestinationExist = theUser.getAccount(fromAcct).getOtherBal(toAcctOthr);
            double amounts = 0;
            if (checkDestinationExist == -1) {
                System.out.println("Account does not exist!");
                printUserMenu(theUser, input);
            } else {
                System.out.println("Account exists");
                do {
                    System.out.printf("Enter the amount to transfer: $");
                    amounts = input.nextDouble();
                    if(amounts <= 0){
                        System.out.println("Amount must be greater than zero.");
                    } else if (amounts > acctBal) {
                        System.out.printf("Amount must not be greater than\n" + "balance of $%.02f.\n", acctBal);
                    } else if ((BigDecimal.valueOf(amounts).scale() > 2)){
                        System.out.println("Amount must not have more than 2dp.");
                    } 
                } while(amounts < 0 || amounts > acctBal || (BigDecimal.valueOf(amounts).scale() > 2));
            }
            // takes rest of input
            input.nextLine();

            // get a memo
            System.out.print("Enter a memo: ");
            memo = input.nextLine();
            theUser.getAccount(fromAcct).otherTransfer(toAcctOthr, memo, amounts);
            System.out.println("Transferred to OTHER " + toAcctOthr + " successfully!"); 
        }
        else{
            double amount;
            do{
                System.out.printf("Enter the amount to transfer: $");
                amount = input.nextDouble();
                if(amount <= 0){
                    System.out.println("Amount must be greater than zero.");
                } else if (amount > acctBal) {
                    System.out.printf("Amount must not be greater than\n" + "balance of $%.02f.\n", acctBal);
                } else if ((BigDecimal.valueOf(amount).scale() > 2)){
                    System.out.println("Amount must not have more than 2dp.");
                }
            }while(amount < 0 || amount > acctBal || (BigDecimal.valueOf(amount).scale() > 2));

            // takes rest of input
            input.nextLine();

            // get a memo
            System.out.print("Enter a memo: ");
            memo = input.nextLine();
            theUser.getAccount(fromAcct).transfer(theUser.getAccount(toAcct), memo, amount);
        }
    }


    public static void printUserMenu(User theUser, Scanner input) {
        theUser.printAccountSummary();
        int choice;
        do {
            System.out.printf("Welcome %s, what would you like to do? \n", theUser.getFirstName());
            System.out.println("    1) Show Account Transaction History");
            System.out.println("    2) Withdrawal");
            System.out.println("    3) Deposit");
            System.out.println("    4) Transfer");
            System.out.println("    5) Quit");
            System.out.println();
            System.out.print("Enter choice: ");
            choice = input.nextInt();
            if (choice < 1 || choice > 5) {
                System.out.println("Invalid choice! Please choice 1-5");
            }
        } while (choice < 1 || choice > 5);
        switch (choice) {
            case 1:
                Main.showTransactionHistory(theUser, input);
                break;
            case 2:
                Main.withdrawalFunds(theUser, input);
                break;
            case 3:
                Main.depositFunds(theUser, input);
                break;
            case 4:
                Main.transferFunds(theUser, input);
                break;
            case 5:
                input.nextLine();
        }

        if (choice != 5) {
            Main.printUserMenu(theUser, input);
        }
    }
}
