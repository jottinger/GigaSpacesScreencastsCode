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
        long sum = runCalculationSet(service, numbers);
        long end = System.currentTimeMillis();

        System.out.println("discovered factors: " + sum + " elapsed time: " + (end - start) + "ms");
    }

    private static long runCalculationSet(ExecutorService service, List<String> numbers) {
        List<Future<TreeSet<BigInteger>>> futures = new ArrayList<Future<TreeSet<BigInteger>>>();

        submitCalculations(service, numbers, futures);

        return countFactors(futures);
    }

    @SuppressWarnings({"ConstantConditions"})
    private static long countFactors(List<Future<TreeSet<BigInteger>>> futures) {
        // Why can't IDEA handle this method? It says it's too complex to analyze. No idea why.
        // ... probably the future stuff. But meh, whatever.
        long sum = 0L;
        for (Future<TreeSet<BigInteger>> future : futures) {
            try {
                TreeSet<BigInteger> set=future.get();
                sum += set.size();
            } catch (Exception ignored) {
            }
        }
        return sum;
    }

    private static void submitCalculations(ExecutorService service, List<String> numbers, List<Future<TreeSet<BigInteger>>> futures) {
        for (final String n : numbers) {
            CalculateFactor c = new CalculateFactor(n);
            futures.add(service.submit(c));
        }
        service.shutdown();
    }
}
