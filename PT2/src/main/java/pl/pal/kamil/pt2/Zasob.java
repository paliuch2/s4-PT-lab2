package pl.pal.kamil.pt2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Zasob {

   private  List<Long> list = new ArrayList<>();
    private  List<Long> synchro = Collections.synchronizedList(list);


    public List<Long> getSynchroList() {
        return synchro;
    }

    public void setSynchroList(List<Long> synchro) {
        this.synchro = synchro;
    }




    public synchronized Long take() throws InterruptedException {
        while (synchro.isEmpty()) {
            wait();
        }
       return synchro.remove(0);
    }

    public synchronized void put(Long value) {
        this.synchro.add(value);
        notifyAll();
    }



}
