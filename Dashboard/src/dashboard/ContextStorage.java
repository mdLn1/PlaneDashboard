package dashboard;

import eu.hansolo.steelseries.extras.TrafficLight;
import java.util.HashMap;

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

    public PairHeads getConstraints(String gg) {
        for (Object gs : gauges.keySet()) {
            if (gs instanceof GaugeSetup) {
                GaugeSetup gauge = (GaugeSetup) gs;
                if (gauge.getTitle().equals(gg)) {
                    return gauges.get(gs);
                }
            } else if (gs instanceof TrafficLightSetup) {
                return new PairHeads(1,2);
            }
        }
        return new PairHeads(0, 0);
    }

    public Object getGauge(String title) {
        for (Object s : gauges.keySet()) {
            GaugeSetup gs = (GaugeSetup) s;
            if (gs.getTitle().equals(title)) {
                return s;
            }

        }

        return null;
    }

    public void editGaugeTitle(String oldTitle, String newTitle) {
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
