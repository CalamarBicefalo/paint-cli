package com.springernature.paint

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.*
import org.junit.Test

class CanvasTest {

    @Test
    fun `canvas to string - when created - prints graphical representation`() {
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
}