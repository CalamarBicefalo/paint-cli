package com.springernature.paint

import com.springernature.paint.CanvasDrawingCommandExecutor.Companion.isCreateCommand
import com.springernature.paint.CanvasDrawingCommandExecutor.Companion.isQuitCommand

fun main(args: Array<String>) {
    startPaint()
}

fun startPaint() {
    var commandExecutor: CanvasDrawingCommandExecutor? = null
    print("Enter command: ")
    while (true) {
        val command: String = readLine() ?: continue
        if (isCreateCommand(command)) {
            commandExecutor = CanvasDrawingCommandExecutor.create(command)
            println(commandExecutor.canvas.render())
            print("Enter command: ")
        } else if (isQuitCommand(command)) {
            println("Thanks for drawing!")
            break
        } else {
            println(commandExecutor?.execute(command)?.canvas?.render() ?: println("Please, create a canvas first"))
            print("Enter command: ")
        }
    }

}