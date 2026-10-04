import java.util.Scanner;

public class Driver {
    public static void main(String[] args) {
        String[] passwords = {"abc", "Abcd1234!", "password", "Hello123"};
        for (String password : passwords) {
            System.out.println("Password: " + password);
            System.out.println("Length >= 8: " + PasswordChecker.hasMinimumLength(password));
            System.out.println("Contains uppercase: " + PasswordChecker.hasUppercase(password));
            System.out.println("Contains digit: " + PasswordChecker.hasDigit(password));
            System.out.println("Contains special character: "
                    + PasswordChecker.hasSpecialCharacter(password));
            System.out.println("Strength: " + PasswordChecker.strength(password));
            System.out.println();
        }

        String[] logLines = {
            "10:05 alice Hello there",
            "10:06 bob I have a question",
            "malformed line",
            "10:07 carol Goodbye"
        };

        Scanner scanner = new Scanner(System.in);
        System.out.print("Keyword: ");
        String keyword = scanner.nextLine();

        StringBuilder report = new StringBuilder();
        int matches = ChatFilter.appendMatches(logLines, keyword, report);
        System.out.println("Matches: " + matches);
        System.out.println(report);
    }
}