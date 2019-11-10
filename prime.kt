// Author: Payton Elsey

fun prime(x: Int) {
    var isPrime = true
    
    for (i in 2..(x/2)) {
        if (x % i == 0) {
            isPrime = false
           	break
        }
    }
    
    if (x == 1) {
        println("1 is neither")
    }
    
    else {
        if (isPrime)
            println(x.toString() + " is prime")
        
        else 
            println(x.toString() + " is not prime")
    }
}

fun main() {
    prime(14)
}
