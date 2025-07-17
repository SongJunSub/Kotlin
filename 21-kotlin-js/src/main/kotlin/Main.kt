import kotlinx.browser.document

fun main() {
    document.body?.innerHTML = "<h1>Hello from Kotlin/JS!</h1>"

    val button = document.createElement("button")
    button.textContent = "Click me!"
    document.body?.appendChild(button)

    button.onclick = { _ ->
        document.body?.append("\nButton clicked!")
    }
}