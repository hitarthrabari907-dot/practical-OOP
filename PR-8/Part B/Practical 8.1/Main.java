public class Main {

    
    static class BankException extends Exception {

        public BankException(String message) {
            super(message);
        }
    }

    static class InsufficientFundsException extends BankException {

        long shortfall;

        public InsufficientFundsException(
                String message, long shortfall) {

            super(message);
            this.shortfall = shortfall;
        }

        public long getShortfall() {
            return shortfall;
        }
    }

    static class AccountNotFoundException
            extends BankException {

        public AccountNotFoundException(String message) {
            super(message);
        }
    }

    static class InvalidAmountException
            extends BankException {

        public InvalidAmountException(String message) {
            super(message);
        }
    }

    static class Account {

        private String accountNumber;
        private String name;
        private long balance;

        public Account(
                String accountNumber,
                String name,
                long balance) {

            this.accountNumber = accountNumber;
            this.name = name;
            this.balance = balance;
        }

        public long getBalance() {
            return balance;
        }

        
        public void deposit(long amount)
                throws InvalidAmountException {

            if (amount <= 0) {
                throw new InvalidAmountException(
                        "Amount must be greater than 0");
            }

            balance += amount;

            System.out.println(
                    "Deposit successful: ₹" + amount);
        }

       
        public void withdraw(long amount)
                throws InsufficientFundsException,
                       InvalidAmountException {

            if (amount <= 0) {
                throw new InvalidAmountException(
                        "Amount must be greater than 0");
            }

            if (amount > balance) {

                long shortfall = amount - balance;

                throw new InsufficientFundsException(
                        "Insufficient balance",
                        shortfall);
            }

            balance -= amount;

            System.out.println(
                    "Withdrawal successful: ₹" + amount);
        }

       
        public void transfer(
                Account to,
                long amount)
                throws BankException {

            try {

                if (to == null) {
                    throw new AccountNotFoundException(
                            "Destination account not found");
                }

                withdraw(amount);
                to.deposit(amount);

                System.out.println(
                        "Transfer successful: ₹" + amount);

            } catch (BankException e) {

                throw e;

            } finally {

                System.out.println(
                        "Transfer operation completed");
            }
        }
    }

    static class BankResource
            implements AutoCloseable {

        public void use() {
            System.out.println(
                    "Using bank resource");
        }

        @Override
        public void close() {
            System.out.println(
                    "Bank resource closed");
        }
    }

    public static void main(String[] args) {

        Account a1 =
                new Account("101", "Ansh", 5000);

        Account a2 =
                new Account("102", "Rahul", 2000);

        try {

            a1.deposit(1000);

        } catch (InvalidAmountException e) {

            System.out.println(
                    "Deposit Error: "
                    + e.getMessage());

        } finally {

            System.out.println(
                    "Deposit finished");
        }


        try {

            a1.withdraw(1000);

        } catch (InsufficientFundsException e) {

            System.out.println(
                    "Error: " + e.getMessage());

            System.out.println(
                    "Shortfall: ₹"
                    + e.getShortfall());

        } catch (InvalidAmountException e) {

            System.out.println(
                    "Error: " + e.getMessage());

        } finally {

            System.out.println(
                    "Withdrawal finished");
        }
        try {

            a1.withdraw(10000);

        } catch (InsufficientFundsException e) {

            System.out.println(
                    "Error: " + e.getMessage());

            System.out.println(
                    "Shortfall: ₹"
                    + e.getShortfall());

        } catch (InvalidAmountException e) {

            System.out.println(
                    "Error: " + e.getMessage());

        } finally {

            System.out.println(
                    "Withdrawal test finished");
        }


        try {

            a1.transfer(a2, 500);

        } catch (BankException e) {

            System.out.println(
                    "Transfer Error: "
                    + e.getMessage());

        } finally {

            System.out.println(
                    "Transfer test finished");
        }


       
        try (BankResource resource =
                     new BankResource()) {

            resource.use();

        } catch (Exception e) {

            System.out.println(
                    "Resource Error: "
                    + e.getMessage());
        }


        System.out.println();

        System.out.println(
                "Ansh Balance: ₹"
                + a1.getBalance());

        System.out.println(
                "Rahul Balance: ₹"
                + a2.getBalance());
    }
}