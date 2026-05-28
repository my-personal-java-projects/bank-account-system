import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.NoSuchElementException;
import static org.junit.jupiter.api.Assertions.*;


class BankTest {

    private Bank bank;

    @BeforeEach
    void setUp() {
        bank = new Bank("Test Bank");
    }


    @Test
    void createAccount_shouldReturnAccountWithCorrectOwner() {
        BankAccount account = bank.createAccount("Alice Dlamini", 1000.00);
        assertEquals("Alice Dlamini", account.getOwnerName());
    }

    @Test
    void createAccount_shouldReturnAccountWithCorrectBalance() {
        BankAccount account = bank.createAccount("Bob Nkosi", 500.00);
        assertEquals(500.00, account.getBalance(), 0.001);
    }

    @Test
    void createAccount_shouldAssignUniqueAccountNumbers() {
        BankAccount a1 = bank.createAccount("Alice", 100);
        BankAccount a2 = bank.createAccount("Bob", 100);
        assertNotEquals(a1.getAccountNumber(), a2.getAccountNumber());
    }

    @Test
    void createAccount_accountNumbersShouldStartWithACC() {
        BankAccount account = bank.createAccount("Test", 0);
        assertTrue(account.getAccountNumber().startsWith("ACC"));
    }

    @Test
    void createAccount_shouldBeRetrievableByNumber() {
        BankAccount created = bank.createAccount("Zanele Mokoena", 750.00);
        BankAccount found = bank.findAccount(created.getAccountNumber());
        assertEquals(created.getAccountNumber(), found.getAccountNumber());
    }


    @Test
    void findAccount_nonExistentNumber_shouldThrowNoSuchElementException() {
        assertThrows(NoSuchElementException.class,
                () -> bank.findAccount("ACC9999"));
    }

    @Test
    void findAccount_shouldReturnCorrectAccount() {
        bank.createAccount("First", 100);
        BankAccount second = bank.createAccount("Second", 200);
        BankAccount found = bank.findAccount(second.getAccountNumber());
        assertEquals("Second", found.getOwnerName());
    }


    @Test
    void getAllAccounts_initiallyEmpty() {
        assertTrue(bank.getAllAccounts().isEmpty());
    }

    @Test
    void getAllAccounts_shouldGrowWithEachCreatedAccount() {
        bank.createAccount("One", 100);
        bank.createAccount("Two", 200);
        bank.createAccount("Three", 300);
        assertEquals(3, bank.getAllAccounts().size());
    }

    @Test
    void getAllAccounts_shouldBeUnmodifiable() {
        bank.createAccount("Test", 100);
        assertThrows(UnsupportedOperationException.class,
                () -> bank.getAllAccounts().clear());
    }


    @Test
    void getBankName_shouldReturnConstructorValue() {
        assertEquals("Test Bank", bank.getBankName());
    }
}
