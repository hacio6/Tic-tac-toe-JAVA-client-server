import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.StringJoiner;

/**
 * Created by Hacio-PC on 2016-04-09.
 */
public class Server extends Game {
    public static int PORT = 1234; //Port
    public static boolean flaga = true; //flaga
    private static ServerSocket myServerSocket; //Gniazdo Serwera
    private static Socket ClientSocket;  //Gniazdo klienta
    //public String a;
    public static MainForm gra;


    public static void main (String[] args) throws IOException
    {

        myServerSocket = null;//stworzenie gniazda servwera i przypisanie mu portu (tu 9999)
        try
        {
            myServerSocket = new ServerSocket(PORT); //Stawiamy serwer na danym porcie
        }
        catch(IOException e)
        {
            System.err.println("Nie mozna polaczyc na porcie "+PORT);//Nie mozna polaczyc
            System.exit(1);//I wylaczamy
        }

        System.out.print("Czekamy na polaczenie z klientem...");

        Thread t = new Thread(new Runnable() //Tworzymy nowy watek! //I to sie dzieje rownolegle z nastepnym watkiem
        {
            public void run()
            {
                try
                {
                    while(flaga)
                    {
                        System.out.print(".");
                        Thread.sleep(1000);//Daje wrazenie ze czekamy, co 1s stawiamy kropke
                    }
                }
                catch(InterruptedException ie)
                {
                    //
                }
                System.out.println("Polaczono z klientem na porcie " + PORT);
                gra = new MainForm();
                gra.setTitle("Kólko i krzyzyk - serwer");


            }
        });
        t.start();


        ClientSocket = null; //poki co nie ma klienta wiec nic nie ustawiamy
        try //probujemy podlaczyc klienta
        {
            ClientSocket = myServerSocket.accept();
            flaga=false;//jak poczylo to juz nie oczekujemy wiec przerwie tamten watek
        }
        catch(IOException e) //Jak sie nie da polaczyc
        {
            System.err.println("Nie udalo sie!");
            t.interrupt();//przerwanie dzialania watka
            System.exit(1);
        }

        final PrintWriter out = new PrintWriter(ClientSocket.getOutputStream(),true);
        final BufferedReader in = new BufferedReader(new InputStreamReader(ClientSocket.getInputStream()));
        Scanner pisz = new Scanner(System.in);
        String a="";


        t=new Thread(new Runnable()
        {
            public void run()
            {
                try
                {
                    //Thread.sleep(3000);
                    while (true)
                    {
                        String a="";
                        System.out.print("Serwer pisze: ");
                        a = pisz.nextLine();
                        //System.out.println(System.currentTimeMillis()+"Serwer: " + a);
                        out.println(a);
                        String input = in.readLine();
                        if(input != null)
                        {
                            System.out.println("Klient napisal: "+input);
                            if(input.equals("STOP"))
                            {
                                System.out.println(System.currentTimeMillis()+" Wrong answer");
                                out.println("STOP");
                                out.close();
                                in.close();
                                ClientSocket.close();
                                myServerSocket.close();
                                break;
                            }
                        }
                        else
                        {
                            //
                        }
                    }
                    //Thread.sleep(3000);
                }
                catch(Exception e)
                {
                    System.err.println("Nieoczekiwany blad!");
                }
            }
        });
        t.start();
    }
}
