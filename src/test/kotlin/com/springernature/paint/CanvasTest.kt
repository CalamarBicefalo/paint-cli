package com.springernature.paint

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class CanvasTest {

    @Test
    fun `canvas - when empty - prints empty canvas`() {
        val expectedCanvas =
                """#-----
                   #|   |
                   #|   |
                   #|   |
                   #|   |
                   #-----
                   #""".trimMargin("#")
        val canvas = Canvas(3, 4)

        val canvasPrint = canvas.toString()

        assertThat(canvasPrint).isEqualTo(expectedCanvas)
    }

    @Test
    fun `canvas - when horizontal line drawn - prints canvas with line`() {
        val expectedCanvas =
                """#----------------------
                   #|                    |
                   #|xxxxxx              |
                   #|                    |
                   #|                    |
                   #----------------------
                   #""".trimMargin("#")
        val canvas = Canvas(20, 4)
        canvas.draw(Line(Point(1,2), Point(6,2)))

        val canvasPrint = canvas.toString()

        assertThat(canvasPrint).isEqualTo(expectedCanvas)
    }

    @Test
    fun `canvas - when reversed horizontal line drawn - prints canvas with line`() {
        val expectedCanvas =
                """#----------------------
                   #|                    |
                   #|xxxxxx              |
                   #|                    |
                   #|                    |
                   #----------------------
                   #""".trimMargin("#")
        val canvas = Canvas(20, 4)
        canvas.draw(Line(Point(6,2), Point(1,2)))

        val canvasPrint = canvas.toString()

        assertThat(canvasPrint).isEqualTo(expectedCanvas)
    }

    @Test
    fun `canvas - when vertical line drawn - prints canvas with line`() {
        val expectedCanvas =
                """#----------------------
                   #|                    |
                   #|                    |
                   #|     x              |
                   #|     x              |
                   #----------------------
                   #""".trimMargin("#")
        val canvas = Canvas(20, 4)
        canvas.draw(Line(Point(6,3), Point(6,4)))

        val canvasPrint = canvas.toString()

        assertThat(canvasPrint).isEqualTo(expectedCanvas)
    }

    @Test
    fun `canvas - when reversed vertical line drawn - prints canvas with line`() {
        val expectedCanvas =
                """#----------------------
                   #|                    |
                   #|                    |
                   #|     x              |
                   #|     x              |
                   #----------------------
                   #""".trimMargin("#")
        val canvas = Canvas(20, 4)
        canvas.draw(Line(Point(6,4), Point(6,3)))

        val canvasPrint = canvas.toString()

        assertThat(canvasPrint).isEqualTo(expectedCanvas)
    }

    @Test
    fun `canvas - when several lines - prints canvas with lines`() {
        val expectedCanvas =
                """#----------------------
                   #|     x              |
                   #|xxxxxx              |
                   #|     x              |
                   #|     x              |
                   #----------------------
                   #""".trimMargin("#")
        val canvas = Canvas(20, 4)
        canvas.draw(Line(Point(1,2), Point(6,2)))
        canvas.draw(Line(Point(6,1), Point(6,4)))

        val canvasPrint = canvas.toString()

        assertThat(canvasPrint).isEqualTo(expectedCanvas)
    }

    @Test
    fun `canvas - when rectangle - prints canvas with rectangle`() {
        val expectedCanvas =
                """#----------------------
                   #|                    |
                   #|               xxxxx|
                   #|               x   x|
                   #|               xxxxx|
                   #----------------------
                   #""".trimMargin("#")
        val canvas = Canvas(20, 4)
        canvas.draw(Rectangle(Point(16,2), Point(20,4)))

        val canvasPrint = canvas.toString()

        assertThat(canvasPrint).isEqualTo(expectedCanvas)
    }
}