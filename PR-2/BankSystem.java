public class BankSystem { 
 
    static class Customer { 
 
        private String name; 
        private String email; 
        private String mobile; 
        private final String customerId; 
 
        private static long customerCounter = 100; 
 
        private static String generateCustomerId() { 
            customerCounter++; 
            return "CUST" + customerCounter; 
        } 
 
        public Customer(String name, String email, String mobile) { 
            this.name = name; 
            this.email = email; 
            this.mobile = mobile; 
            this.customerId = generateCustomerId(); 
        } 
 
        public String getName() { 
            return name; 
        } 
 
        public String getEmail() { 
            return email; 
        } 
 
        public String getMobile() { 
            return mobile; 
        } 
 
        public String getCustomerId() { 
            return customerId; 
        } 
    } 
 
    static class Account { 
 
        private final String accountNumber; 
        private String ownerName; 
        private long balance; 
        private boolean active; 
 
        private static int accountCounter = 0; 
 
        public Account(String ownerName, long openingBalance) { 
            accountCounter++; 
 
            this.accountNumber = "AC" + accountCounter; 
 
            this.ownerName = ownerName; 
            this.balance = openingBalance; 
            this.active = true; 
        } 
 
        public Account(String ownerName) { 
            this(ownerName, 0); 
        } 
 
        public void deposit(long amount) { 
            balance += amount; 
        } 
 
        public boolean withdraw(long amount) { 
            if (amount <= balance) { 
                balance -= amount; 
                return true; 
            } 
 
            return false; 
        } 
 
        public String getAccountNumber() { 
            return accountNumber; 
        } 
 
        public String getOwnerName() { 
            return ownerName; 
        } 
 
        public long getBalance() { 
            return balance; 
        } 
 
        public boolean isActive() { 
            return active; 
        } 
    } 
 
    public static void main(String[] args) { 
 
        Account[] accounts = new Account[3]; 
 
        accounts[0] = new Account("Ansh", 1000); 
        accounts[1] = new Account("Jeet", 2000); 
        accounts[2] = new Account("Kshitij"); 
 
        accounts[0].deposit(500); 
        accounts[0].withdraw(200); 
 
        accounts[1].deposit(1000); 
        accounts[1].withdraw(500); 
 
        accounts[2].deposit(300); 
        accounts[2].withdraw(100); 
 
        for (Account account : accounts) { 
            System.out.println( 
                    account.getAccountNumber() + " - " 
                    + account.getOwnerName() + " - Balance: ₹" 
                    + account.getBalance() 
            ); 
        } 
    } 
} 
