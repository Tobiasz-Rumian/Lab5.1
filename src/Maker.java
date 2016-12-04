import java.util.ArrayList;


class Maker {
    private ArrayList<Producer> producers = new ArrayList<>();
    private ArrayList<Buyer> buyers = new ArrayList<>();
    private boolean currentFreezeState = false;

    Maker() {
        for (int i = 0; i < Gui.getHowManyProducers(); i++) producers.add(new Producer(Gui.getBuffer(), i));
        for (int i = 0; i < Gui.getHowManyBuyers(); i++) buyers.add(new Buyer(Gui.getBuffer(), i));
        for (Producer p : producers) p.start();
        for (Buyer k : buyers) k.start();
    }

    void exit() {
        for (Producer p : producers) p.kill();
        for (Buyer k : buyers) k.kill();
        producers.clear();
        buyers.clear();
    }

    void freeze() {
        currentFreezeState = !currentFreezeState;
        for (Producer p : producers) p.setFreeze(currentFreezeState);
        for (Buyer k : buyers) k.setFreeze(currentFreezeState);
    }
}
