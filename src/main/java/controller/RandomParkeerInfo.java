package controller;

public class RandomParkeerInfo {


    public static char krijgRandomAZ() {
        return (char) (65 + (int) (Math.random() * ((90 - 65) + 1)));
    }

    public static int krijgRandomGetal() {
        return  (0 + (int) (Math.random() * ((9 - 0) + 1)));
    }

    public static int krijgRandomParkeerduur() {
        return  (1 + (int) (Math.random() * ((24 - 1) + 1)));
    }

    public static String krijgRandomKenteken() {
        String kenteken = String.format("%c%c-%d%d-%c%c", krijgRandomAZ(), krijgRandomAZ(), krijgRandomGetal(),
                krijgRandomGetal(), krijgRandomAZ(), krijgRandomAZ());
        return kenteken;
    }

}
