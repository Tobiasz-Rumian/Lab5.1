import java.awt.*;

public class Circle {
    private double x, y;
    private Color color = new Color(20);
    private float r=15;
    private int currentState=0;
    private Color defaultColor;

    Circle(double px, double py) {
        this.x = px;
        this.y = py;
    }

    void moveX(double px){
        x=px;
    }

    void draw(Graphics g) {
        g.setColor(color);
        g.drawOval((int) (x - r), (int) (y - r), (int) (2 * r), (int) (2 * r));
        g.fillOval((int) (x - r), (int) (y - r), (int) (2 * r), (int) (2 * r));
    }

    public void setColor(Color color) {
        this.color = color;
    }
    public void setCurrentState(int currentState){
        this.currentState=currentState;
    }
    public int getCurrentState(){
        return currentState;
    }
    public Color getDefaultColor(){
        return defaultColor;
    }
    public void setDefaultColor(Color color){
        defaultColor=color;
        this.color=color;
    }
}
