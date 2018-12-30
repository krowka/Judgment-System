import java.io.FileNotFoundException;

public class Commands {
    private JudgmentSystem judgmentSystem;

    public Commands(JudgmentSystem judgmentSystem) throws FileNotFoundException {
        this.judgmentSystem = judgmentSystem;
    }

    public String run(String input) throws Exception {
        String command = input;
        String args = "";
        if (command.contains(" ")) { // checking if command contains optional arguments
            String[] line = input.split(" ", 2);
            command = line[0];
            args = line[1];
        }

        switch (command) {
            case "rubrum":
                return rubrum(args);
            case "content":
                return content(args);
            case "judge":
                return judge(args);
            case "judges":
                return judges();
            case "months":
                return months();
            case "courts":
                return courts();
            case "regulations":
                return regulations();
            case "help":
                return help();
            case "quit":
                ;
                return "quit";
            default:
                String result = "Nie rozpoznano komendy: " + command + ".\nWpisz 'help' w celu uzyskania pomocy...";
                return result;
        }
    }

    private String rubrum(String args) {
        String[] arguments = args.split(", ");
        String result = "";
        for (String caseNumber : arguments) {
            if (this.judgmentSystem.judgmentHashMap.containsKey(caseNumber)) {
                result = result.concat(this.judgmentSystem.judgmentHashMap.get(caseNumber).rubrum() + "\n");
            } else {
                result = result.concat("Nie znaleziono orzeczenia o sygnaturze: " + caseNumber + "\n");
            }
        }
        return result;
    }

    private String content(String args) throws Exception {
        String[] arguments = args.split(", ");
        String result = "";
        for (String caseNumber : arguments) {
            if (this.judgmentSystem.judgmentHashMap.containsKey(caseNumber)) {
                result = result.concat(this.judgmentSystem.judgmentHashMap.get(caseNumber).reason() + "\n");
            } else {
                result = result.concat("Nie znaleziono orzeczenia o określonej sygnaturze" + caseNumber);
            }
        }
        return result;
    }

    private String judge(String args) {
        String[] judgesNames = args.split(", ");
        String result = "";
        for (String judgeName : judgesNames) {
            Judge j = new Judge(judgeName);
            if (this.judgmentSystem.judges.contains(j)) {
                result = result.concat(judgeName.toUpperCase() + "\n" + this.judgmentSystem.judges.get(this.judgmentSystem.judges.indexOf(j)).casesToString() + "\n");
            } else {
                result = result.concat("Nie znaleziono sędziego: " + judgeName + "\n");
            }
        }
        return result;
    }

    private String judges() {
        int i;
        String result = "";
        for (i = 0; i < 10; i++) {
            Judge j = this.judgmentSystem.judges.get(i);
            result = result.concat(j.getName() + " " + j.getNumber() + "\n");

        }

        while (this.judgmentSystem.judges.get(i).getNumber() == this.judgmentSystem.judges.get(i - 1).getNumber()) {
            Judge j = this.judgmentSystem.judges.get(i++);
            result = result.concat(j.getName() + " " + j.getNumber() + " (ex aequo)\n");
        }
        return result;
    }

    private String months() {
        Year y = this.judgmentSystem.years.get(0);
        StringBuilder result = y.getMonths();
        return result.toString();
    }

    private String courts() {
        String result = "";
        for (CourtType courtType : this.judgmentSystem.courtTypes) {
            result = result.concat(courtType.toString() + ": " + courtType.getNumber() + "\n");
        }
        return result;
    }

    private String regulations() {
        int i = 0;
        String result = "";
        for (i = 0; i < 10; i++) {
            Regulation r = this.judgmentSystem.regulations.get(i);
            result = result.concat(r.getID() + " - " + r.getNumber() + "\n");
        }

        while (this.judgmentSystem.regulations.get(i).getNumber() == this.judgmentSystem.regulations.get(i - 1).getNumber()) { // case when two or more judges tie for last place in the limited results output
            Regulation r = this.judgmentSystem.regulations.get(i++);
            result = result.concat(r.getID() + " - " + r.getNumber() + " (ex aequo)\n");
        }
        return result;
    }

    private String help() {
        String result = "SPIS DOSTEPNYCH KOMEND:\n" +
                "rubrum - wyświetlenie metryki jednego lub wielu orzeczeń, na " + "podstawie sygnatury (kolejne sygnatury muszą być odzielone przecinkami)\n" +
                "content - wyświetlenie uzasadnienia\n" + "judge - wyświetlenie orzeczeń dla wybranego sędziego\n" +
                "judges - wyświetlenie 10 sędziów, którzy wydali najwięcej orzeczeń, wraz z liczbą orzeczeń\n" +
                "months - wyświetlenie liczby wydanych orzeczeń w poszczególnych miesiącach\n" +
                "courts - wyświetlenie liczby orzeczeń ze względu na typ sądu\n" +
                "regulations - wyświetlenie 10 najczęściej przywoływanych ustaw\n" +
                "jury - wyświetlenie liczby spraw przypadających na określony skład sędziowski (określoną liczbę sędziów)\n" +
                "quit - wyjście z programu\n";
        return result;

    }
}
