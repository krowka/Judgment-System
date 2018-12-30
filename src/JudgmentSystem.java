import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.PrintWriter;
import java.util.*;
//import java.io.Console;
import java.util.List;

public class JudgmentSystem extends JFrame implements KeyListener {
    private JPanel jPanel;
    private JLabel jLabel;
    private JTextField textField;
    private JTextArea textArea;
    private JScrollPane scrollPane;
    private Loader loader;
    private boolean keepHistory; // if true program will save history log into given file
    private PrintWriter printWriter;
    private String loadMessage;
    private Commands command;
    private List<String> historyLog;
    public final List<Judgment> judgments;
    public final List<Judge> judges;
    public final Map<String, Judgment> judgmentHashMap;
    public final List<Regulation> regulations;
    public final List<Year> years;
    public final List<CourtType> courtTypes;
    private int index = 0; // index of current position while iterating history log
    //private ListIterator listIterator;

    public JudgmentSystem(String path) throws Exception {
        this.loader = new Loader(path);
        this.command = new Commands(this);
        this.judgments = loader.getJudgments();
        this.judges = loader.getJudges();
        this.regulations = loader.getRegulations();
        this.years = loader.getYears();
        this.courtTypes = loader.getCourtTypes();
        this.judgmentHashMap = new HashMap<>();
        init(); // initalizng GUI
    }

    @Override
    public void keyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_UP && index > 0) {
            textField.setText(historyLog.get(--index));

            /*if(listIterator.hasPrevious())
                textField.setText(listIterator.previous().toString());*/
        }

        if (evt.getKeyCode() == KeyEvent.VK_DOWN && index < historyLog.size() - 1) {
            textField.setText(historyLog.get(++index));

            /*if(listIterator.hasNext())
                textField.setText(listIterator.next().toString());*/
        }
        try {
            if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                String input = textField.getText();
                textField.setText(""); // clearing text field after entering command
                historyLog.add(input);
                index = historyLog.size();
                //this.listIterator = historyLog.listIterator(historyLog.size());
                String result = command.run(input);
                textArea.append(result + "\n");
                if (result.equals("quit")) {
                    if (keepHistory)
                        printWriter.close(); // saving our history log to file before leaving
                    System.exit(0);
                }
                if (keepHistory) {
                    result = result.replaceAll("\n", System.lineSeparator()); // print writer does not support special characters like 'n'
                    printWriter.println(input + System.lineSeparator() + result);
                }
            }
        } catch (Exception e) {
            textArea.append(e.toString() + "\n");
        }
    }

    // Not Used, but need to provide an empty body for compilation
    @Override
    public void keyTyped(KeyEvent evt) {
    }

    @Override
    public void keyReleased(KeyEvent evt) {
    }

    public void execute() {
        //Console console = System.console();
        for (Judgment judgment : judgments)
            judgmentHashMap.put(judgment.getCaseNumber(), judgment); // mapping sygnature onto judment
        judges.sort(Comparator.comparing(Judge::getNumber)); //sorting...
        Collections.reverse(judges);//... in descending order
        regulations.sort(Comparator.comparing(Regulation::getNumber));
        Collections.reverse(regulations);

        /*System.out.println("Aby uzyskać pomoc wpisz 'help' ...");
        while (true) {
            try {
                command.run(scanner.nextLine());
                //command.run(console.readLine());
            }catch (Exception ex){
                System.out.println(ex);
            }
        }*/
    }

    public void setHistoryPath(String historyPath) {
        try {
            this.printWriter = new PrintWriter(historyPath);
            this.keepHistory = true;
        } catch (Exception ex) {
            textArea.append("Problem z zapisem do wskazanego pliku\n");
        }
    }

    public String getLoadMessage() {
        return this.loadMessage + "\nLICZBA PRZETWORZONYCH PLIKÓW: " + this.judgments.size() + "\n";
    }

    private void init() {
        this.loadMessage = loader.getMessage();
        this.historyLog = new ArrayList<>();
        this.keepHistory = false;
        this.jPanel = new JPanel();
        this.jLabel = new JLabel("Wpisz komendę: ");
        jPanel.add(jLabel);
        this.textField = new JTextField();
        textField.setPreferredSize(new Dimension(745, 25));
        textField.addKeyListener(this);
        jPanel.add(textField);
        this.textArea = new JTextArea();
        jPanel.add(textArea);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        this.scrollPane = new JScrollPane(textArea);
        this.scrollPane.setPreferredSize(new Dimension(950, 520));
        this.scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        jPanel.add(scrollPane);
        add(jPanel);
        pack();
        setSize(new Dimension(1000, 600));
        setTitle("JUDGMENT 2.0");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() { // save history to file before closing, while using exit button
            public void run() {
                if (keepHistory)
                    printWriter.close();
            }
        }));
        setLocationRelativeTo(null);
        textArea.append("W celu uzyskania pomocy wpisz 'help'\n");
    }
}
