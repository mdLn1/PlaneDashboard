package dashboard;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ContextStorage {
    
    private static ContextStorage instance = null;

    private HashMap<Object,PairHeads> gauges;

    private ContextStorage() {
        gauges = new HashMap<>();
    }
    
    public static synchronized ContextStorage getInstance() {
        if (instance == null) {
            instance = new ContextStorage();
        }
        return instance;
    }

    public synchronized void addGauge(Object s, PairHeads c) {
        gauges.put(s,c);
    }
    
    public HashMap<Object,PairHeads> getGauges()
    {
        return gauges;
    }
    
    public PairHeads getConstraints(String gg) {
        for (Object gs : gauges.keySet()) {
            if (gs instanceof GaugeSetup) {
                GaugeSetup gauge = (GaugeSetup) gs;
                if (gauge.getTitle().equals(gg)) {
                    return gauges.get(gs);
                }
            }
        }
        return new PairHeads(0, 0);
    }

    
    public Object getGauge(String title)
    {
        for (Object s: gauges.keySet())
        {
            if (s instanceof SpecialisedGauge) {
                SpecialisedGauge sg = (SpecialisedGauge) s;

                if (sg.getTitle().equals(title)) {
                    return s;
                }
            } else if (s instanceof RegularGauge) {
                RegularGauge rg = (RegularGauge) s;

                if (rg.getTitle().equals(title)) {
                    return s;
                }
            }
            
        }
        
        return null;
    }
    
}
