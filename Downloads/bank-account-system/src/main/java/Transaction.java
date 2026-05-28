import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Immutable record of a single account transaction.
 */
public class Transaction {

    public enum Type { DEPOSIT, WITHDRAWAL, TRANSFER_IN, TRANSFER_OUT }

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final Type type;
    private final double amount;
    private final double balanceAfter;
    private final String description;
    private final LocalDateTime timestamp;

    public Transaction(Type type, double amount, double balanceAfter, String description) {
        this.type         = type;
        this.amount       = amount;
        this.balanceAfter = balanceAfter;
        this.description  = description;
        this.timestamp    = LocalDateTime.now();
    }

    public Type getType()          { return type; }
    public double getAmount()      { return amount; }
    public double getBalanceAfter(){ return balanceAfter; }
    public String getDescription() { return description; }

    @Override
    public String toString() {
        return String.format("[%s] %-14s R%,.2f  |  Balance: R%,.2f  |  %s",
                timestamp.format(FORMATTER),
                type,
                amount,
                balanceAfter,
                description);
    }
}
