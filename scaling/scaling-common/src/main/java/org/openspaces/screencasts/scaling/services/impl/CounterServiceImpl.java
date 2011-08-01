package org.openspaces.screencasts.scaling.services.impl;

import org.openspaces.core.GigaSpace;
import org.openspaces.screencasts.scaling.model.CalculationEvent;
import org.openspaces.screencasts.scaling.services.CounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;

@Service
public class CounterServiceImpl implements CounterService {
    @Autowired
    GigaSpace space;


    @Override
    @Transactional
    public void trackEvent(BigInteger source, long currentTimeInMS) {
        CalculationEvent event=new CalculationEvent();
        event.setTimeSlice(System.currentTimeMillis());
        event.setDigit(source.mod(BigInteger.TEN).intValue());
        space.write(event);
    }
}
