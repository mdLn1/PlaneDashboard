package dashboard;

import eu.hansolo.steelseries.extras.WindDirection;
import eu.hansolo.steelseries.gauges.AbstractRadial;
import eu.hansolo.steelseries.gauges.Radial;

public class GaugeFactory {
    
    public static AbstractRadial createRadialGauge(String type)
    {
        if(type.equals("Wind"))
        {
            return new WindDirection();
        } 
        else
        {
            return new Radial();
        }
    }
    
}
