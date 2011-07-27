package com.gigaspaces.inventory.runtime;

import com.gigaspaces.inventory.events.InventoryEventHandler;
import com.gigaspaces.inventory.model.Item;
import com.gigaspaces.inventory.model.ScanEvent;
import org.openspaces.core.GigaSpace;
import org.openspaces.core.GigaSpaceConfigurer;
import org.openspaces.core.space.UrlSpaceConfigurer;
import org.openspaces.events.notify.SimpleNotifyContainerConfigurer;
import org.openspaces.events.notify.SimpleNotifyEventListenerContainer;

public class InventoryApp {
    final GigaSpace gigaSpace = new GigaSpaceConfigurer(
            new UrlSpaceConfigurer("jini://*/*/mySpace").create()).create();

    public InventoryApp() {
        ensureExists("sku1234", "Flower Curler");
        ensureExists("sku1235", "Math Paintbrush");
        ensureExists("sku1236", "Bucket o' Mush");
        SimpleNotifyEventListenerContainer notifyEventListenerContainer = new SimpleNotifyContainerConfigurer(gigaSpace)
                .eventListenerAnnotation(new InventoryEventHandler()).notifyContainer();
    }

    private void ensureExists(String sku, String description) {
        Item template = new Item(null, sku, null);
        Item spaceObject = gigaSpace.read(template);
        if (spaceObject == null) { // not found!
            template.setDescription(description);
            template.setCount(0L);
            gigaSpace.write(template);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new InventoryApp().run();
    }

    private void run() throws InterruptedException {
        ScanEvent event=new ScanEvent("sku1234");
        gigaSpace.write(event);

        event=new ScanEvent("sku1234");
        gigaSpace.write(event);
        Thread.sleep(60000);
    }
}
