package org.openspaces.screencasts.scaling.executor;

import org.openspaces.screencasts.scaling.tasks.CalculateFactor;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.math.BigInteger;
import java.util.TreeSet;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.testng.Assert.assertEquals;

public class CalculationTest {

    @DataProvider
    Object[][] generateCalculations() {
        return new Object[][]{
                {"64", 2},
                { "71298918273", 5}
        };
    }

    @Test(dataProvider = "generateCalculations")
    public void testCalculationThroughExecutor(String source, Integer count) throws ExecutionException, InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(1);
        CalculateFactor factor=new CalculateFactor(source);
        Future<TreeSet<BigInteger>> future=service.submit(factor);
        assertEquals(future.get().size(), count.intValue());
        service.shutdown();
    }
}
