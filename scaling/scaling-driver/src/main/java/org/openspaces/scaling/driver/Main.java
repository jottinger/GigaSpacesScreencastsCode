package org.openspaces.scaling.driver;

import com.j_spaces.core.IJSpace;
import org.openspaces.core.GigaSpace;
import org.openspaces.core.GigaSpaceConfigurer;
import org.openspaces.core.space.UrlSpaceConfigurer;
import org.openspaces.screencasts.scaling.model.Calculation;

import java.math.BigInteger;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws Exception {
        UrlSpaceConfigurer configurer = new UrlSpaceConfigurer("jini://*/*/scaling-datagrid");
        configurer.lookupGroups("Gigaspaces-XAPPremium-8.0.3-ga");
        //configurer.lookupLocators("127.0.0.1");
        IJSpace space = configurer.create();
        GigaSpace gigaSpace = new GigaSpaceConfigurer(space).gigaSpace();
        Calculation calc = new Calculation();
        StringBuilder number = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 14; i++) {
            number.append("" + random.nextInt(10));
        }
        calc.setEventClass("eventDriven");
        calc.setProcessed(false);
        calc.setSource(new BigInteger(number.toString()));
        gigaSpace.write(calc);
        configurer.destroy();
    }
}
