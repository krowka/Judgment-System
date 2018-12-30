import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Judge implements Comparable<Judge> {
    private String name;
    private List<Judgment> judgments;
    private int number;

    public Judge(String name) {
        this.name = name;
        this.number = 1;
        judgments = new ArrayList<>();
    }

    public void addJudgment(Judgment judgment) {
        this.judgments.add(judgment);
    }

    public String getName() {
        return this.name;
    }

    public String toString() {
        return this.name;
    }

    public StringBuilder casesToString() {
        StringBuilder sb = new StringBuilder("Całkowita liczba orzeczeń: " + this.judgments.size() + "\n");
        for (Judgment judgment : this.judgments) {
            sb.append("- " + judgment.getCaseNumber() + "\n");
        }
        return sb;
    }

    public void increment() {
        this.number = this.number + 1;
    }

    public int getJudgments() {
        return this.judgments.size();
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Judge judge = (Judge) o;
        return this.name.equals(judge.name);
    }

    public int getNumber() {
        return this.number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public int compareTo(Judge o) {
        //int comparision = (this.number < o.number) ? -1 : (this.number > o.number) ? +1 : 0;
        //int comparision = this.number - o.number;
        int comparision = Integer.compare(this.number, o.getNumber());
        if (comparision == 0) {
            return this.name.compareTo(o.getName());
        } else return comparision;

        //if(this.number == o.number)
        //return this.name.compareTo(o.name);
        //else
        //return this.number - o.number;
    }
}
