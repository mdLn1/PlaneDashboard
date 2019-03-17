package dashboard;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ContextStorage {
    
    private static ContextStorage instance = null;

    private HashMap<GaugeSetup,PairHeads> gauges;
    private List<SpecialisedGauge> specialisedGauges;
    private List<RegularGauge> regularGauges;

    private ContextStorage() {
        gauges = new HashMap<>();
        specialisedGauges = new ArrayList<>();
        regularGauges = new ArrayList<>();
    }
    
    public static synchronized ContextStorage getInstance() {
        if (instance == null) {
            instance = new ContextStorage();
        }
        return instance;
    }

    public synchronized void addGauge(GaugeSetup s, PairHeads c) {
        gauges.put(s,c);
    }
    
    public HashMap<GaugeSetup,PairHeads> getGauges()
    {
        return gauges;
    }
    
    public PairHeads getConstraints(String gg)
    {
        for(GaugeSetup gs: gauges.keySet())
        {
            if (gs.getTitle().equals(gg))
            {
                return gauges.get(gs);
            }
        }
        return new PairHeads(0,0);
    }
    
    public void addSpecialisedGauge(SpecialisedGauge s)
    {
        specialisedGauges.add(s);
    }
    
    public void addRegularGauge(RegularGauge s)
    {
        regularGauges.add(s);
    }
    
    public SpecialisedGauge getSpecialisedGauge(String title)
    {
        for (SpecialisedGauge s: specialisedGauges)
        {
            if (s.getTitle().equals(title))
            {
                return s;
            }
        }
        
        return null;
    }
    
    public RegularGauge getRegularGauge(String title)
    {
        for (RegularGauge s: regularGauges)
        {
            if (s.getTitle().equals(title))
            {
                return s;
            }
        }
        
        return null;
    }
    
}
