/*
 *  Klasa Producer
 *  Tworzy wątki producentów i zajmuje się ich przemieszczaniem
 *
 *  @author Tobiasz Rumian
 *  @version 1.1
 *   Data: 08 Grudzień 2016 r.
 *   Indeks: 226131
 *   Grupa: śr 13:15 TN
 */
class Producer extends User {

    private static long item = 0;
    private volatile boolean kill = false;
    Producer(Buffer c, int id) {
        buffer = c;
        this.id = id;
    }

    public void run() {
        while (!kill) {
            while (freeze) sleepWell(1000);
            item++;
            buffer.put(id, item);
            sleepWell(1000);
        }
        System.err.println("x");
    }
    public void kill(){
        kill=true;
    }
}





