// Filename: GenericContainersDemo.java

// 1. Generic Box class
class Box<T> {
    private T item;

    public void set(T item) {
        this.item = item;
    }

    public T get() {
        return item;
    }
}

// 2. Generic Pair class with two type parameters (K and V)
class Pair<K, V> {
    private K key;
    private V value;

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() { return key; }
    public V getValue() { return value; }
}

public class GenericContainersDemo {
    
    // 3. Generic method to return the first element of any array
    public static <T> T getFirst(T[] array) {
        if (array == null || array.length == 0) {
            return null;
        }
        return array[0];
    }

    public static void main(String[] args) {
        System.out.println("=== 1. Testing Generic Box (Type Safety without Casts) ===");
        // Box for String
        Box<String> stringBox = new Box<>();
        stringBox.set("Java Generics Lab");
        String text = stringBox.get(); // No explicit cast needed!
        System.out.println("Box content (String): " + text);

        // Box for Integer
        Box<Integer> intBox = new Box<>();
        intBox.set(505);
        int number = intBox.get();
        System.out.println("Box content (Integer): " + number);

        System.out.println("\n=== 2. Testing Generic Pair ===");
        Pair<String, Integer> studentScore = new Pair<>("yug", 92);
        System.out.println("Pair -> Student: " + studentScore.getKey() + ", Score: " + studentScore.getValue());

        System.out.println("\n=== 3. Testing Generic Method (getFirst) ===");
        String[] names = {"Priya", "Rahul", "Amit", "Neha"};
        Integer[] scores = {85, 90, 78, 88};

        System.out.println("First name in array: " + getFirst(names));
        System.out.println("First score in array: " + getFirst(scores));
    }
}