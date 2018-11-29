import java.util.Objects;

public class Judge {
    private String name;

    public Judge(String name){
        this.name = name;
    }

    /*public boolean equals(Judge judge){
        return this.name == judge.name;
    }*/

    public String toString(){
        return this.name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Judge judge = (Judge) o;
        return this.name.equals(judge.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
