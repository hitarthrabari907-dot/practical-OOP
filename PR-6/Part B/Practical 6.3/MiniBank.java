public class MiniBank {

    static class Account {
        long balance;

        Account(long balance) {
            this.balance = balance;
        }
    }

  
    @FunctionalInterface
    interface WithdrawRule {
        boolean allow(Account account, long amount);
    }

    public static void main(String[] args) {

        Account account = new Account(5000);

      
        WithdrawRule rule1 = new WithdrawRule() {
            @Override
            public boolean allow(Account account, long amount) {
                return amount > 0 && amount <= account.balance;
            }
        };

        System.out.println("Anonymous Class: "
                + rule1.allow(account, 2000));

      
        WithdrawRule rule2 =
                (acc, amount) -> amount > 0 && amount <= acc.balance;

        System.out.println("Lambda: "
                + rule2.allow(account, 6000));
    }
}