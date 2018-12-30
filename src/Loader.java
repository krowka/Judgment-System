import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;


public class Loader {
    private final File folder;
    private List<Judgment> judgments;
    private List<Judge> judges;
    private List<Regulation> regulations;
    private List<Year> years;
    private List<CourtType> courtTypes;
    private String message = "";

    public Loader(String path) throws Exception {
        this.folder = new File(path);
        this.judgments = new ArrayList<>();
        this.judges = new ArrayList<>();
        this.regulations = new ArrayList<>();
        this.years = new ArrayList<>();
        this.years.add(new Year(0)); // year '0' stores all judgments
        this.courtTypes = new ArrayList<>();
        this.load();
    }

    public List<Judgment> getJudgments() {
        return this.judgments;
    }

    public List<Judge> getJudges() {
        return this.judges;
    }

    public List<Regulation> getRegulations() {
        return this.regulations;
    }

    public List<Year> getYears() {
        return this.years;
    }

    public List<CourtType> getCourtTypes() {
        return this.courtTypes;
    }

    public void listFilesForFolder(File folder) throws Exception {
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } else {
                if (fileEntry.getName().contains(".json")) {
                    Parser parser = new Parser(fileEntry.getPath(), judges, regulations, years, courtTypes);
                    judgments.addAll(parser.parse());
                    message = message.concat("POMYŚLNIE PRZETWORZONO: " + fileEntry.getName() + "\n");
                } else if (fileEntry.getName().contains(".html")) {
                    HTMLParser parserHTML = new HTMLParser(fileEntry, judges, regulations, years, courtTypes);
                    judgments.add(parserHTML.parse());
                    message = message.concat("POMYŚLNIE PRZETWORZONO: " + fileEntry.getName() + "\n");
                } else {
                    String extension = "";
                    int i = fileEntry.getName().lastIndexOf('.');
                    if (i > 0) {
                        extension = fileEntry.getName().substring(i + 1);
                    }
                    message = message.concat("Plik " + fileEntry.getName() + " nie został przetworzony. " +
                            "Nie obsługiwany typ pliku: ." + extension + "\n");
                }
            }
        }
    }

    public String getMessage() {
        return this.message;
    }

    private void load() throws Exception {
        if (!folder.exists()) {
            throw new FileNotFoundException("Nie istnieje folder o podanej scieżce");
        }
        listFilesForFolder(this.folder);
    }
}
