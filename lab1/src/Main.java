import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Lab 1, task 4");
        System.out.println("Enter a text:");
        String text = scanner.nextLine();
        System.out.println("Result is: " + Function7(text).toString());
        // test data = aaaaa a abc cba dddascxas opd hello why
    }

    private static Iterable<String> Function(String text) {
        var resultArr = new ArrayList<String>();
        for (String word : text.split(" ")) {
            if (word.length() == 1) {
                resultArr.add(word);
            } else {
                var wordCharsArr = word.toCharArray();
                boolean passed = true;
                for (int i = 1; i < word.length(); i++) {
                    if (wordCharsArr[i - 1] > wordCharsArr[i]) {
                        passed = false;
                        break;
                    }
                }
                if (passed) {
                    resultArr.add(word);
                }
            }
        }
        return resultArr;
    }

    private static Iterable<String> Function7(String text) {
        Predicate<String> filter = (x) -> {
            if (x.length() == 1) {
                return true;
            }

            var wordCharsArr = x.toCharArray();
            for (int i = 1; i < x.length(); i++) {
                if (wordCharsArr[i - 1] < wordCharsArr[i]) {
                     return  false;
                }
            }
            return true;
        };

        return Arrays.stream(text.split(" "))
                .filter(filter)
                .toList();
    }
}
