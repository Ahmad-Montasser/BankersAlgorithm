package com.company;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Request {


    public enum RequestType {allocate, release}
    private static List<RequestType> TYPES = Collections.unmodifiableList(Arrays.asList(RequestType.values()));

    private static int SIZE = TYPES.size();


    private int[] resources;
    private int processNum;
    private boolean safe;
    public Request(int[] resources, int processNum) {
        this.resources = resources;
        this.processNum = processNum;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("P"+processNum + " ");

        for (int resource : resources) {
            s.append(resource).append(" ");
        }
        s.append(safe);
        return s.toString();
    }
    public String ReleaseString() {
        StringBuilder s = new StringBuilder("P"+processNum + " ");

        for (int resource : resources) {
            s.append(resource).append(" ");
        }
        return s.toString();
    }

    public static RequestType getRandomRequestType(Random random) {
        return TYPES.get(random.nextInt(SIZE));
    }

    public int getResourceNumber(int resourceNumber) {
        return resources[resourceNumber];
    }

    public void setSafe(boolean safe) {
        this.safe = safe;
    }

    public int getProcessNum() {
        return processNum;
    }

    public boolean isSafe() {
        return safe;
    }
}
