package g60731.atlir4.othello.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    void Constructor_Size_Not_Between_3_And_15(){
        assertThrows(IllegalArgumentException.class, () -> new Board(0));
        assertThrows(IllegalArgumentException.class, () -> new Board(2));
        assertThrows(IllegalArgumentException.class, () -> new Board(16));
    }

    @Test
    void initilize_delault_size(){
        Board board = new Board(8);

        Map<Position, ColorToken> blackToken = board.getMap(ColorToken.BLACK);
        Map<Position, ColorToken> whiteToken = board.getMap(ColorToken.WHITE);

        assertNotNull(blackToken.get(new Position(3, 4)));
        assertNotNull(blackToken.get(new Position(4, 3)));
        assertNotNull(whiteToken.get(new Position(3, 3)));
        assertNotNull(whiteToken.get(new Position(4, 4)));
    }

    @Test
    void initilize_Not_default_size(){
        Board board = new Board(10);

        Map<Position, ColorToken> blackToken = board.getMap(ColorToken.BLACK);
        Map<Position, ColorToken> whiteToken = board.getMap(ColorToken.WHITE);

        assertNotNull(blackToken.get(new Position(4, 5)));
        assertNotNull(blackToken.get(new Position(5, 4)));
        assertNotNull(whiteToken.get(new Position(4, 4)));
        assertNotNull(whiteToken.get(new Position(5, 5)));
    }

    @Test
    void possibleMove_begin_game() {
        Board board = new Board(8);

        ArrayList<Position> possibleMove = board.getMovePossible();

        assertTrue(possibleMove.contains(new Position(3, 2)));
        assertTrue(possibleMove.contains(new Position(2, 3)));
        assertTrue(possibleMove.contains(new Position(5, 4)));
        assertTrue(possibleMove.contains(new Position(4, 5)));
    }

    @Test
    void possibleMove_begin_game_2() {
        Board board = new Board(8);

        board.add(new Position(4, 5), ColorToken.BLACK);
        board.searchPossibleMove(ColorToken.WHITE);
        ArrayList<Position> possibleMove = board.getMovePossible();

        assertTrue(possibleMove.contains(new Position(5, 3)));
        assertTrue(possibleMove.contains(new Position(3, 5)));
        assertTrue(possibleMove.contains(new Position(5, 5)));
    }

    @Test
    void possibleMove_begin_game_2_Tokens() {
        Board board = new Board(8);

        board.add(new Position(4, 5), ColorToken.BLACK);
        Map<Position, ColorToken> blackToken = board.getMap(ColorToken.BLACK);
        Map<Position, ColorToken> whiteToken = board.getMap(ColorToken.WHITE);

        assertNotNull(blackToken.get(new Position(3, 4)));
        assertNotNull(blackToken.get(new Position(4, 3)));
        assertNotNull(blackToken.get(new Position(4, 4)));
        assertNotNull(blackToken.get(new Position(4, 5)));
        assertNotNull(whiteToken.get(new Position(3, 3)));
    }

    @Test
    void possibleMove_begin_game_3() {
        Board board = new Board(8);

        board.add(new Position(4, 5), ColorToken.BLACK);
        board.searchPossibleMove(ColorToken.WHITE);
        board.add(new Position(3, 5), ColorToken.WHITE);
        board.searchPossibleMove(ColorToken.BLACK);
        ArrayList<Position> possibleMove = board.getMovePossible();

        assertTrue(possibleMove.contains(new Position(2, 2)));
        assertTrue(possibleMove.contains(new Position(2, 3)));
        assertTrue(possibleMove.contains(new Position(2, 4)));
        assertTrue(possibleMove.contains(new Position(2, 5)));
        assertTrue(possibleMove.contains(new Position(2, 6)));
    }

    @Test
    void possibleMove_begin_game_4() {
        Board board = new Board(8);

        board.add(new Position(4, 5), ColorToken.BLACK);
        board.searchPossibleMove(ColorToken.WHITE);
        board.add(new Position(3, 5), ColorToken.WHITE);
        board.searchPossibleMove(ColorToken.BLACK);
        board.add(new Position(2, 6), ColorToken.BLACK);
        board.searchPossibleMove(ColorToken.WHITE);
        ArrayList<Position> possibleMove = board.getMovePossible();

        assertTrue(possibleMove.contains(new Position(3, 6)));
        assertTrue(possibleMove.contains(new Position(5, 2)));
        assertTrue(possibleMove.contains(new Position(5, 3)));
        assertTrue(possibleMove.contains(new Position(5, 4)));
        assertTrue(possibleMove.contains(new Position(5, 5)));
        assertTrue(possibleMove.contains(new Position(5, 6)));
    }

    @Test
    void possibleMove_begin_game_5() {
        Board board = new Board(8);

        board.add(new Position(4, 5), ColorToken.BLACK);
        board.searchPossibleMove(ColorToken.WHITE);
        board.add(new Position(3, 5), ColorToken.WHITE);
        board.searchPossibleMove(ColorToken.BLACK);
        board.add(new Position(2, 6), ColorToken.BLACK);
        board.searchPossibleMove(ColorToken.WHITE);
        board.add(new Position(3, 6), ColorToken.WHITE);
        board.searchPossibleMove(ColorToken.BLACK);
        ArrayList<Position> possibleMove = board.getMovePossible();

        assertTrue(possibleMove.contains(new Position(2, 2)));
        assertTrue(possibleMove.contains(new Position(2, 3)));
        assertTrue(possibleMove.contains(new Position(2, 4)));
        assertTrue(possibleMove.contains(new Position(2, 5)));
        assertTrue(possibleMove.contains(new Position(2, 7)));
        assertTrue(possibleMove.contains(new Position(4, 6)));
    }

    @Test
    void possibleMove_begin_game_5_With_Tokens() {
        Board board = new Board(8);

        board.add(new Position(4, 5), ColorToken.BLACK);
        board.searchPossibleMove(ColorToken.WHITE);
        board.add(new Position(3, 5), ColorToken.WHITE);
        board.searchPossibleMove(ColorToken.BLACK);
        board.add(new Position(2, 6), ColorToken.BLACK);
        board.searchPossibleMove(ColorToken.WHITE);
        board.add(new Position(3, 6), ColorToken.WHITE);
        board.searchPossibleMove(ColorToken.BLACK);

        Map<Position, ColorToken> blackToken = board.getMap(ColorToken.BLACK);
        Map<Position, ColorToken> whiteToken = board.getMap(ColorToken.WHITE);

        assertNotNull(blackToken.get(new Position(4, 3)));
        assertNotNull(blackToken.get(new Position(4, 4)));
        assertNotNull(blackToken.get(new Position(4, 5)));
        assertNotNull(blackToken.get(new Position(2, 6)));
        assertNotNull(whiteToken.get(new Position(3, 3)));
        assertNotNull(whiteToken.get(new Position(3, 4)));
        assertNotNull(whiteToken.get(new Position(3, 5)));
        assertNotNull(whiteToken.get(new Position(3, 6)));
    }

    @Test
    void possibleMove_begin_game_5_Possible_move() {
        Board board = new Board(8);

        board.add(new Position(4, 5), ColorToken.BLACK);
        board.searchPossibleMove(ColorToken.WHITE);
        board.add(new Position(3, 5), ColorToken.WHITE);
        board.searchPossibleMove(ColorToken.BLACK);
        board.add(new Position(2, 6), ColorToken.BLACK);
        board.searchPossibleMove(ColorToken.WHITE);
        board.add(new Position(3, 6), ColorToken.WHITE);
        board.searchPossibleMove(ColorToken.BLACK);
        var possibleMove = board.getMapPossible();

        int expectedSize = 6;
        var position1 = possibleMove.get(new Position(2, 2));
        var position2 = possibleMove.get(new Position(2, 3));
        var position3 = possibleMove.get(new Position(2, 4));
        var position4 = possibleMove.get(new Position(2, 5));
        var position5 = possibleMove.get(new Position(2, 7));
        var position6 = possibleMove.get(new Position(4, 6));

        assertTrue(position1.contains(new Position(3, 3)));
        assertTrue(position2.contains(new Position(3, 3)));
        assertTrue(position2.contains(new Position(3, 4)));
        assertTrue(position3.contains(new Position(3, 4)));
        assertTrue(position4.contains(new Position(3, 4)));
        assertTrue(position4.contains(new Position(3, 5)));
        assertTrue(position5.contains(new Position(3, 6)));
        assertTrue(position6.contains(new Position(3, 6)));
        assertEquals(expectedSize, possibleMove.size());
    }

    @Test
    void possibleMove_begin_game_4_Possible_move() {
        Board board = new Board(8);

        board.add(new Position(4, 5), ColorToken.BLACK);
        board.searchPossibleMove(ColorToken.WHITE);
        board.add(new Position(3, 5), ColorToken.WHITE);
        board.searchPossibleMove(ColorToken.BLACK);
        board.add(new Position(2, 6), ColorToken.BLACK);
        board.searchPossibleMove(ColorToken.WHITE);
        var possibleMove = board.getMapPossible();

        int expectedSize = 6;
        var position1 = possibleMove.get(new Position(3, 6));
        var position2 = possibleMove.get(new Position(5, 2));
        var position3 = possibleMove.get(new Position(5, 3));
        var position4 = possibleMove.get(new Position(5, 4));
        var position5 = possibleMove.get(new Position(5, 5));
        var position6 = possibleMove.get(new Position(5, 6));

        assertTrue(position1.contains(new Position(3, 5)));
        assertTrue(position2.contains(new Position(4, 3)));
        assertTrue(position3.contains(new Position(4, 3)));
        assertTrue(position4.contains(new Position(4, 4)));
        assertTrue(position5.contains(new Position(4, 4)));
        assertTrue(position6.contains(new Position(4, 5)));
        assertEquals(expectedSize, possibleMove.size());
    }

    @Test
    void possibleMove_begin_game_3_Possible_move() {
        Board board = new Board(8);

        board.add(new Position(4, 5), ColorToken.BLACK);
        board.searchPossibleMove(ColorToken.WHITE);
        board.add(new Position(3, 5), ColorToken.WHITE);
        board.searchPossibleMove(ColorToken.BLACK);
        var possibleMove = board.getMapPossible();

        int expectedSize = 5;
        var position1 = possibleMove.get(new Position(2, 2));
        var position2 = possibleMove.get(new Position(2, 3));
        var position3 = possibleMove.get(new Position(2, 4));
        var position4 = possibleMove.get(new Position(2, 5));
        var position5 = possibleMove.get(new Position(2, 6));

        assertTrue(position1.contains(new Position(3, 3)));
        assertTrue(position2.contains(new Position(3, 3)));
        assertTrue(position2.contains(new Position(3, 4)));
        assertTrue(position3.contains(new Position(3, 4)));
        assertTrue(position4.contains(new Position(3, 4)));
        assertTrue(position4.contains(new Position(3, 5)));
        assertTrue(position5.contains(new Position(3, 5)));
        assertEquals(expectedSize, possibleMove.size());
    }

    @Test
    void possibleMove_begin_game_2_Possible_move() {
        Board board = new Board(8);

        board.add(new Position(4, 5), ColorToken.BLACK);
        board.searchPossibleMove(ColorToken.WHITE);
        var possibleMove = board.getMapPossible();

        int expectedSize = 3;
        var position1 = possibleMove.get(new Position(5, 3));
        var position2 = possibleMove.get(new Position(3, 5));
        var position3 = possibleMove.get(new Position(5, 5));

        assertTrue(position1.contains(new Position(4, 3)));
        assertTrue(position2.contains(new Position(3, 4)));
        assertTrue(position3.contains(new Position(4, 4)));

        assertEquals(expectedSize, possibleMove.size());
    }

    @Test
    void possibleMove_Invalid_Move() {
        Board board = new Board(8);

        assertThrows(IllegalArgumentException.class
                , () -> board.add(new Position(4, 6)
                        , ColorToken.BLACK));
    }
}