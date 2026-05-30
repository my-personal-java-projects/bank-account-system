import java.util.*;

/**
 * Manages a collection of BankAccounts. Acts as a simple in-memory bank.
 */
public class Bank {

    private final String bankName;
    private final Map<String, BankAccount> accounts = new LinkedHashMap<>();
    private int nextAccountNumber = 1001;

    public Bank(String bankName) {
        this.bankName = bankName;
    }

    /**
     * Creates a new account and returns it.
     */
    public BankAccount createAccount(String ownerName, double initialDeposit) {
        String accNum = "ACC" + nextAccountNumber++;
        BankAccount account = new BankAccount(accNum, ownerName, initialDeposit);
        accounts.put(accNum, account);
        return account;
    }

    /**
     * Finds an account by account number.
     *
     * @throws NoSuchElementException if account not found
     */
    public BankAccount findAccount(String accountNumber) {
        BankAccount account = accounts.get(accountNumber);
        if (account == null) {
            throw new NoSuchElementException("Account not found: " + accountNumber);
        }
        return account;
    }

    public Collection<BankAccount> getAllAccounts() {
        return Collections.unmodifiableCollection(accounts.values());
    }

    public String getBankName() { return bankName; }
}
