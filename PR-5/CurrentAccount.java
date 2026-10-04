public class CurrentAccount extends Account {
    private long overdraftLimit;
    
    // Constructor with ownerName, opening balance, and overdraft limit
    public CurrentAccount(String ownerName, long openingBalance, long overdraftLimit) {
        super(ownerName, openingBalance);
        this.overdraftLimit = overdraftLimit;
    }
    
    // Constructor with only ownerName (default overdraft limit 50000)
    public CurrentAccount(String ownerName) {
        super(ownerName, 0);
        this.overdraftLimit = 50000;
    }
    
    @Override
    public double interestRate() {
        return 0;
    }
    
    @Override
    public boolean canWithdraw(long amount) {
        return (getCurrentBalance() - amount) >= -overdraftLimit;
    }
    
    public long getOverdraftLimit() {
        return overdraftLimit;
    }
}
