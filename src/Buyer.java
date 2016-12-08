/*
 *  Klasa Buyer
 *  Tworzy wątki kupców i zajmuje się ich przemieszczaniem
 *
 *  @author Tobiasz Rumian
 *  @version 1.1
 *   Data: 08 Grudzień 2016 r.
 *   Indeks: 226131
 *   Grupa: śr 13:15 TN
 */
public class Buyer extends User {
    private volatile boolean kill = false;

    Buyer(Buffer buffer, int id) {
        this.buffer = buffer;
        this.id = id;
    }


    public void run() {
        while (!kill) {
            while (freeze) sleepWell(1000);
            buffer.get(id);
            sleepWell(1000);
        }
        System.err.println("x");
    }

    void kill() {
        kill = true;
    }
}
