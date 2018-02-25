package org.openbw.bwapi4j;

import org.junit.Before;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.FromDataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(Theories.class)
public class BWMapImplTest {
    private final BWMapImpl sut = new BWMapImpl();

    @DataPoints("validPositions")
    public final static WalkPosition[] validMapPositions = {
            new WalkPosition(0, 0),
            new WalkPosition(9, 19),
    };

    @DataPoints("invalidPositions")
    public final static WalkPosition[] invalidMapPositions = {
            new WalkPosition(-1, 0),
            new WalkPosition(5, 20),
    };

    @Before
    public void setup() {
        sut.width = 10;
        sut.height = 20;
    }

    @Theory
    public void shouldAcceptValidPositions(@FromDataPoints("validPositions") WalkPosition walkPosition) {
        assertTrue(sut.isValidPosition(walkPosition));
    }

    @Theory
    public void shouldDenyInvalidPositions(@FromDataPoints("invalidPositions") WalkPosition walkPosition) {
        assertFalse(sut.isValidPosition(walkPosition));
    }
}