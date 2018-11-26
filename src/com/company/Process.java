package com.company;

public class Process {
    private int[] allocated;
    private int[] required;
    private boolean finished = false;
    private static int remainingProcesses;
    private int processNumber;

    public Process(int[] allocated, int[] max, int processNumber) {
        this.allocated = allocated;
        this.processNumber = processNumber;
        required = new int[allocated.length];
        for (int i = 0; i < this.allocated.length; i++) {
            required[i] = max[i] - allocated[i];
        }
    }

    public boolean isSafe(int[] remainingResources) { // determine weather the process could terminate safely without entering the unsafe state
        for (int i = 0; i < allocated.length; i++) {
            if (remainingResources[i] >= required[i])
                continue;
            return false;
        }
        return true;
    }

    public int[] releaseResources() { // returns the allocated resources by the processes (free them)
        finished = true;
        remainingProcesses--;
        return allocated;
    }

    public static void setRemainingProcesses(int remainingProcesses) {
        Process.remainingProcesses = remainingProcesses;
    }

    public static int getRemainingProcesses() {
        return remainingProcesses;
    }

    public boolean isFinished() {
        return finished;
    }

    @Override
    public String toString() {
        return "p" + processNumber;
    }//7eta zabrana deeh
}
