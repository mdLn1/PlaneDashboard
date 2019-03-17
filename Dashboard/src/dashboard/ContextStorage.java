package dashboard;

import java.awt.GridBagConstraints;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class ContextStorage {
    
    private static ContextStorage instance = null;

    private HashMap<GaugeSetup,GridBagConstraints> gauges;

    private ContextStorage() {
        gauges = new HashMap<>();
    }
    
    public static synchronized ContextStorage getInstance() {
        if (instance == null) {
            instance = new ContextStorage();
        }
        return instance;
    }

    public synchronized void addGauge(GaugeSetup s, GridBagConstraints c) {
        gauges.put(s,c);
    }
    
    public HashMap<GaugeSetup,GridBagConstraints> getGauges()
    {
        return gauges;
    }
    
    public GridBagConstraints getConstraints(GaugeSetup gg)
    {
        return gauges.get(gg);
    }
    
}
