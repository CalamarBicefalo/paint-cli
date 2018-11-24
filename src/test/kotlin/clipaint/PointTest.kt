package clipaint

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class PointTest {

    @Test
    fun `neighbours - return surrounding neighbours`() {
        val point = Point(3, 4)

        val neighbours = point.neighbours()

        assertThat(neighbours).containsExactlyInAnyOrder(
                Point(2, 3),
                Point(3, 3),
                Point(4, 3),
                Point(2, 4),
                Point(4, 4),
                Point(2, 5),
                Point(3, 5),
                Point(4, 5)
        )
    }

    @Test
    fun `getPoints - returns point`() {
        val point = Point(3, 4)

        val points = point.getPoints()

        assertThat(points).containsExactlyInAnyOrder(Point(3, 4))
    }


    @Test
    fun `comparing - when both bigger - returns bigger`() {
        assertThat(Point(3, 4) > Point(1, 2)).isTrue()
    }

    @Test
    fun `comparing - when both smaller - returns smaller`() {
        assertThat(Point(1, 2) < Point(6, 7)).isTrue()
    }

    @Test
    fun `comparing - when equals - returns equals`() {
        assertThat(Point(1, 2) == Point(1, 2)).isTrue()
    }

    @Test
    fun `comparing - when x equals - unties with y`() {
        assertThat(Point(1, 2) < Point(1, 4)).isTrue()
    }

    @Test
    fun `comparing - when y equals - uses x`() {
        assertThat(Point(1, 2) < Point(9, 2)).isTrue()
    }
}