package com.springernature.paint

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.*
import org.junit.Assert.*
import org.junit.Test

class LineTest {

    @Test
    fun `isOblique - when horizontal - returns false`() {
        val line = Line(Point(2, 2), Point(7, 2))

        assertThat(line.isOblique()).isFalse()
    }

    @Test
    fun `isOblique - when vertical - returns false`() {
        val line = Line(Point(7, 5), Point(7, 2))

        assertThat(line.isOblique()).isFalse()
    }

    @Test
    fun `isOblique - when oblique - returns true`() {
        val line = Line(Point(2, 2), Point(7, 5))

        assertThat(line.isOblique()).isTrue()
    }
}