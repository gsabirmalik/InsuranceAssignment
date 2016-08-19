package com.duke;

public class SystemTimeKeeper implements TimeKeeper {
    @Override
    public long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }
}
