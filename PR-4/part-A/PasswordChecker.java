public class PasswordChecker {
	public static boolean hasMinimumLength(String password) {
		return password != null && password.length() >= 8;
	}

	public static boolean hasUppercase(String password) {
		return password != null && password.matches(".*[A-Z].*");
	}

	public static boolean hasDigit(String password) {
		return password != null && password.matches(".*[0-9].*");
	}

	public static boolean hasSpecialCharacter(String password) {
		return password != null && password.matches(".*[^A-Za-z0-9].*");
	}

	public static String strength(String password) {
		int passedRules = 0;
		passedRules += hasMinimumLength(password) ? 1 : 0;
		passedRules += hasUppercase(password) ? 1 : 0;
		passedRules += hasDigit(password) ? 1 : 0;
		passedRules += hasSpecialCharacter(password) ? 1 : 0;

		if (passedRules <= 1) {
			return "Weak";
		}
		if (passedRules <= 3) {
			return "Medium";
		}
		return "Strong";
	}
}
