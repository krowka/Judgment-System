import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Parser {
    private FileReader file;
    private JSONParser parser;

    public Parser (String source) throws FileNotFoundException {
        this.file = new FileReader(source);
        this.parser = new JSONParser();
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

    private Judgment toJudgment(JSONObject jsonObject){
        Long id = (Long) jsonObject.get("id");
        CourtType courtType = CourtType.valueOf((String) jsonObject.get("courtType"));
        String caseNumber = (String) ((JSONObject) ((JSONArray) jsonObject.get("courtCases")).get(0)).get("caseNumber");
        //List<String> caseNumberList = toCaseNumberList((JSONArray) jsonObject.get("courtCases"));
        List<Judge> judgeList = toJudgeList((JSONArray) jsonObject.get("judges"));
        String textContent = (String) jsonObject.get("textContent");
        List<Regulation> referencedRegulationList = toReferencedRegulationsList((JSONArray) jsonObject.get("referencedRegulations"));
        String judgmentDate = (String) jsonObject.get("judgmentDate");
        return new Judgment(id, courtType, caseNumber, judgeList, textContent, referencedRegulationList,judgmentDate);
    }

    private List<String> toCaseNumberList(JSONArray courtCases){
        List<String> caseNumberList = new ArrayList<>();
        for (Object courtCase : courtCases) {
            String caseNumber = (String) ((JSONObject) courtCase).get("caseNumber");
            caseNumberList.add(caseNumber);
        }
        return  caseNumberList;
    }

    private List<Judge> toJudgeList(JSONArray judges){
        List<Judge> judgeList = new ArrayList<>();
        for (Object judge : judges) {
            String judgeName = (String) ((JSONObject) judge).get("name");
            List<SpecialRoles> sr = toSpecialRoles((JSONArray) ((JSONObject) judge).get("specialRoles"));
            judgeList.add(new Judge(judgeName, sr));
        }
        return judgeList;
    }

    private List<Regulation> toReferencedRegulationsList(JSONArray referencedRegulations){
        List<Regulation> referencedRegulationsList = new ArrayList<>();
        for(Object referencedRegulation : referencedRegulations){
            JSONObject refReg = (JSONObject) referencedRegulation;
            String journalTitle = (String) refReg.get("journalTitle");
            Long journalNo = (Long) refReg.get("journalNo");
            Long journalYear = (Long) refReg.get("journalYear");
            Long journalEntry = (Long) refReg.get("journalEntry");
            String text = (String) refReg.get("text");
            Regulation regulation = new Regulation(journalTitle, journalNo, journalYear, journalEntry, text);
            referencedRegulationsList.add(regulation);
        }
        return referencedRegulationsList;
    }

    private List<SpecialRoles> toSpecialRoles(JSONArray jsonArraySpecialRoles){
        List<SpecialRoles> specialRoles = new ArrayList<>();
        for (int i = 0; i < jsonArraySpecialRoles.size(); i ++){
            SpecialRoles role = SpecialRoles.valueOf((String) jsonArraySpecialRoles.get(i));
            specialRoles.add(role);
        }
        return specialRoles;
    }
}
