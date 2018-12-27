import java.io.FileNotFoundException;
import java.util.*;

public class JudgmentSystem {
    public final Loader loader;
    public final List<Judgment> judgments;
    public final List<Judge> judges;
    public final Map<String, Judgment> judgmentHashMap;
    public final List<Regulation> regulations;
    public final List<Year> years;
    public final List<CourtType> courtTypes;


    public JudgmentSystem(String path) throws Exception {
        this.loader = new Loader(path);
        this.judgments = loader.getJudgments();
        this.judges = loader.getJudges();
        this.regulations = loader.getRegulations();
        this.years = loader.getYears();
        this.courtTypes = loader.getCourtTypes();
        this.judgmentHashMap = new HashMap<>();
    }

    public void execute() throws FileNotFoundException {
        for (Judgment judgment : judgments)
            judgmentHashMap.put(judgment.getCaseNumber(), judgment);
        judges.sort(Comparator.comparing(Judge::getNumber));
        Collections.reverse(judges);
        regulations.sort(Comparator.comparing(Regulation::getNumber));
        Collections.reverse(regulations);
        Scanner scanner = new Scanner(System.in);
        Commands command = new Commands(this);
        //System.out.println(judgments.size());
        //System.out.println(judges.size());
        //System.out.println(regulations.size());
        //System.out.println(judgmentHashMap.size());
        System.out.println("Aby uzyskać pomoc wpisz 'help' ...");
        while (true) {
            try {
                command.run(scanner.nextLine());
            }catch (Exception ex){
                System.out.println(ex);
            }
        }

    }

}
