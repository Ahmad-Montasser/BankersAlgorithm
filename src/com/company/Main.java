package com.company;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Number Of processes");
        int n = sc.nextInt(); // number of processes
        System.out.println("Enter Number Of resources");
        int m = sc.nextInt(); // number of resources
        Process[] processes = new Process[n];
        int[] maxResources = new int[m]; // the max instances of resources of each resource type
        int[] remainingResources; // the unallocated resources of each resource type
        ArrayList<Process> result = new ArrayList<>(n); // the final pattern of the execution
        System.out.print("Please Enter Max Number of Resources ");
        for (int i = 0; i < m; i++) {
            maxResources[i] = sc.nextInt(); // gets the maximum instances of each resource
        }
        remainingResources = maxResources;
        for (int i = 0; i < n; i++) {
            int[] allocated = new int[m]; // gets the allocated instances of each resource by each process
            int[] max = new int[m]; // gets the maximum instances of resources that the process wants to finish execution
            System.out.print("Enter allocated instances of resources for process " + i + " :");
            for (int j = 0; j < m; j++) {
                allocated[j] = sc.nextInt();
                remainingResources[j] -= allocated[j]; // subtracts the allocated instances from the total unallocated resource
            }
            System.out.print("Enter the maximum instances needed of resources for process " + i + " :");
            for(int j = 0; j < m; j++) {
                max[j] = sc.nextInt(); // saves the max instances that the process require
            }
            processes[i] = new Process(allocated, max, i);
        }
        Process.setRemainingProcesses(n);
        int finishedProcesses; // gets the value of the finished processes at the end and the last of the loop iteration
        boolean validExecution = true; // indicates weather or not a pattern was found
        while (Process.getRemainingProcesses() != 0) {
            finishedProcesses = result.size(); // gets the number of finished processes
            for (Process p : processes) {
                if (p.isFinished())
                    continue;
                if (p.isSafe(remainingResources)) {
                    int[] freed = p.releaseResources();
                    for (int i = 0; i < m; i++) {
                        remainingResources[i] += freed[i]; //add the allocated resources by the process to the total unallocated resources
                    }
                    result.add(p);
                }
            }
            if(result.size() == finishedProcesses) { // compare the size of the finished processes with the initial size before the loop
                validExecution = false;
                break;
            }
        }
        if(validExecution) {
            for (Process p : result)
                System.out.print(p + " ");
            System.out.println();
        } else {
            System.out.println("There's no valid pattern to finish these processes in the safe state");
        }
    }
}
