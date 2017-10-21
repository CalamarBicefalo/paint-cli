package com.springernature.paint

class Canvas(val width: Int, val height: Int) {

    override fun toString(): String {
        var output = ""
        val horizontal = drawHorizontalDivider()
        output += horizontal
        for (i in 1..height){
            output += "|" + " ".repeat(width) + "|\n"
        }
        output += horizontal
        return output
    }

    private fun drawHorizontalDivider(): String {
        var horizontal = "-".repeat(width + 2)
        horizontal += "\n"
        return horizontal
    }
}