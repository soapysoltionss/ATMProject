import java.util.ArrayList;

/** account details*/
public class Account {
    /** Name of the account */
    private String name;
    /** Current balance of the account */
    private double balance;
    /** Account ID number */
    private String uuid;
    /** User object that owns this account */
    private User holder;
    /** List of account transactions */
    private ArrayList<Transaction> transactions;

    /**
     *
     * @param name      name of account
     * @param holder    User object that holds this account
     * @param theBank   the bank that issues the account
     */
    public Account(String name, User holder, Bank theBank){

        // set the name and holder of account
        this.name = name;
        this.holder = holder;

        // get new account UUID
        this.uuid = theBank.getNewAccountUUID();

        // init transactions
        this.transactions = new ArrayList<Transaction>();


    }
    public String getUUID(){
        return this.uuid;
    }

    public String getSummaryLine(){

        // get the account balance
        double balance = this.getBalance();
        // format the summary line, depending on the balance (positive or negative)
        if (balance >= 0){
            return String.format("%s : $%.02f : %s", this.uuid, balance, this.name);
        }else{
            return String.format("%s : $%.02f : %s", this.uuid, balance, this.name);
        }
    }

    /**
     * Get the balance of this account by adding the amounts of the transactions
     * @return      the balance value
     */
    public double getBalance(){

        double balance = 0;
        for (Transaction t: this.transactions){
            balance += t.getAmount();
        }
        return balance;
    }

    /**
     * Print account transaction history
     */
    public void printTransactionHistory(){

        System.out.printf("\nTransaction history for account %s\n", this.uuid);
        for(int t = this.transactions.size() - 1; t >= 0; t--){
            System.out.println(this.transactions.get(t).getSummaryLine());
        }
        System.out.println();
    }

    public void addTransaction(double amount, String memo){

        // create new transaction object and add to list
        Transaction newTrans = new Transaction(amount, memo, this);
        transactions.add(newTrans);
    }
}
