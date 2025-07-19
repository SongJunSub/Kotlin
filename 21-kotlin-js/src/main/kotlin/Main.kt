import kotlinx.browser.document
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLUListElement
import org.w3c.dom.HTMLLIElement

fun main() {
    document.body?.innerHTML = "<h1>Hello from Kotlin/JS!</h1>"

    val button = document.createElement("button")
    button.textContent = "Click me!"
    document.body?.appendChild(button)

    button.onclick = { _ ->
        document.body?.append("\nButton clicked!")
    }

    // 동적으로 리스트 생성 예제
    val listContainer = document.createElement("div") as HTMLDivElement
    listContainer.id = "list-container"
    document.body?.appendChild(listContainer)

    val items = listOf("Item 1", "Item 2", "Item 3")
    createList(listContainer, items)
}

fun createList(container: HTMLDivElement, items: List<String>) {
    val ul = document.createElement("ul") as HTMLUListElement
    items.forEach { itemText ->
        val li = document.createElement("li") as HTMLLIElement
        li.textContent = itemText
        ul.appendChild(li)
    }
    container.appendChild(ul)
}