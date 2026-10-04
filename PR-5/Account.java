public abstract class Account {
    private final String accountNumber;
    private String ownerName;
    private long balance;
    private boolean active;
    
    private static int accountCounter = 0;
    
    // Constructor with ownerName and opening balance
    public Account(String ownerName, long openingBalance) {
        this.accountNumber = generateAccountNumber();
        this.ownerName = ownerName;
        this.balance = openingBalance;
        this.active = true;
    }
    
    // Constructor with only ownerName (opening balance is 0)
    public Account(String ownerName) {
        this(ownerName, 0);
    }
    
    // Abstract methods
    public abstract double interestRate();
    public abstract boolean canWithdraw(long amount);
    
    // Private static method to generate account number
    private static String generateAccountNumber() {
        return "AC" + String.format("%04d", ++accountCounter);
    }
    
    // Deposit method
    public void deposit(long amount) {
        if (amount > 0 && active) {
            balance += amount;
            System.out.println("Deposited: ₹" + amount + " | New Balance: ₹" + balance);
        } else {
            System.out.println("Invalid deposit amount or account is inactive");
        }
    }
    
    // Withdraw method
    public boolean withdraw(long amount) {
        if (amount > 0 && canWithdraw(amount) && active) {
            balance -= amount;
            System.out.println("Withdrawn: ₹" + amount + " | New Balance: ₹" + balance);
            return true;
        } else {
            System.out.println("Insufficient balance or invalid amount");
            return false;
        }
    }
    
    // Getter methods
    public String getAccountNumber() {
        return accountNumber;
    }
    
    public String getOwnerName() {
        return ownerName;
    }
    
    public long getBalance() {
        return balance;
    }
    
    protected void setBalance(long newBalance) {
        this.balance = newBalance;
    }
    
    protected long getCurrentBalance() {
        return balance;
    }
    
    public boolean isActive() {
        return active;
    }
    
    // Override toString() method
    @Override
    public String toString() {
        return "Account{" +
                "accountNumber='" + accountNumber + '\'' +
                ", ownerName='" + ownerName + '\'' +
                ", balance=" + balance +
                '}';
    }
    
    // Override equals() method
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return accountNumber.equals(account.accountNumber);
    }
    
    // Override hashCode() method
    @Override
    public int hashCode() {
        return accountNumber.hashCode();
    }
}
