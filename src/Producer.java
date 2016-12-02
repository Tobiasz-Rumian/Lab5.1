
class Producer extends User {

    private static long item = 0;
    public Producer(Buffer c, int id){
        buffer = c;
        this.id = id;
    }

    public void run(){
        while(!kill){
            while(freeze) sleepWell(1000);
            item++;
            buffer.put(id, item);
            sleepWell(1000);
        }
    }
}





