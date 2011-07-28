package org.openspaces.screencasts.scaling.cmdline;

import org.openspaces.screencasts.scaling.tasks.CalculateFactor;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.TreeSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        List<String> numbers = new ArrayList<String>();
        for (int i = 0; i < 200; i++) {
            StringBuilder sb = new StringBuilder();
            int len = 13; //+random.nextInt(3);
            for (int j = 0; j < len; j++) {
                sb.append(String.valueOf(random.nextInt(10)));
            }
            numbers.add(sb.toString());
        }

        ExecutorService service = Executors.newFixedThreadPool(1);
        runFactorization(service, numbers);
        service = Executors.newFixedThreadPool(2);
        runFactorization(service, numbers);
        service = Executors.newFixedThreadPool(4);
        runFactorization(service, numbers);
    }

    private static void runFactorization(ExecutorService service, List<String> numbers) {
        long start = System.currentTimeMillis();
        List<Future<TreeSet<BigInteger>>> futures = new ArrayList<Future<TreeSet<BigInteger>>>();

        for (final String n : numbers) {
            CalculateFactor c = new CalculateFactor(n);
            futures.add(service.submit(c));
        }
        service.shutdown();

        long sum = 0;
        for (Future<TreeSet<BigInteger>> c : futures) {
            try {
                sum += c.get().size();
            } catch (Exception ignored) {
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("discovered factors: " + sum + " elapsed time: " + (end - start) + "ms");
    }
}
