// Filename: BoundedStatsDemo.java

public class BoundedStatsDemo {

    // Bounded generic method: N sirf Number ya uske subclasses (Integer, Double, Long, etc.) ho sakta hai
    public static <N extends Number> double average(N[] values) {
        if (values == null || values.length == 0) {
            return 0.0;
        }

        double sum = 0.0;
        for (N val : values) {
            // doubleValue() method sabhi Number subclasses mein available hoti hai
            sum += val.doubleValue();
        }

        return sum / values.length;
    }

    public static void main(String[] args) {
        System.out.println("=== Testing Bounded Type Parameters (<N extends Number>) ===\n");

        // 1. Integer Array
        Integer[] intValues = {10, 20, 30, 40, 50};
        System.out.println("Integer Array Average: " + average(intValues));

        // 2. Double Array
        Double[] doubleValues = {12.5, 15.5, 20.0};
        System.out.println("Double Array Average: " + average(doubleValues));

        // 3. Long Array
        Long[] longValues = {100L, 200L, 300L, 400L};
        System.out.println("Long Array Average: " + average(longValues));

        /* 
         * WHY NON-NUMBER TYPES ARE REFUSED:
         * Agar aap yahan String[] ya koi doosra non-Number type pass karne ki koshish karoge:
         * 
         * String[] words = {"Hello", "World"};
         * System.out.println(average(words)); // <-- Compile-time Error!
         * 
         * Kyun refuse hota hai?
         * 1. '<N extends Number>' bound ki wajah se compiler ko pata hai ki yeh method sirf numbers ke liye hai.
         * 2. Non-number types (jaise String) ke paas 'doubleValue()' method nahi hoti, aur math calculations ke liye numbers zaroori hain.
         * 3. Yeh check compile-time par hi ho jata hai, jisse runtime errors (ClassCastException) se bachav hota hai.
         */
        System.out.println("\n[Note]: Non-Number types (like String[]) are refused at compile-time");
        System.out.println("because they do not extend 'Number' and lack mathematical conversion methods.");
    }
}