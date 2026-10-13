import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

// ==========================================
// 1. Customer and Account Classes (Serializable)
// ==========================================
class Customer implements Serializable {
    private static final long serialVersionUID = 1L;
    String name;
    String email;

    public Customer(String name, String email) {
        this.name = name;
        this.email = email;
    }

    @Override
    public String toString() {
        return "Customer{name='" + name + "', email='" + email + "'}";
    }
}

abstract class Account implements Serializable {
    private static final long serialVersionUID = 1L;
    String accountNumber;
    Customer customer;
    double balance;

    public Account(String accountNumber, Customer customer, double balance) {
        this.accountNumber = accountNumber;
        this.customer = customer;
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Account{accNo='" + accountNumber + "', customer=" + customer + ", balance=" + balance + "}";
    }
}

class SavingsAccount extends Account {
    private static final long serialVersionUID = 1L;
    double interestRate;

    public SavingsAccount(String accountNumber, Customer customer, double balance, double interestRate) {
        super(accountNumber, customer, balance);
        this.interestRate = interestRate;
    }

    @Override
    public String toString() {
        return "SavingsAccount{accNo='" + accountNumber + "', customer=" + customer + ", balance=" + balance + ", rate=" + interestRate + "%}";
    }
}

// ==========================================
// 2. StatePersister Class (Save & Load Accounts)
// ==========================================
class StatePersister {
    public static void save(Account[] accounts, Path file) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file.toFile()))) {
            oos.writeObject(accounts);
            System.out.println("-> Accounts successfully saved to: " + file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Account[] load(Path file) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file.toFile()))) {
            Account[] accounts = (Account[]) ois.readObject();
            System.out.println("-> Accounts successfully loaded from: " + file);
            return accounts;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}

// ==========================================
// 3. TransactionLog Class (Append-only logs)
// ==========================================
class TransactionLog {
    public static void append(Path file, String line) {
        try {
            // Agar logs folder nahi hai toh pehle bana lo
            if (file.getParent() != null && !Files.exists(file.getParent())) {
                Files.createDirectories(file.getParent());
            }
            Files.write(file, List.of(line), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

// ==========================================
// 4. ReportGenerator Class (NIO Walk & Summary)
// ==========================================
class ReportGenerator {
    public static void generateReport(Path logsDir, Path reportFile) {
        long totalDeposits = 0;
        long totalWithdrawals = 0;

        StringBuilder reportContent = new StringBuilder();
        reportContent.append("=== MINI-BANK END OF DAY SUMMARY REPORT ===\n\n");

        try {
            if (!Files.exists(logsDir)) {
                Files.createDirectories(logsDir);
                System.out.println("Logs directory created.");
                return;
            }

            // Files.newDirectoryStream se logs folder ke saare files read karna
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(logsDir)) {
                for (Path entry : stream) {
                    if (Files.isRegularFile(entry)) {
                        // File attributes padhna (size aur last-modified time)
                        BasicFileAttributes attrs = Files.readAttributes(entry, BasicFileAttributes.class);
                        long size = attrs.size();
                        var lastModified = attrs.lastModifiedTime();

                        reportContent.append("Log File: ").append(entry.getFileName()).append("\n");
                        reportContent.append("  - Size: ").append(size).append(" bytes\n");
                        reportContent.append("  - Last Modified: ").append(lastModified).append("\n");
                        reportContent.append("  - Transactions:\n");

                        // BufferedReader se line by line read karna
                        try (BufferedReader reader = Files.newBufferedReader(entry)) {
                            String line;
                            while ((line = reader.readLine()) != null) {
                                reportContent.append("    -> ").append(line).append("\n");
                                
                                // Deposits aur Withdrawals calculate karna
                                if (line.startsWith("DEPOSIT:")) {
                                    long amount = Long.parseLong(line.split(":")[1].trim());
                                    totalDeposits += amount;
                                } else if (line.startsWith("WITHDRAWAL:")) {
                                    long amount = Long.parseLong(line.split(":")[1].trim());
                                    totalWithdrawals += amount;
                                }
                            }
                        }
                        reportContent.append("--------------------------------------------------\n");
                    }
                }
            }

            // Totals report mein append karna
            reportContent.append("\n=== FINANCIAL SUMMARY ===\n");
            reportContent.append("Total Deposits   : ₹").append(totalDeposits).append("\n");
            reportContent.append("Total Withdrawals: ₹").append(totalWithdrawals).append("\n");

            // Report file mein likhna
            Files.write(reportFile, reportContent.toString().getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            System.out.println("-> End-of-Day report generated successfully at: " + reportFile);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

// ==========================================
// 5. Main Application Class
// ==========================================
public class MiniBankApp {
    public static void main(String[] args) {
        Path accountFile = Paths.get("accounts.ser");
        Path logsDir = Paths.get("logs");
        Path reportFile = Paths.get("eod_report.txt");

        System.out.println("=== 1. Initializing & Saving Accounts ===");
        Customer c1 = new Customer("Aarav Shah", "aarav@bank.com");
        Customer c2 = new Customer("Priya Patel", "priya@bank.com");

        Account[] accounts = {
            new SavingsAccount("ACC-001", c1, 15000.0, 4.5),
            new SavingsAccount("ACC-002", c2, 28000.0, 4.0)
        };

        // Accounts save karna
        StatePersister.save(accounts, accountFile);

        System.out.println("\n=== 2. Reloading Accounts from File ===");
        Account[] reloadedAccounts = StatePersister.load(accountFile);
        if (reloadedAccounts != null) {
            for (Account acc : reloadedAccounts) {
                System.out.println("Loaded -> " + acc);
            }
        }

        System.out.println("\n=== 3. Simulating Transactions (Logging) ===");
        Path logFile1 = logsDir.resolve("branch_A.log");
        Path logFile2 = logsDir.resolve("branch_B.log");

        // Transaction log entries append karna
        TransactionLog.append(logFile1, "DEPOSIT:5000");
        TransactionLog.append(logFile1, "WITHDRAWAL:1200");
        TransactionLog.append(logFile2, "DEPOSIT:10000");
        TransactionLog.append(logFile2, "WITHDRAWAL:3500");
        System.out.println("-> Transaction logs written successfully inside 'logs/' folder.");

        System.out.println("\n=== 4. Generating End-of-Day Report ===");
        ReportGenerator.generateReport(logsDir, reportFile);

        // Print final report content on console
        try {
            System.out.println("\n--- Report File Content Preview ---");
            String reportText = Files.readString(reportFile);
            System.out.println(reportText);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}