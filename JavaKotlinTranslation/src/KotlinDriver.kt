import java.text.NumberFormat
import java.util.Scanner

object KotlinDriver {
    enum class dateOption {
        APHELION,
        PERIHELION,
        JAN, FEB, MAR, APR,
        MAY, JUN, JUL, AUG,
        SEP, OCT, NOV, DEC,
        INVALID,
        QUIT
    }

    private fun getUserDateOption(input: String): dateOption {
        val retVal: dateOption

        when (input) {
            "aphelion" -> retVal = dateOption.APHELION
            "perihelion" -> retVal = dateOption.PERIHELION
            "january" -> retVal = dateOption.JAN
            "february" -> retVal = dateOption.FEB
            "march" -> retVal = dateOption.MAR
            "april" -> retVal = dateOption.APR
            "may" -> retVal = dateOption.MAY
            "june" -> retVal = dateOption.JUN
            "july" -> retVal = dateOption.JUL
            "august" -> retVal = dateOption.AUG
            "september" -> retVal = dateOption.SEP
            "october" -> retVal = dateOption.OCT
            "november" -> retVal = dateOption.NOV
            "december" -> retVal = dateOption.DEC
            "q" -> retVal = dateOption.QUIT
            else -> retVal = dateOption.INVALID
        }

        return retVal
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val userInput = Scanner(System.`in`)
        var input: String
        var option = dateOption.INVALID

        while (option == dateOption.INVALID) {
            print("Enter 'aphelion', 'perihelion', or a month of the year (or 'q' to exit): ")
            input = userInput.nextLine().toLowerCase()
            println()
            option = getUserDateOption(input)
            if (option == dateOption.INVALID) {
                println("Invalid option.")
            }
        }

        if (option != dateOption.QUIT) {
            val koc: KotlinOrbitCalc
            if (option == dateOption.APHELION || option == dateOption.PERIHELION) {
                koc = KotlinOrbitCalc(option, 0, 0, 0, 0)
            } else {
                var day = -1
                while (day < 0) {
                    try {
                        print("What day of the month would you like to check? ")
                        day = userInput.nextInt()
                        println()
                    } catch (e: NumberFormatException) {
                        println()
                        println("Invalid number")
                        day = -1
                    }

                }

                var hour = -1
                while (hour < 0) {
                    try {
                        print("What hour in the day would you like to check (24 hour time)? ")
                        hour = userInput.nextInt()
                        if (hour > 24) {
                            throw NumberFormatException()
                        }
                    } catch (e: NumberFormatException) {
                        println()
                        println("Invalid number")
                        hour = -1
                    }

                }
                var minute = -1
                while (minute < 0) {
                    try {
                        print("What minute in this hour would you like to check? ")
                        minute = userInput.nextInt()
                        if (minute > 59) {
                            throw NumberFormatException()
                        }
                        println()
                    } catch (e: NumberFormatException) {
                        println()
                        println("Invalid number")
                        minute = -1
                    }

                }

                var second = -1
                while (second < 0) {
                    try {
                        print("What second in this minute would you like to check? ")
                        second = userInput.nextInt()
                        if (second > 59) {
                            throw NumberFormatException()
                        }
                        println()
                    } catch (e: NumberFormatException) {
                        println()
                        println("Invalid number")
                        second = -1
                    }

                }
                koc = KotlinOrbitCalc(option, day, hour, minute, second)
            }

            val myFormat = NumberFormat.getInstance()
            myFormat.isGroupingUsed = true // this will also round numbers, 3

            println("X distance from center of ellipse: " + myFormat.format(koc.getxPos()) + " km")
            println("Y distance from center of ellipse: " + myFormat.format(koc.getyPos()) + " km")
            System.out.printf(
                "Angle of earth relative to Sun (with Jan 1, 12:00 a.m. being 0 deg): %.2f degrees\n",
                koc.getAngle()
            )
            println("Earth distance from sun: " + myFormat.format(koc.distanceFromSun) + " km")
        }
    }
}
