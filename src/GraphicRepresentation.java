import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GraphicRepresentation extends JPanel {
    private ArrayList<Circle> circlesProducers = new ArrayList<>();
    private ArrayList<Circle> circlesBuyers = new ArrayList<>();
    private ArrayList<Double> producerLocation = new ArrayList<>();
    private ArrayList<Double> buyerLocation = new ArrayList<>();
    private boolean kill = false;
    private JLabel label = new JLabel("");

    GraphicRepresentation() {
        for (int i = 0; i < 3; i++) {
            producerLocation.add(0d);
            producerLocation.add(0d);
            producerLocation.add(0d);
            buyerLocation.add(0d);
            buyerLocation.add(0d);
            buyerLocation.add(0d);
        }
        this.add(label);
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        for (Circle c : circlesProducers) {
            c.draw(g);
        }
        for (Circle c : circlesBuyers) c.draw(g);
    }

    void createFigures() {

        for (int i = 0; i < Gui.getHowManyProducers(); i++) {
            Circle circle = new Circle(producerLocation.get(0), 100 + i * 40);
            circle.setDefaultColor(Color.green);
            circlesProducers.add(circle);
        }
        for (int i = 0; i < Gui.getHowManyBuyers(); i++) {
            Circle circle = new Circle(buyerLocation.get(0), 100 + i * 40);
            circle.setDefaultColor(Color.yellow);
            circlesBuyers.add(circle);
        }
        repaint();
    }

    short moveLevel(int index, boolean user, boolean up) {
        if (kill) return 0;
        Circle circle;
        double move;
        int i;
        if (up) i = 1;
        else i = -1;
        if (user) {
            circle = circlesProducers.get(index);
            move = producerLocation.get(circle.getCurrentState() + i);
        } else {
            circle = circlesBuyers.get(index);
            move = buyerLocation.get(circle.getCurrentState() + i);
        }

        if (circle.getCurrentState() == 0 && !up) return -1;
        if (circle.getCurrentState() == 2 && up) return 1;

        if (circle.getCurrentState() == 2 && !up) {
            circle.setColor(circle.getDefaultColor());
        }
        circle.setCurrentState(circle.getCurrentState() + i);
        circle.moveX(move);
        if (circle.getCurrentState() == 2) {
            circle.setColor(Color.red);
        }

        repaint();

        return 0;
    }

    void refreshCoordinates() {

        producerLocation.set(0, Gui.getFrameSize().getWidth() * (1d / 7d));
        producerLocation.set(1, Gui.getFrameSize().getWidth() * (2d / 7d));
        producerLocation.set(2, Gui.getFrameSize().getWidth() * (3d / 7d));
        buyerLocation.set(0, Gui.getFrameSize().getWidth() * (6d / 7d));
        buyerLocation.set(1, Gui.getFrameSize().getWidth() * (5d / 7d));
        buyerLocation.set(2, Gui.getFrameSize().getWidth() * (4d / 7d));

        for (Circle c : circlesBuyers) {
            c.moveX(buyerLocation.get(c.getCurrentState()));
        }
        for (Circle c : circlesProducers) {
            c.moveX(producerLocation.get(c.getCurrentState()));
        }
        repaint();
    }

    void kill() {
        circlesBuyers.clear();
        circlesProducers.clear();
        kill = true;
        repaint();
    }
    void setLabel(String text){
        label.setText(text);
    }
}
