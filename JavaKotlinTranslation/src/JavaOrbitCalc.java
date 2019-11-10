public class JavaOrbitCalc {
    private long seconds;
    private double xPos;
    private double yPos;
    private double angle;
    private double distanceFromSun;

    public JavaOrbitCalc(JavaDriver.dateOption option, int days, int hours,
                         int minutes, int seconds){
        init_seconds(option, days, hours, minutes, seconds);
        init_position();
    }

    private void init_seconds(JavaDriver.dateOption option, int days, int hours,
                              int minutes, int seconds){
        final int SECONDS_IN_DAY = 86400;
        final int SECONDS_IN_HOUR = 3600;
        final int SECONDS_IN_MINUTE = 60;

        int numDays = 0;
        int secondsCalc = 0;
        if (option.equals(JavaDriver.dateOption.PERIHELION)){
            // January 4th - 4 days in
            numDays = 4;
            for (int i = 0; i < numDays; i++){
                secondsCalc += SECONDS_IN_DAY;
            }
            for (int i = 0; i < 23; i++){
                secondsCalc += SECONDS_IN_HOUR;
            }
            this.seconds = secondsCalc;
        }
        else if(option.equals(JavaDriver.dateOption.APHELION)){
            // July 4th - 185 days in
            numDays = 185;
            for (int i = 0; i < numDays; i++){
                secondsCalc += SECONDS_IN_DAY;
            }
            for(int i = 0; i < 18; i++){
                secondsCalc += SECONDS_IN_HOUR;
            }
            this.seconds = secondsCalc;
        }
        else{
            switch (option){
                case JAN:
                    numDays = 0;
                    break;
                case FEB:
                    numDays = 31;
                    break;
                case MAR:
                    numDays = 59;
                    break;
                case APR:
                    numDays = 90;
                    break;
                case MAY:
                    numDays = 120;
                    break;
                case JUN:
                    numDays = 151;
                    break;
                case JUL:
                    numDays = 181;
                    break;
                case AUG:
                    numDays = 212;
                    break;
                case SEP:
                    numDays = 243;
                    break;
                case OCT:
                    numDays = 273;
                    break;
                case NOV:
                    numDays = 304;
                    break;
                case DEC:
                    numDays = 334;
                    break;
            }
            numDays += days;
            for (int i = 0; i < numDays; i++){
                secondsCalc += SECONDS_IN_DAY;
            }
            for (int i = 0; i < hours; i++){
                secondsCalc += SECONDS_IN_HOUR;
            }
            for (int i = 0; i < minutes; i++){
                secondsCalc += SECONDS_IN_MINUTE;
            }
            secondsCalc += seconds;
            this.seconds = secondsCalc;
        }
    }

    private void init_position(){
        final int SEMIMINOR_AXIS = 149577046; // km
        final int SEMIMAJOR_AXIS = 149597900; // km
        final int SUN_FOCUS_OFFSET = 2499787; // km
        final int NUM_SECONDS_IN_YEAR = 31622400;

        this.angle = Math.atan2((double)this.seconds, ((double)NUM_SECONDS_IN_YEAR/2.0));
        this.xPos = SEMIMAJOR_AXIS*Math.cos(this.angle);
        this.yPos = SEMIMINOR_AXIS*Math.sin(this.angle);


        double xSun = Math.pow(this.xPos - SUN_FOCUS_OFFSET, 2);
        double ySun = Math.pow(this.yPos, 2);
        this.distanceFromSun = Math.sqrt(xSun + ySun);

    }

    public double getxPos() {
        return xPos;
    }

    public double getyPos(){
        return yPos;
    }

    public double getAngle(){
        return angle * 180;
    }

    public double getDistanceFromSun(){
        return distanceFromSun;
    }
}
