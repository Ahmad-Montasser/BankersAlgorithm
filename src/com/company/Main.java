package com.company;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        Random r = new Random();
        int processesFinished = 0;
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Number Of processes");
        int n = sc.nextInt(); // number of processes
        System.out.println("Enter Number Of resources");
        int m = sc.nextInt(); // number of resources
        Process[] processes = new Process[n];
        int[] maxResources = new int[m]; // the max instances of resources of each resource type
        int[] remainingResources; // the unallocated resources of each resource type
        System.out.print("Please Enter Max Number of Resources ");
        for (int i = 0; i < m; i++) {
            maxResources[i] = sc.nextInt(); // gets the maximum instances of each resource
        }
        remainingResources = maxResources;
        for (int i = 0; i < n; i++) {
            int[] allocated = new int[m]; // gets the allocated instances of each resource by each process
            int[] max = new int[m]; // gets the maximum instances of resources that the process wants to finish execution
            for (int j = 0; j < m; j++) {
                max[j] = r.nextInt(maxResources[j]);
            }
            processes[i] = new Process(max, allocated, i);
        }
        for (int i = 0; i < n; i++) {
            System.out.println(processes[i]);
        }

        while (true) {

            int processNum = r.nextInt(n);
            int[] resources = new int[m];
            if (processes[processNum].isFinished())
                continue;
            for (int i = 0; i < m; i++)
                resources[i] = r.nextInt(maxResources[i]);
            Request request = new Request(resources, processNum);
            ResourceSystem rs = new ResourceSystem(remainingResources, processes);
            if (rs.isSafe(request)) {
                for (int i = 0; i < m; i++) {
                    remainingResources[i] -= request.resources[i];
                    processes[request.processNum].updateResources(i, request.resources[i]);
                }
                if (!processes[request.processNum].isFinished()) {
                    processes[request.processNum].incRequestsAccepted();
                    if (processes[request.processNum].getRequestsAccepted() >= 4) {
                        for (int i = 0; i < remainingResources.length; i++)
                            remainingResources[i] += processes[request.processNum].releaseResources()[i];
                        processesFinished++;
                    }
                } else {
                    for (int i = 0; i < remainingResources.length; i++)
                        remainingResources[i] += processes[request.processNum].releaseResources()[i];
                    processesFinished++;
                }

                if (processesFinished == processes.length) {
                    break;
                }

            }
            if (request.safe)
                System.out.println(request);
        }

    }
}
