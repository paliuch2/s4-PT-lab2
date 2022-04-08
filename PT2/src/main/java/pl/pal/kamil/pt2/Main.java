package pl.pal.kamil.pt2;

//KAMIL PALUSZEWSKI 180194

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        ArrayList<Thread> watki = new ArrayList<>();
        Zasob zasob = new Zasob();

        String sciezka_in = "txt/liczby.txt";
        String sciezka_out = "txt/dzielniki.txt";

        File f = new File(sciezka_out);

        if (!f.exists())
        {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Zapis zapis = null;
        try {
            zapis = new Zapis(new FileWriter(sciezka_out, false));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Path path = Paths.get(sciezka_in);
        File dir = new File(sciezka_in);

        int liczba_watkow = 1;

        if (args.length >= 1)
        {
            liczba_watkow = Integer.parseInt(args[0]);
        }

        for (int i=0; i<liczba_watkow; i++)
        {
            watki.add(new Thread(new Konsument(zasob,zapis)));
        }

        for (Thread watek : watki)
        {
            watek.start();
            System.out.println("Uruchomiono watek.");
        }


        try (Scanner s = new Scanner(dir)) {

            while (s.hasNextLine()) {
                try
                {
                    String wczytane = s.nextLine();
                    Long liczba = Long.parseLong(wczytane);
                    zasob.put(liczba);
                } catch (NumberFormatException n) {
                    System.out.println("Niepoprawny format liczby.");
                }


                }
            } catch (FileNotFoundException e) {
            System.out.println("Nie znaleziono pliku.");
        }

        String str = "";
        Scanner sc = new Scanner(System.in);
        boolean exit = false;

    while (exit == false)
    {
        str = sc.next();
        switch(str) {
            case "exit":
                exit = true;
                break;
            default:
                try { long liczba = Long.parseLong(str);
                zasob.put(liczba);}
                catch (NumberFormatException n) {
                    System.out.println("Niepoprawny format liczby.");
                }
                break;

        }
    }

    System.out.println("Zamykanie");

        for (Thread watek : watki)
        {
            watek.interrupt();
        }
        for (Thread watek : watki)
        {
            watek.join();
            System.out.println("Wątek ukończył pracę.");
        }
        zasob.getSynchroList().clear();

       System.out.println(zasob.getSynchroList().get(0)); ;

        try {
            zapis.writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}

