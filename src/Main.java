import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj sciężkę do folderu z plikami: ");
        String path = scanner.next();
        try {
            JudgmentSystem judgmentSystem = new JudgmentSystem(path);
            judgmentSystem.execute();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
