fun main() {
    val string = "\"A computer once beat me at chess, but it was no match for me at kick boxing.\" — Emo Philips"
    var substring = "chess"
    
    if (string.containsSubstring(substring))
        println("The string does contain the substring")
    else
        println("The string does not contain the substring")
}

fun String.containsSubstring(substring: String): Boolean {
    var containsSubstring = false
    if (this.length<=substring.length) {
        return containsSubstring
    }

    for (i in 0..this.length-substring.length-1) {
        if (this.subSequence(i, i+substring.length)) {
            containsSubstring = true
            break
        }
    }

    return containsSubstring
}