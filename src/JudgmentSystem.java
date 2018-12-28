import java.util.*;
import java.io.Console;
import java.util.List;

public class JudgmentSystem{
    public Loader loader;
    public final List<Judgment> judgments;
    public final List<Judge> judges;
    public final Map<String, Judgment> judgmentHashMap;
    public final List<Regulation> regulations;
    public final List<Year> years;
    public final List<CourtType> courtTypes;


    public JudgmentSystem(String path) throws Exception {
        //scanner = new Scanner(System.in);
        this.loader = new Loader(path);
        this.judgments = loader.getJudgments();
        this.judges = loader.getJudges();
        this.regulations = loader.getRegulations();
        this.years = loader.getYears();
        this.courtTypes = loader.getCourtTypes();
        this.judgmentHashMap = new HashMap<>();
        execute();
    }

    public void execute() throws Exception {
        Console console = System.console();
        Scanner scanner = new Scanner(System.in);
        for (Judgment judgment : judgments)
            judgmentHashMap.put(judgment.getCaseNumber(), judgment);
        judges.sort(Comparator.comparing(Judge::getNumber));
        Collections.reverse(judges);
        regulations.sort(Comparator.comparing(Regulation::getNumber));
        Collections.reverse(regulations);
        Commands command = new Commands(this);
        //System.out.println(judgments.size());
        //System.out.println(judges.size());
        //System.out.println(regulations.size());
        //System.out.println(judgmentHashMap.size());
        System.out.println("Aby uzyskać pomoc wpisz 'help' ...");
        while (true) {
            try {
                command.run(scanner.nextLine());
                //command.run(console.readLine());
            }catch (Exception ex){
                System.out.println(ex);
            }
        }

    }

}
