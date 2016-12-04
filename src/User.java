abstract class User extends Thread {
    int id;
    boolean kill = false;
    boolean freeze = false;
    Buffer buffer;

    void sleepWell(int time) {
        try {
            sleep((int) (Math.random() * time));
        } catch (InterruptedException ignored) {
        }
    }

    void kill() {
        kill = true;
    }

    void setFreeze(boolean freeze) {
        this.freeze = freeze;
    }
}
