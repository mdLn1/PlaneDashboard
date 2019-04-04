package DesignPatterns;

import Interfaces.SetPanel;
import UIClassesForGauges.TrafficLightSetup;
import UIClassesForGauges.RegularGauge;
import UIClassesForGauges.SpecialisedGauge;
import UIClassesForGauges.GaugeSetup;
import dashboard.PairHeads;
import java.util.HashMap;

// singleton class to store the gauges
public class ContextStorage {

    private static ContextStorage instance = null;

    private HashMap<Object, PairHeads> gauges;
    
    // private controller
    private ContextStorage() {
        gauges = new HashMap<>();
    }

    // static method to return the instance, if exists
    public static synchronized ContextStorage getInstance() {
        if (instance == null) {
            instance = new ContextStorage();
        }
        return instance;
    }
    
    // add a gauge to the gauges HashMap together with its positions in GridBagLayout
    public synchronized void addGauge(Object s, PairHeads c) {
        if (s instanceof RegularGauge) {
            GaugeSetup gauge = (GaugeSetup) s;
            gauge.getGauge().setLedVisible(false);
            gauges.put(s, c);
        } else {
            gauges.put(s, c);
        }
    }

    // return all the gauges saved
    public HashMap<Object, PairHeads> getGauges() {
        return gauges;
    }
    
    // clear the HashMap
    public synchronized void reinitializeGauges() {
        gauges.clear();
        
    }

    // get the postions of the gauge looked for
    public PairHeads getConstraints(Object gauge) {
        for (Object gs : gauges.keySet()) {
            if (gs.equals(gauge)) {
                return gauges.get(gs);
            }
        }
        return null;
    }

    // find a gauge using its title
    public Object getGauge(String title) {
        for (Object s : gauges.keySet()) {
            SetPanel gs = (SetPanel) s;
            if (gs.getTitle().equals(title)) {
                return s;
            }

        }
        return null;
    }

    // change the title of a gauge
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
