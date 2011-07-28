package org.openspaces.screencasts.scaling.tasks;

import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CalculateFactor implements Callable<TreeSet<BigInteger>>  {
    public static final BigInteger ZERO = BigInteger.ZERO;
    public static final BigInteger ONE = BigInteger.ONE;
    public static final BigInteger TWO = new BigInteger("2");
    private BigInteger source;

    public CalculateFactor(String n) {
        this(new BigInteger(n));
    }

    public CalculateFactor(BigInteger bigInteger) {
        this.source=bigInteger;
    }

    public TreeSet<BigInteger> call() {
        BigInteger n = source;
        TreeSet<BigInteger> factors = new TreeSet<BigInteger>();

        for (BigInteger i = TWO; i.compareTo(n.divide(i)) != 1; i = i.add(ONE)) {
            while (n.mod(i).equals(ZERO)) {
                factors.add(i);
                n = n.divide(i);
            }
        }
        if (n.compareTo(ONE) > -1) {
            factors.add(n);
        }
        return factors;
    }

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        List<String> numbers = new ArrayList<String>();
        for (int i = 0; i < 200; i++) {
            StringBuilder sb = new StringBuilder();
            int len=13 ; //+random.nextInt(3);
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

    private static void runFactorization(ExecutorService service, List<String>numbers) {
        long start = System.currentTimeMillis();
        List<Future<TreeSet<BigInteger>>> futures = new ArrayList<Future<TreeSet<BigInteger>>>();

        for (final String n : numbers) {
            CalculateFactor c = new CalculateFactor(n);
            futures.add(service.submit(c));
        }
        service.shutdown();

        long sum=0;
        for (Future<TreeSet<BigInteger>> c : futures) {
            try {
                sum+=c.get().size();
            } catch (Exception ignored) {
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("discovered factors: "+sum+" elapsed time: "+(end-start)+"ms");
    }
}
