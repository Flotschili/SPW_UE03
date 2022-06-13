package spw4.game2048;

import java.util.Arrays;
import java.util.Random;

public class GameImpl implements Game {

    private int[][] gameField;
    private final Random random;
    private int move;
    private int score;

    public GameImpl() {
        this(new Random());
    }

    public GameImpl(Random random){
        gameField = new int[4][4];
        this.random = random;
    }

    public int getMoves() {
        return move;
    }

    public int getScore() {
        return score;
    }

    public int getValueAt(int x, int y) {
        return gameField[x][y];
    }


    private boolean equalNeighboursExist() {
        // horizontal neighbours
        for (int row = 0; row < 4; row++)
            for (int column = 0; column < 3; column++)
                if(gameField[row][column] == gameField[row][column + 1])
                    return true;

        // vertical neighbours
        for (int column = 0; column < 4; column++)
            for (int row = 0; row < 3; row++)
                if(gameField[row][column] == gameField[row + 1][column])
                    return true;

        return false;
    }

    private boolean fieldContainsZero(){
        for (int x = 0; x < 4; x++)
            for (int y = 0; y < 4; y++)
                if (gameField[x][y] == 0)
                    return true;
        return false;
    }

    public boolean isOver() {

        if (fieldContainsZero())
            return false;

        return !equalNeighboursExist();
    }

    public boolean isWon() {
        for (int x = 0; x < 4; x++)
            for (int y = 0; y < 4; y++)
                if (gameField[x][y] == 2048)
                    return true;

        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Moves:  ").append(getMoves()).append("\t\t").append("Score:  ").append(getScore()).append("\n");
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int value = getValueAt(i,j);
                if (value == 0)
                    sb.append(".\t\t");
                else
                    sb.append(value).append("\t\t");
            }
            sb.append("\n");
        }


        return sb.toString().trim();
    }

    private void fillGameField(){
        for (int i = 0; i < 4; i++) {
            Arrays.fill(gameField[i], 0);
        }

        addTile();
        addTile();
    }

    private void addTile() {
        int x = 0;
        int y = 0;
        if (!fieldContainsZero())
            return;
        do {
            x = random.nextInt(4);
            y = random.nextInt(4);
        } while (gameField[x][y] != 0);

        gameField[x][y] = random.nextInt(10) < 9 ? 2 : 4;
    }

    public void initialize() {
        fillGameField();
        score = 0;

    }

    private void pushRight(int[] line, int i){
        boolean stackAffine = true;
        for (int j = i; j < 3; j++) {  // moving tile as far right as possible
            if(line[j + 1] == 0) {                  // space free -> move tile
                line[j + 1] = line[j];
                line[j] = 0;
            } else if (stackAffine && line[j + 1] == line[j]) {    // same value -> stack tiles
                line[j + 1] *= 2;
                score += line[j + 1];
                line[j] = 0;
                stackAffine = false;
            } else {
                return;
            }
        }
    }

    private void processLine(int[] line){
        for (int i = 2; i >= 0; i--) {     // going from right to left
            pushRight(line, i);
        }
    }

    private void reverseLine(int[] line){
        for (int i = 0; i < 2; i++) {
            int tmp = line[i];
            line[i] = line[3 - i];
            line[3 - i] = tmp;
        }
    }


    private void transposeField(){
        int[][] help = new int[4][4];

        for(int x = 0; x < 4; x++) {
            for(int y = 0; y < 4; y++) {
                help[x][y] = gameField[y][x];
            }
        }
        gameField = help;
    }

    public void move(Direction direction) {
        move++;

        if (direction == Direction.up || direction == Direction.down){
            transposeField();
        }
        for (int row = 0; row < 4; row++) {
            int[] arr = null;
            if (direction == Direction.right || direction == Direction.down){
                arr = gameField[row];
            }else if(direction == Direction.left || direction == Direction.up){
                arr = gameField[row];
                reverseLine(arr);
            }
            processLine(arr);

            if (direction == Direction.left || direction == Direction.up){
                reverseLine(arr);
            }
            gameField[row] = arr;

        }
        if (direction == Direction.up || direction == Direction.down){
            transposeField();
        }
        addTile();
               
    }
}
