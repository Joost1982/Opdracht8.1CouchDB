package controller;

import database.AutoCouchDBDAO;
import database.CouchDBaccess;
import model.Auto;

import java.util.ArrayList;
import java.util.Scanner;
/* Ombouwing van de 8.1 opdracht van Programming om CouchDB te testen.
    Door Joost van der Horst*/
public class Launcher {

    private final static int AANTALAUTOS = 100;
    private static ArrayList<Auto> autos = new ArrayList<>();

    public static void main(String[] args) {

        AutoCouchDBDAO cdbAuto = new AutoCouchDBDAO(connectDB());

        //invoer

        int keuze = keuzeInvoer();
        //handmatige invoer
        if (keuze == 1) {
            autos = verzamelAutoInfo();
            slaOpInDB(autos);
        }
        //invoer genereren
        else if (keuze == 2){
            for (int i = 0; i < AANTALAUTOS; i++) {
                Auto auto = new Auto(RandomParkeerInfo.krijgRandomKenteken(), RandomParkeerInfo.krijgRandomParkeerduur());
                autos.add(auto);
            }
            slaOpInDB(autos);
        }

        //informatie opvragen

        //parkeerduur informatie opvragen
        else {
            int keuze2 = keuzeInvoer2();
            //alle auto's
            if (keuze2 == 1) {
                ArrayList<Auto> autos = cdbAuto.getAllAutos();
                for (Auto a : autos) {
                    System.out.println(a);
                }
                //kosten informatie per kenteken
                cdbAuto.printOverzichtKostenPerKenteken();  // via een couchDB-view waarin alle kosten per kenteken opgeteld zijn (kentekens komen maar 1x voor)
            }
            //specifiek kenteken
            else {
                System.out.println(cdbAuto.getAutoByKenteken(vraagKenteken()));
            }
        }
    }


    public static String vraagKenteken() {
        Scanner input = new Scanner(System.in);
        System.out.printf("Welke kenteken (AZ-00-AZ)?: ");
        return input.next();
    }

    public static void slaOpInDB(ArrayList<Auto> autos) {
        AutoCouchDBDAO cdbAuto = new AutoCouchDBDAO(connectDB());
        for (Auto a : autos) {
            cdbAuto.saveSingleAuto(a);
        }
    }

    public static CouchDBaccess connectDB() {
        CouchDBaccess db = new CouchDBaccess();
        try {
            db.setupConnection();   //verbind en maakt ook de database "parkeren" aan als die nog niet bestaat
            System.out.println("Connection open");
        } catch (Exception e) {
            System.out.println("\nEr is iets fout gegaan\n");
            e.printStackTrace();
        }

        return db;
    }

    public static int keuzeInvoer() {
        System.out.println("Wil je de parkeerduur per auto (1) handmatig invoeren, (2) automatisch genereren of " +
                "(3) parkeerduur informatie opvragen?");
        Scanner input = new Scanner(System.in);

        int keuze;
        do {
            System.out.printf("Maak uw keuze (1, 2 of 3):  ");
            keuze = input.nextInt();
            if (keuze != 1 && keuze != 2 && keuze != 3) {
                System.out.println("Foute keuze!");
            }
        } while (keuze != 1 && keuze != 2 && keuze != 3);

        return keuze;
    }

    public static int keuzeInvoer2() {
        System.out.println("Wil je de parkeerduur van (1) alle auto's inzien of (2) van een specifiek kenteken?");
        Scanner input = new Scanner(System.in);

        int keuze;
        do {
            System.out.printf("Maak uw keuze (1 of 2):  ");
            keuze = input.nextInt();
            if (keuze != 1 && keuze != 2) {
                System.out.println("Foute keuze!");
            }
        } while (keuze != 1 && keuze != 2);

        return keuze;
    }


    public static ArrayList<Auto> verzamelAutoInfo() {

        Scanner input = new Scanner(System.in);
        ArrayList<Auto> autos = new ArrayList<>();

        System.out.print("Hoeveel auto's hebben er geparkeerd? ");
        int aantalAutos = input.nextInt();

        //Vraag de gebruiker per auto om het kenteken en de parkeerduur in gehele uren. (de parkeerduur kan maximaal 24 uur zijn)
        for (int i = 0; i < aantalAutos; i++) {

            System.out.printf("Auto %d", (i + 1));
            System.out.print("\n\t\tKenteken: ");
            String kenteken = input.next();

            System.out.print("\t\tGeparkeerde uren (max. 24): ");
            int parkeerDuur = input.nextInt();
            while (parkeerDuur > 24) {
                System.out.println("\t\tParkeerduur kan maximaal 24 uur zijn!");
                System.out.print("\t\tGeparkeerde uren (max. 24): ");
                parkeerDuur = input.nextInt();
            }
            Auto auto = new Auto(kenteken, parkeerDuur);
            autos.add(auto);
        }
        return autos;
    }

    public static void toonOverzicht(ArrayList<Auto> autos) {

        //Druk een overzicht af met van elke auto het kenteken, de parkeerduur in uren en de parkeerkosten.
        System.out.println("\nParkeeroverzicht");
        System.out.printf("%-15s %15s %15s", "kenteken", "uren", "bedrag");
        for (Auto a : autos) {
            System.out.printf("\n%-15s %15s %15.2f", a.getKenteken(), a.getParkeerDuur(), a.getParkeerGeld());
        }
        System.out.println("\nTotaal van alle parkeergelden: " + berekenTotaleParkeerKosten(autos) + " euro.");
    }


    public static double berekenTotaleParkeerKosten(ArrayList<Auto> autos) {
        double totaleParkeerkosten = 0.0;
        for (Auto a : autos) {
            totaleParkeerkosten += a.getParkeerGeld();
        }
        return totaleParkeerkosten;
    }

}



