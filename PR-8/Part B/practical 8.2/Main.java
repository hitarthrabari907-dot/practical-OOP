public class Main {

    // Custom exception
    static class InsufficientFundsException extends Exception {
        public InsufficientFundsException(String message) {
            super(message);
        }
    }

    // Custom exception
    static class InvalidAmountException extends Exception {
        public InvalidAmountException(String message) {
            super(message);
        }
    }

    // Account
    static class Account {

        long balance;

        Account(long balance) {
            this.balance = balance;
        }

        // Deposit
        public void deposit(long amount)
                throws InvalidAmountException {

            if (amount <= 0) {
                throw new InvalidAmountException(
                        "Deposit amount must be greater than 0");
            }

            balance += amount;

            System.out.println(
                    "Deposit successful: ₹" + amount);
        }

        // Withdraw
        public void withdraw(long amount)
                throws InsufficientFundsException,
                       InvalidAmountException {

            if (amount <= 0) {
                throw new InvalidAmountException(
                        "Withdrawal amount must be greater than 0");
            }

            if (amount > balance) {
                throw new InsufficientFundsException(
                        "Insufficient balance");
            }

            balance -= amount;

            System.out.println(
                    "Withdrawal successful: ₹" + amount);
        }
    }

    public static void main(String[] args) {

        Account account = new Account(5000);

        try {

            account.deposit(1000);

            account.withdraw(2000);

            System.out.println(
                    "Balance: ₹" + account.balance);

        } catch (InsufficientFundsException e) {

            System.out.println(
                    "Error: " + e.getMessage());

        } catch (InvalidAmountException e) {

            System.out.println(
                    "Error: " + e.getMessage());
        }
    }
}