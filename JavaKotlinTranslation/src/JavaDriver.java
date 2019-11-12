import java.text.NumberFormat;
import java.util.Scanner;

public class JavaDriver {
    public enum dateOption {
        APHELION,
        PERIHELION,
        JAN, FEB, MAR, APR,
        MAY, JUN, JUL, AUG,
        SEP, OCT, NOV, DEC,
        INVALID, QUIT
    }

    private static dateOption getUserDateOption(String input){
        dateOption retVal;

        switch (input){
            case "aphelion":
                retVal = dateOption.APHELION;
                break;
            case "perihelion":
                retVal = dateOption.PERIHELION;
                break;
            case "january":
                retVal = dateOption.JAN;
                break;
            case "february":
                retVal = dateOption.FEB;
                break;
            case "march":
                retVal = dateOption.MAR;
                break;
            case "april":
                retVal = dateOption.APR;
                break;
            case "may":
                retVal = dateOption.MAY;
                break;
            case "june":
                retVal = dateOption.JUN;
                break;
            case "july":
                retVal = dateOption.JUL;
                break;
            case "august":
                retVal = dateOption.AUG;
                break;
            case "september":
                retVal = dateOption.SEP;
                break;
            case "october":
                retVal = dateOption.OCT;
                break;
            case "november":
                retVal = dateOption.NOV;
                break;
            case "december":
                retVal = dateOption.DEC;
                break;
            case "q":
                retVal = dateOption.QUIT;
                break;
            default:
                retVal = dateOption.INVALID;
                break;
        }

        return retVal;
    }

    public static void main(String[] args){
        Scanner userInput = new Scanner(System.in);
        String input;
        dateOption option = dateOption.INVALID;

        while (option == dateOption.INVALID){
            System.out.print("Enter 'aphelion', 'perihelion', or a month of the year (or 'q' to exit): ");
            input = userInput.nextLine().toLowerCase();
            System.out.println();
            option = getUserDateOption(input);
            if (option.equals(dateOption.INVALID)){
                System.out.println("Invalid option.");
            }
        }

        if (!option.equals(dateOption.QUIT)) {
            JavaOrbitCalc joc;
            if (option.equals(dateOption.APHELION) || option.equals(dateOption.PERIHELION)){
                joc = new JavaOrbitCalc(option, 0, 0, 0, 0);
            }
            else {
                int day = -1;
                while(day < 0){
                    try{
                        System.out.print("What day of the month would you like to check? ");
                        day = userInput.nextInt();
                        System.out.println();
                    }
                    catch (NumberFormatException e){
                        System.out.println();
                        System.out.println("Invalid number");
                        day = -1;
                    }
                }

                int hour = -1;
                while(hour < 0){
                    try{
                        System.out.print("What hour in the day would you like to check (24 hour time)? ");
                        hour = userInput.nextInt();
                        if (hour > 24){
                            throw new NumberFormatException();
                        }
                    }
                    catch (NumberFormatException e){
                        System.out.println();
                        System.out.println("Invalid number");
                        hour = -1;
                    }
                }
                int minute = -1;
                while(minute < 0){
                    try {
                        System.out.print("What minute in this hour would you like to check? ");
                        minute = userInput.nextInt();
                        if (minute > 59){
                            throw new NumberFormatException();
                        }
                        System.out.println();
                    }
                    catch (NumberFormatException e){
                        System.out.println();
                        System.out.println("Invalid number");
                        minute = -1;
                    }
                }

                int second = -1;
                while(second < 0) {
                    try {
                        System.out.print("What second in this minute would you like to check? ");
                        second = userInput.nextInt();
                        if (second > 59) {
                            throw new NumberFormatException();
                        }
                        System.out.println();
                    } catch (NumberFormatException e) {
                        System.out.println();
                        System.out.println("Invalid number");
                        second = -1;
                    }
                }
                joc = new JavaOrbitCalc(option, day, hour, minute, second);
            }

            NumberFormat myFormat = NumberFormat.getInstance();
            myFormat.setGroupingUsed(true); // this will also round numbers, 3

            System.out.println("X distance from center of ellipse: " + myFormat.format(joc.getxPos()) + " km");
            System.out.println("Y distance from center of ellipse: " + myFormat.format(joc.getyPos()) + " km");
            System.out.printf("Angle of earth relative to Sun (with Jan 1, 12:00 a.m. being 0 deg): %.2f degrees\n",
                    joc.getAngle());
            System.out.println("Earth distance from sun: " + myFormat.format(joc.getDistanceFromSun()) + " km");
        }
    }
}
