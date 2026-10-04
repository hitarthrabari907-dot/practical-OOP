import java.util.Scanner;

public class MiniBank {
    
    enum MenuOption {
        OPEN_ACCOUNT,
        DEPOSIT,
        WITHDRAW,
        TRANSFER,
        EXIT
    }
    
    public static void main(String[] args) {
        // Create and print header
        BankInfo bankInfo = new BankInfo("MiniBank", "Main Branch");
        System.out.println("=".repeat(40));
        System.out.println("Welcome to " + bankInfo.name());
        System.out.println("Branch: " + bankInfo.branch());
        System.out.println("=".repeat(40));
        System.out.println();
        
        // Practical 5: Polymorphism with different Account types
        System.out.println("--- Practical 5: Polymorphism with Account Subclasses ---\n");
        
        // Create array of different account types
        Account[] accounts = new Account[3];
        accounts[0] = new SavingsAccount("Rajesh Kumar", 5000, 1000);
        accounts[1] = new CurrentAccount("Priya Sharma", 10000, 50000);
        accounts[2] = new FixedDepositAccount("Amit Patel", 100000);
        
        System.out.println("--- Account Details and Interest Rates ---");
        for (int i = 0; i < accounts.length; i++) {
            Account acc = accounts[i];
            System.out.println("\nAccount " + i + ": " + acc.getAccountNumber() + " (" + acc.getOwnerName() + ")");
            System.out.println("  Type: " + acc.getClass().getSimpleName());
            System.out.println("  Balance: ₹" + acc.getBalance());
            System.out.println("  Interest Rate: " + acc.interestRate() + "%");
            
            // Pattern instanceof to handle FixedDepositAccount specially
            if (acc instanceof FixedDepositAccount) {
                System.out.println("  Status: LOCKED (Fixed Deposit - No withdrawals allowed)");
            } else if (acc instanceof SavingsAccount savings) {
                System.out.println("  Minimum Balance: ₹" + savings.getMinBalance());
            } else if (acc instanceof CurrentAccount current) {
                System.out.println("  Overdraft Limit: ₹" + current.getOverdraftLimit());
            }
        }
        
        // Demonstrate withdrawal behavior based on account type
        System.out.println("\n--- Withdrawal Tests (Polymorphic Behavior) ---");
        
        // Test SavingsAccount (can withdraw if min balance maintained)
        System.out.println("\nSavingsAccount - Attempt to withdraw ₹4500:");
        accounts[0].withdraw(4500);
        System.out.println("Final balance: ₹" + accounts[0].getBalance());
        
        // Test CurrentAccount (can go into overdraft)
        System.out.println("\nCurrentAccount - Attempt to withdraw ₹20000:");
        accounts[1].withdraw(20000);
        System.out.println("Final balance: ₹" + accounts[1].getBalance());
        
        // Test FixedDepositAccount (cannot withdraw)
        System.out.println("\nFixedDepositAccount - Attempt to withdraw ₹10000:");
        accounts[2].withdraw(10000);
        System.out.println("Final balance: ₹" + accounts[2].getBalance());
        
        System.out.println();
        Scanner scanner = new Scanner(System.in);
        MenuOption choice;
        
        // Main loop
        try {
            while (true) {
                displayMenu();
                System.out.print("Enter your choice (1-5): ");
                int input = scanner.nextInt();
                
                choice = getMenuOption(input);
                
                if (choice == null) {
                    System.out.println("Invalid choice. Please try again.\n");
                    continue;
                }
                
                String result = switch (choice) {
                    case OPEN_ACCOUNT -> "Open Account — to be implemented in a later lab";
                    case DEPOSIT -> "Deposit — to be implemented in a later lab";
                    case WITHDRAW -> "Withdraw — to be implemented in a later lab";
                    case TRANSFER -> "Transfer — to be implemented in a later lab";
                    case EXIT -> {
                        System.out.println("\nThank you for using MiniBank. Goodbye!");
                        yield null;
                    }
                };
                
                if (result == null) break;
                
                System.out.println(result + "\n");
            }
        } finally {
            scanner.close();
        }
    }
    
    // Display menu
    private static void displayMenu() {
        System.out.println("--- MiniBank Menu ---");
        System.out.println("1. Open Account");
        System.out.println("2. Deposit");
        System.out.println("3. Withdraw");
        System.out.println("4. Transfer");
        System.out.println("5. Exit");
    }
    
    // Convert input to MenuOption
    private static MenuOption getMenuOption(int choice) {
        return switch (choice) {
            case 1 -> MenuOption.OPEN_ACCOUNT;
            case 2 -> MenuOption.DEPOSIT;
            case 3 -> MenuOption.WITHDRAW;
            case 4 -> MenuOption.TRANSFER;
            case 5 -> MenuOption.EXIT;
            default -> null;
        };
    }
}
