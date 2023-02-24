package com.atm;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        Scanner input = new Scanner(System.in);
        Bank theBank = new Bank("Bank of Kek");
        System.out.println("Testing: Current users = " + theBank.countUsers());
        User curUser;
        while(true) {
            curUser = Main.mainMenuPrompt(theBank, input);
            Main.printUserMenu(curUser, input);
        }

        // Bank theBank = new Bank("Bank of Kek");
        // System.out.println(theBank.countUsers());
        // theBank.printInfo();
        // User currentUser = theBank.userLogin("8133389705", "6969");
        //Account newAcc = currentUser.addAccount("savings", theBank);

        // Account currentAccount = currentUser.getAccount(0);

        // currentAccount.transfer(currentUser.getAccount(1), 15);

        //currentAccount.deposit(100);
        //User firstUser = theBank.addUser("Kek", "asdasd", "1111");
       // Account savingsAccountForFirstUser = firstUser.addAccount("current", theBank);
        //System.out.println(firstUser);
        //System.out.println(savingsAccountForFirstUser);
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
            
            authUser = theBank.userLogin(userID, pin);
            if (authUser == null) {
                System.out.println("Incorrect user ID/Pin combination! Please try again. ");
            }
        } while (authUser == null);
        return authUser;
    }

    public static void showTransactionHistory(User theUser, Scanner input) {
        int theAccount;
        do {
            System.out.printf("Enter the number(1-%d) of the account\n" + "whose transaction you want to see",theUser.numOfAccounts());
            theAccount = input.nextInt()-1;
            if (theAccount < 0 || theAccount >= theUser.numOfAccounts()) {
                System.out.println("Invalid account. Please try again.");
            } 
        } while (theAccount < 0 || theAccount >= theUser.numOfAccounts());
        theUser.printTransactionHistory(theAccount);
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
                //Main.withdrawalFunds(theUser, input);
                break;
            case 3:
                //Main.depositFunds(theUser, input);
                break;
            case 4:
                //Main.transferFunds(theUser, input);
                break;
            case 5:
                input.nextLine();
        }

        if (choice != 5) {
            Main.printUserMenu(theUser, input);
        }
    }
}

