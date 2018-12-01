package com.company;

public class Request {
    int[] resources;
    int processNum;
    boolean safe;

    public Request(int[] resources, int processNum) {
        this.resources = resources;
        this.processNum = processNum;
    }

    @Override
    public String toString() {
        String s = processNum + " ";

        for (int i = 0; i < resources.length; i++) {
            s += resources[i] + " ";
        }
        s+=safe;
        return s;
    }

}
