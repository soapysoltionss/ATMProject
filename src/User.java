/** user details*/
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
public class User {
    /** First name of user */
    private String firstName;
    /** Last name of user */
    private String lastName;
    /** ID number of the user*/
    private String uuid;
    /**MD5 hash of the user's pin */
    private byte pinhash[];
    /** List of accounts */
    private ArrayList<Account> accounts;

    /**
     *
     * @param firstName     users first name
     * @param lastName      users last name
     * @param pin           user's account pin
     * @param theBank       the Bank object that the user is a customer of
     */
    public User(String firstName, String lastName, String pin, Bank theBank){
        // set users name
        this.firstName = firstName;
        this.lastName = lastName;
        // store the pin's MD5, rather than the original value, for security reasons
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            this.pinhash = md.digest(pin.getBytes());
        } catch (NoSuchAlgorithmException e) {
            System.err.println("error, caught noSuchAlgorithmException");
            e.printStackTrace();
            System.exit(1);
        }
        // get a new, unique universal ID for user
        this.uuid = theBank.getNewUserUUID();

        //create empty list of accounts
        this.accounts = new ArrayList<Account>();

        //print log message
        System.out.printf("New user %s, %s with ID %s created.\n", lastName, firstName, this.uuid);
    }

    /**
     * Add account for user
     * @param anAcct    the account to add
     */
    public void addAccount(Account anAcct){
        this.accounts.add(anAcct);
    }

    public String getUUID(){
        return this.uuid;
    }

    /**
     * Check if a given pin matches the true User pin
     * @param aPin      check pin
     * @return          pin validation
     */
    public boolean validatePin(String aPin){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return MessageDigest.isEqual(md.digest(aPin.getBytes()), this.pinhash);
        } catch (NoSuchAlgorithmException e) {
            System.err.println("error, caught NoSuchAlgorithmException");
            e.printStackTrace();
            System.exit(1);
        }
        return false;
    }

    /**
     * Return user first name
     * @return      the first name
     */
    public String getFirstName(){
        return this.firstName;
    }

    public void printAccountSummary(){

        System.out.printf("\n\n%s's accounts summary ", this.firstName);
        for(int a = 0; a < this.accounts.size(); a++){
            System.out.printf("\n%d) %s", a+1, this.accounts.get(a).getSummaryLine());
        }
        System.out.println();
    }

    /**
     * Get the number of user accounts
     * @return nubmer of accounts
     */
    public int numAccounts(){
        return this.accounts.size();
    }

    /**
     * Print account transaction history for 1 account
     * @param acctIndex     index of account used
     */
    public void printTransactionHistory(int acctIndex){
        this.accounts.get(acctIndex).printTransactionHistory();
    }

    public double getAccountBalance(int acctIndex){
        return this.accounts.get(acctIndex).getBalance();
    }

    public String getAccountUUID(int acctIndex){
        return this.accounts.get(acctIndex).getUUID();
    }

    public void addAccountTransaction(int acctIndex, double amount, String memo){
        this.accounts.get(acctIndex).addTransaction(amount, memo);
    }
}
