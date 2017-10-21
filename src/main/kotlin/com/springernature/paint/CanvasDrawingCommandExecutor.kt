package com.springernature.paint

class CanvasDrawingCommandExecutor(val canvas: Canvas) {

    companion object {

        fun isCreateCommand(command: String) = extractCommand(getParts(command)) == "C"
        fun isQuitCommand(command: String) = extractCommand(getParts(command)) == "Q"

        fun create(command: String): CanvasDrawingCommandExecutor {
            return CanvasDrawingCommandExecutor(
                    CharCanvas(
                            extractInt(getParts(command),1),
                            extractInt(getParts(command),2)
                    )
            )
        }
        private fun extractCommand(from: List<String>) = from[0]
        private fun extractInt(from: List<String>, index: Int) = from[index].toInt()
        private fun getParts(command: String) = command.split(" ")

    }

    fun execute(command: String): CanvasDrawingCommandExecutor {
        val parts = getParts(command)

        when (extractCommand(parts)) {
            "L" -> canvas.draw(Line(extractPoint(parts), extractPoint(parts,1)))
            "R" -> canvas.draw(Rectangle(extractPoint(parts), extractPoint(parts,1)))
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