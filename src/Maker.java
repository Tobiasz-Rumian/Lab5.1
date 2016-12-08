/*
 *  Klasa Maker
 *  Zajmuje się tworzeniem wątków i przechowywaniem ich.
 *
 *  @author Tobiasz Rumian
 *  @version 1.1
 *   Data: 08 Grudzień 2016 r.
 *   Indeks: 226131
 *   Grupa: śr 13:15 TN
 */

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
        for (Producer c : producers) {
            c.kill();
        }
        for (Buyer c : buyers) {
            c.kill();
        }
        buyers = new ArrayList<>();
        producers = new ArrayList<>();
    }

    void freeze() {
        currentFreezeState = !currentFreezeState;
        for (Producer p : producers) p.setFreeze(currentFreezeState);
        for (Buyer k : buyers) k.setFreeze(currentFreezeState);
    }
}
