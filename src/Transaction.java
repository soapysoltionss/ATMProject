import java.util.Date;

/** Transaction details */
public class Transaction {
    /** The amount of current transaction */
    private double amount;
    /** Time and date of current transaction */
    private Date timestamp;
    /** A memo for current transaction */
    private String memo;
    /** The account in which transaction was performed */
    private Account inAccount;

    /**
     * Create new transaction
     * @param amount        the amount transacted
     * @param inAccount     the account transaction belongs to
     */
    public Transaction(double amount, Account inAccount){

        this.amount = amount;
        this.inAccount = inAccount;
        this.timestamp = new Date();
        this.memo = "";
    }

    /**
     * Create new transaction
     * @param amount    the amount transacted
     * @param memo      the memo for transaction
     * @param inAccount the account transaction belongs to
     */
    public Transaction(double amount, String memo, Account inAccount){

        // call the 2 arg constructor first
        this(amount, inAccount);

        //set the memo
        this.memo = memo;
    }

    /**
     * Get the amount of the transaction
     * @return      the amount
     */
    public double getAmount(){
        return this.amount;
    }

    /**
     * Get a string for transaction summary
     * @return summary string
     */
    public String getSummaryLine(){

        if(this.amount >= 0){
            return String.format("%s : $%0.2f : %s", this.timestamp.toString(), this.amount, this.memo);
        }else{
            return String.format("%s : $%0.2f : %s", this.timestamp.toString(), -this.amount, this.memo);
        }
    }

}
