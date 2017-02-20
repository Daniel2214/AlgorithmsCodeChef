import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by chris on 18/02/2017.
 */
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int testNumber = Integer.parseInt(scanner.nextLine());
        List<String> tests = new ArrayList<>();
        for (int i = 0; i < testNumber; i++) {
            tests.add(scanner.nextLine());
        }
        for (String test: tests) {
            System.out.println(findPunished(test));
        }
    }

    public static int findPunished(String students) {
        int punished = 0;
        for (int i = 0; i < students.length(); i++) {
            if(students.charAt(i) == '<' && i != students.length() - 1 && students.charAt(i + 1) == '>') {
                punished++;
                i++;
            }
        }
        return punished;
    }
}