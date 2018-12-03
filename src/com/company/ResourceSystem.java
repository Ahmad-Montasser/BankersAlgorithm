package com.company;

public class ResourceSystem {
    private int[] remainingResources;
    private Process[] processesArray;

    public ResourceSystem(int[] remainingResources, Process[] processesArray) {
        this.remainingResources = new int[remainingResources.length];
        this.processesArray = new Process[processesArray.length];
        System.arraycopy(remainingResources, 0, this.remainingResources, 0, remainingResources.length);
        copyProcessesArray(processesArray);
    }

    private void copyProcessesArray(Process[] processesArray) {
        for (int i = 0; i < processesArray.length; i++) {
            this.processesArray[i] = new Process(processesArray[i]); // copy the given array to the class array
        }
    }

    public boolean isSafe(Request request) {
        int counter = 0, previousLoopCounter;
        Process selectedProcess = processesArray[request.getProcessNum()];
        for (int i = 0; i < remainingResources.length; i++) {
            remainingResources[i] -= request.getResourceNumber(i);
            selectedProcess.updateResources(i, request.getResourceNumber(i));
        }

        while (true) {
            previousLoopCounter = counter;
            counter=0;
            for (Process process : processesArray) {
                if (process.isFinished()) // if the process has finished then there is no need to check it again
                    continue;
                if (process.isSafe(remainingResources)) { // checks weather the process could terminate or not
                    for (int i = 0; i < remainingResources.length; i++)
                        remainingResources[i] += process.getAllocatedResource(i); // add the allocated resources to the remaining resources
                }
            }
            for (Process p:processesArray) {
                if(p.isFinished())
                    counter++;
            }
            if (counter == processesArray.length) { // if the number of finished processes is equal to the number of processes
                request.setSafe(true); // this request is safe
                return true;
            }
            if (counter == previousLoopCounter) { // if the whole loop finished and the number of finished processes is the same
                request.setSafe(false); // this request won't finish (is not safe)
                return false;
            }
        }
    }
}
