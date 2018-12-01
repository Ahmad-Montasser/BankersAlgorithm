package com.company;

public class Process {
    private int[] allocated;
    private int[] required;
    private boolean finished = false;
    private int processNumber;
    private int requestsAccepted=0;
    private int[] max;

    public Process(Process process) {
         allocated= process.getAllocated();
         required=process.getRequired();
         processNumber=process.getProcessNumber();
         requestsAccepted=process.getRequestsAccepted();
         max=process.getMax();
         finished=process.finished;
    }

    public Process(int[] max, int[] allocated , int processNumber) {
        this.allocated = allocated;
        this.processNumber = processNumber;
        this.max=max;
        required = new int[allocated.length];
        for (int i = 0; i < this.allocated.length; i++) {
            required[i] = max[i] - allocated[i];
        }
    }

    public int getProcessNumber() {
        return processNumber;
    }


    public int[] getMax() {

        return max;
    }

    public int[] getAllocated() {
        return allocated;
    }

    public int[] getRequired() {
        return required;
    }

    public void setRequired(int[] required) {
        this.required = required;
    }

    public int getRequestsAccepted() {
        return requestsAccepted;
    }

    public void incRequestsAccepted() {
        this.requestsAccepted = requestsAccepted+1;
    }

    public void updateResources(int index, int quantity){
        required[index]-=quantity;
        allocated[index] =max[index]-required[index];
        boolean dummy=true;
        for (int i=0;i<allocated.length;i++){
            if(allocated[i]>=max[i])
                continue;
            dummy=false;
            break;
        }
        finished=dummy;

    }
    public void AcceptProcess(){
        requestsAccepted++;
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
        return allocated;
    }



    public boolean isFinished() {
      return finished;
    }

    @Override
    public String toString() {
        String dummy=" ";
        for (int i=0;i<max.length;i++)
            dummy+= max[i]+" ";
        return "p" + processNumber+dummy;

    }
}
