package com.company;

public class Process {
    int[] allocated;
    int[] required;
    int[] max;
    boolean finished=false;
    static int remainingProcesses;
    int processNumber;

    public Process(int[] allocated, int[] max, int processNumber) {
        this.allocated = allocated;
        this.max = max;
        this.processNumber = processNumber;
        required = new int[allocated.length];
        for (int i = 0; i< this.allocated.length; i++)
        {
            required[i]=max[i]-allocated[i];

        }
    }

    public static void setRemainingProcesses(int remainingProcesses) {
        Process.remainingProcesses = remainingProcesses;
    }

    public boolean isSafe(int[] remainingResources) {
        for (int i = 0; i <allocated.length ; i++) {
            if(remainingResources[i]>=(max[i]-allocated[i]))
                continue;
            return false;
        }
        return true;
    }

    public int[] releaseResources() {
        finished=true;
        remainingProcesses--;
        return allocated;
    }
}
