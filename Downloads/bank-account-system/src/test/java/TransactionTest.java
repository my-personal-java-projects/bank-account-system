import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class TransactionTest {

    @Test
    void constructor_shouldStoreAllFields() {
        Transaction t = new Transaction(Transaction.Type.DEPOSIT, 500.00, 1500.00, "Salary");
        assertEquals(Transaction.Type.DEPOSIT, t.getType());
        assertEquals(500.00, t.getAmount(), 0.001);
        assertEquals(1500.00, t.getBalanceAfter(), 0.001);
        assertEquals("Salary", t.getDescription());
    }

    @Test
    void toString_shouldContainType() {
        Transaction t = new Transaction(Transaction.Type.WITHDRAWAL, 200.00, 800.00, "ATM");
        assertTrue(t.toString().toUpperCase().contains("WITHDRAWAL"));
    }

    @Test
    void toString_shouldContainAmount() {
        Transaction t = new Transaction(Transaction.Type.DEPOSIT, 1000.00, 2000.00, "Test");
        assertTrue(t.toString().contains("1") && t.toString().contains("000"));
    }

    @Test
    void toString_shouldContainDescription() {
        Transaction t = new Transaction(Transaction.Type.TRANSFER_IN, 300.00, 1300.00, "From ACC1002");
        assertTrue(t.toString().contains("From ACC1002"));
    }

    @Test
    void toString_shouldContainTimestamp() {
        Transaction t = new Transaction(Transaction.Type.DEPOSIT, 100.00, 100.00, "Open");
        // Timestamp format: yyyy-MM-dd — check year is present
        assertTrue(t.toString().contains("2026") || t.toString().matches(".*\\d{4}-\\d{2}-\\d{2}.*"));
    }

    @Test
    void typeEnum_shouldHaveAllFourValues() {
        Transaction.Type[] types = Transaction.Type.values();
        assertEquals(4, types.length);
    }
}
