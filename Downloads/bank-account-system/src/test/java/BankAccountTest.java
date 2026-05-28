import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for BankAccount.
 */
class BankAccountTest {

    private BankAccount account;

    @BeforeEach
    void setUp() {
        account = new BankAccount("ACC001", "Thabo Nkosi", 1000.00);
    }

    // ── Constructor ──────────────────────────────────────────────────────────

    @Test
    void constructor_shouldSetOwnerAndAccountNumber() {
        assertEquals("Thabo Nkosi", account.getOwnerName());
        assertEquals("ACC001", account.getAccountNumber());
    }

    @Test
    void constructor_shouldSetInitialBalance() {
        assertEquals(1000.00, account.getBalance(), 0.001);
    }

    @Test
    void constructor_withInitialDeposit_shouldCreateOneTransaction() {
        assertEquals(1, account.getTransactions().size());
    }

    @Test
    void constructor_withZeroDeposit_shouldHaveNoTransactions() {
        BankAccount empty = new BankAccount("ACC002", "Lerato Dlamini", 0);
        assertTrue(empty.getTransactions().isEmpty());
    }

    @Test
    void constructor_negativeInitialDeposit_shouldThrow() {
        assertThrows(IllegalArgumentException.class,
                () -> new BankAccount("ACC003", "Test", -100));
    }

    // ── Deposit ──────────────────────────────────────────────────────────────

    @Test
    void deposit_shouldIncreaseBalance() {
        account.deposit(500.00, "Salary");
        assertEquals(1500.00, account.getBalance(), 0.001);
    }

    @Test
    void deposit_shouldAddTransactionToHistory() {
        account.deposit(250.00, "Gift");
        assertEquals(2, account.getTransactions().size());
    }

    @Test
    void deposit_transactionType_shouldBeDeposit() {
        account.deposit(100.00, "Test");
        Transaction last = account.getTransactions().get(account.getTransactions().size() - 1);
        assertEquals(Transaction.Type.DEPOSIT, last.getType());
    }

    @Test
    void deposit_zeroAmount_shouldThrow() {
        assertThrows(IllegalArgumentException.class,
                () -> account.deposit(0, "Zero deposit"));
    }

    @Test
    void deposit_negativeAmount_shouldThrow() {
        assertThrows(IllegalArgumentException.class,
                () -> account.deposit(-50, "Negative deposit"));
    }

    // ── Withdrawal ───────────────────────────────────────────────────────────

    @Test
    void withdraw_shouldDecreaseBalance() {
        account.withdraw(400.00, "Groceries");
        assertEquals(600.00, account.getBalance(), 0.001);
    }

    @Test
    void withdraw_shouldAddTransactionToHistory() {
        account.withdraw(200.00, "Electricity");
        assertEquals(2, account.getTransactions().size());
    }

    @Test
    void withdraw_transactionType_shouldBeWithdrawal() {
        account.withdraw(100.00, "Test");
        Transaction last = account.getTransactions().get(account.getTransactions().size() - 1);
        assertEquals(Transaction.Type.WITHDRAWAL, last.getType());
    }

    @Test
    void withdraw_exactBalance_shouldSetBalanceToZero() {
        account.withdraw(1000.00, "All funds");
        assertEquals(0.00, account.getBalance(), 0.001);
    }

    @Test
    void withdraw_insufficientFunds_shouldThrowIllegalStateException() {
        assertThrows(IllegalStateException.class,
                () -> account.withdraw(1500.00, "Too much"));
    }

    @Test
    void withdraw_insufficientFunds_shouldNotChangeBalance() {
        double balanceBefore = account.getBalance();
        try { account.withdraw(9999.00, "Too much"); } catch (IllegalStateException ignored) {}
        assertEquals(balanceBefore, account.getBalance(), 0.001);
    }

    @Test
    void withdraw_zeroAmount_shouldThrow() {
        assertThrows(IllegalArgumentException.class,
                () -> account.withdraw(0, "Zero"));
    }

    // ── Transfer ─────────────────────────────────────────────────────────────

    @Test
    void transferTo_shouldDeductFromSource() {
        BankAccount target = new BankAccount("ACC002", "Nomsa Zulu", 0);
        account.transferTo(target, 300.00);
        assertEquals(700.00, account.getBalance(), 0.001);
    }

    @Test
    void transferTo_shouldAddToTarget() {
        BankAccount target = new BankAccount("ACC002", "Nomsa Zulu", 500.00);
        account.transferTo(target, 300.00);
        assertEquals(800.00, target.getBalance(), 0.001);
    }

    @Test
    void transferTo_sourceTransaction_shouldBeTransferOut() {
        BankAccount target = new BankAccount("ACC002", "Nomsa Zulu", 0);
        account.transferTo(target, 200.00);
        Transaction last = account.getTransactions().get(account.getTransactions().size() - 1);
        assertEquals(Transaction.Type.TRANSFER_OUT, last.getType());
    }

    @Test
    void transferTo_targetTransaction_shouldBeTransferIn() {
        BankAccount target = new BankAccount("ACC002", "Nomsa Zulu", 0);
        account.transferTo(target, 200.00);
        Transaction last = target.getTransactions().get(target.getTransactions().size() - 1);
        assertEquals(Transaction.Type.TRANSFER_IN, last.getType());
    }

    @Test
    void transferTo_insufficientFunds_shouldThrow() {
        BankAccount target = new BankAccount("ACC002", "Nomsa Zulu", 0);
        assertThrows(IllegalStateException.class,
                () -> account.transferTo(target, 9999.00));
    }

    @Test
    void transferTo_insufficientFunds_neitherBalanceShouldChange() {
        BankAccount target = new BankAccount("ACC002", "Nomsa Zulu", 500.00);
        try { account.transferTo(target, 9999.00); } catch (IllegalStateException ignored) {}
        assertEquals(1000.00, account.getBalance(), 0.001);
        assertEquals(500.00, target.getBalance(), 0.001);
    }

    // ── History immutability ─────────────────────────────────────────────────

    @Test
    void getTransactions_shouldReturnUnmodifiableList() {
        assertThrows(UnsupportedOperationException.class,
                () -> account.getTransactions().clear());
    }

    // ── toString ─────────────────────────────────────────────────────────────

    @Test
    void toString_shouldContainAccountNumberAndOwner() {
        String output = account.toString();
        assertTrue(output.contains("ACC001"));
        assertTrue(output.contains("Thabo Nkosi"));
    }
}
