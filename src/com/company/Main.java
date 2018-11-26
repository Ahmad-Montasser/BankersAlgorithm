package com.company;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Number Of processes");
        int n = sc.nextInt();
        System.out.println("Enter Number Of resources");
        int m = sc.nextInt();
        Process[] processes = new Process[n];
        int[] maxResources = new int[m];
        int[] remainingResources ;
        ArrayList<String> result = new ArrayList<>(n);
        for (int i=0;i<m ; i++) {
            System.out.println("Please Enter Max Number of Resource");
            maxResources[i] = sc.nextInt();
        }
        int x;
        remainingResources=maxResources;
        for (int i = 0; i < n; i++) {
            System.out.println("Please enter Allocated & max resources");
            int[] allocated = new int[m];
            int[] max = new int[m];
            for (int j = 0; j < m; j++) {
                allocated[j] = sc.nextInt();
                remainingResources[j] -= allocated[j];
                max[j] = sc.nextInt();
            }
            processes[i] = new Process(allocated, max, i);
        }
        Process.setRemainingProcesses(n);
        while (Process.remainingProcesses != 0) {
            for (Process p : processes) {
                if (p.finished)
                    continue;
                if (p.isSafe(remainingResources)) {
                    int[] freed = p.releaseResources();
                    for (int i = 0; i < m; i++) {
                        remainingResources[i] += freed[i];
                    }
                    result.add("p"+p.processNumber);
                }
            }
        }
        for(String i : result)
            System.out.print(i+" ");

    }
}
