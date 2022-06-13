package spw4.game2048;

import java.util.Random;

public class RandomStub extends Random {
    private final int []numbers;
    private int start = 0;


    public RandomStub(int ...numbers){
        this.numbers = numbers;
    }

    @Override
    public int nextInt() {
        int val = numbers[start%numbers.length];
        start++;
        return val;
    }

    @Override
    public int nextInt(int bound) {
        return nextInt();
    }
}
