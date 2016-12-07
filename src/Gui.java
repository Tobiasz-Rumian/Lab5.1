import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.*;

public class Gui extends JFrame {
    private JButton buttonStart = new JButton("Start");
    private JButton buttonStop = new JButton("Zatrzymaj");
    private JButton buttonFreeze = new JButton("Zamróź");
    private JSlider sliderProducers = new JSlider(1, 10);
    private JSlider sliderBuyers = new JSlider(1, 10);
    private JSlider sliderBuffer = new JSlider(1, 10);
    private JLabel labelProducers = new JLabel("Ilu producentów?");
    private JLabel labelBuyers = new JLabel("Ilu kupców?");
    private JLabel labelBuffer = new JLabel("Jak duży bufer?");
    private JLabel labelHMProducers = new JLabel(Integer.toString(sliderProducers.getValue()));
    private JLabel labelHMBuyers = new JLabel(Integer.toString(sliderBuyers.getValue()));
    private JLabel labelHMBuffer = new JLabel(Integer.toString(sliderBuffer.getValue()));
    private JMenuBar menuBar = new JMenuBar();
    private static int howManyProducers = 5;
    private static int howManyBuyers = 5;
    private Maker maker = null;
    private JTextArea textArea = new JTextArea();
    private static Buffer buffer = null;
    private JScrollPane scrollPane = new JScrollPane(textArea);
    private GraphicRepresentation graphicRepresentation = new GraphicRepresentation();
    private static Dimension frameSize = new Dimension(800, 700);

    private Gui() {
        super("Tobiasz Rumian Laboratorium 5.1");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(frameSize);
        buttonStop.setEnabled(false);
        buttonFreeze.setEnabled(false);
        buffer = new Buffer(this, graphicRepresentation);
        DefaultCaret caret = (DefaultCaret) textArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        this.addComponentListener(new CustomComponentListener());
        this.addWindowStateListener(new CustomWindowStateListener());
        menuBar.add(buttonStart);
        menuBar.add(new JSeparator(JSeparator.VERTICAL));
        menuBar.add(labelProducers);
        menuBar.add(sliderProducers);
        menuBar.add(labelHMProducers);
        menuBar.add(new JSeparator(JSeparator.VERTICAL));
        menuBar.add(labelBuyers);
        menuBar.add(sliderBuyers);
        menuBar.add(labelHMBuyers);
        menuBar.add(new JSeparator(JSeparator.VERTICAL));
        menuBar.add(labelBuffer);
        menuBar.add(sliderBuffer);
        menuBar.add(labelHMBuffer);
        menuBar.add(new JSeparator(JSeparator.VERTICAL));
        menuBar.add(buttonFreeze);
        menuBar.add(buttonStop);
        JPanel panel = new JPanel();
        textArea.setEditable(false);
        setContentPane(panel);
        scrollPane.setPreferredSize(new Dimension((int) frameSize.getWidth() - 20, (int) (frameSize.getHeight() * 0.25)));
        panel.add(scrollPane);
        panel.add(graphicRepresentation);
        graphicRepresentation.setVisible(true);
        graphicRepresentation.setPreferredSize(new Dimension((int) frameSize.getWidth(), (int) (frameSize.getWidth() * 0.75)));
        graphicRepresentation.setBackground(Color.blue);
        setJMenuBar(menuBar);
        setVisible(true);

        buttonStart.addActionListener(actionEvent -> {
            buttonStart.setEnabled(false);
            sliderBuyers.setEnabled(false);
            sliderProducers.setEnabled(false);
            buttonStop.setEnabled(true);
            buttonFreeze.setEnabled(true);
            howManyBuyers = sliderBuyers.getValue();
            howManyProducers = sliderProducers.getValue();
            graphicRepresentation.createFigures();
            buffer.setHowMany(sliderBuffer.getValue());
            maker = new Maker();
        });
        sliderProducers.addChangeListener(changeEvent -> labelHMProducers.setText(Integer.toString(sliderProducers.getValue())));
        sliderBuyers.addChangeListener(changeEvent -> labelHMBuyers.setText(Integer.toString(sliderBuyers.getValue())));
        sliderBuffer.addChangeListener(changeEvent -> labelHMBuffer.setText(Integer.toString(sliderBuffer.getValue())));
        buttonStop.addActionListener(actionEvent -> {
            buttonStart.setEnabled(true);
            sliderBuyers.setEnabled(true);
            sliderProducers.setEnabled(true);
            buttonStop.setEnabled(false);
            buttonFreeze.setEnabled(false);
            textArea.setText("");
            maker.exit();
            graphicRepresentation.kill();
            graphicRepresentation = new GraphicRepresentation();
            maker = new Maker();
            buffer = new Buffer(this, graphicRepresentation);
            System.gc();
        });
        buttonFreeze.addActionListener(actionEvent -> {
            maker.freeze();
            buffer.freeze();
        });
    }

    public static void main(String[] args) {
        new Gui();
    }

    static int getHowManyProducers() {
        return howManyProducers;
    }

    static int getHowManyBuyers() {
        return howManyBuyers;
    }

    void addToTextArea(String text) {
        textArea.append(text + "\n");
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }

    static Buffer getBuffer() {
        return buffer;
    }

    static Dimension getFrameSize() {
        return frameSize;
    }

    private void setComponentsSize(ComponentEvent e) {
        frameSize = e.getComponent().getBounds().getSize();
        graphicRepresentation.setPreferredSize(new Dimension((int) frameSize.getWidth(), (int) (frameSize.getWidth() * 0.75)));
        scrollPane.setPreferredSize(new Dimension((int) frameSize.getWidth() - 20, (int) (frameSize.getHeight() * 0.25)));
        graphicRepresentation.refreshCoordinates();
    }

    class CustomComponentListener implements ComponentListener {

        public void componentResized(ComponentEvent e) {
            setComponentsSize(e);
        }

        public void componentMoved(ComponentEvent e) {
        }

        public void componentShown(ComponentEvent e) {
        }

        public void componentHidden(ComponentEvent e) {
        }
    }

    class CustomWindowStateListener implements WindowStateListener {

        @Override
        public void windowStateChanged(WindowEvent e) {
            setComponentsSize(e);
        }
    }
}
