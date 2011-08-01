package org.openspaces.screencasts.scaling.event;

import org.openspaces.screencasts.scaling.model.Calculation;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.math.BigInteger;

import static org.testng.Assert.*;

public class EventListenerTest {
    @Test
    public void testTemplate() {
        EventListener listener = new EventListener();
        Calculation c = listener.getTemplate();
        assertFalse(c.getProcessed());
        assertEquals(c.getEventClass(), "eventDriven");
        assertNull(c.getSource());
        assertNull(c.getFactors());
        assertNull(c.getId());
    }

    @DataProvider
    Object[][] generateCalculations() {
        return new Object[][]{
                {"64", 2},
                { "71298918273", 5}
        };
    }

    @Test(dataProvider = "generateCalculations")
    public void testCalculationEvent(String source, Integer count) {
        EventListener listener = new EventListener();

        Calculation c=listener.getTemplate();
        c.setSource(new BigInteger(source));

        Calculation d=listener.handleEvent(c);
        assertTrue(d.getProcessed());
        assertEquals(d.getFactors().size(),count.intValue());
    }
}
