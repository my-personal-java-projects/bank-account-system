# Bank Account System 

A console-based Java application simulating a real bank. Supports multiple accounts, deposits, withdrawals, inter-account transfers, and full timestamped transaction history — all in memory. Built to demonstrate multi-class OOP design, business rule enforcement, exception handling, and thorough unit testing with JUnit 5.

---

## Project Overview

| Detail | Info |
|---|---|
| Language | Java 17+ |
| Build Tool | Maven |
| Testing | JUnit 5 (Jupiter) |
| Type | Console Application |


---

## Features

- Create unlimited bank accounts with unique auto-generated account numbers
- Deposit and withdraw with full input validation
- Transfer funds between any two accounts atomically
- View a full timestamped transaction statement per account
- List all accounts with current balances
- Business rules enforced: no negative deposits, no overdrafts, no zero-value transactions
- Two demo accounts (Alice & Bob) pre-loaded on startup for quick testing
- Starting salary for Software Engineering track listed on BBD's site: **R40 000/month** — this project is proof you can build production-style systems

---

## Project Structure

```
bank-account-system/
├── pom.xml                              ← Maven build + JUnit 5 dependency
├── README.md
├── docs/
│   └── uml-diagram.md                  ← UML class diagram
└── src/
    ├── main/
    │   └── java/
    │       ├── Main.java                ← Entry point
    │       ├── Bank.java                ← Account registry + account number generation
    │       ├── BankAccount.java         ← Account state, business rules, transaction log
    │       ├── BankUI.java              ← Console menu and all user interaction
    │       └── Transaction.java         ← Immutable timestamped transaction record
    └── test/
        └── java/
            ├── BankTest.java            ← 12 tests for Bank registry
            ├── BankAccountTest.java     ← 24 tests for account operations
            └── TransactionTest.java     ← 6 tests for transaction records
```

---

## Running the Application in IntelliJ IDEA

### Step 1 — Open the project
1. Open IntelliJ IDEA
2. Click **File → Open**
3. Select the `bank-account-system` folder (the one containing `pom.xml`)
4. IntelliJ will detect Maven and download JUnit 5 automatically

### Step 2 — Set the Java SDK
1. Go to **File → Project Structure → Project**
2. Set **SDK** to Java 17 or higher
3. Click **Apply → OK**

### Step 3 — Run
- Right-click `Main.java` → **Run 'Main.main()'**
- The bank menu starts in the terminal panel at the bottom

---

## Usage Example

```
=========================================
       BBD COMMUNITY BANK v1.0
=========================================

Demo accounts created. Use 'List Accounts' to see them.

--- MENU ---
  1. Create Account
  2. Deposit
  3. Withdraw
  4. Transfer
  5. View Statement
  6. List All Accounts
  0. Exit
Choice: 6

  === All Accounts ===
  Account [ACC1001] — Owner: Alice Dlamini          Balance: R5,000.00
  Account [ACC1002] — Owner: Bob Nkosi              Balance: R2,500.00

Choice: 4
  Source account number: ACC1001
  Target account number: ACC1002
  Amount to transfer (R): 1000
  Transferred R1000.00 from ACC1001 to ACC1002

Choice: 3
  Account number: ACC1001
  Amount to withdraw (R): 99999
  Error: Insufficient funds. Balance: R4,000.00, Requested: R99,999.00

Choice: 5
  Account number: ACC1001
  === Statement for Account [ACC1001] — Owner: Alice Dlamini  Balance: R4,000.00 ===
  [2026-05-28 10:30:00] DEPOSIT         R5,000.00  |  Balance: R5,000.00  |  Account opened
  [2026-05-28 10:31:05] TRANSFER_OUT    R1,000.00  |  Balance: R4,000.00  |  Transfer to ACC1002
```

---

## Running the Tests

### In IntelliJ
- Right-click `src/test/java` → **Run 'All Tests'**
- Results show as green ticks or red crosses in the Run panel

### With Maven (terminal)
```bash
# From inside the bank-account-system folder:
mvn test

# Run one test class:
mvn test -Dtest=BankAccountTest

# Run one specific test:
mvn test -Dtest=BankAccountTest#withdraw_insufficientFunds_shouldThrowIllegalStateException
```

### Test Coverage Summary

| Test Class | Tests | What It Covers |
|---|---|---|
| `BankAccountTest` | 24 | Constructor, deposit (happy path + edge cases), withdrawal (happy path + insufficient funds), transfer (source/target balances + transaction types), history immutability |
| `BankTest` | 12 | Account creation, unique account numbers, `findAccount` happy path + not found, `getAllAccounts` growth + immutability |
| `TransactionTest` | 6 | Field storage, toString content (type, amount, description, timestamp) |
| **Total** | **42** | |

---

## Class Responsibilities

### `Main`
Entry point. Creates and starts a `BankUI`. No business logic.

### `Bank`
An in-memory registry of all `BankAccount` objects. Generates sequential account numbers (`ACC1001`, `ACC1002`, …), stores accounts in a `LinkedHashMap` for insertion-order iteration, and exposes `createAccount()`, `findAccount()`, and `getAllAccounts()`. Throws `NoSuchElementException` for unknown account numbers.

### `BankAccount`
Encapsulates one account's state: balance, owner name, account number, and transaction list. Enforces all business rules:
- No negative or zero deposits/withdrawals
- No withdrawals exceeding the current balance
- All business rule violations throw typed exceptions (`IllegalArgumentException`, `IllegalStateException`)

`transferTo()` coordinates with a target account, updating both sides and correcting transaction types to `TRANSFER_OUT` / `TRANSFER_IN`.

### `BankUI`
Drives the numbered console menu. Prompts for input, delegates every action to `Bank` or `BankAccount`, and catches all exceptions gracefully without crashing. No arithmetic or business rules exist here.

### `Transaction`
An immutable record of one financial event. Stores transaction type, amount, resulting balance, a description, and a `LocalDateTime` timestamp set at creation. `toString()` formats the statement line shown in the account view.

---

## Key Java Concepts Demonstrated

| Concept | Where |
|---|---|
| OOP & encapsulation | Private fields across all classes; exposed only through methods |
| Multi-class design | 5 classes with distinct roles, cleanly separated |
| Business rule enforcement | `BankAccount` validates every operation and throws typed exceptions |
| Collections | `ArrayList` for transactions, `LinkedHashMap` for account registry |
| Unmodifiable collections | `Collections.unmodifiableList()`, `Collections.unmodifiableCollection()` |
| Enums | `Transaction.Type` — 4 typed values for transaction categories |
| LocalDateTime | Timestamping transactions at the moment of creation |
| Exception hierarchy | `IllegalArgumentException` vs `IllegalStateException` vs `NoSuchElementException` — each used for the correct semantic reason |
| Unit testing | 42 tests across 3 test classes covering happy paths and edge cases |

---

## UML Diagram

See [`docs/uml-diagram.md`](docs/uml-diagram.md)

---

## Possible Extensions

- Add account types: `SavingsAccount` (monthly interest), `ChequeAccount` (overdraft limit)
- Persist all accounts and transactions to a JSON file using Jackson
- Add PIN authentication per account
- Replace `LinkedHashMap` with a SQLite database via JDBC
- Build a REST API layer using Spring Boot
- Add an admin role with the ability to freeze or delete accounts
