import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

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

        @MaxLength(10)
        private String name;

        public Account(long accountNumber, long balance, String name) {
            this.accountNumber = accountNumber;
            this.balance = balance;
            this.name = name;
        }
    }

  
    static class AnnotationValidator {

        public static String[] validate(Object obj) {

            List<String> errors = new ArrayList<>();

            // Get all fields
            Field[] fields = obj.getClass().getDeclaredFields();

            for (Field field : fields) {

                try {
                    
                    field.setAccessible(true);

                   
                    Object value = field.get(obj);

                    
                    if (field.isAnnotationPresent(Positive.class)) {

                        Positive annotation =
                                field.getAnnotation(Positive.class);

                        if (value instanceof Number) {

                            long number =
                                    ((Number) value).longValue();

                            if (number <= 0) {
                                errors.add(
                                    field.getName()
                                    + ": "
                                    + annotation.message()
                                );
                            }
                        }
                    }

                    
                    if (field.isAnnotationPresent(MaxLength.class)
                            && value != null) {

                        MaxLength annotation =
                                field.getAnnotation(MaxLength.class);

                        int maxLength = annotation.value();

                        if (value.toString().length() > maxLength) {

                            errors.add(
                                field.getName()
                                + ": maximum length is "
                                + maxLength
                            );
                        }
                    }

                } catch (IllegalAccessException e) {

                    errors.add(
                        field.getName()
                        + ": cannot access field"
                    );
                }
            }

            return errors.toArray(new String[0]);
        }
    }

   
    public static void main(String[] args) {

        
        Account account =
                new Account(101, -500, "VeryLongAccountName");

       
        String[] errors =
                AnnotationValidator.validate(account);

        if (errors.length == 0) {

            System.out.println("Account is valid!");

        } else {

            System.out.println("Validation Errors:");

            for (String error : errors) {
                System.out.println("- " + error);
            }
        }
    }
}