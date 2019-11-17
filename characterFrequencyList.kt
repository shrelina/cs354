import java.util.*

fun main() {
    val freqMap: MutableMap<Char,Int> = mutableMapOf()  //Create a mapping of characters to frequencies
    print("Enter a string: ")                           //Prompt user to enter string
    val input = readLine().toString()                    //Read line from user input

    //log the frequency of each character, then print
    input.forEach { c -> logChar(c,freqMap) }
    println(freqMap)
}

private fun logChar(c: Char, map: MutableMap<Char,Int>) {
    if (map.contains(c))
        map[c] = map[c]!! + 1   //!! - assert that map[c] is not null
    else
        map[c] = 1              //put value in map with default frequency of one
}