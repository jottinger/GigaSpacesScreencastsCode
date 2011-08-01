package org.openspaces.screencasts.scaling.listeners;

import org.openspaces.core.GigaSpace;
import org.openspaces.events.EventDriven;
import org.openspaces.events.adapter.SpaceDataEvent;
import org.openspaces.events.polling.Polling;
import org.openspaces.screencasts.scaling.model.CalculationEvent;
import org.openspaces.screencasts.scaling.model.CalculationEventCounter;

@EventDriven
@Polling
public class CounterListener {
    final CalculationEventCounter template = new CalculationEventCounter();
    final GigaSpace space;

    public CounterListener(GigaSpace space, Integer digit) {
        template.setDigit(digit);
        this.space = space;
    }

    @SpaceDataEvent
    CalculationEvent handleEvent(CalculationEvent event, GigaSpace space) {
        template.setTimeSlice(System.currentTimeMillis() / 60000);
        CalculationEventCounter counter = space.take(template, 10);
        if (counter == null) {
            counter = new CalculationEventCounter();
            counter.setDigit(template.getDigit());
            counter.setCounter(0L);
            counter.setTimeSlice(template.getTimeSlice());
        }
        counter.setCounter(counter.getCounter() + 1);
        space.write(counter);
        return null;
    }
}
