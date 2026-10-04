public record Command(TransactionType type, String accountNumber, long amount) {
    public Command {
        if (type == null) {
            throw new IllegalArgumentException("Transaction type is required.");
        }
        if (accountNumber == null || accountNumber.isBlank()
                || accountNumber.chars().anyMatch(Character::isWhitespace)) {
            throw new IllegalArgumentException("A single account number is required.");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive.");
        }
    }
}