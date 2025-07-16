package coroutines

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val channel = Channel<Int>()
    launch {
        // producer
        for (x in 1..5) {
            println("Sending $x")
            channel.send(x)
        }
        channel.close() // We're done sending
    }
    // consumer
    for (y in channel) {
        println("Received $y")
    }
    println("Done!")
}