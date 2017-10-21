package com.springernature.paint

class CanvasDrawingCommandExecutor(val canvas: Canvas) {

    fun execute(command: String) {
        val parts = command.split(" ")

        when (extractCommand(parts)) {
            "L" -> canvas.draw(Line(extractPoint(parts), extractPoint(parts,1)))
            "R" -> canvas.draw(Rectangle(extractPoint(parts), extractPoint(parts,1)))
            "B" -> canvas.draw(ColourFill(extractPoint(parts), extractColour(parts)))
        }
    }

    private fun extractCommand(from: List<String>) = from[0]

    private fun extractColour(from: List<String>): Char {
        return from[3].first()
    }

    private fun extractPoint(from: List<String>, index: Int = 0) = Point(
            from[index * 2 + 1].toInt(),
            from[index * 2 + 2].toInt()
    )

}