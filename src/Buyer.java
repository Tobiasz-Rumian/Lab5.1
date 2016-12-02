/**
 * Created by Tobiasz Rumian on 26.11.2016.
 */
public class Buyer extends User {

    public Buyer(Buffer buffer, int id){
        this.buffer = buffer;
        this.id = id;
    }

    public void run(){
        while(!kill){
            while(freeze)sleepWell(1000);
            buffer.get(id);
            sleepWell(1000);
        }
    }
}
