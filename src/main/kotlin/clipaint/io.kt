package clipaint

fun startPaint() {
    var commandExecutor: CanvasDrawingCommandExecutor? = null
    print("Enter command: ")
    while (true) {
        val command: String = readLine()?.trim() ?: continue
        try {
            if (CanvasDrawingCommandExecutor.isCreateCommand(command)) {
                commandExecutor = CanvasDrawingCommandExecutor.create(command)
                println(commandExecutor.renderCanvas())
                print("Enter command: ")
            } else if (CanvasDrawingCommandExecutor.isQuitCommand(command)) {
                println("Thanks for drawing!")
                break
            } else {
                println(commandExecutor?.execute(command)?.renderCanvas() ?: "Please, create a canvas first")
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

        private val CLEAR = "C"
        private val CREATE = "C"
        private val QUIT = "Q"
        private val LINE = "L"
        private val RECTANGLE = "R"
        private val FILL_BUCKET = "B"

        fun isCreateCommand(command: String): Boolean {
            val createCommand = extractCommand(getParts(command)) == CREATE
            val hasCanvasSize = getParts(command).size > 1
            return createCommand && hasCanvasSize
        }

        fun isQuitCommand(command: String) = extractCommand(getParts(command)) == QUIT

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
            val clearRegex = Regex("^$CLEAR")
            val createRegex = Regex("^$CREATE$pointRegex$")
            val lineRegex = Regex("^$LINE$pointRegex$pointRegex$")
            val rectangleRegex = Regex("^$RECTANGLE$pointRegex$pointRegex$")
            val fillRegex = Regex("^$FILL_BUCKET$pointRegex\\s.$")
            val quitRegex = Regex("^$QUIT$")

            val validCommands = listOf(
                    createRegex, lineRegex, rectangleRegex, fillRegex, quitRegex, clearRegex
            )
            if (validCommands.firstOrNull { command.matches(it) } == null)
                throw InvalidCommandException()
        }

    }

    fun execute(command: String): CanvasDrawingCommandExecutor {
        val parts = getPartsValidating(command)

        when (extractCommand(parts)) {
            LINE -> canvas.draw(Line(extractPoint(parts), extractPoint(parts, 1)))
            RECTANGLE -> canvas.draw(Rectangle(extractPoint(parts), extractPoint(parts, 1)))
            FILL_BUCKET -> canvas.draw(ColourFill(extractPoint(parts), extractColour(parts)))
            CLEAR -> canvas.clear()
        }
        return this // for fluent APIs could use kotlin functional helpers too e.g. .let / .apply ... but I like fluent APIs
    }

    fun renderCanvas() = this.canvas.render()

    private fun extractColour(from: List<String>): Char {
        return from[3].first()
    }

    private fun extractPoint(from: List<String>, index: Int = 0) = Point(
            from[index * 2 + 1].toInt(),
            from[index * 2 + 2].toInt()
    )

}