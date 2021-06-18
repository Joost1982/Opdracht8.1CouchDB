package model;

public class Auto {

    private String kenteken;
    private int parkeerDuur;
    private double parkeerGeld;

    public Auto(String kenteken, int parkeerduur) {
        this.kenteken = kenteken;
        this.parkeerDuur = parkeerduur;
        berekenParkeergeld(parkeerduur);
    }


    private void berekenParkeergeld(int mpParkeerduur) {

        //Het tarief voor de eerste 3 uur is € 3,75 per uur. Voor de uren daarna is het tarief € 2,75 per uur. De
        //maximale parkeerkosten bedragen € 25,--, dit is de prijs van een dagkaart.

        double kosten = 0.0;
        final double STARTKOSTEN = 3.75;
        final double LATEREKOSTEN = 2.75;

        if (mpParkeerduur <= 3) {
            kosten = STARTKOSTEN * mpParkeerduur;
        }
        else {
            kosten = (STARTKOSTEN * 3) + ( (mpParkeerduur - 3) * LATEREKOSTEN );
        }

        if (kosten > 25.0) {
            kosten = 25.0;
        }
        this.parkeerGeld = kosten;
    }


    public String getKenteken() {
        return kenteken;
    }

    public int getParkeerDuur() {
        return parkeerDuur;
    }

    public double getParkeerGeld() {
        return parkeerGeld;
    }

    @Override
    public String toString() {
        return String.format("Kenteken: %s\n\tParkeerduur (uren): %d\n\tParkeerkosten (euro): %.2f", kenteken, parkeerDuur, parkeerGeld);
    }
}
