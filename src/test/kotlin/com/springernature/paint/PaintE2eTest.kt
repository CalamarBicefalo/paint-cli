package com.springernature.paint

import kotlinx.coroutines.experimental.async
import org.assertj.core.api.Assertions.assertThat
import org.awaitility.Awaitility.*
import org.junit.*
import org.junit.contrib.java.lang.system.SystemOutRule
import org.junit.contrib.java.lang.system.TextFromStandardInputStream.emptyStandardInputStream
import java.util.regex.Pattern


class PaintE2eTest {

    @get:Rule
     val systemOutRule = SystemOutRule().enableLog()

    @get:Rule
    val systemInMock = emptyStandardInputStream()

    @Before
    fun setUpStreams() {
        clearOutput()
    }

    @Test
    fun `paint - user drawing journey`() {
        async {
            startPaint()
        }

        assertOutput("Enter command: ")

        sendCommand("C 10 6")
        assertCanvas("""
                        ------------
                        |          |
                        |          |
                        |          |
                        |          |
                        |          |
                        |          |
                        ------------
                        """)

        sendCommand("L 2 3 6 3")
        assertCanvas("""
                        ------------
                        |          |
                        |          |
                        | xxxxx    |
                        |          |
                        |          |
                        |          |
                        ------------
                        """)

        sendCommand("L 2 3 16 3")
        assertOutput("Shape out of canvas\n")


        sendCommand("R 3 4 10 6")
        assertCanvas("""
                        ------------
                        |          |
                        |          |
                        | xxxxx    |
                        |  xxxxxxxx|
                        |  x      x|
                        |  xxxxxxxx|
                        ------------
                        """)

        sendCommand("B 1 1 t")
        assertCanvas("""
                       ------------
                       |tttttttttt|
                       |tttttttttt|
                       |txxxxxtttt|
                       |ttxxxxxxxx|
                       |ttx      x|
                       |ttxxxxxxxx|
                       ------------
                        """)

        sendCommand("B 4 5 p")
        assertCanvas("""
                       ------------
                       |tttttttttt|
                       |tttttttttt|
                       |txxxxxtttt|
                       |ttxxxxxxxx|
                       |ttxppppppx|
                       |ttxxxxxxxx|
                       ------------
                        """)

        sendCommand("B 3 4 u")
        assertCanvas("""
                       ------------
                       |tttttttttt|
                       |tttttttttt|
                       |tuuuuutttt|
                       |ttuuuuuuuu|
                       |ttuppppppu|
                       |ttuuuuuuuu|
                       ------------
                        """)

        sendCommand("Q")
        assertOutput("Thanks for drawing!\n")
    }

    private fun assertOutput(content: String) {
        await().untilAsserted { assertThat(readOutput()).isEqualTo(content) }
        clearOutput()
    }

    private fun assertCanvas(canvas: String){
        val trimSpaces = Pattern.compile("^(\\s)+", Pattern.MULTILINE).toRegex()
        assertOutput(canvas
                .trim()
                .replace(trimSpaces,"")
                + "\n\nEnter command: ")
    }

    private fun readOutput(): String? {
        return systemOutRule.log
    }

    fun sendCommand(command: String) {
        systemInMock.provideLines(command)
    }
    fun clearOutput() = systemOutRule.clearLog()

}