import java.io.Console;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Console console = System.console();
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Podaj scieżkę folderu z plikami: ");
            //JudgmentSystem judgmentSystem = new JudgmentSystem(scanner.next());
            //JudgmentSystem judgmentSystem = new JudgmentSystem(console.readLine());
            JudgmentSystem judgmentSystem = new JudgmentSystem(scanner.nextLine());
            judgmentSystem.execute();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
