public class Main {

    static class BankResource implements AutoCloseable {

        public void use() {
            System.out.println("Bank resource is being used.");
        }

        @Override
        public void close() {
            System.out.println("Bank resource closed.");
        }
    }

    public static void main(String[] args) {

        
        try {

            System.out.println("Bank operation started.");

            int balance = 5000;
            int withdraw = 2000;

            if (withdraw > balance) {
                throw new Exception("Insufficient balance.");
            }

            balance = balance - withdraw;

            System.out.println(
                    "Withdrawal successful: ₹" + withdraw);

            System.out.println(
                    "Remaining balance: ₹" + balance);

        } catch (Exception e) {

            System.out.println(
                    "Sorry, the operation failed: "
                    + e.getMessage());

        } finally {

            System.out.println(
                    "Bank operation completed.");
        }


      
        try (BankResource resource =
                     new BankResource()) {

            resource.use();

        } catch (Exception e) {

            System.out.println(
                    "Resource error: "
                    + e.getMessage());
        }
    }
}