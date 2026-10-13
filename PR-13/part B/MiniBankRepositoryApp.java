import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.util.*;

// ==========================================
// 1. Custom @Id Annotation (Runtime Retention)
// ==========================================
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface Id {
}

// ==========================================
// 2. Account Entity Class with @Id
// ==========================================
class Account {
    @Id // Reflection is field ko map key banane ke liye dhundhega
    String accountNumber;
    String accountHolder;
    double balance;

    public Account(String accountNumber, String accountHolder, double balance) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Account{accNo='" + accountNumber + "', holder='" + accountHolder + "', balance=₹" + balance + "}";
    }
}

// ==========================================
// 3. Reusable Generic Repository
// ==========================================
class Repository<T> {
    private Map<String, T> storage = new HashMap<>();

    // Save entity using Reflection to find @Id field value as the map key
    public T save(T entity) {
        try {
            Class<?> clazz = entity.getClass();
            String idValue = null;

            // Class ke saare fields check karna @Id ke liye
            for (Field field : clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(Id.class)) {
                    field.setAccessible(true); // Private field access karne ke liye
                    Object value = field.get(entity);
                    if (value != null) {
                        idValue = value.toString();
                    }
                    break;
                }
            }

            if (idValue == null) {
                throw new RuntimeException("Entity mein koi valid @Id field nahi mila!");
            }

            storage.put(idValue, entity);
            return entity;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Find entity by ID
    public T findById(String id) {
        return storage.get(id);
    }

    // Return all entities in an ArrayList
    public ArrayList<T> findAll() {
        return new ArrayList<>(storage.values());
    }

    // Delete entity by ID
    public boolean delete(String id) {
        return storage.remove(id) != null;
    }

    // @SafeVarargs method to save multiple items at once
    @SafeVarargs
    public final void saveAll(T... items) {
        for (T item : items) {
            save(item);
        }
    }
}

// ==========================================
// 4. Utility Class with Bounded Generic Method
// ==========================================
class MathUtils {
    // Bounded generic method for Number arrays
    public static <N extends Number> double average(N[] values) {
        if (values == null || values.length == 0) {
            return 0.0;
        }
        double sum = 0.0;
        for (N val : values) {
            sum += val.doubleValue();
        }
        return sum / values.length;
    }
}

// ==========================================
// 5. Main Application Class
// ==========================================
public class MiniBankRepositoryApp {
    public static void main(String[] args) {
        System.out.println("=== 1. Initializing Generic Repository ==   ");
        Repository<Account> accountRepo = new Repository<>();

        // Creating Account objects
        Account acc1 = new Account("ACC-301", "Aarav Shah", 25000.0);
        Account acc2 = new Account("ACC-302", "Priya Patel", 42000.0);
        Account acc3 = new Account("ACC-303", "Rohan Verma", 15000.0);

        // Saving accounts using saveAll (with @SafeVarargs)
        accountRepo.saveAll(acc1, acc2, acc3);
        System.out.println("-> Multiple accounts saved successfully using @SafeVarargs!");

        System.out.println("\n=== 2. Fetching Account by ID (Reflection @Id test) ===");
        Account found = accountRepo.findById("ACC-302");
        if (found != null) {
            System.out.println("Found Account -> " + found);
        }

        System.out.println("\n=== 3. Printing All Accounts in Repository ===");
        ArrayList<Account> allAccounts = accountRepo.findAll();
        for (Account acc : allAccounts) {
            System.out.println(acc);
        }

        System.out.println("\n=== 4. Testing Bounded Generic Method (Average) ===");
        Double[] balances = {25000.0, 42000.0, 15000.0};
        double avgBalance = MathUtils.average(balances);
        System.out.println("Average Account Balance: ₹" + avgBalance);
    }
}