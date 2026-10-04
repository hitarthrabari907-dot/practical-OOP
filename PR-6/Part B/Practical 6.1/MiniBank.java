public class MiniBank {


    interface Transactable {
        void deposit(long amount);
        boolean withdraw(long amount);
    }

    static class Account implements Transactable {

        private long balance;

        Account(long balance) {
            this.balance = balance;
        }

        @Override
        public void deposit(long amount) {
            balance = balance + amount;
            System.out.println("Deposited: ₹" + amount);
        }

        @Override
        public boolean withdraw(long amount) {
            if (amount <= balance) {
                balance = balance - amount;
                System.out.println("Withdrawn: ₹" + amount);
                return true;
            }

            System.out.println("Insufficient balance");
            return false;
        }

        public long getBalance() {
            return balance;
        }
    }

    public static void main(String[] args) {

        Account account = new Account(5000);

        account.deposit(1000);
        account.withdraw(2000);

        System.out.println("Final Balance: ₹" + account.getBalance());
    }
}