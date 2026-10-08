import java.lang.annotation.*;
import java.lang.reflect.Field;

public class MiniBank {

   
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @interface Id {
    }

   
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @interface Positive {
        String message() default "must be > 0";
    }

   
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @interface MaxLength {
        int value();
    }

   
    static class Account {

        @Id
        private long accountNumber;

        @Positive
        private long balance;

        @MaxLength(20)
        private String name;

        public Account(long accountNumber, long balance, String name) {
            this.accountNumber = accountNumber;
            this.balance = balance;
            this.name = name;
        }

        public long getAccountNumber() {
            return accountNumber;
        }

        public long getBalance() {
            return balance;
        }

        @Override
        public String toString() {
            return "Account Number: " + accountNumber
                    + ", Balance: ₹" + balance
                    + ", Name: " + name;
        }
    }

        public static void main(String[] args) {

        Account account =
                new Account(101, 5000, "Ansh");

        System.out.println(account);

        
        for (Field field : Account.class.getDeclaredFields()) {

            if (field.isAnnotationPresent(Id.class)) {
                System.out.println(
                        field.getName() + " has @Id"
                );
            }

            if (field.isAnnotationPresent(Positive.class)) {
                Positive p =
                        field.getAnnotation(Positive.class);

                System.out.println(
                        field.getName()
                        + " has @Positive: "
                        + p.message()
                );
            }

            if (field.isAnnotationPresent(MaxLength.class)) {
                MaxLength m =
                        field.getAnnotation(MaxLength.class);

                System.out.println(
                        field.getName()
                        + " has @MaxLength("
                        + m.value() + ")"
                );
            }
        }
    }
}