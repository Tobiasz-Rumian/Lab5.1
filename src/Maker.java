import java.util.ArrayList;

/**
 * Created by Tobiasz Rumian on 26.11.2016.
 */
public class Maker {
    private ArrayList<Producer> producers = new ArrayList<>();
    private ArrayList<Buyer> buyers       = new ArrayList<>();
    private boolean currentFreezeState    = false;
    public Maker(){
        for(int i=0;i<Gui.getHowManyProducers();i++) producers.add(new Producer(Gui.getBufor(),i));
        for(int i=0;i<Gui.getHowManyBuyers();i++)    buyers.add(new Buyer(Gui.getBufor(),i));
        for (Producer p:producers)                   p.start();
        for (Buyer k:buyers)                         k.start();
    }
    public void exit(){
        for (Producer p:producers)                   p.kill();
        for (Buyer k:buyers)                         k.kill();
        producers.clear();
        buyers.clear();
    }
    public void freeze(){
        currentFreezeState = !currentFreezeState;
        for (Producer p:producers)                   p.setFreeze(currentFreezeState);
        for (Buyer k:buyers)                         k.setFreeze(currentFreezeState);
    }
}
