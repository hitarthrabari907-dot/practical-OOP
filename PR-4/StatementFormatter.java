public class StatementFormatter {
    private StatementFormatter() {
    }

    public static String buildStatement(Account account) {
        if (account == null) {
            throw new IllegalArgumentException("Account cannot be null.");
        }

        String lineSeparator = System.lineSeparator();
        StringBuilder statement = new StringBuilder("Account Statement");
        statement.append(lineSeparator).append("Account Number: ").append(account.getAccountNumber());
        statement.append(lineSeparator).append("Account Holder: ").append(account.getOwnerName());
        statement.append(lineSeparator).append("Balance: ").append(account.getBalance());
        return statement.toString();
    }
}