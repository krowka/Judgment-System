import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class HTMLParser {
    private Document document;
    private List<Judge> judges;
    private List<Regulation> regulations;
    private List<Year> years;
    private List<CourtType> courtTypes;

    public HTMLParser(File file, List<Judge> judges, List<Regulation> regulations, List<Year> years,
                      List<CourtType> courtTypes) throws IOException {
        this.document = Jsoup.parse(file, "UTF-8");
        this.judges = judges;
        this.regulations = regulations;
        this.years = years;
        this.courtTypes = courtTypes;
    }

    public Judgment parse() {
        String sygnature;
        CourtType courtType;
        Map<Judge, List<SpecialRoles>> judgesMap;
        String textContent;
        List<Regulation> regulations = new ArrayList<>();
        String judgmentDate;
        String[] tmp = document.getElementsByClass("war_header").text().split("-");
        sygnature = tmp[0];
        sygnature = sygnature.substring(0, sygnature.length()-1);
        Elements table = document.getElementsByClass("info-list pb-none");
        Elements rows = document.getElementsByClass("niezaznaczona");
        Elements cells = table.select("td");
        judgmentDate = cells.get(3).text(); // cells 3 - data orzeczenia
        addYear(judgmentDate);
        courtType = addCourt(cells.get(10).text()); // cells 10 - rodzaj sadu
        judgesMap = createJudgeMap(cells.get(13).toString()); // cell 13 sedziowie;
        for (Element e : rows) {
            if (e.text().contains("Powołane przepisy")) {
                Elements p = e.getElementsByClass("info-list-value").select("a");
                regulations = addRegulations(p.text());
                //regulations.addAll(addRegulations(p.text()));
            }
        }
        if (rows.get(rows.size() - 1).text().contains("Uzasadnienie")) {
            Element u = rows.get(rows.size() - 1);
            textContent = u.text();
        } else {
            Element s = rows.get(rows.size() - 1);
            textContent = "UWAGA!\nNie znaleziono uzasadnienia dla podanego orzeczenia dlatego za uzasadnienie " +
                    "przyjęto sentecję.\n\n" + s.text();
        }

        Judgment judgment = new Judgment(courtType, sygnature, judgesMap, textContent, regulations, judgmentDate);
        List<Judge> judgeList = new ArrayList<>(judgesMap.keySet());
        addJudgmentToJudges(judgeList, judgment);
        return judgment;
    }

    private void addYear(String judgmentDate) {
        String[] date = judgmentDate.split("-");
        int y = Integer.parseInt(date[0]);
        int m = Integer.parseInt(date[1]);
        this.years.get(0).addJudgment(m);
        if (this.years.contains(new Year(y)))
            this.years.get(this.years.indexOf(new Year(y))).addJudgment(m);
        else {
            Year newYear = new Year(y);
            newYear.addJudgment(m);
            this.years.add(newYear);
        }
    }

    private CourtType addCourt(String courtTypeString) {
        CourtType courtType;
        if (courtTypeString.contains("Naczelny"))
            courtType = CourtType.SUPREME_ADMINISTRATIVE;
        else
            courtType = CourtType.PROVINCIAL_ADMINISTRATIVE;

        if (courtTypes.contains(courtType)) {
            courtTypes.get(courtTypes.indexOf(courtType)).increment();
        } else {
            courtTypes.add(courtType);
        }
        return courtType;
    }


    private void addToMap(String string, Map<Judge, List<SpecialRoles>> judgesMap){
        String judge =  string.replaceAll("<.*?> ", "");
        judge =  judge.replaceAll(" <.*?>", "");
        List<SpecialRoles> sr = new ArrayList<>();
        Judge j;
        if(judge.contains("/")) {
            String[] tmp = judge.split("/");
            tmp[0] = tmp[0].substring(0, tmp[0].length() - 1); // removing space at the last character
            if (tmp[1].contains("przewodniczący"))
                sr.add(SpecialRoles.PRESIDING_JUDGE);
            if (tmp[1].contains("sprawozdawca"))
                sr.add(SpecialRoles.REPORTING_JUDGE);
            j = new Judge(tmp[0]);
        }
        else
            j = new Judge(judge);

        judgesMap.put(j, sr);
    }

    private Map<Judge, List<SpecialRoles>> createJudgeMap(String stringJudges){
        Map<Judge, List<SpecialRoles>> judgesMap = new HashMap<>();
        if(stringJudges.contains("<br>")){
            String[] judges = stringJudges.split("<br>"); // spliting into array of judges and their special roles
            for(String judge : judges)
                addToMap(judge, judgesMap);
        }
        else{
            addToMap(stringJudges,judgesMap);
        }
        return judgesMap;
    }

    private List<Regulation> addRegulations(String regulationsString){
        List<Regulation> regulationList = new ArrayList<>();
        String[] regs = regulationsString.split("Dz.U.");
        regs = Arrays.copyOfRange(regs, 1, regs.length);
        for(String s : regs){
            s = s.replaceAll("[^0-9]+", " ");
            String[] tmp = s.split(" ");
            Regulation r = new Regulation(Long.parseLong(tmp[2]), Long.parseLong(tmp[1]), Long.parseLong(tmp[3]));
            if(!this.regulations.contains(r))
                this.regulations.add(r);
            else{
                this.regulations.get(this.regulations.indexOf(r)).increment();
            }
            regulationList.add(r);
        }
        return regulationList;
    }

    private void addJudgmentToJudges(List<Judge> judgesList, Judgment judgment) {
        for (Judge j : judgesList) {
            if (judges.contains(j)) {
                judges.get(judges.indexOf(j)).increment();
                judges.get(judges.indexOf(j)).addJudgment(judgment);
            } else{
                j.addJudgment(judgment);
                judges.add(j);
            }
        }
    }
}
