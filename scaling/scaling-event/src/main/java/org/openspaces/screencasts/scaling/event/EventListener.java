package org.openspaces.screencasts.scaling.event;

import org.openspaces.events.EventDriven;
import org.openspaces.events.EventTemplate;
import org.openspaces.events.adapter.SpaceDataEvent;
import org.openspaces.events.polling.Polling;
import org.openspaces.screencasts.scaling.model.Calculation;
import org.openspaces.screencasts.scaling.tasks.CalculateFactor;

@EventDriven
@Polling
public class EventListener {

    @EventTemplate
    public Calculation getTemplate() {
                Calculation c=new Calculation();
        c.setEventClass("eventDriven");
        c.setProcessed(false);
        return c;
    }

    @SpaceDataEvent
    public Calculation handleEvent(Calculation calculation) {
        CalculateFactor factor=new CalculateFactor(calculation.getSource());
        calculation.setProcessed(true);
        calculation.setFactors(factor.call());
        return calculation;
    }
}
