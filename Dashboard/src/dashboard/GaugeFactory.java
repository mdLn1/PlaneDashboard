package dashboard;

import eu.hansolo.steelseries.extras.WindDirection;
import eu.hansolo.steelseries.gauges.AbstractGauge;
import eu.hansolo.steelseries.gauges.LinearBargraph;
import eu.hansolo.steelseries.gauges.Radial;
import eu.hansolo.steelseries.gauges.Radial1Square;
import eu.hansolo.steelseries.gauges.Radial2Top;
import java.util.HashMap;

public class GaugeFactory {
     
    private static final String[] ELEMENT_TYPES = {"Direction Radial", "Half Dial", "Quarter Dial", "Simple Radial", "VerticalBar" };

    static final HashMap<String, Class> investments = new HashMap<>();
    
    static {
        investments.put(ELEMENT_TYPES[0], WindDirection.class);
        investments.put(ELEMENT_TYPES[1], Radial2Top.class);
        investments.put(ELEMENT_TYPES[2], Radial1Square.class);
        investments.put(ELEMENT_TYPES[3], Radial.class);
        investments.put(ELEMENT_TYPES[4], LinearBargraph.class);
    }

    static public String[] getDescriptions() {
        return investments.keySet().toArray(new String[]{});
    }

    public static AbstractGauge createRadialGauge(String type) {
        try {
            if (investments.containsKey(type)) {
                Class theClass = (Class) investments.get(type);
                return (AbstractGauge) theClass.newInstance();
            } else {
                return new Radial();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    
}
