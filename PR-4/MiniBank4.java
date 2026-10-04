public class MiniBank4 {
	public static void main(String[] args) {
		System.out.println("Mobile valid: " + Validator.isValidMobile("9876543210"));
		System.out.println("Mobile invalid: " + Validator.isValidMobile("12345"));
		System.out.println("Email valid: " + Validator.isValidEmail("user@example.com"));
		System.out.println("Email invalid: " + Validator.isValidEmail("user@.com"));
		System.out.println("PAN valid: " + Validator.isValidPan("ABCDE1234F"));
		System.out.println("PAN invalid: " + Validator.isValidPan("ABCD12345F"));
		System.out.println("IFSC valid: " + Validator.isValidIfsc("HDFC0001234"));
		System.out.println("IFSC invalid: " + Validator.isValidIfsc("HDFC1234567"));

		Command command = CommandParser.parse("DEPOSIT AC0001 500");
		System.out.println("Type: " + command.type());
		System.out.println("Account: " + command.accountNumber());
		System.out.println("Amount: " + command.amount());
	}
}
