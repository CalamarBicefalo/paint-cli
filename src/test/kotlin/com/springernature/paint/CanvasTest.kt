package com.springernature.paint

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class CanvasTest {

    @Test
    fun `canvas - when empty - prints empty canas`() {
        val expectedCanvas =
                """#-----
                   #|   |
                   #|   |
                   #|   |
                   #|   |
                   #-----
                   #""".trimMargin("#")
        val canvas = Canvas(3, 4)
        assertThat(canvas.toString()).isEqualTo(expectedCanvas)
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
        assertThat(canvas.toString()).isEqualTo(expectedCanvas)
    }
}