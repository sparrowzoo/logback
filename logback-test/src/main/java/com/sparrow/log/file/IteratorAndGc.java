package com.sparrow.log.file;

import java.util.ArrayList;

/**
 * javac IteratorAndGc.java   &&   java -Xms180m -Xmx180m IteratorAndGc
 */
public class IteratorAndGc {

    // number of strings and the size of every string
    static final int N = 7500;

    public static void main(String[] args) {
        System.gc();

        gcInMethod();

        System.gc();
        showMemoryUsage("GC after the method body");

        ArrayList<String> strings2 = generateLargeStringsArray(N);
        showMemoryUsage("Third allocation outside the method is always successful");
    }

    // main testable method
    public static void gcInMethod() {

        showMemoryUsage("Before first memory allocating");
        ArrayList<String> strings = generateLargeStringsArray(N);
        showMemoryUsage("After first memory allocation");


        // this is only one difference - after the iterator created, memory won't be collected till end of this function
        for (String string : strings);//out of memory
        //for(int i=0;i<strings.size();i++);//is right
        showMemoryUsage("After iteration");

        strings = null; // discard the reference to the array

        // one says this doesn't guarantee garbage collection,
        // Oracle says "the Java Virtual Machine has made a best effort to reclaim space from all discarded objects".
        // but no matter - the program behavior remains the same with or without this line. You may skip it and test.
        System.gc();

        showMemoryUsage("After force GC in the method body");

        try {
            System.out.println("Try to allocate memory in the method body again:");
            ArrayList<String> strings2 = generateLargeStringsArray(N);
            showMemoryUsage("After secondary memory allocation");
        } catch (OutOfMemoryError e) {
            showMemoryUsage("!!!! Out of memory error !!!!");
            System.out.println();
        }
    }

    // function to allocate and return a reference to a lot of memory
    private static ArrayList<String> generateLargeStringsArray(int N) {
        ArrayList<String> strings = new ArrayList<>(N);
        for (int i = 0; i < N; i++) {
            StringBuilder sb = new StringBuilder(N);
            for (int j = 0; j < N; j++) {
                sb.append((char)Math.round(Math.random() * 0xFFFF));
            }
            strings.add(sb.toString());
        }

        return strings;
    }

    // helper method to display current memory status
    public static void showMemoryUsage(String action) {
        long free = Runtime.getRuntime().freeMemory();
        long total = Runtime.getRuntime().totalMemory();
        long max = Runtime.getRuntime().maxMemory();
        long used = total - free;
        System.out.printf("\t%40s: %10dk of max %10dk%n", action, used / 1024, max / 1024);
    }
}