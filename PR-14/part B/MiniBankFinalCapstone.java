import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.*;

// ==========================================
// 1. Custom Annotations & Exceptions
// ==========================================
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface Id {}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface Audit {}

class BankingException extends RuntimeException {
    public BankingException(String message) {
        super(message);
    }
}

// ==========================================
// 2. Model Classes
// ==========================================
class Customer {
    String name;
    String email;

    public Customer(String name, String email) {
        this.name = name;
        this.email = email;
    }
}

class Account {
    @Id
    String accountNumber;
    Customer customer;
    double balance;

    public Account(String accountNumber, Customer customer, double balance) {
        this.accountNumber = accountNumber;
        this.customer = customer;
        this.balance = balance;
    }
}

// ==========================================
// 3. Validator Class
// ==========================================
class Validator {
    public static void validateAmount(double amount) {
        if (amount <= 0) {
            throw new BankingException("Error: Amount must be greater than zero.");
        }
    }

    public static void validateWithdrawal(Account account, double amount) {
        validateAmount(amount);
        if (account.balance < amount) {
            throw new BankingException("Error: Insufficient funds! Current balance: ₹" + account.balance);
        }
    }
}

// ==========================================
// 4. Reusable Generic Repository
// ==========================================
class Repository<T> {
    private Map<String, T> storage = new ConcurrentHashMap<>();

    public T save(T entity) {
        try {
            Field field = entity.getClass().getDeclaredField("accountNumber");
            field.setAccessible(true);
            String id = (String) field.get(entity);
            storage.put(id, entity);
            return entity;
        } catch (Exception e) {
            throw new RuntimeException("Failed to save entity reflection error", e);
        }
    }

    public T findById(String id) {
        return storage.get(id);
    }

    public List<T> findAll() {
        return new ArrayList<>(storage.values());
    }
}

// ==========================================
// 5. BankService Layer (with @Audit Logger)
// ==========================================
class BankService {
    private Repository<Account> accountRepo = new Repository<>();

    @Audit
    public void openAccount(String accNo, String name, String email, double initialDeposit) {
        if (initialDeposit < 0) {
            throw new BankingException("Initial deposit cannot be negative.");
        }
        Customer customer = new Customer(name, email);
        Account account = new Account(accNo, customer, initialDeposit);
        accountRepo.save(account);
        System.out.println("[Audit] Account opened successfully: " + accNo);
    }

    @Audit
    public void deposit(String accNo, double amount) {
        Validator.validateAmount(amount);
        Account acc = accountRepo.findById(accNo);
        if (acc == null) throw new BankingException("Account not found: " + accNo);
        
        acc.balance += amount;
        System.out.println("[Audit] Deposited ₹" + amount + " to " + accNo + ". New Balance: ₹" + acc.balance);
    }

    @Audit
    public void withdraw(String accNo, double amount) {
        Account acc = accountRepo.findById(accNo);
        if (acc == null) throw new BankingException("Account not found: " + accNo);
        
        Validator.validateWithdrawal(acc, amount);
        acc.balance -= amount;
        System.out.println("[Audit] Withdrew ₹" + amount + " from " + accNo + ". Remaining Balance: ₹" + acc.balance);
    }

    @Audit
    public void transfer(String fromAcc, String toAcc, double amount) {
        withdraw(fromAcc, amount);
        deposit(toAcc, amount);
        System.out.println("[Audit] Transferred ₹" + amount + " from " + fromAcc + " to " + toAcc);
    }

    public Account getAccount(String accNo) {
        return accountRepo.findById(accNo);
    }

    public List<Account> listAccounts() {
        return accountRepo.findAll();
    }
}

// ==========================================
// 6. Concurrent Transaction Processor (Batch)
// ==========================================
class TransactionProcessor {
    public static void processBatchDeposits(BankService service, String accNo, double amount, int threads) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(threads);
        CountDownLatch latch = new CountDownLatch(threads);

        for (int i = 0; i < threads; i++) {
            executor.submit(() -> {
                try {
                    synchronized (service.getAccount(accNo)) {
                        service.deposit(accNo, amount);
                    }
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();
        executor.shutdown();
    }
}

// ==========================================
// 7. Main Application & Simulated Tests
// ==========================================
public class MiniBankFinalCapstone {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== MINI-BANK FINAL CAPSTONE (MILESTONE 3) ===\n");
        BankService bank = new BankService();

        // 1. Open Accounts
        bank.openAccount("ACC-001", "Aarav Shah", "aarav@mail.com", 10000.0);
        bank.openAccount("ACC-002", "Priya Patel", "priya@mail.com", 5000.0);

        // 2. Perform Transactions
        System.out.println("\n--- Testing Normal Transactions ---");
        bank.deposit("ACC-001", 2000.0);
        bank.withdraw("ACC-002", 1500.0);

        // 3. Testing Exception Handling (Over withdrawal)
        try {
            System.out.println("\n--- Testing Withdrawal Over Limit ---");
            bank.withdraw("ACC-002", 50000.0);
        } catch (BankingException e) {
            System.out.println("Caught Expected Exception: " + e.getMessage());
        }

        // 4. Testing Concurrent Batch Deposits
        System.out.println("\n--- Testing Concurrent Thread-Safe Deposits ---");
        TransactionProcessor.processBatchDeposits(bank, "ACC-001", 100.0, 10);
        System.out.println("Final Balance in ACC-001 after 10 concurrent deposits of ₹100: ₹" + bank.getAccount("ACC-001").balance);

        // 5. Listing All Accounts
        System.out.println("\n--- Final Account Listing ---");
        for (Account acc : bank.listAccounts()) {
            System.out.println("Account: " + acc.accountNumber + " | Holder: " + acc.customer.name + " | Balance: ₹" + acc.balance);
        }

        // 6. Reflective Audit Logger Demonstration
        System.out.println("\n--- Reflective @Audit Demonstration ---");
        for (Method m : BankService.class.getDeclaredMethods()) {
            if (m.isAnnotationPresent(Audit.class)) {
                System.out.println("[Reflective Logger] Audited Service Method Found: " + m.getName());
            }
        }
    }
}