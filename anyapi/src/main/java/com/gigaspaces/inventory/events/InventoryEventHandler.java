package com.gigaspaces.inventory.events;

import com.gigaspaces.inventory.model.Item;
import com.gigaspaces.inventory.model.ScanEvent;
import org.openspaces.core.GigaSpace;
import org.openspaces.events.EventDriven;
import org.openspaces.events.EventTemplate;
import org.openspaces.events.adapter.SpaceDataEvent;
import org.openspaces.events.notify.Notify;

@EventDriven
@Notify(performTakeOnNotify = true, ignoreEventOnNullTake = true)
public class InventoryEventHandler {
    @EventTemplate
    ScanEvent unprocessedData() {
        return new ScanEvent();
    }

    @SpaceDataEvent
    public Item eventListener(ScanEvent event, GigaSpace space) {
        Item template = new Item();
        template.setId(event.getItemId());
        Item item = space.take(template,30);
        if (item != null) {
            item.setCount(item.getCount() + 1);
            System.out.println("updated: " + item);
        } else {
            System.out.println("couldn't find " + template);
        }
        return item;
    }
}
