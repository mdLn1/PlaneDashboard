
package dashboard;

import eu.hansolo.steelseries.tools.Section;
import java.awt.Color;

public final class SpecialisedGauge extends RegularGauge {
    
    private boolean dangerZone = false;
    private double dangerZoneMin = 0.0;
    private double dangerZoneMax = 0.0;

    public SpecialisedGauge(String name, String type) {
        super(name, type);
    }
    
    
    public boolean hasDangerZone()
    {
        return dangerZone;
    }
    
    public void setDangerZone(boolean value)
    {
        dangerZone = value;
    }
    
    public double getDangerZoneMin() {
        return dangerZoneMin;
    }
    
    public double getDangerZoneMax() {
        return dangerZoneMax;
    }

    public void setDangerZoneMin(double min) {
        dangerZoneMin = min;
        getGauge().setTrackStart(dangerZoneMax);
        
    }
     public void setDangerZoneMax(double max) {
        dangerZoneMax = max;
        getGauge().setTrackStop(dangerZoneMax);
        
    }
    
    public void setDangerZoneRange(double min, double max) {
        getGauge().addSection(new Section(min,max, Color.GREEN));
    }
    
}
