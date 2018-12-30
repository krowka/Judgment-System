import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Main extends JFrame implements KeyListener {
    private boolean hasCorrectSourcePath; // true if given path exists, false otherwise
    private JudgmentSystem judgmentSystem;
    private JPanel panel;
    private JLabel label;
    private JTextField textField;
    private JTextArea textArea;
    private JScrollPane scrollPane;

    public Main() {
        init();
    }

    public static void main(String[] args){
        EventQueue.invokeLater(() -> {
            Main main = new Main();
            main.setVisible(true);
        });
    }

    @Override
    public void keyPressed(KeyEvent evt) {
        try {
            if (hasCorrectSourcePath && evt.getKeyCode() == KeyEvent.VK_ENTER) {
                if (!textField.getText().equals(""))
                    this.judgmentSystem.setHistoryPath(textField.getText()); // getting path to file where history logs will be stored
                this.dispose();
                judgmentSystem.setVisible(true);
                judgmentSystem.execute();
            } else if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                this.judgmentSystem = new JudgmentSystem(textField.getText()); // getting path to folder with files to proceed
                textField.setText(""); // clearing text field
                textArea.append(judgmentSystem.getLoadMessage());
                label.setText("Jeśli chcesz zapisywać historię poleceń podaj scieżkę do pliku, w przeciwnym wypadku pozostaw to pole puste i naciśnij [ENTER] aby kontynować: ");
                this.hasCorrectSourcePath = true; // no problem occurred so path is correct
            }
        } catch (Exception ex) {
            textArea.append(ex.toString() + ". Wprowadź scieżkę ponownie...\n");
            textField.setText("");
        }
    }

    // Not Used, but need to provide an empty body for compilation
    @Override
    public void keyTyped(KeyEvent evt) { }

    @Override
    public void keyReleased(KeyEvent evt) { }

    private void init(){
        this.hasCorrectSourcePath = false;
        this.panel = new JPanel();
        this.label = new JLabel("Podaj scieżkę do folderu z plikami do przetworzenia (obsługiwane rozszerzenia plików to .html oraz .json): ");
        panel.add(label);
        this.textField = new JTextField();
        textField.setPreferredSize(new Dimension(950, 25));
        textField.addKeyListener(this);
        panel.add(textField);
        this.textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        panel.add(textArea);
        this.scrollPane = new JScrollPane(textArea);
        this.scrollPane.setPreferredSize(new Dimension(950, 100));
        this.scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        panel.add(scrollPane);
        add(panel);
        pack();
        setSize(new Dimension(1000, 200));
        setTitle("JUDGMENT 2.0");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
}