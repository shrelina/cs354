fun main(args: Array<String>) {
    val string = "\"A computer once beat me at chess, but it was no match for me at kick boxing.\" — Emo Philips"
    var substring = "chess"
    println("Original String: $string")
    println("Potential Substring: $substring")

    if (string.containsSubstring(substring))
        println("\nThe string contains the substring")
    else
        println("\nThe string does not contain the substring")
}

//extend String class with method containsSubstring()
fun String.containsSubstring(substring: String): Boolean {
    var containsSubstring = false

    //if the original string is shorter than the potential substring, return false
    if (this.length<=substring.length)
        return containsSubstring

    //look for substring inside original string
    for (i in 0 until this.length-substring.length) {
        if (this.substring(i, i+substring.length).compareTo(substring)==0) {
            //substring was found; set value to  true and break out of the loop
            containsSubstring = true
            break
        }
    }

    return containsSubstring
}