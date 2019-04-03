package dashboard;

import eu.hansolo.steelseries.extras.TrafficLight;
import eu.hansolo.steelseries.extras.WindDirection;
import eu.hansolo.steelseries.gauges.LinearBargraph;
import eu.hansolo.steelseries.gauges.Radial;
import eu.hansolo.steelseries.gauges.Radial1Square;
import eu.hansolo.steelseries.gauges.Radial2Top;
import java.util.HashMap;
import javax.swing.JComponent;

public class GaugeFactory {
     
    private static final String[] ELEMENT_TYPES = {"Direction Radial", "Half Dial",
        "Quarter Dial", "Simple Radial", "VerticalBar", "Traffic Light" };

    static final HashMap<String, Class> savedGauges = new HashMap<>();
    
    static {
        savedGauges.put(ELEMENT_TYPES[0], WindDirection.class);
        savedGauges.put(ELEMENT_TYPES[1], Radial2Top.class);
        savedGauges.put(ELEMENT_TYPES[2], Radial1Square.class);
        savedGauges.put(ELEMENT_TYPES[3], Radial.class);
        savedGauges.put(ELEMENT_TYPES[4], LinearBargraph.class);
        savedGauges.put(ELEMENT_TYPES[5], TrafficLight.class);
    }

    static public String[] getDescriptions() {
        return savedGauges.keySet().toArray(new String[]{});
    }

    public static JComponent createRadialGauge(String type) {
        try {
            if (savedGauges.containsKey(type)) {
                Class theClass = (Class) savedGauges.get(type);
                return (JComponent) theClass.newInstance();
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    
}
