import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        Parser parser = new Parser("judgments.json");
        List<Judgment> judgments = parser.parse();
        //Judgment j = judgments.get(0);
        System.out.println(judgments.toString());
    }
}
