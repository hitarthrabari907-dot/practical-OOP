import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.util.*;

public class FormValidator {


    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @interface NotBlank {
    }

  
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @interface MaxLength {
        int value();
    }

    
    static class SignupForm {

        @NotBlank
        @MaxLength(20)
        String username;

        @NotBlank
        @MaxLength(50)
        String email;

        @NotBlank
        @MaxLength(10)
        String password;

        SignupForm(String username, String email, String password) {
            this.username = username;
            this.email = email;
            this.password = password;
        }
    }

    
    static List<String> validate(Object object) {

        List<String> errors = new ArrayList<>();

        Class<?> cls = object.getClass();

        for (Field field : cls.getDeclaredFields()) {

            field.setAccessible(true);

            try {
                Object value = field.get(object);

               
                if (field.isAnnotationPresent(NotBlank.class)) {

                    if (value == null ||
                        value.toString().trim().isEmpty()) {

                        errors.add(field.getName() + " must not be blank");
                    }
                }

                
                if (field.isAnnotationPresent(MaxLength.class)
                        && value != null) {

                    MaxLength annotation =
                            field.getAnnotation(MaxLength.class);

                    int max = annotation.value();

                    if (value.toString().length() > max) {

                        errors.add(
                            field.getName()
                            + " must have maximum "
                            + max
                            + " characters"
                        );
                    }
                }

            } catch (IllegalAccessException e) {
                errors.add("Cannot access " + field.getName());
            }
        }

        return errors;
    }

    public static void main(String[] args) {

      
        SignupForm form = new SignupForm(
            "",
            "verylongemailaddress@example.com",
            "12345678901"
        );

        List<String> errors = validate(form);

        if (errors.isEmpty()) {
            System.out.println("Form is valid!");
        } else {
            System.out.println("Validation Errors:");

            for (String error : errors) {
                System.out.println("- " + error);
            }
        }
    }
}