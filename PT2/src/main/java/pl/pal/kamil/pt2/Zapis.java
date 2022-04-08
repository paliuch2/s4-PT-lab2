package pl.pal.kamil.pt2;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Zapis {

    FileWriter writer;

    public Zapis(FileWriter writer) {
       this.writer = writer;

    }


    public synchronized void write(Long liczba, List<Long> dzielniki) throws InterruptedException {




        try {
            writer.write(liczba + ": ");
            for (Long i : dzielniki)
            {   writer.write(i + ", ");}
            writer.write("\n");

        } catch (IOException e) {
            System.out.println("Błąd wejścia/wyjścia");
            e.printStackTrace();
        }




    }

}

