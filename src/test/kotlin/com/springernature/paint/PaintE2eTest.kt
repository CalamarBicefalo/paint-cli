package com.springernature.paint

import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream


class PaintE2eTest {

    private val outContent = ByteArrayOutputStream()

    @Before
    fun setUpStreams() {
        System.setOut(PrintStream(outContent))
    }

    @After
    fun cleanUpStreams() {
        System.setOut(null)
    }

    @Test
    fun `paint - when starting the application - prompts users to enter command`() {
        startPaint()
        assertThat(outContent.toString()).isEqualTo("Enter command: ")
    }

}