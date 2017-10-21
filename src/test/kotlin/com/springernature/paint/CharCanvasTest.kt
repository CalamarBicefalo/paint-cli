package com.springernature.paint

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class CharCanvasTest {

    private val canvas20x4 = CharCanvas(20, 4)

    @Test
    fun `canvas - when empty - prints empty canvas`() {
        val expectedCanvas =
                """#----------------------
                   #|                    |
                   #|                    |
                   #|                    |
                   #|                    |
                   #----------------------
                   #""".trimMargin("#")

        val canvasPrint = canvas20x4.render()

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
        canvas20x4.draw(Line(Point(1,2), Point(6,2)))

        val canvasPrint = canvas20x4.render()

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
        canvas20x4.draw(Line(Point(6,2), Point(1,2)))

        val canvasPrint = canvas20x4.render()

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
        canvas20x4.draw(Line(Point(6,3), Point(6,4)))

        val canvasPrint = canvas20x4.render()

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
        canvas20x4.draw(Line(Point(6,4), Point(6,3)))

        val canvasPrint = canvas20x4.render()

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
        canvas20x4.draw(Line(Point(1,2), Point(6,2)))
        canvas20x4.draw(Line(Point(6,1), Point(6,4)))

        val canvasPrint = canvas20x4.render()

        assertThat(canvasPrint).isEqualTo(expectedCanvas)
    }

    @Test(expected = PaintException::class)
    fun `canvas - when line out of canvas - throws PaintException`() {
        canvas20x4.draw(Line(Point(16,2), Point(25,4)))
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
        canvas20x4.draw(Rectangle(Point(16,2), Point(20,4)))

        val canvasPrint = canvas20x4.render()

        assertThat(canvasPrint).isEqualTo(expectedCanvas)
    }

    @Test(expected = PaintException::class)
    fun `canvas - when rectangle out of canvas - throws PaintException`() {
        canvas20x4.draw(Rectangle(Point(16,2), Point(25,4)))
    }

    @Test
    fun `canvas - when filling empty canvas - prints canvas filled`() {
        val expectedCanvas =
                """#----------------------
                   #|oooooooooooooooooooo|
                   #|oooooooooooooooooooo|
                   #|oooooooooooooooooooo|
                   #|oooooooooooooooooooo|
                   #----------------------
                   #""".trimMargin("#")
        canvas20x4.draw(ColourFill(Point(16,2), 'o'))

        val canvasPrint = canvas20x4.render()

        assertThat(canvasPrint).isEqualTo(expectedCanvas)
    }

    @Test
    fun `canvas - when filling inner gaps - prints canvas with filled gaps`() {
        val expectedCanvas =
                """#----------------------
                   #|                    |
                   #|               xxxxx|
                   #|               xtttx|
                   #|               xxxxx|
                   #----------------------
                   #""".trimMargin("#")
        canvas20x4.draw(Rectangle(Point(16,2), Point(20,4)))
        canvas20x4.draw(ColourFill(Point(17,3), 't'))

        val canvasPrint = canvas20x4.render()

        assertThat(canvasPrint).isEqualTo(expectedCanvas)
    }

    @Test
    fun `canvas - when filling outer gaps - prints canvas with filled gaps`() {
        val expectedCanvas =
                """#----------------------
                   #|iiiiiiiiiiiiiiiiiiii|
                   #|iiiiiiiiiiiiiiixxxxx|
                   #|iiiiiiiiiiiiiiix   x|
                   #|iiiiiiiiiiiiiiixxxxx|
                   #----------------------
                   #""".trimMargin("#")
        canvas20x4.draw(Rectangle(Point(16,2), Point(20,4)))
        canvas20x4.draw(ColourFill(Point(4,3), 'i'))

        val canvasPrint = canvas20x4.render()

        assertThat(canvasPrint).isEqualTo(expectedCanvas)
    }

    @Test
    fun `canvas - when filling lines - prints canvas with coloured lines`() {
        val expectedCanvas =
                """#----------------------
                   #|                    |
                   #|               eeeee|
                   #|               e   e|
                   #|               eeeee|
                   #----------------------
                   #""".trimMargin("#")
        canvas20x4.draw(Rectangle(Point(16,2), Point(20,4)))
        canvas20x4.draw(ColourFill(Point(16,3), 'e'))

        val canvasPrint = canvas20x4.render()

        assertThat(canvasPrint).isEqualTo(expectedCanvas)
    }


    @Test(expected = PaintException::class)
    fun `canvas - when filling out of canvas - throws PaintException`() {
        canvas20x4.draw(ColourFill(Point(16,23), 'e'))
    }
}