package org.openspaces.screencasts.scaling.event;

import org.openspaces.events.EventDriven;
import org.openspaces.events.EventTemplate;
import org.openspaces.events.adapter.SpaceDataEvent;
import org.openspaces.events.notify.Notify;
import org.openspaces.events.polling.Polling;
import org.openspaces.screencasts.scaling.model.Calculation;
import org.openspaces.screencasts.scaling.tasks.CalculateFactor;

@EventDriven
@Notify
public class EventListener {

    public EventListener() {
        System.out.println("EventListener constructed");
    }

    @EventTemplate
    public Calculation getTemplate() {
        Calculation c = new Calculation();
        c.setEventClass("eventDriven");
        c.setProcessed(false);
        System.out.println("Returning template: "+c);
        return c;
    }

    @SpaceDataEvent
    public Calculation handleEvent(Calculation calculation) {
        System.out.println("Received event: "+calculation);
        CalculateFactor factor = new CalculateFactor(calculation.getSource());
        calculation.setProcessed(true);
        calculation.setFactors(factor.call());
        System.out.println("Finished: "+calculation);
        return calculation;
    }
}
