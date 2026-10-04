public class FixedDepositAccount extends Account {
    
    // Constructor with ownerName and opening balance (locked deposit)
    public FixedDepositAccount(String ownerName, long depositAmount) {
        super(ownerName, depositAmount);
    }
    
    // Constructor with only ownerName (deposit amount is 0)
    public FixedDepositAccount(String ownerName) {
        super(ownerName, 0);
    }
    
    @Override
    public double interestRate() {
        return 7.0;
    }
    
    @Override
    public boolean canWithdraw(long amount) {
        // Fixed deposits cannot be withdrawn
        return false;
    }
}
