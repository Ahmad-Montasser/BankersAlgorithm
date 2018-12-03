package com.company;

import java.util.Random;
import java.util.Scanner;

public class Main {
    static Random random;
    static Scanner sc;
    static int n, m;
    static Process[] processes;
    static int[] maxResources;
    static int[] remainingResources; // the unallocated resources of each resource type

    public static void main(String[] args) {
        random = new Random();
        int processesFinished = 0;
        sc = new Scanner(System.in);
        System.out.print("Enter Number Of processes ");
        n = sc.nextInt(); // number of processes
        System.out.print("Enter Number Of resources ");
        m = sc.nextInt(); // number of resources
        processes = new Process[n];
        maxResources = new int[m]; // the max instances of resources of each resource type
        System.out.print("Please Enter Max Number of Resources ");
        for (int i = 0; i < m; i++) {
            maxResources[i] = sc.nextInt(); // gets the maximum instances of each resource
        }
        remainingResources = new int[m];
        System.arraycopy(maxResources, 0, remainingResources, 0, m);
        for (int i = 0; i < n; i++) {
            int[] allocated = new int[m]; // initialize the allocated instances by zeros
            int[] max = new int[m]; // gets the maximum instances of resources that the process wants to finish execution
            for (int j = 0; j < m; j++) {
                max[j] = random.nextInt(maxResources[j] + 1);
            }
            processes[i] = new Process(max, allocated, i);
        }
        for (int i = 0; i < n; i++) {
            System.out.println(processes[i]);
        }

        while (processesFinished != n) {
            int processNum = random.nextInt(n); // gets the process number
            Process selectedProcess = processes[processNum]; // gets the randomized process from the array
            if (selectedProcess.isFinished())
                continue;
            boolean emptyRequest=true;
            Request.RequestType type = Request.getRandomRequestType(random); // gets if the next request is allocation or release
            int[] resources = new int[m]; // array of the requested/released resources
            if (type == Request.RequestType.release) {
                for (int i = 0; i < m; i++)
                    resources[i] = random.nextInt(selectedProcess.getAllocatedResource(i) + 1); // randomize a number in the bounds of the allocated resources to free it
                selectedProcess.releaseResources(resources); // decrement the value of the allocated resources by the process
                for (int i = 0; i < m; i++)
                    remainingResources[i] += resources[i]; // increment the value of the remaining resources by the value of the freed resources
                for (int i=0;i<m;i++)
                    if (resources[i]!=0)
                        emptyRequest=false;
                if(emptyRequest)
                    continue;
                Request request = new Request(resources, processNum);
                request.setSafe(true);
                System.out.println("Release " + request.ReleaseString());

                continue;
            }
            for (int i = 0; i < m; i++) {// generates numbers in the bounds of the required resources to allocate them
                int boundary = Math.min(selectedProcess.getRequiredRecourse(i) + 1, remainingResources[i] + 1);
                resources[i] = random.nextInt(boundary);
            }
            for (int i=0;i<m;i++)// gets rid of empty requests
                if (resources[i]!=0)
                    emptyRequest=false;
            if(emptyRequest)
                continue;
            Request request = new Request(resources, processNum); // initializes a new request

            ResourceSystem rs = new ResourceSystem(remainingResources, processes); // makes the test system
            if (rs.isSafe(request)) { // if this request will keep the system in safe state
                for (int i = 0; i < m; i++) {
                    remainingResources[i] -= request.getResourceNumber(i); // subtract the required resource instances from the remaining resources
                    selectedProcess.updateResources(i, request.getResourceNumber(i)); // update the required and allocated for the selected processs
                }

                selectedProcess.incRequestsAccepted(); //increase the number of accepted requests by one
                if (selectedProcess.getRequestsAccepted() >= Process.getMaxAcceptedRequests()) { // if it reaches the limit
                    for (int i = 0; i < remainingResources.length; i++)
                        remainingResources[i] += selectedProcess.getAllocatedResource(i); // free up the allocated resources by the process
                    processesFinished++; // increment the number of finished processes
                    selectedProcess.hasFinished(); // mark the process as finished one
                }


            }
                System.out.println(request);
        }
    }
}
