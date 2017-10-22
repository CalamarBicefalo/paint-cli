package com.springernature.paint

fun startPaint() {
    var commandExecutor: CanvasDrawingCommandExecutor? = null
    print("Enter command: ")
    while (true) {
        val command: String = readLine()?.trim() ?: continue
        try {
            if (CanvasDrawingCommandExecutor.isCreateCommand(command)) {
                commandExecutor = CanvasDrawingCommandExecutor.create(command)
                println(commandExecutor.canvas.render())
                print("Enter command: ")
            } else if (CanvasDrawingCommandExecutor.isQuitCommand(command)) {
                println("Thanks for drawing!")
                break
            } else {
                println(commandExecutor?.execute(command)?.canvas?.render() ?: "Please, create a canvas first")
                print("Enter command: ")
            }
        } catch (e: PaintException) {
            println(e.message)
            print("Enter command: ")
        }
    }

}

class CanvasDrawingCommandExecutor(val canvas: Canvas) {

    companion object {

        fun isCreateCommand(command: String) = extractCommand(getParts(command)) == "C"
        fun isQuitCommand(command: String) = extractCommand(getParts(command)) == "Q"

        fun create(command: String): CanvasDrawingCommandExecutor {
            return CanvasDrawingCommandExecutor(
                    CharCanvas(
                            extractInt(getPartsValidating(command), 1),
                            extractInt(getPartsValidating(command), 2)
                    )
            )
        }

        private fun extractCommand(from: List<String>) = from[0]
        private fun extractInt(from: List<String>, index: Int) = from[index].toInt()
        private fun getParts(command: String) = command.split(" ")
        private fun getPartsValidating(command: String): List<String> {
            validateCommand(command)
            return getParts(command)
        }

        private fun validateCommand(command: String) {
            val pointRegex = "\\s\\d+\\s\\d+"
            val createRegex = Regex("^C$pointRegex$")
            val lineRegex = Regex("^L$pointRegex$pointRegex$")
            val rectangleRegex = Regex("^R$pointRegex$pointRegex$")
            val fillRegex = Regex("^B$pointRegex\\s.$")
            val quitRegex = Regex("^Q$")

            val validCommands = listOf(
                    createRegex, lineRegex, rectangleRegex, fillRegex, quitRegex
            )
            if (validCommands.firstOrNull { command.matches(it) } == null)
                throw InvalidCommandException()
        }

    }

    fun execute(command: String): CanvasDrawingCommandExecutor {
        val parts = getPartsValidating(command)

        when (extractCommand(parts)) {
            "L" -> canvas.draw(Line(extractPoint(parts), extractPoint(parts, 1)))
            "R" -> canvas.draw(Rectangle(extractPoint(parts), extractPoint(parts, 1)))
            "B" -> canvas.draw(ColourFill(extractPoint(parts), extractColour(parts)))
        }
        return this
    }

    private fun extractColour(from: List<String>): Char {
        return from[3].first()
    }

    private fun extractPoint(from: List<String>, index: Int = 0) = Point(
            from[index * 2 + 1].toInt(),
            from[index * 2 + 2].toInt()
    )

}