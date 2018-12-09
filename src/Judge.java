import java.util.List;
import java.util.Objects;

public class Judge {
    private String name;
    private List<SpecialRoles> specialRoles;

    public Judge(String name, List<SpecialRoles> specialRoles){
        this.name = name;
        this.specialRoles = specialRoles;
        //this.role = role;
    }

    /*public boolean equals(Judge judge){
        return this.name == judge.name;
    }*/

    public String toString(){
        return this.name + " " + this.specialRolesToString();
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

    private String specialRolesToString(){
        if(this.specialRoles.size() == 0)
            return "";
        StringBuilder result = new StringBuilder(" (");
        int i = 0;
        for(SpecialRoles role : specialRoles){
            if(i++ == 0)
                result.append(role.toString());
            else
                result.append(", " + role.toString());
        }
        result.append(")");
        return  result.toString();
    }
}
