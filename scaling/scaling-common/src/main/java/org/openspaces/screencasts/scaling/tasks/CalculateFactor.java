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
    private final BigInteger source;

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
}
