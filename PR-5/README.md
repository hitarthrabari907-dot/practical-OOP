# Practical 5: Polymorphism and Inheritance

## Objectives
- Make Account an abstract class
- Define abstract methods: interestRate() and canWithdraw()
- Create three account subclasses with different behaviors
- Demonstrate run-time polymorphism through method calls
- Use pattern instanceof to handle specific types
- Implement withdrawal rules for each account type

## Files
- `MiniBank.java` - Main application with polymorphism demo
- `Account.java` - Abstract base class
- `SavingsAccount.java` - 4% interest, maintains minimum balance
- `CurrentAccount.java` - 0% interest, allows overdraft
- `FixedDepositAccount.java` - 7% interest, locked (no withdrawals)
- `Customer.java` - From Practical 3
- `BankInfo.java` - Record for bank information

## Concepts Covered
- Abstract classes and abstract methods
- Inheritance and method overriding
- Run-time polymorphism
- Pattern matching with instanceof
- Protected methods for subclass access
- Polymorphic arrays

## Account Types

### SavingsAccount
- Interest Rate: 4.0%
- Withdrawal: Only if balance stays ≥ minBalance (default 1000)
- Use Case: Personal savings

### CurrentAccount
- Interest Rate: 0%
- Withdrawal: Can go negative up to overdraftLimit
- Use Case: Business accounts

### FixedDepositAccount
- Interest Rate: 7.0%
- Withdrawal: Cannot withdraw (locked)
- Use Case: Time deposits, investments

## To Compile and Run
```bash
javac Account.java SavingsAccount.java CurrentAccount.java FixedDepositAccount.java Customer.java BankInfo.java MiniBank.java
java MiniBank
```

## Demo Features
- Create one account of each type
- Display interest rates (polymorphic call)
- Test withdrawal behavior (polymorphic method)
- Use instanceof to identify account types
- Show account-specific properties
