package com.gigaspaces.anyapi;

import com.gigaspaces.async.AsyncResult;
import org.openspaces.core.GigaSpace;
import org.openspaces.core.executor.DistributedTask;
import org.openspaces.core.executor.TaskGigaSpace;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: joeo
 * Date: 4/12/11
 * Time: 9:53 AM
 * To change this template use File | Settings | File Templates.
 */
public class ProgrammerCountTask implements DistributedTask<Integer, Integer> {
    @TaskGigaSpace
    transient GigaSpace gigaSpace;

    @Override
    public Integer execute() throws Exception {
        System.out.println("In remote execute()");
        Programmer[] programmers =
                gigaSpace.readMultiple(new Programmer(), Integer.MAX_VALUE);
        System.out.println("array length: " + programmers.length);
        return programmers.length;
    }

    @SuppressWarnings({"ThrowableResultOfMethodCallIgnored"})
    @Override
    public Integer reduce(List<AsyncResult<Integer>> asyncResults) throws Exception {
        int sum = 0;
        for (AsyncResult<Integer> result : asyncResults) {
            System.out.println(result.getException());
            System.out.println(result.getResult());
            if (result.getException() != null) {
                throw result.getException();
            }
            sum += result.getResult();
        }
        return sum;
    }

}
