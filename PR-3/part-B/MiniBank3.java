public class MiniBank3 {

    // Customer class
    static class Customer implements Cloneable {

        private String name;
        private String email;
        private String mobile;
        private final String customerId;

        private Address address;

        private static long customerCounter = 100;

        private static String generateCustomerId() {
            customerCounter++;
            return "CUST" + customerCounter;
        }

        public Customer(String name, String email, String mobile, Address address) {
            this.name = name;
            this.email = email;
            this.mobile = mobile;
            this.address = address;
            this.customerId = generateCustomerId();
        }

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }

        public String getMobile() {
            return mobile;
        }

        public String getCustomerId() {
            return customerId;
        }

        public Address getAddress() {
            return address;
        }

        // Nested Address class
        public static class Address {

            private String line;
            private String city;
            private String pincode;

            public Address(String line, String city, String pincode) {
                this.line = line;
                this.city = city;
                this.pincode = pincode;
            }

            public String getLine() {
                return line;
            }

            public String getCity() {
                return city;
            }

            public String getPincode() {
                return pincode;
            }
        }

        // Clone method
        @Override
        public Customer clone() {
            try {
                return (Customer) super.clone();
            } catch (CloneNotSupportedException e) {
                return null;
            }
        }
    }

    // Account class
    static class Account {

        private final String accountNumber;
        private String ownerName;
        private long balance;
        private boolean active;

        private static int accountCounter = 0;

        public Account(String ownerName, long openingBalance) {
            accountCounter++;
            this.accountNumber = "AC" + accountCounter;
            this.ownerName = ownerName;
            this.balance = openingBalance;
            this.active = true;
        }

        public Account(String ownerName) {
            this(ownerName, 0);
        }

        public void deposit(long amount) {
            balance += amount;
        }

        public boolean withdraw(long amount) {
            if (amount <= balance) {
                balance -= amount;
                return true;
            }
            return false;
        }

        public String getAccountNumber() {
            return accountNumber;
        }

        public String getOwnerName() {
            return ownerName;
        }

        public long getBalance() {
            return balance;
        }

        // toString method
        @Override
        public String toString() {
            return "Account Number: " + accountNumber
                    + ", Owner: " + ownerName
                    + ", Balance: ₹" + balance;
        }

        // equals method
        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }

            if (!(o instanceof Account)) {
                return false;
            }

            Account other = (Account) o;

            return accountNumber.equals(other.accountNumber);
        }

        // hashCode method
        @Override
        public int hashCode() {
            return accountNumber.hashCode();
        }
    }

    // Main method
    public static void main(String[] args) {

        Account account1 = new Account("Ansh", 1000);
        Account account2 = new Account("Jeet", 2000);

        // Print using toString()
        System.out.println(account1);
        System.out.println(account2);

        // Compare accounts
        System.out.println("Are accounts equal? " + account1.equals(account2));

        // instanceof check
        Object obj = account1;

        if (obj instanceof Account) {
            System.out.println("Object is an Account");
        }

        // Address object
        Customer.Address address = new Customer.Address(
                "ABC Road",
                "Mahisagar",
                "360001"
        );

        // Customer object
        Customer customer = new Customer(
            "Rahul",
            "rahul@example.com",
            "9876543210",
            address
        );

        // Clone customer
        Customer copiedCustomer = customer.clone();

        System.out.println("Customer: " + copiedCustomer.getName());

        System.out.println("City: " + copiedCustomer.getAddress().getCity());
    }
} 
