
package dashboard;

public final class SpecialisedGauge extends RegularGauge {
    
    private boolean dangerZone = false;
    private int dangerZoneMin;
    private int dangerZoneMax;

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
    
    public int getDangerZoneMin() {
        return dangerZoneMin;
    }
    
    public int getDangerZoneMax() {
        return dangerZoneMax;
    }

    public void setDangerZoneMin(int min) {
        dangerZoneMin = min;
    }
     public void setDangerZoneMax(int max) {
        dangerZoneMax = max;
    }
    
    
}
