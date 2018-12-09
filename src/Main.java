import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws Exception {
        Parser parser = new Parser("judgments.json");
        List<Judgment> judgments = parser.parse();
        for(Judgment judgment : judgments){
            System.out.println(judgment.rubrum());
        }
        Map<String, Judgment> judgmentHashMap = new HashMap<>();
        for(Judgment judgment : judgments){
            judgmentHashMap.put(judgment.getCaseNumber(), judgment);
        }
        String syg = "II K 336/17";
        String syg1 = "U 1/86";
        System.out.println(judgmentHashMap.get(syg).rubrum());
        //System.out.println(judgmentHashMap.get(syg).reason());
        System.out.println(judgmentHashMap.get(syg1).rubrum());
        //System.out.println(judgmentHashMap.get(syg1).reason());
    }
}
