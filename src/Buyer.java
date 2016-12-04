public class Buyer extends User {

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
    }
}//TODO: Sprawić aby wątki się kończyły
