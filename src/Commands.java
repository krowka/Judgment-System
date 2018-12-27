import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;

public class Commands{
    private JudgmentSystem judgmentSystem;
    private PrintWriter printWriter;

    public Commands(JudgmentSystem judgmentSystem) throws FileNotFoundException {
        this.judgmentSystem = judgmentSystem;
        this.printWriter = new PrintWriter("history.txt");
    }

    public void run(String input) throws Exception{
        printWriter.println(input);
        String command = input;
        String args = "";
        if(command.contains(" ")) { // checking if command contains optional arguments
            String[] line = input.split(" ", 2);
            command = line[0];
            args = line[1];
        }

        switch(command){
            case "rubrum" :
                rubrum(args);
                break;
            case "content" :
                content(args);
                break;
            case "judge" :
                judge(args);
                break;
            case "judges" :
                judges();
                break;
            case "months" :
                months();
                break;
            case "courts" :
                courts();
                break;
            case "regulations" :
                regulations();
                break;
            case "help" :
                help();
                break;
            case "quit":
                printWriter.close();
                System.exit(0);
                default:
                    String s = "Nie rozpoznano komendy: " + command + ".\nWpisz 'help' w celu uzyskania pomocy...";
                    printWriter.println(s);
                    System.out.println(s);
                    //System.exit(1);
        }
    }

    private void rubrum(String args) throws Exception {
        String[] arguments = formatToCaseNumber(args);
        String s;
        for(String caseNumber : arguments) {
            if(this.judgmentSystem.judgmentHashMap.containsKey(caseNumber)){
                s = this.judgmentSystem.judgmentHashMap.get(caseNumber).rubrum();
                printWriter.println(s);
                System.out.println(s);
            }
            else {
                s = "Nie znaleziono orzeczenia o sygnaturze: " + caseNumber;
                printWriter.println(s);
                System.out.println(s);
            }
        }
    }

    private void content(String args) throws Exception {
        String[] arguments = formatToCaseNumber(args);
        String s;
        for(String caseNumber : arguments){
            if(this.judgmentSystem.judgmentHashMap.containsKey(caseNumber)){
                s = this.judgmentSystem.judgmentHashMap.get(caseNumber).reason();
                printWriter.println(s);
                System.out.println(s);
            }
            else {
                s = "Nie znaleziono orzeczenia o określonej sygnaturze" + caseNumber;
                printWriter.println(s);
                System.out.println(s);
            }
        }
    }

    private void judge(String args){
        String[] judgesNames = formatToJudgesNames(args);
        String s;
        for(String judgeName : judgesNames) {
            Judge j = new Judge(judgeName);
            if (this.judgmentSystem.judges.contains(j)) {
                s = judgeName.toUpperCase() + "\n" + this.judgmentSystem.judges.get(this.judgmentSystem.judges.indexOf(j)).casesToString();
                printWriter.println(s);
                System.out.println(s);
                }
            else {
                s = "Nie znaleziono sędziego: " + judgeName;
                printWriter.println(s);
                System.out.println(s);
            }
        }
    }

    private void judges(){
        int i = 0;
        String s;
        for(i = 0; i < 10; i++){
            Judge j = this.judgmentSystem.judges.get(i);
            s = j.getName() + " " + j.getNumber();
            printWriter.println(s);
            System.out.println(s);
        }

        while(this.judgmentSystem.judges.get(i).getNumber() == this.judgmentSystem.judges.get(i-1).getNumber()){
            Judge j = this.judgmentSystem.judges.get(i++);
            s = j.getName() + " " + j.getNumber() + " (ex aequo)";
            printWriter.println(s);
            System.out.println(s);
        }

    }

    private void months(){
        Year y = this.judgmentSystem.years.get(0);
        StringBuilder s = y.getMonths();
        printWriter.println(s);
        System.out.println(s);
    }

    private void courts(){
        String s;
        for(CourtType courtType : this.judgmentSystem.courtTypes){
            s = courtType.toString() + ": " + courtType.getNumber();
            printWriter.println(s);
            System.out.println(s);
        }
    }

    private void regulations(){
        int i = 0;
        String s;
        for(i = 0; i < 10; i++){
            Regulation r = this.judgmentSystem.regulations.get(i);
            s = r.getID() + " - " + r.getNumber();
            printWriter.println(s);
            System.out.println(s);
        }

        while(this.judgmentSystem.regulations.get(i).getNumber() == this.judgmentSystem.regulations.get(i-1).getNumber()){ // case when two or more judges tie for last place in the limited results output
            Regulation r = this.judgmentSystem.regulations.get(i++);
            s = r.getID() + " - " + r.getNumber() + " (ex aequo)";
            printWriter.println(s);
            System.out.println(s);
        }
    }

    private void help(){
        String s = "SPIS DOSTĘPNYCH KOMEND:\n" +
                "rubrum - wyświetlenie metryki jednego lub wielu orzeczeń, na " + "podstawie sygnatury (kolejne sygnatury muszą być odzielone przecinkami)\n" +
                "content - wyświetlenie uzasadnienia\n" + "judge - wyświetlenie orzeczeń dla wybranego sędziego\n" +
                "judges - wyświetlenie 10 sędziów, którzy wydali najwięcej orzeczeń, wraz z liczbą orzeczeń\n" +
                "months - wyświetlenie liczby wydanych orzeczeń w poszczególnych miesiącach\n" +
                "courts - wyświetlenie liczby orzeczeń ze względu na typ sądu\n" +
                "regulations - wyświetlenie 10 najczęściej przywoływanych ustaw\n" +
                "jury - wyświetlenie liczby spraw przypadających na określony skład sędziowski (określoną liczbę sędziów)\n" +
                "quit - wyjście z programu\n";
        printWriter.println(s);
        System.out.println(s);

    }

    private String[] formatToCaseNumber(String src) throws Exception {
        String[] args = src.split(", ");
        return args;
    }

    private String[] formatToJudgesNames(String src){
        return src.split(", ");
    }
}
