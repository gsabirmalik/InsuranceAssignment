package com.duke;

public class FakeTimeKeeper implements TimeKeeper {

    long _fakeElapsedMillis = 0;
    @Override
    public long getCurrentTimeMillis() {
        return System.currentTimeMillis() + _fakeElapsedMillis;
    }

    public void addFakeElapsedMilliSecs(Long fakeElapsedMillisecs){
        _fakeElapsedMillis = fakeElapsedMillisecs;
    }
}