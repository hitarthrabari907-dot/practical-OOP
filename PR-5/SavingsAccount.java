public class SavingsAccount extends Account {
    private long minBalance;
    
    // Constructor with ownerName, opening balance, and minimum balance
    public SavingsAccount(String ownerName, long openingBalance, long minBalance) {
        super(ownerName, openingBalance);
        this.minBalance = minBalance;
    }
    
    // Constructor with only ownerName (default minimum balance 1000)
    public SavingsAccount(String ownerName) {
        super(ownerName, 0);
        this.minBalance = 1000;
    }
    
    @Override
    public double interestRate() {
        return 4.0;
    }
    
    @Override
    public boolean canWithdraw(long amount) {
        return (getCurrentBalance() - amount) >= minBalance;
    }
    
    public long getMinBalance() {
        return minBalance;
    }
}
