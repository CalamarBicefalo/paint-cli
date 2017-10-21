package com.springernature.paint

open class PaintException(message: String) : RuntimeException(message)
class ShapeOutOfCanvasException : PaintException("Shape out of canvas")
class InvalidCommandException : PaintException("Invalid command")
class UnsupportedShapeException : PaintException("That shape is not supported yet")