import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Judgment {
    private CourtType courtType;
    private String caseNumber;
    //private List<Judge> judges;
    private Map<Judge, List<SpecialRoles>> judges;
    private String textContent;
    private List<Regulation> referencedRegulations;
    private String judgmentDate;

    public Judgment(CourtType courtType, String caseNumber, Map<Judge, List<SpecialRoles>> judges,
                    String textContent, List<Regulation> referencedRegulations, String judgmentDate) {
        this.courtType = courtType;
        this.caseNumber = caseNumber;
        this.judges = judges;
        this.textContent = textContent.replaceAll("<br>", "\n"); // formatting
        this.textContent = this.textContent.replaceAll("<.*?>", ""); // html text
        this.referencedRegulations = referencedRegulations;
        this.judgmentDate = judgmentDate;
    }

    public String getDate() {
        return this.judgmentDate;
    }

    public String reason() {
        return this.textContent;
    }

    public String rubrum() {
        return "\nSYGNATURA: " + this.caseNumber + "\nDATA: " + this.judgmentDate + "\nRODZAJ SĄDU: " +
                this.courtType.toString() + "\nSKLAD SEDZIOWSKI: " + this.judgesToString();
    }

    public List<Judge> getJudges() {
        return new ArrayList<Judge>(this.judges.keySet());
    }

    public String getCaseNumber() {
        return this.caseNumber;
    }

    private String judgesToString() {
        StringBuilder result = new StringBuilder();
        for (Map.Entry judge : judges.entrySet()) {
            result.append("\n\t" + judge.getKey().toString() + "\t" + judge.getValue().toString());
        }
        return result.toString();

        /*Iterator iterator = judges.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry judge = (Map.Entry) iterator.next();
            result.append("\n\t\t\t" + judge.getKey().toString() + "\t" + judge.getValue().toString());
        }*/
    }
}
