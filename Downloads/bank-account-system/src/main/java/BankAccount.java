import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a single bank account with deposit, withdrawal, and transfer capabilities.
 */
public class BankAccount {

    private final String accountNumber;
    private final String ownerName;
    private double balance;
    private final List<Transaction> transactions = new ArrayList<>();

    public BankAccount(String accountNumber, String ownerName, double initialDeposit) {
        if (initialDeposit < 0) {
            throw new IllegalArgumentException("Initial deposit cannot be negative.");
        }
        this.accountNumber = accountNumber;
        this.ownerName     = ownerName;
        this.balance       = initialDeposit;

        if (initialDeposit > 0) {
            transactions.add(new Transaction(
                    Transaction.Type.DEPOSIT, initialDeposit, balance, "Account opened"));
        }
    }

    /**
     * Deposits money into this account.
     */
    public void deposit(double amount, String description) {
        validatePositive(amount, "Deposit");
        balance += amount;
        transactions.add(new Transaction(Transaction.Type.DEPOSIT, amount, balance, description));
    }

    /**
     * Withdraws money from this account.
     *
     * @throws IllegalStateException if funds are insufficient
     */
    public void withdraw(double amount, String description) {
        validatePositive(amount, "Withdrawal");
        if (amount > balance) {
            throw new IllegalStateException(
                    String.format("Insufficient funds. Balance: R%.2f, Requested: R%.2f", balance, amount));
        }
        balance -= amount;
        transactions.add(new Transaction(Transaction.Type.WITHDRAWAL, amount, balance, description));
    }

    /**
     * Transfers money from this account to another.
     */
    public void transferTo(BankAccount target, double amount) {
        validatePositive(amount, "Transfer");
        this.withdraw(amount, "Transfer to " + target.getAccountNumber());
        target.deposit(amount, "Transfer from " + this.accountNumber);

        // Correct the transaction types for clarity
        this.transactions.set(this.transactions.size() - 1,
                new Transaction(Transaction.Type.TRANSFER_OUT, amount, this.balance,
                        "Transfer to " + target.getAccountNumber()));
        target.transactions.set(target.transactions.size() - 1,
                new Transaction(Transaction.Type.TRANSFER_IN, amount, target.balance,
                        "Transfer from " + this.accountNumber));
    }

    private void validatePositive(double amount, String label) {
        if (amount <= 0) {
            throw new IllegalArgumentException(label + " amount must be greater than zero.");
        }
    }

    public String getAccountNumber()          { return accountNumber; }
    public String getOwnerName()              { return ownerName; }
    public double getBalance()                { return balance; }
    public List<Transaction> getTransactions(){ return Collections.unmodifiableList(transactions); }

    @Override
    public String toString() {
        return String.format("Account [%s] — Owner: %-20s Balance: R%,.2f",
                accountNumber, ownerName, balance);
    }
}
