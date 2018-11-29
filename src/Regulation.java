public class Regulation {
    private String journalTitle;
    private Long journalNo;
    private Long journalYear;
    private Long journalEntry;
    private String text;

    public Regulation(String journalTitle, Long journalNo, Long journalYear, Long journalEntry, String text){
        this.journalTitle = journalTitle;
        this.journalNo = journalNo;
        this.journalYear = journalYear;
        this.journalEntry = journalEntry;
        this.text = text;
    }

    public String toString(){
        return "\nTITLE: " + journalTitle + "\nNO: " + journalNo + "\nYEAR: " + journalYear + "\nENTRY: "
                + journalEntry + "\nTEXT: " + text;
    }
}
