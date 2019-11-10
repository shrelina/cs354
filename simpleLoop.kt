// Author: Payton Elsey

fun main() {
    var courses = arrayOf("Operating Systems", "Data Structures", "Programming Languages")
    for (course in courses) {
        if ("Languages" in course)
        	println("We are in " + course)
        else
        	println("We are NOT in " + course)
    }
}
