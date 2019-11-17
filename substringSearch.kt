fun main() {
    val string = "\"A computer once beat me at chess, but it was no match for me at kick boxing.\" — Emo Philips"
    var substring = "chess"
    
    if (string.containsSubstring(substring))
        println("The string does contain the substring")
    else
        println("The string does not contain the substring")
}

//extend String class with method containsSubstring()
fun String.containsSubstring(substring: String): Boolean {
    var containsSubstring = false

    //if the original string is shorter than the potential substring, return false
    if (this.length<=substring.length)
        return containsSubstring


    for (i in 0..this.length-substring.length-1) {
        if (this.substring(i, i+substring.length).compareTo(substring)==0) {
            //subtring was found; set value to  true and break out of the loop
            containsSubstring = true
            break
        }
    }

    return containsSubstring
}