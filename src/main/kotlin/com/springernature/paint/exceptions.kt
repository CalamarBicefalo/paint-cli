package com.springernature.paint

open class PaintException(message: String) : RuntimeException(message)
class ShapeOutOfCanvasException : PaintException("Shape out of canvas")
class InvalidCommandException : PaintException("Invalid command")