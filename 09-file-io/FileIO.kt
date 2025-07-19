package `09-file-io`

import java.io.File

fun main() {
    // 파일을 다루기 위한 File 객체 생성
    // 경로 구분자는 시스템에 맞게 자동으로 변환됨
    val file = File("example.txt")

    // 1. 파일에 텍스트 쓰기 (기존 내용 덮어쓰기)
    file.writeText("Hello, Kotlin File I/O!\n")
    println("${file.name}에 텍스트를 썼습니다.")

    // 2. 파일에 텍스트 추가하기
    file.appendText("이것은 추가된 줄입니다.\n")
    println("${file.name}에 텍스트를 추가했습니다.")

    // 3. 파일 전체 내용 읽기
    val content = file.readText()
    println("\n--- ${file.name} 전체 내용 ---")
    println(content)

    // 4. 파일을 한 줄씩 읽어 리스트로 반환
    val lines = file.readLines()
    println("\n--- ${file.name} 한 줄씩 읽기 ---")
    lines.forEach { println(it) }

    // 5. 대용량 파일을 효율적으로 처리 (use 블록으로 자동 리소스 관리)
    println("\n--- use 블록으로 안전하게 처리 ---")
    file.bufferedReader().use {
        it.forEachLine { line -> println(line) }
    }

    // 6. 파일 삭제
    if (file.exists()) {
        file.delete()
        println("\n${file.name} 파일을 삭제했습니다.")
    }

    // 7. 디렉토리 내 파일 목록 가져오기 및 필터링
    val currentDir = File(".") // 현재 디렉토리
    println("\n--- 현재 디렉토리의 .kt 파일 목록 ---")
    currentDir.listFiles()?.filter { it.isFile && it.extension == "kt" }?.forEach { 
        println(it.name)
    }
}
