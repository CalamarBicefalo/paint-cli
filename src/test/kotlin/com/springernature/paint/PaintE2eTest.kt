package com.springernature.paint

import kotlinx.coroutines.experimental.async
import org.assertj.core.api.Assertions.assertThat
import org.awaitility.Awaitility.*
import org.junit.*
import org.junit.contrib.java.lang.system.SystemOutRule
import org.junit.contrib.java.lang.system.TextFromStandardInputStream.emptyStandardInputStream
import java.util.regex.Pattern

/*
This is an E2E test following a User Journey approach. It exercises the app full stack.

Particularly in WEB development user journeys are a great tool to get confidence on your code while reducing the
painful brittleness, flakiness and slowness associated with these type of tests.

A user journey sticks to the happy path, de-risks all the plumbing in the app and tries to not repeat the same
operations over and over.

e.g. In a Web app, granular tests might imply repeated login which has a toll on reliability and speed.

The key of these tests is to improve readability so you can follow them as a story. Which is what I tried to do here
by creating helper functions.
 */
class PaintE2eTest {

    @Test
    fun `paint - user drawing journey`() {
        startPaintInBackground()
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
        assertOutput("Shape out of canvas\nEnter command: ")


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

    /*
    Typically this is placed on top, however for user journeys I find useful to find the journey script as the
    very first thing. No more than one journey per file is desired as a consequence.
     */
    @get:Rule
    val systemOutRule = SystemOutRule().enableLog()

    @get:Rule
    val systemInMock = emptyStandardInputStream()

    @Before
    fun setUpStreams() {
        clearOutput()
    }

    private fun startPaintInBackground() {
        async {
            startPaint()
        }
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