package com.taek_aaa.locationdiary;

/**
 * Created by taek_aaa on 2016. 11. 29..
 */

/** iter 값에 대해 관리해주는 Class**/
public class IterationClass {
    private int it;

    public int setZero() {
        it = 0;
        return it;
    }

    public int getIteration() {
        return it;
    }

    public int intcrease() {
        it = it + 1;
        return it;
    }

    public int sum(int n) {
        it = it + n;
        return it;
    }

    public int sub(int n){
        it = it - n;
        return it;
    }
}
