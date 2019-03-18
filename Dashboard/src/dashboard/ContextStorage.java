package dashboard;


import java.util.HashMap;

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
        if (s instanceof SpecialisedGauge)
        {
            gauges.put(s,c);
        } else {
            GaugeSetup gauge = (GaugeSetup) s;
            gauge.getGauge().setLedVisible(false);
            gauges.put(s,c);
        }
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
            GaugeSetup gs = (GaugeSetup) s;
            if (gs.getTitle().equals(title))
                return s;
            
        }
        
        return null;
    }
    
    public void editGaugeTitle(String oldTitle, String newTitle) {
        for (Object s: gauges.keySet())
        {
            GaugeSetup gs = (GaugeSetup) s;
            if (gs.getTitle().equals(oldTitle))
            {
                gs.setTitle(newTitle);
            }
            
        }
    }
}
