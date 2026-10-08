public class Main {

    static class BankException extends Exception {
        public BankException(String message) {
            super(message);
        }
    }

    static class Account {

        long balance;

        Account(long balance) {
            this.balance = balance;
        }

        public void transfer(Account to, long amount)
                throws BankException {

            try {

                if (to == null) {
                    throw new BankException(
                            "Account not found");
                }

                if (amount <= 0) {
                    throw new BankException(
                            "Invalid transfer amount");
                }

                if (amount > balance) {
                    throw new BankException(
                            "Insufficient balance");
                }

                balance -= amount;
                to.balance += amount;

                System.out.println(
                        "Transfer successful: ₹" + amount);

            } catch (BankException e) {

                // Re-throw BankException
                throw e;

            } finally {

                System.out.println(
                        "Transfer operation completed");
            }
        }
    }

    public static void main(String[] args) {

        Account account1 = new Account(5000);
        Account account2 = new Account(2000);

        try {

            account1.transfer(account2, 1000);

            System.out.println(
                    "Account 1 Balance: ₹"
                    + account1.balance);

            System.out.println(
                    "Account 2 Balance: ₹"
                    + account2.balance);

        } catch (BankException e) {

            System.out.println(
                    "Transfer Error: "
                    + e.getMessage());
        }
    }
}