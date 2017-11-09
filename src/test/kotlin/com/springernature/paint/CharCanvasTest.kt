package com.springernature.paint

import org.assertj.core.api.AbstractCharSequenceAssert
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.StringAssert
import org.junit.Test
import java.util.regex.Pattern
import java.util.regex.Pattern.*

class CharCanvasTest {

    private val canvas20x4 = CharCanvas(20, 4)

    @Test
    fun `canvas - when empty - prints empty canvas`() {
        val expectedCanvas =
                """
                   ----------------------
                   |                    |
                   |                    |
                   |                    |
                   |                    |
                   ----------------------
                """

        val canvasPrint = canvas20x4.render()

        assertThat(canvasPrint).isEqualToCanvas(expectedCanvas)
    }

    @Test
    fun `canvas - when horizontal line drawn - prints canvas with line`() {
        val expectedCanvas =
                """
                   ----------------------
                   |                    |
                   |xxxxxx              |
                   |                    |
                   |                    |
                   ----------------------
                """
        canvas20x4.draw(Line(Point(1,2), Point(6,2)))

        val canvasPrint = canvas20x4.render()

        assertThat(canvasPrint).isEqualToCanvas(expectedCanvas)
    }

    @Test
    fun `canvas - when reversed horizontal line drawn - prints canvas with line`() {
        val expectedCanvas =
                """
                   ----------------------
                   |                    |
                   |xxxxxx              |
                   |                    |
                   |                    |
                   ----------------------
                """
        canvas20x4.draw(Line(Point(6,2), Point(1,2)))

        val canvasPrint = canvas20x4.render()

        assertThat(canvasPrint).isEqualToCanvas(expectedCanvas)
    }

    @Test
    fun `canvas - when vertical line drawn - prints canvas with line`() {
        val expectedCanvas =
                """
                   ----------------------
                   |                    |
                   |                    |
                   |     x              |
                   |     x              |
                   ----------------------
                """
        canvas20x4.draw(Line(Point(6,3), Point(6,4)))

        val canvasPrint = canvas20x4.render()

        assertThat(canvasPrint).isEqualToCanvas(expectedCanvas)
    }

    @Test
    fun `canvas - when reversed vertical line drawn - prints canvas with line`() {
        val expectedCanvas =
                """
                   ----------------------
                   |                    |
                   |                    |
                   |     x              |
                   |     x              |
                   ----------------------
                """
        canvas20x4.draw(Line(Point(6,4), Point(6,3)))

        val canvasPrint = canvas20x4.render()

        assertThat(canvasPrint).isEqualToCanvas(expectedCanvas)
    }

    @Test(expected = UnsupportedShapeException::class)
    fun `canvas - when oblique line drawn - throws unssuported shape exception`() {
        canvas20x4.draw(Line(Point(1,1), Point(8,4)))
    }

    @Test
    fun `canvas - when several lines - prints canvas with lines`() {
        val expectedCanvas =
                """
                   ----------------------
                   |     x              |
                   |xxxxxx              |
                   |     x              |
                   |     x              |
                   ----------------------
                """
        canvas20x4.draw(Line(Point(1,2), Point(6,2)))
        canvas20x4.draw(Line(Point(6,1), Point(6,4)))

        val canvasPrint = canvas20x4.render()

        assertThat(canvasPrint).isEqualToCanvas(expectedCanvas)
    }

    @Test(expected = PaintException::class)
    fun `canvas - when line out of canvas - throws PaintException`() {
        canvas20x4.draw(Line(Point(16,2), Point(25,4)))
    }

    @Test
    fun `canvas - when rectangle - prints canvas with rectangle`() {
        val expectedCanvas =
                """
                   ----------------------
                   |                    |
                   |               xxxxx|
                   |               x   x|
                   |               xxxxx|
                   ----------------------
                """
        canvas20x4.draw(Rectangle(Point(16,2), Point(20,4)))

        val canvasPrint = canvas20x4.render()

        assertThat(canvasPrint).isEqualToCanvas(expectedCanvas)
    }

    @Test(expected = PaintException::class)
    fun `canvas - when rectangle out of canvas - throws PaintException`() {
        canvas20x4.draw(Rectangle(Point(16,2), Point(25,4)))
    }

    @Test
    fun `canvas - when filling empty canvas - prints canvas filled`() {
        val expectedCanvas =
                """
                   ----------------------
                   |oooooooooooooooooooo|
                   |oooooooooooooooooooo|
                   |oooooooooooooooooooo|
                   |oooooooooooooooooooo|
                   ----------------------
                """
        canvas20x4.draw(ColourFill(Point(16,2), 'o'))

        val canvasPrint = canvas20x4.render()

        assertThat(canvasPrint).isEqualToCanvas(expectedCanvas)
    }

    @Test
    fun `canvas - when filling inner gaps - prints canvas with filled gaps`() {
        val expectedCanvas =
                """
                   ----------------------
                   |                    |
                   |               xxxxx|
                   |               xtttx|
                   |               xxxxx|
                   ----------------------
                """
        canvas20x4.draw(Rectangle(Point(16,2), Point(20,4)))
        canvas20x4.draw(ColourFill(Point(17,3), 't'))

        val canvasPrint = canvas20x4.render()

        assertThat(canvasPrint).isEqualToCanvas(expectedCanvas)
    }

    @Test
    fun `canvas - when filling outer gaps - prints canvas with filled gaps`() {
        val expectedCanvas =
                """
                   ----------------------
                   |iiiiiiiiiiiiiiiiiiii|
                   |iiiiiiiiiiiiiiixxxxx|
                   |iiiiiiiiiiiiiiix   x|
                   |iiiiiiiiiiiiiiixxxxx|
                   ----------------------
                """
        canvas20x4.draw(Rectangle(Point(16,2), Point(20,4)))
        canvas20x4.draw(ColourFill(Point(4,3), 'i'))

        val canvasPrint = canvas20x4.render()

        assertThat(canvasPrint).isEqualToCanvas(expectedCanvas)
    }

    @Test
    fun `canvas - when filling lines - prints canvas with coloured lines`() {
        val expectedCanvas =
                """
                   ----------------------
                   |                    |
                   |               eeeee|
                   |               e   e|
                   |               eeeee|
                   ----------------------
                """
        canvas20x4.draw(Rectangle(Point(16,2), Point(20,4)))
        canvas20x4.draw(ColourFill(Point(16,3), 'e'))

        val canvasPrint = canvas20x4.render()

        assertThat(canvasPrint).isEqualToCanvas(expectedCanvas)
    }


    @Test(expected = PaintException::class)
    fun `canvas - when filling out of canvas - throws PaintException`() {
        canvas20x4.draw(ColourFill(Point(16,23), 'e'))
    }

}

private fun AbstractCharSequenceAssert<*, String>.isEqualToCanvas(expectedCanvas: String) {
    val trimSpaces = compile("^(\\s)+", MULTILINE).toRegex()
    this.isEqualTo(expectedCanvas
            .trim()
            .replace(trimSpaces,"") + "\n")}
