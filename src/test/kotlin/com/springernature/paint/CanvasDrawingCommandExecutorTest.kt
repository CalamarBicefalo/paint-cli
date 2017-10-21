package com.springernature.paint

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
}