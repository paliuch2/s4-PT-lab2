package pl.pal.kamil.pt2;

import java.util.ArrayList;
import java.util.List;

public class Konsument implements Runnable {

    private Zasob zasob;
    private Zapis zapis;


    public Konsument(Zasob zasob, Zapis zapis) {
        this.zasob = zasob;
        this.zapis = zapis;

    }

    @Override
    public void run() {



        while (!Thread.interrupted()) {

            Long liczba = null;

            try {
                liczba = zasob.take();
                System.out.println("Przetwarzanie liczby: " + liczba);
            } catch (InterruptedException ex) {
                break;
            }
            List<Long> dzielniki = new ArrayList<>();
            for (long i = 1; i <= liczba; ++i)
            {
                if (liczba % i == 0)
                {
                    dzielniki.add(i);
                }
            }

            try {
                zapis.write(liczba,dzielniki);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Zakonczono przetwarzanie liczby: " + liczba);

            try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            break;
        }

    }
    }
}

