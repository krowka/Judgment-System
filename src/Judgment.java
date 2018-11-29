import java.util.List;

public class Judgment {
    private Long id;
    private String courtType;
    private List<String> caseNumber;
    private List<Judge> judges;
    private String textContent;
    private List<Regulation> referencedRegulations;
    private String judgmentDate;

    public Judgment(Long id, String courtType, List<String> caseNumber, List<Judge> judges, String textContent, List<Regulation> referencedRegulations, String judgmentDate){
        this.id = id;
        this.courtType = courtType;
        this.caseNumber = caseNumber;
        this.judges = judges;
        this.textContent = textContent;
        this.referencedRegulations = referencedRegulations;
        this.judgmentDate = judgmentDate;
    }

    public String toString(){
        return "\n\nID: " + id + "\nSYGNATURA: " + caseNumber.toString() + "\nSĘDZIOWIE: " + judges.toString()
                + "\nREGULACJE: " + referencedRegulations.toString() + "\nRODZAJ SĄDU:" + courtType
                + "\nTEKST: " + textContent + "\nDATA: " + judgmentDate;
    }
}
