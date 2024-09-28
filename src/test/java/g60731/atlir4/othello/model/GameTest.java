package g60731.atlir4.othello.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    void possibleMove_begin_game() {
        Game game = new Game(8);
        game.initialize();

        Board board = game.getBoard();
        ArrayList<Position> possibleMove = board.getMovePossible();

        assertTrue(possibleMove.contains(new Position(3, 2)));
        assertTrue(possibleMove.contains(new Position(2, 3)));
        assertTrue(possibleMove.contains(new Position(5, 4)));
        assertTrue(possibleMove.contains(new Position(4, 5)));
    }

    @Test
    void possibleMove_begin_game_2() {
        Game game = new Game(8);
        game.initialize();

        game.addPiece(new Position(4, 5), false);
        Board board = game.getBoard();
        ArrayList<Position> possibleMove = board.getMovePossible();

        assertTrue(possibleMove.contains(new Position(5, 3)));
        assertTrue(possibleMove.contains(new Position(3, 5)));
        assertTrue(possibleMove.contains(new Position(5, 5)));
    }

    @Test
    void possibleMove_begin_game_3() {
        Game game = new Game(8);
        game.initialize();

        game.addPiece(new Position(4, 5), false);
        game.addPiece(new Position(3, 5), false);
        Board board = game.getBoard();
        ArrayList<Position> possibleMove = board.getMovePossible();

        assertTrue(possibleMove.contains(new Position(2, 2)));
        assertTrue(possibleMove.contains(new Position(2, 3)));
        assertTrue(possibleMove.contains(new Position(2, 4)));
        assertTrue(possibleMove.contains(new Position(2, 5)));
        assertTrue(possibleMove.contains(new Position(2, 6)));
    }

    @Test
    void possibleMove_begin_game_4() {
        Game game = new Game(8);
        game.initialize();

        game.addPiece(new Position(4, 5), false);
        game.addPiece(new Position(3, 5), false);
        game.addPiece(new Position(2, 6), false);
        Board board = game.getBoard();
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
        Game game = new Game(8);
        game.initialize();

        game.addPiece(new Position(4, 5), false);
        game.addPiece(new Position(3, 5), false);
        game.addPiece(new Position(2, 6), false);
        game.addPiece(new Position(3, 6), false);
        Board board = game.getBoard();
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
        Game game = new Game(8);
        game.initialize();

        game.addPiece(new Position(4, 5), false);
        game.addPiece(new Position(3, 5), false);
        game.addPiece(new Position(2, 6), false);
        game.addPiece(new Position(3, 6), false);

        Board board = game.getBoard();
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
    void game_undo() {
        Game game = new Game(8);
        game.initialize();

        game.addPiece(new Position(4, 5), false);
        game.undoGame();
        Board board = game.getBoard();
        Map<Position, ColorToken> blackToken = board.getMap(ColorToken.BLACK);
        Map<Position, ColorToken> whiteToken = board.getMap(ColorToken.WHITE);

        assertNotNull(blackToken.get(new Position(3, 4)));
        assertNotNull(blackToken.get(new Position(4, 3)));
        assertNotNull(whiteToken.get(new Position(3, 3)));
        assertNotNull(whiteToken.get(new Position(4, 4)));
    }

    @Test
    void game_2_undo() {
        Game game = new Game(8);
        game.initialize();

        game.addPiece(new Position(4, 5), false);
        game.undoGame();
        game.undoGame();
        Board board = game.getBoard();
        Map<Position, ColorToken> blackToken = board.getMap(ColorToken.BLACK);
        Map<Position, ColorToken> whiteToken = board.getMap(ColorToken.WHITE);

        assertNotNull(blackToken.get(new Position(3, 4)));
        assertNotNull(blackToken.get(new Position(4, 3)));
        assertNotNull(whiteToken.get(new Position(3, 3)));
        assertNotNull(whiteToken.get(new Position(4, 4)));
    }

    @Test
    void game_redo() {
        Game game = new Game(8);
        game.initialize();

        game.addPiece(new Position(4, 5), false);
        game.undoGame();
        Board board = game.getBoard();
        Map<Position, ColorToken> blackToken = board.getMap(ColorToken.BLACK);
        Map<Position, ColorToken> whiteToken = board.getMap(ColorToken.WHITE);

        System.out.print("Black: ");
        for (Map.Entry<Position, ColorToken> positionToken : blackToken.entrySet()) {
            System.out.print(positionToken.getKey() + " ");
        }
        System.out.println();
        System.out.print("White: ");
        for (Map.Entry<Position, ColorToken> positionToken : whiteToken.entrySet()) {
            System.out.print(positionToken.getKey() + " ");
        }
        System.out.println();

        game.redoGame();
        board = game.getBoard();
        Map<Position, ColorToken> blackToken2 = board.getMap(ColorToken.BLACK);
        Map<Position, ColorToken> whiteToken2 = board.getMap(ColorToken.WHITE);

        System.out.print("Black2: ");
        for (Map.Entry<Position, ColorToken> positionToken : blackToken2.entrySet()) {
            System.out.print(positionToken.getKey() + " ");
        }
        System.out.println();
        System.out.print("White2: ");
        for (Map.Entry<Position, ColorToken> positionToken : whiteToken2.entrySet()) {
            System.out.print(positionToken.getKey() + " ");
        }
        System.out.println();

        assertNotNull(blackToken.get(new Position(3, 4)));
        assertNotNull(blackToken.get(new Position(4, 3)));
        assertNotNull(blackToken.get(new Position(4, 4)));
        assertNotNull(blackToken.get(new Position(4, 5)));
        assertNotNull(whiteToken.get(new Position(3, 3)));

    }

    @Test
    void game_pass() {
        Game game = new Game(8);
        game.initialize();

        ArrayList<Position> black = new ArrayList<>();
        black.add(new Position(7, 0));
        black.add(new Position(7, 1));
        ArrayList<Position> white = new ArrayList<>();
        white.add(new Position(6, 0));
        white.add(new Position(6, 1));
        ArrayList<Position>[] save = new ArrayList[] {black, white};
        Board board = game.getBoard();
        board.setBoard(save);
        board.searchPossibleMove(game.getCurrentPlayer());
        game.addPiece(new Position(5, 0), false);

        ColorToken color = game.getCurrentPlayer();
        ColorToken expectedColor = ColorToken.BLACK;
        assertEquals(expectedColor, color);

    }

    @Test
    void game_end() {
        Game game = new Game(8);
        game.initialize();

        ArrayList<Position> black = new ArrayList<>();
        black.add(new Position(7, 0));
        black.add(new Position(7, 1));
        ArrayList<Position> white = new ArrayList<>();
        white.add(new Position(6, 0));
        white.add(new Position(6, 1));
        ArrayList<Position>[] save = new ArrayList[] {black, white};
        Board board = game.getBoard();
        board.setBoard(save);
        board.searchPossibleMove(game.getCurrentPlayer());
        game.addPiece(new Position(5, 0), false);
        game.addPiece(new Position(5, 1), false);

        GameState state = game.getGameState();
        GameState expectedState = GameState.ENDGAME;
        assertEquals(expectedState, state);

    }

    @Test
    void game_3x3_1() {
        Game game = new Game(3);
        game.initialize();

        game.addPiece(new Position(2, 1), false);
        Board board = game.getBoard();
        Map<Position, ColorToken> blackToken = board.getMap(ColorToken.BLACK);
        Map<Position, ColorToken> whiteToken = board.getMap(ColorToken.WHITE);
        Map<Position, ArrayList<Position>> possibleMove = board.getMapPossible();
        ArrayList<Position> test = new ArrayList<>(
                possibleMove.get(new Position(2, 0)));
        assertNotNull(blackToken.get(new Position(0, 1)));
        assertNotNull(blackToken.get(new Position(1, 1)));
        assertNotNull(blackToken.get(new Position(2, 1)));
        assertNotNull(blackToken.get(new Position(1, 0)));
        assertNotNull(whiteToken.get(new Position(0, 0)));
        assertEquals(1, test.size());
        assertEquals(new Position(1, 0), test.getFirst());
    }

    @Test
    void game_3x3_2() {
        Game game = new Game(3);
        game.initialize();

        game.addPiece(new Position(2, 1), false);
        game.addPiece(new Position(2, 0), false);
        Board board = game.getBoard();
        Map<Position, ColorToken> blackToken = board.getMap(ColorToken.BLACK);
        Map<Position, ColorToken> whiteToken = board.getMap(ColorToken.WHITE);

        assertNotNull(blackToken.get(new Position(0, 1)));
        assertNotNull(blackToken.get(new Position(1, 1)));
        assertNotNull(blackToken.get(new Position(2, 1)));
        assertNotNull(whiteToken.get(new Position(1, 0)));
        assertNotNull(whiteToken.get(new Position(0, 0)));
        assertNotNull(whiteToken.get(new Position(2, 0)));
    }

    @Test
    void game_3x3_3() {
        Game game = new Game(3);
        game.initialize();

        game.addPiece(new Position(2, 1), false);
        game.addPiece(new Position(2, 0), false);
        Board board = game.getBoard();
        Map<Position, ColorToken> blackToken = board.getMap(ColorToken.BLACK);
        Map<Position, ColorToken> whiteToken = board.getMap(ColorToken.WHITE);
        Map<Position, ArrayList<Position>> possibleMove = board.getMapPossible();
        ArrayList<Position> test = new ArrayList<>(
                possibleMove.get(new Position(0, 2)));

        assertNotNull(blackToken.get(new Position(0, 1)));
        assertNotNull(blackToken.get(new Position(1, 1)));
        assertNotNull(blackToken.get(new Position(2, 1)));
        assertNotNull(whiteToken.get(new Position(1, 0)));
        assertNotNull(whiteToken.get(new Position(0, 0)));
        assertNotNull(whiteToken.get(new Position(2, 0)));
        assertEquals(ColorToken.WHITE, game.getCurrentPlayer());
        assertEquals(2
                , possibleMove.get(new Position(0, 2)).size());
        assertEquals(new Position(0, 1), test.get(0));
        assertEquals(new Position(1, 1), test.get(1));
        assertEquals(GameState.RUNNING, game.getGameState());

    }
}