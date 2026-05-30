# UML Class Diagram — Bank Account System

```
┌──────────────────────────────────────┐
│               <<main>>               │
│                Main                  │
├──────────────────────────────────────┤
│ + main(args: String[]) : void        │
└─────────────────┬────────────────────┘
                  │ creates
                  ▼
┌──────────────────────────────────────────┐
│                BankUI                    │
├──────────────────────────────────────────┤
│ - bank    : Bank                         │
│ - scanner : Scanner                      │
├──────────────────────────────────────────┤
│ + start()              : void            │
│ - handleChoice()       : boolean         │
│ - createAccount()      : void            │
│ - deposit()            : void            │
│ - withdraw()           : void            │
│ - transfer()           : void            │
│ - viewStatement()      : void            │
│ - listAllAccounts()    : void            │
│ - promptAccount()      : BankAccount     │
│ - promptAmount()       : double          │
│ - seedDemoAccounts()   : void            │
└─────────────────┬────────────────────────┘
                  │ uses
                  ▼
┌──────────────────────────────────────────┐
│                  Bank                    │
├──────────────────────────────────────────┤
│ - bankName          : String             │
│ - accounts          : Map<String, BankAccount>│
│ - nextAccountNumber : int                │
├──────────────────────────────────────────┤
│ + createAccount(name, deposit): BankAccount│
│ + findAccount(accountNumber)  : BankAccount│
│ + getAllAccounts()  : Collection<BankAccount>│
│ + getBankName()     : String             │
└─────────────────┬────────────────────────┘
                  │ manages  1..*
                  ▼
┌──────────────────────────────────────────┐
│              BankAccount                 │
├──────────────────────────────────────────┤
│ - accountNumber : String                 │
│ - ownerName     : String                 │
│ - balance       : double                 │
│ - transactions  : List<Transaction>      │
├──────────────────────────────────────────┤
│ + deposit(amount, desc)    : void        │
│ + withdraw(amount, desc)   : void        │
│ + transferTo(target, amount): void       │
│ + getBalance()             : double      │
│ + getAccountNumber()       : String      │
│ + getOwnerName()           : String      │
│ + getTransactions()        : List<...>   │
│ + toString()               : String      │
└─────────────────┬────────────────────────┘
                  │ creates  0..*
                  ▼
┌──────────────────────────────────────────┐
│              Transaction                 │
├──────────────────────────────────────────┤
│ - type         : Type                    │
│ - amount       : double                  │
│ - balanceAfter : double                  │
│ - description  : String                  │
│ - timestamp    : LocalDateTime           │
├──────────────────────────────────────────┤
│ + getType()          : Type              │
│ + getAmount()        : double            │
│ + getBalanceAfter()  : double            │
│ + getDescription()   : String            │
│ + toString()         : String            │
└─────────────────┬────────────────────────┘
                  │ contains
                  ▼
┌──────────────────────────┐
│  <<enumeration>>         │
│  Type                    │
├──────────────────────────┤
│  DEPOSIT                 │
│  WITHDRAWAL              │
│  TRANSFER_IN             │
│  TRANSFER_OUT            │
└──────────────────────────┘
```

## Relationships Summary

| From | To | Type | Description |
|---|---|---|---|
| Main | BankUI | Dependency | creates and calls `start()` |
| BankUI | Bank | Composition | owns the bank instance |
| Bank | BankAccount | Composition | creates and stores accounts (1 bank → many accounts) |
| BankAccount | Transaction | Composition | owns its transaction list (1 account → many transactions) |
| Transaction | Type | Composition | owns the transaction type enum |
| BankAccount | BankAccount | Association | `transferTo()` interacts with another account |
