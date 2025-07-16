package kotlindsl

interface Element {
    fun render(builder: StringBuilder, indent: String)
}

class TextElement(val text: String) : Element {
    override fun render(builder: StringBuilder, indent: String) {
        builder.append("$indent$text\n")
    }
}

@DslMarker
anotation class HtmlTagMarker

@HtmlTagMarker
abstract class Tag(val name: String) : Element {
    val children = arrayListOf<Element>()
    val attributes = hashMapOf<String, String>()

    protected fun <T : Element> doInit(element: T, init: T.() -> Unit): T {
        element.init()
        children.add(element)
        return element
    }

    override fun render(builder: StringBuilder, indent: String) {
        builder.append("$indent<$name")
        for ((attr, value) in attributes) {
            builder.append(" $attr=\"$value\"")
        }
        builder.append(">\n")
        for (c in children) {
            c.render(builder, indent + "  ")
        }
        builder.append("$indent</$name>\n")
    }

    override fun toString(): String {
        val builder = StringBuilder()
        render(builder, "")
        return builder.toString()
    }
}

abstract class TagWithText(name: String) : Tag(name) {
    operator fun String.unaryPlus() {
        children.add(TextElement(this))
    }
}

class HTML : TagWithText("html") {
    fun head(init: Head.() -> Unit) = doInit(Head(), init)
    fun body(init: Body.() -> Unit) = doInit(Body(), init)
}

class Head : TagWithText("head") {
    fun title(init: Title.() -> Unit) = doInit(Title(), init)
}

class Title : TagWithText("title")

class Body : TagWithText("body") {
    fun h1(init: H1.() -> Unit) = doInit(H1(), init)
    fun p(init: P.() -> Unit) = doInit(P(), init)
    fun a(href: String, init: A.() -> Unit) {
        val a = A()
        a.attributes["href"] = href
        doInit(a, init)
    }
}

class H1 : TagWithText("h1")
class P : TagWithText("p")
class A : TagWithText("a")

fun html(init: HTML.() -> Unit): HTML {
    val html = HTML()
    html.init()
    return html
}

fun main() {
    val myHtml = html {
        head {
            title {
                +"My HTML Page"
            }
        }
        body {
            h1 {
                +"Welcome to Kotlin DSL"
            }
            p {
                +"This is an example of building HTML using Kotlin DSL."
            }
            a("https://kotlinlang.org") {
                +"Kotlin Official Website"
            }
        }
    }
    println(myHtml)
}
