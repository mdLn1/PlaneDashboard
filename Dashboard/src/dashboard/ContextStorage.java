package dashboard;

import Interfaces.SetPanel;
import UIClasses.TrafficLightSetup;
import UIClasses.RegularGauge;
import UIClasses.SpecialisedGauge;
import UIClasses.GaugeSetup;
import java.util.HashMap;

// singleton pattern to store the gauges
public class ContextStorage {

    private static ContextStorage instance = null;

    private HashMap<Object, PairHeads> gauges;
    
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
        if (s instanceof SpecialisedGauge) {
            gauges.put(s, c);
        } else if (s instanceof RegularGauge) {
            GaugeSetup gauge = (GaugeSetup) s;
            gauge.getGauge().setLedVisible(false);
            gauges.put(s, c);
        } else if (s instanceof TrafficLightSetup) {
            gauges.put(s, c);
        }
    }

    public HashMap<Object, PairHeads> getGauges() {
        return gauges;
    }
    
    
    public synchronized void reinitializeGauges() {
        gauges.clear();
        
    }

    public PairHeads getConstraints(Object gauge) {
        for (Object gs : gauges.keySet()) {
            if (gs.equals(gauge)) {
                return gauges.get(gs);
            }
        }
        return null;
    }

    public Object getGauge(String title) {
        for (Object s : gauges.keySet()) {
            SetPanel gs = (SetPanel) s;
            if (gs.getTitle().equals(title)) {
                return s;
            }

        }
        return null;
    }

    public synchronized void editGaugeTitle(String oldTitle, String newTitle) {
        for (Object s : gauges.keySet()) {
            if (s instanceof SpecialisedGauge) {
                SpecialisedGauge sg = (SpecialisedGauge) s;
                if (sg.getTitle().equals(oldTitle)) {
                    sg.setTitle(newTitle);
                    PairHeads pair = gauges.get(s);

                    gauges.remove(s);

                    addGauge(sg, pair);
                    return;
                }
            } else if (s instanceof RegularGauge) {
                RegularGauge rg = (RegularGauge) s;
                if (rg.getTitle().equals(oldTitle)) {
                    rg.setTitle(newTitle);
                    PairHeads pair = gauges.get(s);

                    gauges.remove(s);

                    addGauge(rg, pair);
                    return;
                }

            }
        }
    }
}
