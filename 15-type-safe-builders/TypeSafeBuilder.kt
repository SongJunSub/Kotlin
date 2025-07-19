package `15-type-safe-builders`

// --- DSL의 데이터 모델 ---

// HTML 태그를 표현하는 기본 인터페이스
interface Element {
    fun render(builder: StringBuilder, indent: String)
}

// 텍스트를 표현하는 클래스
class TextElement(val text: String) : Element {
    override fun render(builder: StringBuilder, indent: String) {
        builder.append("$indent$text\n")
    }
}

// 모든 태그의 부모 클래스
abstract class Tag(val name: String) : Element {
    val children = arrayListOf<Element>()

    // 자식 태그를 추가하는 함수
    protected fun <T : Element> initTag(tag: T, init: T.() -> Unit): T {
        tag.init()
        children.add(tag)
        return tag
    }

    override fun render(builder: StringBuilder, indent: String) {
        builder.append("$indent<$name>\n")
        for (c in children) {
            c.render(builder, "$indent  ")
        }
        builder.append("$indent</$name>\n")
    }
}

// --- HTML DSL 구현 ---

class HTML : Tag("html")
class Table : Tag("table")
class TR : Tag("tr")
class TD(val attributes: MutableMap<String, String> = mutableMapOf()) : Tag("td") {
    fun attribute(key: String, String) {
        attributes[key] = value
    }

    override fun render(builder: StringBuilder, indent: String) {
        builder.append("$indent<$name")
        if (attributes.isNotEmpty()) {
            attributes.forEach { (key, value) ->
                builder.append(" $key=\"$value\"")
            }
        }
        builder.append(">$indent</$name>\n")
    }
}

fun html(init: HTML.() -> Unit): HTML = HTML().apply(init)

fun HTML.table(init: Table.() -> Unit) = initTag(Table(), init)
fun Table.tr(init: TR.() -> Unit) = initTag(TR(), init)
fun TR.td(init: TD.() -> Unit) = initTag(TD(), init)
fun TR.td(attributes: Map<String, String>, init: TD.() -> Unit) = initTag(TD(attributes.toMutableMap()), init)
fun Tag.text(content: String) = children.add(TextElement(content))

// --- DSL 사용 예제 ---

fun createHtmlTable(): String {
    val result = html {
        table {
            // 헤더 행
            tr {
                td { text("이름") }
                td { text("나이") }
            }
            // 데이터 행
            val people = listOf("Alice" to 29, "Bob" to 31)
            for ((name, age) in people) {
                tr {
                    td { text(name) }
                    td(mapOf("class" to "data-cell")) { text(age.toString()) }
                }
            }
        }
    }
    val builder = StringBuilder()
    result.render(builder, "")
    return builder.toString()
}

fun main() {
    val tableHtml = createHtmlTable()
    println(tableHtml)
}
