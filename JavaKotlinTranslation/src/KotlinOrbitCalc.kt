class KotlinOrbitCalc(
    option: KotlinDriver.dateOption, days: Int, hours: Int,
    minutes: Int, seconds: Int
) {
    private var seconds: Long = 0
    private var xPos: Double = 0.toDouble()
    private var yPos: Double = 0.toDouble()
    private var angle: Double = 0.toDouble()
    var distanceFromSun: Double = 0.toDouble()
        private set

    init {
        init_seconds(option, days, hours, minutes, seconds)
        init_position()
    }

    private fun init_seconds(
        option: KotlinDriver.dateOption, days: Int, hours: Int,
        minutes: Int, seconds: Int
    ) {
        val SECONDS_IN_DAY = 86400
        val SECONDS_IN_HOUR = 3600
        val SECONDS_IN_MINUTE = 60

        var numDays = 0
        var secondsCalc = 0
        if (option == KotlinDriver.dateOption.PERIHELION) {
            // January 4th - 4 days in
            numDays = 4
            for (i in 0 until numDays) {
                secondsCalc += SECONDS_IN_DAY
            }
            for (i in 0..22) {
                secondsCalc += SECONDS_IN_HOUR
            }
            this.seconds = secondsCalc.toLong()
        } else if (option == KotlinDriver.dateOption.APHELION) {
            // July 4th - 185 days in
            numDays = 185
            for (i in 0 until numDays) {
                secondsCalc += SECONDS_IN_DAY
            }
            for (i in 0..17) {
                secondsCalc += SECONDS_IN_HOUR
            }
            this.seconds = secondsCalc.toLong()
        } else {
            when (option) {
                KotlinDriver.dateOption.JAN -> numDays = 0
                KotlinDriver.dateOption.FEB -> numDays = 31
                KotlinDriver.dateOption.MAR -> numDays = 59
                KotlinDriver.dateOption.APR -> numDays = 90
                KotlinDriver.dateOption.MAY -> numDays = 120
                KotlinDriver.dateOption.JUN -> numDays = 151
                KotlinDriver.dateOption.JUL -> numDays = 181
                KotlinDriver.dateOption.AUG -> numDays = 212
                KotlinDriver.dateOption.SEP -> numDays = 243
                KotlinDriver.dateOption.OCT -> numDays = 273
                KotlinDriver.dateOption.NOV -> numDays = 304
                KotlinDriver.dateOption.DEC -> numDays = 334
            }
            numDays += days
            for (i in 0 until numDays) {
                secondsCalc += SECONDS_IN_DAY
            }
            for (i in 0 until hours) {
                secondsCalc += SECONDS_IN_HOUR
            }
            for (i in 0 until minutes) {
                secondsCalc += SECONDS_IN_MINUTE
            }
            secondsCalc += seconds
            this.seconds = secondsCalc.toLong()
        }
    }

    private fun init_position() {
        val SEMIMINOR_AXIS = 149577046 // km
        val SEMIMAJOR_AXIS = 149597900 // km
        val SUN_FOCUS_OFFSET = 2499787 // km
        val NUM_SECONDS_IN_YEAR = 31622400

        this.angle = Math.atan2(this.seconds.toDouble(), NUM_SECONDS_IN_YEAR.toDouble() / 2.0)
        this.xPos = SEMIMAJOR_AXIS * Math.cos(this.angle)
        this.yPos = SEMIMINOR_AXIS * Math.sin(this.angle)


        val xSun = Math.pow(this.xPos - SUN_FOCUS_OFFSET, 2.0)
        val ySun = Math.pow(this.yPos, 2.0)
        this.distanceFromSun = Math.sqrt(xSun + ySun)

    }

    fun getxPos(): Double {
        return xPos
    }

    fun getyPos(): Double {
        return yPos
    }

    fun getAngle(): Double {
        return angle * 180
    }
}
