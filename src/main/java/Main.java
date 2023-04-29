import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static AtomicInteger count3 = new AtomicInteger();
    public static AtomicInteger count4 = new AtomicInteger();
    public static AtomicInteger count5 = new AtomicInteger();
    public static String[] texts = new String[100_000];

    public static void main(String[] args) throws InterruptedException {
        generateWord();
        List<Thread> list = List.of(
                new Thread(() -> {
                    for (String text : texts) {
                        if (isPalindrome(text)) {
                            increment(text.length());
                        }
                    }
                }),
                new Thread(() -> {
                    for (String text : texts) {
                        if (isAscendingOrder(text)) {
                            increment(text.length());
                        }
                    }
                }),
                new Thread(() -> {
                    for (String text : texts) {
                        if (isSameChar(text)) {
                            increment(text.length());
                        }
                    }
                }));
        for (Thread thread : list) {
            thread.start();
            thread.join();
        }
        System.out.println("Красивых слов с длиной 3: " + count3 + " шт");
        System.out.println("Красивых слов с длиной 4: " + count4 + " шт");
        System.out.println("Красивых слов с длиной 5: " + count5 + " шт");
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static boolean isPalindrome(String text) {
        return text.equalsIgnoreCase(new StringBuilder(text).reverse().toString());
    }

    public static boolean isAscendingOrder(String text) {
        for (int i = 1; i < text.length(); i++) {
            if (text.charAt(i) < text.charAt(i - 1))
                return false;
        }
        return true;
    }

    public static boolean isSameChar(String text) {
        for (int i = 1; i < text.length(); i++) {
            if (text.charAt(i) != text.charAt(i - 1))
                return false;
        }
        return true;
    }

    public static void increment(int num) {
        if (num == 3) {
            count3.addAndGet(1);
        }
        if (num == 4) {
            count4.addAndGet(1);
        }
        if (num == 5) {
            count5.addAndGet(1);
        }
    }

    private static void generateWord() {
        Random random = new Random();
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }
    }

}
