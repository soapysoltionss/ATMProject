import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        // init Scanner
        Scanner input = new Scanner(System.in);

        // init Bank
        Bank theBank = new Bank("Bank of kek");

        // add a user, which also creates a savings account
        User aUser = theBank.addUser("Kek", "w", "1111");

        // add a checking account for user
        Account newAccount = new Account("Checking", aUser, theBank);
        aUser.addAccount(newAccount);
        theBank.addAccount(newAccount);

        User curUser;
        while (true) {

            //stay in login prompt until successful login
            curUser = Main.mainMenuPrompt(theBank, input);

            // stay in main menu until user quits
            Main.printUserMenu(curUser, input);
        }

    }

    /**
     * Main Menu
     * @param theBank bank object
     * @param input scanner object for user input
     * @return
     */
    public static User mainMenuPrompt(Bank theBank, Scanner input) {
        //inits
        String userID;
        String pin;
        User authUser;

        // prompt user for userID/pin combination until correct
        do {
            System.out.printf("\n\n Welcome to %s\n\n", theBank.getName());
            System.out.print("Enter User ID: ");
            userID = input.nextLine();
            System.out.print("Enter pin: ");
            pin = input.nextLine();

            // try to get the user object corresponding to the ID and pin combination
            authUser = theBank.userLogin(userID, pin);
            if (authUser == null) {
                System.out.println("Incorrect user ID/pin combination. " + "Please try again.");
            }
        } while (authUser == null); // continue looping until successful login

        return authUser;
    }

    public static void printUserMenu(User theUser, Scanner input) {

        // print a summary of user's accounts
        theUser.printAccountSummary();

        // init
        int choice;

        // user menu
        do {
            System.out.printf("Welcome %s, what would you like to do?\n", theUser.getFirstName());
            System.out.println("  1) Show Account transaction history");
            System.out.println("  2) Withdrawal");
            System.out.println("  3) Deposit");
            System.out.println("  4) Transfer");
            System.out.println("  5) Quit");
            System.out.println();
            System.out.print("Enter choice: ");
            choice = input.nextInt();

            if (choice < 1 || choice > 5) {
                System.out.println("Invalid choice. Please choose 1-5");
            }
        } while (choice < 1 || choice > 5);

        // process the choice
        switch (choice) {
            case 1:
                Main.showTransHistory(theUser, input);
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
                // takes rest of input
                input.nextLine();
        }

        // redisplay this menu unless the user quits
        if (choice != 5) {
            Main.printUserMenu(theUser, input);
        }
    }

    /**
     * SHow account transaction history
     * @param theUser   login user object
     * @param input     scanner object for user input
     */
    public static void showTransHistory(User theUser, Scanner input) {

        int theAcct;

        do {
            System.out.printf("Enter the number(1-%d) of the account\n" + "whose transactions you want to see", theUser.numAccounts());
            theAcct = input.nextInt()-1;
            if (theAcct < 0 || theAcct >= theUser.numAccounts()){
                System.out.println("Invalid account. Please try again.");
            }
        } while(theAcct < 0 || theAcct >= theUser.numAccounts());

        // print transaction history
        theUser.printTransactionHistory(theAcct);
    }

    /**
     * Transfer funds from one account to another
     * @param theUser   login user object
     * @param input     scanner object for user input
     */
    public static void transferFunds(User theUser, Scanner input) {

        // inits
        int fromAcct;
        int toAcct;
        double amount;
        double acctBal;

        // get account to transfer from
        do{
            System.out.printf("Enter the number (1-%d) of the account\n" + "to transfer from: ", theUser.numAccounts());
            fromAcct = input.nextInt()-1;
            if (fromAcct < 0 || fromAcct >= theUser.numAccounts()){
                System.out.println("Invalid account. Please try again.");
            }
        } while(fromAcct < 0 || fromAcct >= theUser.numAccounts());
        acctBal = theUser.getAccountBalance(fromAcct);

        // get account to transfer to
        do{
            System.out.printf("Enter the number (1-%d) of the account\n" + "to transfer from: ", theUser.numAccounts());
            toAcct = input.nextInt()-1;
            if (toAcct < 0 || toAcct >= theUser.numAccounts()){
                System.out.println("Invalid account. Please try again.");
            }
        } while(toAcct < 0 || toAcct >= theUser.numAccounts());

        // get transfer amount
        do{
            System.out.printf("Enter the amount to transfer (max $%.02f): $", acctBal);
            amount = input.nextDouble();
            if(amount < 0){
                System.out.println("Amount must be greater than zero.");
            } else if (amount > acctBal) {
                System.out.printf("Amount must not be greater than\n" + "balance of $%.02f.\n", acctBal);
            }
        }while(amount < 0 || amount > acctBal);

        // do transfer
        theUser.addAccountTransaction(fromAcct, -1*amount, String.format("Transfer to account %s", theUser.getAccountUUID(toAcct)));
    }

    /**
     * Fund withdrawal from account
     * @param theUser   login user object
     * @param input     scanner object for user input
     */
    public static void withdrawalFunds(User theUser, Scanner input) {

        // inits
        int fromAcct;
        double amount;
        double acctBal;
        String memo;

        // get account to transfer from
        do{
            System.out.printf("Enter the number (1-%d) of the account\n" + "to withdraw from: ", theUser.numAccounts());
            fromAcct = input.nextInt()-1;
            if (fromAcct < 0 || fromAcct >= theUser.numAccounts()){
                System.out.println("Invalid account. Please try again.");
            }
        } while(fromAcct < 0 || fromAcct >= theUser.numAccounts());
        acctBal = theUser.getAccountBalance(fromAcct);

        // get transfer amount
        do{
            System.out.printf("Enter the amount to transfer (max $%.02f): $", acctBal);
            amount = input.nextDouble();
            if(amount < 0){
                System.out.println("Amount must be greater than zero.");
            } else if (amount > acctBal) {
                System.out.printf("Amount must not be greater than\n" + "balance of $%.02f.\n", acctBal);
            }
        }while(amount < 0 || amount > acctBal);

        // takes rest of input
        input.nextLine();

        // get a memo
        System.out.print("Enter a memo: ");
        memo = input.nextLine();

        //do the withdrawal
        theUser.addAccountTransaction(fromAcct, -1*amount, memo);
    }

    public static void depositFunds(User theUser, Scanner input) {

        // inits
        int toAcct;
        double amount;
        double acctBal;
        String memo;

        // get account to deposit in
        do{
            System.out.printf("Enter the number (1-%d) of the account\n" + "to deposit in: ", theUser.numAccounts());
            toAcct = input.nextInt()-1;
            if (toAcct < 0 || toAcct >= theUser.numAccounts()){
                System.out.println("Invalid account. Please try again.");
            }
        } while(toAcct < 0 || toAcct >= theUser.numAccounts());
        acctBal = theUser.getAccountBalance(toAcct);

        // get transfer amount
        do{
            System.out.printf("Enter the amount to deposit: $" );
            amount = input.nextDouble();
            if(amount < 0){
                System.out.println("Amount must be greater than zero.");
            }
        }while(amount < 0);

        // takes rest of input
        input.nextLine();

        // get a memo
        System.out.println("Enter a memo: ");
        memo = input.nextLine();

        //do the withdrawal
        theUser.addAccountTransaction(toAcct, amount, memo);
    }
}