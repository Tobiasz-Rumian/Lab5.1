/**
 * Created by Tobiasz Rumian on 27.11.2016.
 */
public abstract class User extends Thread{
    protected int id;
    protected boolean kill = false;
    protected boolean freeze = false;
    protected Buffer buffer;

    public void sleepWell(int time){
        try {
            sleep((int)(Math.random() * time));
        } catch (InterruptedException ignored) {}
    }
    public void kill(){
        kill=true;
    }
    public void setFreeze(boolean freeze){
        this.freeze=freeze;
    }
}
