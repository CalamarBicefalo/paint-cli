package com.springernature.paint

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.runners.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CanvasDrawingCommandExecutorTest {

    @InjectMocks
    lateinit var subject: CanvasDrawingCommandExecutor

    @Mock
    lateinit var canvas: Canvas

    @Test
    fun `draw line - draws line in canvas`() {
        subject.execute("L 1 2 4 2")

        verify(canvas).draw(Line(Point(1,2), Point(4,2)))
    }

    @Test
    fun `draw rectangle - draws rectangle in canvas`() {
        subject.execute("R 1 2 6 12")

        verify(canvas).draw(Rectangle(Point(1,2), Point(6,12)))
    }

    @Test
    fun `fill colour - fills colour in canvas`() {
        subject.execute("B 10 3 y")

        verify(canvas).draw(ColourFill(Point(10,3), 'y'))
    }

    @Test
    fun `isCreateCommand - returns true for the right command`() {
        val isCreateCommand = CanvasDrawingCommandExecutor.isCreateCommand("C 12 4")

        assertThat(isCreateCommand).isTrue()
    }

    @Test
    fun `isCreateCommand - returns false for unknown command`() {
        val isCreateCommand = CanvasDrawingCommandExecutor.isCreateCommand("F 12 4")

        assertThat(isCreateCommand).isFalse()
    }

    @Test
    fun `isQuitCommand - returns true for the right command`() {
        val isQuitCommand = CanvasDrawingCommandExecutor.isQuitCommand("Q")

        assertThat(isQuitCommand).isTrue()
    }

    @Test
    fun `isQuitCommand - returns false for unknown command`() {
        val isQuitCommand = CanvasDrawingCommandExecutor.isQuitCommand("F 12 4")

        assertThat(isQuitCommand).isFalse()
    }

    @Test
    fun `create - returns new command executor with preconfigured canvas`() {
        val executor = CanvasDrawingCommandExecutor.create("C 12 4")

        assertThat(executor.canvas).isEqualTo(CharCanvas(12,4))
    }
}