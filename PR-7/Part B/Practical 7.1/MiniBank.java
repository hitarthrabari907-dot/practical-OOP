public class MiniBank {

    @FunctionalInterface
    interface WithdrawRule {
        boolean allow(Account account, long amount);
    }

  
    static class Account {

        long balance;

        Account(long balance) {
            this.balance = balance;
        }

        @Override
        public String toString() {
            return "Account Balance: ₹" + balance;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;

            if (!(obj instanceof Account))
                return false;

            Account other = (Account) obj;

            return balance == other.balance;
        }

        @Override
        public int hashCode() {
            return Long.hashCode(balance);
        }
    }

   
    static class PremiumAccount extends Account {

        PremiumAccount(long balance) {
            super(balance);
        }

        @Override
        public String toString() {
            return "Premium Account Balance: ₹" + balance;
        }
    }

    public static void main(String[] args) {

        Account account = new Account(5000);
        PremiumAccount premium = new PremiumAccount(10000);

        System.out.println(account);
        System.out.println(premium);

        WithdrawRule rule =
            (acc, amount) -> amount > 0 && amount <= acc.balance;

        System.out.println("Withdraw ₹2000: "
                + rule.allow(account, 2000));
    }
}