package coroutines

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.delay

class CounterManager {
    private val _count = MutableStateFlow(0)
    val count: StateFlow<Int> = _count.asStateFlow()

    fun increment() {
        _count.value++
    }
}

fun main() = runBlocking {
    val counterManager = CounterManager()

    // Collector 1
    launch {
        counterManager.count.collect { currentCount ->
            println("Collector 1: Count is $currentCount")
        }
    }

    // Collector 2
    launch {
        delay(100)
        counterManager.count.collect { currentCount ->
            println("Collector 2: Count is $currentCount")
        }
    }

    delay(200)
    counterManager.increment() // count = 1
    delay(200)
    counterManager.increment() // count = 2
    delay(200)
    counterManager.increment() // count = 3

    println("Final count: ${counterManager.count.value}")
}