import java.util.List;

public class Judgment {
    private Long id;
    private CourtType courtType;
    private String caseNumber;
    private List<Judge> judges;
    private String textContent;
    private List<Regulation> referencedRegulations;
    private String judgmentDate;

    public Judgment(Long id, CourtType courtType, String caseNumber, List<Judge> judges, String textContent,
                    List<Regulation> referencedRegulations, String judgmentDate){
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

    public String reason(){
        return textContent;
    }

    public String rubrum(){
        return "\nSYGNATURA: " + this.caseNumber + "\nDATA: " + this.judgmentDate + "\nRODZAJ SĄDU: " +
                this.courtType.toString() + "\nSĘDZIOWIE: " + this.judgesToString();
    }

    public String getCaseNumber() {
        return this.caseNumber;
    }

    private String judgesToString(){
        String s = "";
        StringBuilder result = new StringBuilder(s);
        for(Judge judge : this.judges){
                result.append("\n\t\t\t" + judge.toString());
        }
        return result.toString();
    }
}
