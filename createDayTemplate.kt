import java.io.File

fun main(args: Array<String>) {
    if (args.size != 1) {
        println("Usage: add day <day_number>")
        return
    }

    val dayNumber = args[0].toIntOrNull()
    if (dayNumber == null || dayNumber <= 0) {
        println("Invalid day number: ${args[0]}")
        return
    }

    val templateFile = File("kotlin/solutions/Template8.kt")
    val newFile = File("kotlin/solutions/Day${dayNumber.toString().padStart(2, '0')}.kt")

    if (!templateFile.exists()) {
        println("Template file does not exist: ${templateFile.path}")
        return
    }

    if (newFile.exists()) {
        println("File already exists: ${newFile.path}")
        return
    }

    templateFile.copyTo(newFile)
    println("Created new file: ${newFile.path}")
}