/*
 *  Klasa Gui
 *  Klasa obsługuje okno programu.
 *
 *  @author Tobiasz Rumian
 *  @version 1.1
 *   Data: 08 Grudzień 2016 r.
 *   Indeks: 226131
 *   Grupa: śr 13:15 TN
 */

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.*;
import java.net.MalformedURLException;
import java.net.URL;

public class Gui extends JFrame {
    private JButton buttonStart = new JButton("Start");
    private JButton buttonStop = new JButton("Zatrzymaj");
    private JButton buttonFreeze = new JButton("Zamróź");
    private JButton buttonAbout = new JButton("Autor");
    private JSlider sliderProducers = new JSlider(1, 1, 10, 5);
    private JSlider sliderBuyers = new JSlider(1, 1, 10, 5);
    private JSlider sliderBuffer = new JSlider(1, 1, 10, 5);
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
        createSliders();
        menuBar.add(buttonFreeze);
        menuBar.add(buttonStop);
        menuBar.add(buttonAbout);
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
            buttonStart.setEnabled(false);
            sliderBuyers.setEnabled(true);
            sliderProducers.setEnabled(true);
            buttonStop.setEnabled(false);
            buttonFreeze.setEnabled(false);
            textArea.setText("");
            maker.exit();
            graphicRepresentation.kill();
            graphicRepresentation = new GraphicRepresentation();
        });
        buttonFreeze.addActionListener(actionEvent -> {
            maker.freeze();
            buffer.freeze();
        });
        buttonAbout.addActionListener(actionEvent -> {
            About about;
            try {
                about = new About(this);
                about.setVisible(true);
            } catch (Exception event) {
                System.err.println(event.getMessage());
            }
        });
    }

    private void createSliders() {
        sliderProducers.setMajorTickSpacing(5);
        sliderProducers.setMinorTickSpacing(1);
        sliderProducers.setPaintTicks(true);
        sliderProducers.setPaintLabels(true);
        JMenu menuProducers = new JMenu("Ilu producentów?");
        menuProducers.add(sliderProducers);
        menuProducers.add(labelHMProducers);
        menuBar.add(menuProducers);
        sliderBuyers.setMajorTickSpacing(5);
        sliderBuyers.setMinorTickSpacing(1);
        sliderBuyers.setPaintTicks(true);
        sliderBuyers.setPaintLabels(true);
        JMenu menuBuyers = new JMenu("Ilu kupców?");
        menuBuyers.add(sliderBuyers);
        menuBuyers.add(labelHMBuyers);
        menuBar.add(menuBuyers);
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

    private class About extends JDialog {
        About(JFrame owner) throws MalformedURLException {
            super(owner, "O Autorze", true);
            URL url = null;
            try {
                url = new URL("https://media.giphy.com/media/l0HlIKdi4DIEDk92g/giphy.gif");
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
            Icon icon = new ImageIcon(url);
            JLabel label = new JLabel(icon);
            add(new JLabel("Autor:\t Tobiasz Rumian\t Indeks: 226131"), BorderLayout.NORTH);
            add(label, BorderLayout.CENTER);
            JButton ok = new JButton("ok");
            ok.addActionListener(e -> setVisible(false));
            add(ok, BorderLayout.SOUTH);
            setSize(400, 400);
        }
    }
}
