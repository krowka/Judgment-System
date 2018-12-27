import java.util.Objects;

public class Regulation {
    private Long journalNo;
    private Long journalYear;
    private Long journalEntry;
    private int number;

    public Regulation(Long journalNo, Long journalYear, Long journalEntry){
        this.journalNo = journalNo;
        this.journalYear = journalYear; // klucz
        this.journalEntry = journalEntry; //klucz
        this.number = 1;
    }

    public void increment(){
        this.number++;
    }

    public int getNumber(){ return this.number; }

    public String getID(){
        return journalYear + "/" + journalEntry + "/" + journalNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Regulation that = (Regulation) o;
        return this.journalYear.equals(that.journalYear) &&
                this.journalEntry.equals(that.journalEntry);
    }

    @Override
    public int hashCode() {
        return Objects.hash(journalYear, journalEntry);
    }
}
