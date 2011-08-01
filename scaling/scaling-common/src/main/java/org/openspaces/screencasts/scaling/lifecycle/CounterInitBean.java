package org.openspaces.screencasts.scaling.lifecycle;

import org.hibernate.dialect.Oracle10gDialect;
import org.openspaces.core.GigaSpace;
import org.openspaces.events.polling.SimplePollingContainerConfigurer;
import org.openspaces.events.polling.SimplePollingEventListenerContainer;
import org.openspaces.screencasts.scaling.listeners.CounterListener;
import org.openspaces.screencasts.scaling.model.CalculationEvent;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This bean sets up listeners for each of the counter types.
 */
@Service
public class CounterInitBean implements InitializingBean {
    @Autowired
    GigaSpace space;


    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("CounterInitBean running");
        for (int i = 0; i < 10; i++) {
            CalculationEvent template=new CalculationEvent();
            template.setDigit(i);
            SimplePollingEventListenerContainer container = new SimplePollingContainerConfigurer(space)
                    .template(template)
                    .eventListenerAnnotation(new CounterListener(space, i))
                    .pollingContainer();
            System.out.println("Started event listener for "+i);
        }
    }
}
