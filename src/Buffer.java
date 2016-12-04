import java.util.ArrayList;
import java.util.Random;


public class Buffer {
    ArrayList<Long> contents = new ArrayList<>();
    private int howMany = 0;
    private boolean freeze = false;
    private boolean empty = true;
    private boolean full = false;
    private Gui gui = null;
    private GraphicRepresentation graphicRepresentation = null;
    private Random random = new Random();

    Buffer(Gui gui, GraphicRepresentation graphicRepresentation) {
        this.gui = gui;
        this.graphicRepresentation = graphicRepresentation;
    }

    synchronized long get(int id) {
        gui.addToTextArea("Konsument #" + id + " chce zabrac");
        if (graphicRepresentation.moveLevel(id, false, true) != 0) return 0;
        while (empty) {
            try {
                gui.addToTextArea("Konsument #" + id + "   bufor pusty - czekam");
                wait();
            } catch (InterruptedException ignored) {
            }
        }
        if (graphicRepresentation.moveLevel(id, false, true) != 0) return 0;
        int get = random.nextInt(contents.size());
        long returning = contents.get(get);
        contents.remove(get);
        gui.addToTextArea("Konsument #" + id + "      zabral: " + returning);
        empty = contents.size() == 0;
        full = contents.size() >= howMany;
        graphicRepresentation.setLabel(contents.toString());
        notifyAll();
        try {
            wait(1000);
        } catch (InterruptedException ignored) {
        }
        while (freeze) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException ignored) {
            }
        }
        if (graphicRepresentation.moveLevel(id, false, false) != 0) return 0;
        if (graphicRepresentation.moveLevel(id, false, false) != 0) return 0;
        return returning;
    }

    synchronized void put(int id, long value) {
        gui.addToTextArea("Producent #" + id + "  chce oddac: " + value);
        if (graphicRepresentation.moveLevel(id, true, true) != 0) return;
        while (full) {
            try {
                gui.addToTextArea("Producent #" + id + "   bufor zajety - czekam");
                wait();
            } catch (InterruptedException ignored) {
            }
        }
        if (graphicRepresentation.moveLevel(id, true, true) != 0) return;

        contents.add(value);
        gui.addToTextArea("Producent #" + id + "       oddal: " + value);
        empty = contents.size() == 0;
        full = contents.size() >= howMany;
        graphicRepresentation.setLabel(contents.toString());
        notifyAll();
        try {
            wait(1000);
        } catch (InterruptedException ignored) {
        }
        while (freeze) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException ignored) {
            }
        }
        if (graphicRepresentation.moveLevel(id, true, false) != 0) return;
        if (graphicRepresentation.moveLevel(id, true, false) != 0) return;
    }

    void setHowMany(int howMany) {
        this.howMany = howMany;
    }

    void freeze() {
        freeze = !freeze;
    }
}
