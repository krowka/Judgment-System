import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Parser {
    private FileReader file;
    private JSONParser parser;
    private List<Judge> judges;
    private List<Regulation> regulations;
    private List<Year> years;
    private List<CourtType> courtTypes;

    public Parser(String source, List<Judge> judges, List<Regulation> regulations, List<Year> years,
                  List<CourtType> courtTypes) throws FileNotFoundException {
        this.file = new FileReader(source);
        this.parser = new JSONParser();
        this.judges = judges;
        this.regulations = regulations;
        this.years = years;
        this.courtTypes = courtTypes;
    }

    public List<Judgment> parse() throws ParseException, IOException {
        JSONObject JSONFile = (JSONObject) this.parser.parse(this.file);
        JSONArray JSONArrayJudgments = (JSONArray) JSONFile.get("items");
        List<Judgment> judgmentList = new ArrayList<>();
        for (Object jo : JSONArrayJudgments) { // jo = JSONObject
            judgmentList.add(toJudgment((JSONObject) jo));
        }
        return judgmentList;
    }

    private Judgment toJudgment(JSONObject jsonObject) {
        CourtType courtType = toCourtType(jsonObject);
        String caseNumber = (String) ((JSONObject) ((JSONArray) jsonObject.get("courtCases")).get(0)).get("caseNumber");
        Map<Judge, List<SpecialRoles>> judgesMap = toJudgeMap((JSONArray) jsonObject.get("judges"));
        String textContent = (String) jsonObject.get("textContent");
        List<Regulation> referencedRegulationList = toReferencedRegulationsList((JSONArray) jsonObject.get("referencedRegulations"));
        String judgmentDate = toJudgmentDate(jsonObject);
        Judgment judgment = new Judgment(courtType, caseNumber, judgesMap, textContent, referencedRegulationList, judgmentDate);
        List<Judge> judgeList = new ArrayList<>(judgesMap.keySet());
        addJudgmentToJudges(judgeList, judgment);
        return judgment;
    }

    private CourtType toCourtType(JSONObject jsonObject) {
        CourtType courtType = CourtType.valueOf((String) jsonObject.get("courtType"));
        if (courtTypes.contains(courtType)) {
            courtTypes.get(courtTypes.indexOf(courtType)).increment();
        } else {
            courtTypes.add(courtType);
        }
        return courtType;
    }

    private String toJudgmentDate(JSONObject jsonObject) {
        String judgmentDate = (String) jsonObject.get("judgmentDate");
        String[] date = judgmentDate.split("-");
        int m = Integer.parseInt(date[1]);
        int y = Integer.parseInt(date[0]);
        years.get(0).addJudgment(m);
        if (years.contains(new Year(y)))
            years.get(years.indexOf(new Year(y))).addJudgment(m);
        else {
            Year newYear = new Year(y);
            newYear.addJudgment(m);
            years.add(newYear);
        }
        return judgmentDate;
    }

    private Map<Judge, List<SpecialRoles>> toJudgeMap(JSONArray JSONJudges) {
        Map<Judge, List<SpecialRoles>> judgesMap = new HashMap<>();
        for (Object judge : JSONJudges) {
            String judgeName = (String) ((JSONObject) judge).get("name");
            List<SpecialRoles> sr = toSpecialRoles((JSONArray) ((JSONObject) judge).get("specialRoles"));
            Judge j = new Judge(judgeName);
            judgesMap.put(j, sr);
        }
        return judgesMap;
    }

    private List<Regulation> toReferencedRegulationsList(JSONArray referencedRegulations) {
        List<Regulation> referencedRegulationsList = new ArrayList<>();
        for (Object referencedRegulation : referencedRegulations) {
            JSONObject refReg = (JSONObject) referencedRegulation;
            Long journalNo = (Long) refReg.get("journalNo");
            Long journalYear = (Long) refReg.get("journalYear");
            Long journalEntry = (Long) refReg.get("journalEntry");
            Regulation regulation = new Regulation(journalNo, journalYear, journalEntry);
            if (!regulations.contains(regulation))
                regulations.add(regulation);
            else {
                regulations.get(regulations.indexOf(regulation)).increment();
            }
            referencedRegulationsList.add(regulation);
        }
        return referencedRegulationsList;
    }

    private List<SpecialRoles> toSpecialRoles(JSONArray jsonArraySpecialRoles) {
        List<SpecialRoles> specialRoles = new ArrayList<>();
        for (int i = 0; i < jsonArraySpecialRoles.size(); i++) {
            SpecialRoles role = SpecialRoles.valueOf((String) jsonArraySpecialRoles.get(i));
            specialRoles.add(role);
        }
        return specialRoles;
    }

    private void addJudgmentToJudges(List<Judge> judgesList, Judgment judgment) {
        for (Judge judge : judgesList) {
            if (judges.contains(judge)) {
                judges.get(judges.indexOf(judge)).increment();
                judges.get(judges.indexOf(judge)).addJudgment(judgment);
            } else {
                judge.addJudgment(judgment);
                judges.add(judge);
            }
        }
    }
}
