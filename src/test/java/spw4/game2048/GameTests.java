package spw4.game2048;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;


public class GameTests {

    @Test
    void gameCtorTest(){
        var sut = new GameImpl();
    }

    @Nested
    class WhenConstructedTests {
        private Game sut;

        @BeforeEach
        void constructingGame() {
            sut = new GameImpl();
        }

        @Test
        void getMovesWhenGameNotStartedReturnsZero(){
            var expected = 0;

            assertEquals(expected, sut.getMoves());
        }

        @Test
        void getScoreWhenGameNotStartedReturnsZero(){
            var expected = 0;

            assertEquals(expected, sut.getScore());
        }

        @Test
        void getValueAtReturnsZeroOnAllPositionsWhenGameNotStarted(){
            var expected = 0;

            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    assertEquals(expected, sut.getValueAt(i,j));
                }
            }
        }

        @Test
        void isOverReturnsFalseWhenGameNotStarted() {
            var expected = false;

            assertEquals(expected, sut.isOver());
        }

        @Test
        void isWonReturnsFalseWhenGameNotStarted() {
            var expected = false;

            assertEquals(expected, sut.isOver());
        }

        @Nested
        class WhenInitializedTests {
            @BeforeEach
            void initializingGame() {
                sut.initialize();
            }

            @Test
            void getScoreReturnsZero(){
                var expected = 0;

                assertEquals(expected, sut.getScore());
            }

            @Test
            void getMovesReturnsZero(){
                var expected = 0;

                assertEquals(expected, sut.getMoves());
            }

            @Test
            void isOverReturnsFalse() {
                var expected = false;

                assertEquals(expected, sut.isOver());
            }

            @Test
            void isWonReturnsFalse() {
                var expected = false;

                assertEquals(expected, sut.isOver());
            }

            @Test
            void getValueAtReturnsFourteenZerosTwoNotZero(){
                int expected = 14;
                int result = 0;

                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 4; j++) {
                        if (sut.getValueAt(i,j) == 0)
                            result++;
                    }
                }
                assertEquals(expected, result);
            }


            @Test
            void toStringTest() {
                var result = sut.toString();
                assertAll(
                        () -> assertFalse(result.isEmpty()),
                        () -> assertTrue(result.contains("2") || result.contains("4"))
                );

            }

            @Test
            void gameFieldContainsFourProbability(){
                int testBatch = 1_000_000;
                double fourChance = 0.1;
                int expectedNumberOfFour = (int)(testBatch * fourChance * 2 - testBatch * fourChance * fourChance);
                int result = 0;
                double sigma = 0.1; // tolerance
                for (int i = 0; i < 1_000_000; i++) {
                    var sut = new GameImpl();
                    sut.initialize();
                    var str = sut.toString();
                    if(str.contains("4")) result++;
                }
                assertTrue(result <= (expectedNumberOfFour*(1+sigma)) && result >= (expectedNumberOfFour*(1-sigma)));

            }

            @Nested
            class gameplayTests{
                @Test
                void testFirstMoveRight(){
                    RandomStub randomStub = new RandomStub(0,0,8,0,3,0,1,1,4);
                    var sut = new GameImpl(randomStub);
                    var expectedMoves = 1;
                    var expectedScore = 4;
                    var expectedNumberAtCords = 4;
                    var expectedX = 0;
                    var expectedY = 3;
                    sut.initialize();
                    sut.move(Direction.right);
                    assertAll(
                            () -> assertEquals(expectedMoves, sut.getMoves()),
                            () -> assertEquals(expectedScore, sut.getScore()),
                            () -> assertEquals(expectedNumberAtCords, sut.getValueAt(expectedX, expectedY))
                    );
                }

                @Test
                void testFirstMoveLeft(){
                    RandomStub randomStub = new RandomStub(0,0,8,0,3,0,1,1,4);
                    var sut = new GameImpl(randomStub);
                    var expectedMoves = 1;
                    var expectedScore = 4;
                    var expectedNumberAtCords = 4;
                    var expectedX = 0;
                    var expectedY = 0;
                    sut.initialize();
                    sut.move(Direction.left);
                    assertAll(
                            () -> assertEquals(expectedMoves, sut.getMoves()),
                            () -> assertEquals(expectedScore, sut.getScore()),
                            () -> assertEquals(expectedNumberAtCords, sut.getValueAt(expectedX, expectedY))
                    );
                }

                @Test
                void testFirstMoveUp(){
                    RandomStub randomStub = new RandomStub(0,0,8,3,0,0,1,1,4);
                    var sut = new GameImpl(randomStub);
                    var expectedMoves = 1;
                    var expectedScore = 4;
                    var expectedNumberAtCords = 4;
                    var expectedX = 0;
                    var expectedY = 0;
                    sut.initialize();
                    sut.move(Direction.up);
                    assertAll(
                            () -> assertEquals(expectedMoves, sut.getMoves()),
                            () -> assertEquals(expectedScore, sut.getScore()),
                            () -> assertEquals(expectedNumberAtCords, sut.getValueAt(expectedX, expectedY))
                    );
                }

                @Test
                void testFirstMoveDown(){
                    RandomStub randomStub = new RandomStub(0,0,8,3,0,0,1,1,4);
                    var sut = new GameImpl(randomStub);
                    var expectedMoves = 1;
                    var expectedScore = 4;
                    var expectedNumberAtCords = 4;
                    var expectedX = 3;
                    var expectedY = 0;
                    sut.initialize();
                    sut.move(Direction.down);
                    assertAll(
                            () -> assertEquals(expectedMoves, sut.getMoves()),
                            () -> assertEquals(expectedScore, sut.getScore()),
                            () -> assertEquals(expectedNumberAtCords, sut.getValueAt(expectedX, expectedY))
                    );
                }

                @Test
                void getMoveAfterFirstMoveReturnsOne() {
                    RandomStub randomStub = new RandomStub(0,0,8,3,0,0,1,1,4);
                    var sut = new GameImpl(randomStub);
                    int expected = 1;
                    sut.initialize();

                    sut.move(Direction.left);

                    assertEquals(expected, sut.getMoves());
                }

                @Test
                void getScoreAfterTwoTwosMergedReturnsFour() {
                    RandomStub randomStub = new RandomStub(0,0,8,0,3,0,1,1,4);
                    var sut = new GameImpl(randomStub);
                    int expected = 4;
                    sut.initialize();

                    sut.move(Direction.left);

                    assertEquals(expected, sut.getScore());
                }

                @Test
                void isWonIsOverAfterFirstMoveReturnsFalse() {
                    RandomStub randomStub = new RandomStub(0,0,8,0,3,0,1,1,4);
                    var sut = new GameImpl(randomStub);
                    sut.initialize();

                    sut.move(Direction.left);
                    assertAll(
                            () -> assertFalse(sut.isWon()),
                            () -> assertFalse(sut.isOver())
                    );

                }

                @Test
                void botPlaysGame(){
                    int number = 0;
                    var sut = new GameImpl();
                    sut.initialize();

                    while (!sut.isOver() || sut.isWon()) {
                        switch (number++%4){
                            case 0 -> sut.move(Direction.up);
                            case 1 -> sut.move(Direction.right);
                            case 2 -> sut.move(Direction.left);
                            case 3 -> sut.move(Direction.down);

                        }
                    }
                    assertTrue(sut.isOver());
                }
            }
        }
    }
}
