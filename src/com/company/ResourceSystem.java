package com.company;

import java.util.Arrays;

public class ResourceSystem {
    int[] remainingResources;
    Process[] processesArray;

    public ResourceSystem(int[] remainingResources, Process[] processesArray) {
        this.remainingResources = new int[remainingResources.length];
        this.processesArray = new Process[processesArray.length];
        System.arraycopy(remainingResources, 0, this.remainingResources, 0, remainingResources.length);
    for (int i=0;i<processesArray.length;i++){
        this.processesArray[i]=new Process(processesArray[i]);
    }
    }

    public boolean isSafe(Request request) {
        for (int i = 0; i < remainingResources.length; i++) {
            if (processesArray[request.processNum].getRequired()[i] < request.resources[i]) {
                request.safe = false;
                return false;
            }
        }

        int counter = 0;
        for (int i = 0; i < remainingResources.length; i++) {
            remainingResources[i] -= request.resources[i];
            processesArray[request.processNum].updateResources(i, request.resources[i]);

        }
        while (true) {
            boolean processFinished = false;
            for (int i = 0; i < processesArray.length; i++) {
                if (processesArray[i].isSafe(remainingResources) && !processesArray[i].isFinished()) {
                    processFinished = true;
                    counter++;
                    int[] released = processesArray[i].releaseResources();
                    for (int j = 0; j < remainingResources.length; j++) {
                        remainingResources[j] += released[j];
                    }
                }
            }
            if (counter == processesArray.length) {
                request.safe = true;
                return true;
            }
            if (!processFinished) {
                request.safe = false;
                return false;
            }
        }
    }

}
