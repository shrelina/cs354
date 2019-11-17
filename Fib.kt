/**
 * @author Tray Simpson
 */

fun main() {
    fib(10);
}

fun fib(count: Int) {
    var x = 0
    var y = 1
    var z: Int
    for(i in 1..count){
        println(x)
        z = x
        x = x + y
        y = z
    }
}