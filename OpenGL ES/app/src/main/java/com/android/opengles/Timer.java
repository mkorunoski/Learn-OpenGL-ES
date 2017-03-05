package com.android.opengles;

public class Timer {

    private long start;

    public Timer() {
        start = System.nanoTime();
    }
    public long elapsed() { return System.nanoTime() - start; }
}
