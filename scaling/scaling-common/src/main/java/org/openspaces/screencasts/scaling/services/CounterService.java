package org.openspaces.screencasts.scaling.services;

import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;

public interface CounterService {
    @Transactional
    void trackEvent(BigInteger source, long currentTimeInMS);
}
