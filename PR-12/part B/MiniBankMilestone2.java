import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

class Customer {
    private final String name;
    private final String email;

    public Customer(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "Customer{name='" + name + "', email='" + email + "'}";
    }
}

abstract class Account implements Comparable<Account> {
    private final String accountNumber;
    private final Customer customer;
    private double balance;

    public Account(String accountNumber, Customer customer, double balance) {
        this.accountNumber = accountNumber;
        this.customer = customer;
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public Customer getCustomer() {
        return customer;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            this.balance += amount;
        }
    }

    @Override
    public int compareTo(Account other) {
        return this.accountNumber.compareTo(other.accountNumber);
    }

    @Override
    public String toString() {
        return "Account{accNo='" + accountNumber + "', customer=" + customer + ", balance=₹" + balance + "}";
    }
}

class SavingsAccount extends Account {
    private final double interestRate;

    public SavingsAccount(String accountNumber, Customer customer, double balance, double interestRate) {
        super(accountNumber, customer, balance);
        this.interestRate = interestRate;
    }

    public double getInterestRate() {
        return interestRate;
    }

    @Override
    public String toString() {
        return "SavingsAccount{accNo='" + getAccountNumber() + "', customer=" + getCustomer() + ", balance=₹" + getBalance() + ", rate=" + interestRate + "%}";
    }
}

class AccountStore {
    private final Map<String, Account> accountMap = new HashMap<>();
    private final List<String> transactionHistory = new ArrayList<>();

    public void add(Account account) {
        accountMap.put(account.getAccountNumber(), account);
        transactionHistory.add("Added account: " + account.getAccountNumber());
    }

    public Account get(String accountNumber) {
        return accountMap.get(accountNumber);
    }

    public Collection<Account> getAll() {
        return new ArrayList<>(accountMap.values());
    }

    public List<Account> sortedByBalance() {
        List<Account> list = new ArrayList<>(accountMap.values());
        list.sort((a1, a2) -> Double.compare(a2.getBalance(), a1.getBalance()));
        return list;
    }

    public List<String> getTransactionHistory() {
        return new ArrayList<>(transactionHistory);
    }
}

class TransactionProcessor {
    private final ConcurrentHashMap<String, Account> concurrentAccounts = new ConcurrentHashMap<>();

    public void addAccount(Account account) {
        concurrentAccounts.put(account.getAccountNumber(), account);
    }

    public synchronized void processDeposit(String accountNumber, double amount) {
        Account acc = concurrentAccounts.get(accountNumber);
        if (acc != null) {
            acc.deposit(amount);
            System.out.println("[Thread-Safe] Deposited ₹" + amount + " to " + accountNumber + ". New Balance: ₹" + acc.getBalance());
        }
    }
}

public class MiniBankMilestone2 {
    public static void main(String[] args) {
        System.out.println("=== 1. Initializing AccountStore & Adding Accounts ===");
        AccountStore store = new AccountStore();

        Customer c1 = new Customer("Aarav Shah", "aarav@bank.com");
        Customer c2 = new Customer("Priya Patel", "priya@bank.com");
        Customer c3 = new Customer("Rohan Verma", "rohan@bank.com");

        store.add(new SavingsAccount("ACC-101", c1, 12000.0, 4.0));
        store.add(new SavingsAccount("ACC-102", c2, 45000.0, 4.5));
        store.add(new SavingsAccount("ACC-103", c3, 25000.0, 3.8));

        System.out.println("\n=== 2. Fetching Single Account by Number (ACC-102) ===");
        Account fetched = store.get("ACC-102");
        System.out.println("Found -> " + fetched);

        System.out.println("\n=== 3. Accounts Sorted by Balance (Highest to Lowest) ===");
        List<Account> sortedList = store.sortedByBalance();
        for (Account acc : sortedList) {
            System.out.println(acc);
        }

        System.out.println("\n=== 4. Testing TransactionProcessor with ConcurrentHashMap ===");
        TransactionProcessor processor = new TransactionProcessor();
        processor.addAccount(fetched);
        processor.processDeposit("ACC-102", 5000.0);

        System.out.println("\n=== 5. Transaction History Log ===");
        for (String txn : store.getTransactionHistory()) {
            System.out.println("Log -> " + txn);
        }
    }
}