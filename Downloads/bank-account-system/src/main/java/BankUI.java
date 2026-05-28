import java.util.NoSuchElementException;
import java.util.Scanner;

// Console UI for the Bank Account System.

public class BankUI {

    private final Bank bank = new Bank("BBD Community Bank");
    private final Scanner scanner = new Scanner(System.in);

    public void start() {
        printBanner();
        seedDemoAccounts();

        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();
            running = handleChoice(choice);
        }
        System.out.println("\nThank you for banking with " + bank.getBankName() + ". Goodbye!");
        scanner.close();
    }

    private boolean handleChoice(String choice) {
        System.out.println();
        switch (choice) {
            case "1" -> createAccount();
            case "2" -> deposit();
            case "3" -> withdraw();
            case "4" -> transfer();
            case "5" -> viewStatement();
            case "6" -> listAllAccounts();
            case "0" -> { return false; }
            default  -> System.out.println("  Invalid option. Please choose 0–6.");
        }
        System.out.println();
        return true;
    }

    private void createAccount() {
        System.out.print("  Owner name: ");
        String name = scanner.nextLine().trim();
        double deposit = promptAmount("  Initial deposit (R): ");
        BankAccount account = bank.createAccount(name, deposit);
        System.out.println("  Account created: " + account);
    }

    private void deposit() {
        BankAccount account = promptAccount();
        if (account == null) return;
        double amount = promptAmount("  Amount to deposit (R): ");
        account.deposit(amount, "Counter deposit");
        System.out.printf("  Deposited R%.2f. New balance: R%.2f%n", amount, account.getBalance());
    }

    private void withdraw() {
        BankAccount account = promptAccount();
        if (account == null) return;
        double amount = promptAmount("  Amount to withdraw (R): ");
        try {
            account.withdraw(amount, "Counter withdrawal");
            System.out.printf("  Withdrawn R%.2f. New balance: R%.2f%n", amount, account.getBalance());
        } catch (IllegalStateException e) {
            System.out.println("  Error: " + e.getMessage());
        }
    }

    private void transfer() {
        System.out.print("  Source account number: ");
        BankAccount source = findAccount(scanner.nextLine().trim());
        if (source == null) return;

        System.out.print("  Target account number: ");
        BankAccount target = findAccount(scanner.nextLine().trim());
        if (target == null) return;

        double amount = promptAmount("  Amount to transfer (R): ");
        try {
            source.transferTo(target, amount);
            System.out.printf("  Transferred R%.2f from %s to %s%n",
                    amount, source.getAccountNumber(), target.getAccountNumber());
        } catch (IllegalStateException e) {
            System.out.println("  Error: " + e.getMessage());
        }
    }

    private void viewStatement() {
        BankAccount account = promptAccount();
        if (account == null) return;
        System.out.println("\n  === Statement for " + account + " ===");
        if (account.getTransactions().isEmpty()) {
            System.out.println("  No transactions yet.");
        } else {
            account.getTransactions().forEach(t -> System.out.println("  " + t));
        }
    }

    private void listAllAccounts() {
        System.out.println("  === All Accounts ===");
        bank.getAllAccounts().forEach(a -> System.out.println("  " + a));
    }

    private BankAccount promptAccount() {
        System.out.print("  Account number: ");
        return findAccount(scanner.nextLine().trim());
    }

    private BankAccount findAccount(String number) {
        try {
            return bank.findAccount(number);
        } catch (NoSuchElementException e) {
            System.out.println("  Account not found: " + number);
            return null;
        }
    }

    private double promptAmount(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                double value = Double.parseDouble(scanner.nextLine().trim());
                if (value <= 0) { System.out.println("  Amount must be greater than zero."); continue; }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("  Invalid amount. Please enter a number.");
            }
        }
    }

    private void seedDemoAccounts() {
        bank.createAccount("Alice Dlamini", 5000.00);
        bank.createAccount("Bob Nkosi", 2500.00);
        System.out.println("Demo accounts created. Use 'List Accounts' to see them.\n");
    }

    private void printMenu() {
        System.out.println("--- MENU ---");
        System.out.println("  1. Create Account");
        System.out.println("  2. Deposit");
        System.out.println("  3. Withdraw");
        System.out.println("  4. Transfer");
        System.out.println("  5. View Statement");
        System.out.println("  6. List All Accounts");
        System.out.println("  0. Exit");
        System.out.print("Choice: ");
    }

    private void printBanner() {
        System.out.println("=========================================");
        System.out.println("       BBD COMMUNITY BANK v1.0          ");
        System.out.println("=========================================\n");
    }
}
