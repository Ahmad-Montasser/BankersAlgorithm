package com.company;

import java.util.Arrays;

public class Process {
    private int[] allocated;
    private int[] required;
    private int[] max;
    private boolean finished = false;
    private int processNumber;
    private int requestsAccepted = 0;
    private static int MAX_ACCEPTED_REQUESTS = 4;

    public Process(int[] max, int[] allocated, int processNumber) {
        this.allocated = new int[allocated.length];
        System.arraycopy(allocated, 0, this.allocated, 0, allocated.length);
        this.processNumber = processNumber;
        this.max = max;
        required = new int[allocated.length];
        for (int i = 0; i < this.allocated.length; i++) {
            required[i] = max[i] - allocated[i];
        }
    }

    public Process(Process p) {
        allocated = new int[p.allocated.length];
        required = new int[p.required.length];
        max = new int[p.max.length];
        System.arraycopy(p.allocated, 0, allocated, 0, allocated.length);
        System.arraycopy(p.required, 0, required, 0, required.length);
        System.arraycopy(p.max, 0, max, 0, max.length);
        finished = p.finished;
        processNumber = p.processNumber;
        requestsAccepted = p.requestsAccepted;
    }

    public int getAllocatedResource(int resourceNumber) {
        return allocated[resourceNumber];
    }

    public int getRequiredRecourse(int resourceNumber) {
        return required[resourceNumber];
    }

    public int getRequestsAccepted() {
        return requestsAccepted;
    }

    public void incRequestsAccepted() {
        requestsAccepted++;
    }

    public void updateResources(int index, int quantity) {
        required[index] -= quantity; // subtracts the allocated resources from the required array
        allocated[index] += quantity; // adds the allocated resources to the allocated array
    }


    public boolean isSafe(int[] remainingResources) { // determine weather the process could terminate safely without entering the unsafe state
        if (Arrays.equals(max, allocated)) { // if the process allocates the maximum number of resources that it needs
            finished = true;// then this process has finished
            return true;
        }
        for (int i = 0; i < allocated.length; i++) {
            if (remainingResources[i] >= required[i])
                continue;
            finished = false;
            return false;
        }
        finished = true;
        return true;
    }

    public void releaseResources(int[] resources) { // returns the allocated resources by the processes (free them)
        for (int i = 0; i < allocated.length; i++) {
            allocated[i] -= resources[i];
            required[i] += resources[i];
        }
    }


    public boolean isFinished() {
        return finished;
    }

    public void hasFinished() {
        finished = true; // called when the process has finished
        allocated = new int[allocated.length];
        required = new int[required.length];
        max = new int[max.length];
    }

    public static int getMaxAcceptedRequests() {
        return MAX_ACCEPTED_REQUESTS;
    }

    @Override
    public String toString() {
        StringBuilder dummy = new StringBuilder(" ");
        for (int i = 0; i < max.length; i++)
            dummy.append(max[i]).append(" ");
        return "P" + processNumber + dummy.toString();
    }

}
