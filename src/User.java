/*
 *  Klasa User
 *  Klasa abstrakcyjna zawierająca podwaliny pod klasy producer i buyer.
 *
 *  @author Tobiasz Rumian
 *  @version 1.1
 *   Data: 08 Grudzień 2016 r.
 *   Indeks: 226131
 *   Grupa: śr 13:15 TN
 */
abstract class User extends Thread {
    int id;

    boolean freeze = false;
    Buffer buffer;

    void sleepWell(int time) {
        try {
            sleep((int) (Math.random() * time));
        } catch (InterruptedException ignored) {
        }
    }

    void setFreeze(boolean freeze) {
        this.freeze = freeze;
    }
}
