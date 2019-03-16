
package dashboard;

public final class SpecialisedGauge extends RegularGauge {
    
    private boolean dangerZone = false;
    private PairHeads dangerZoneRange;

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
    
    public PairHeads getDangerZoneRange() {
        return dangerZoneRange;
    }

    public void setDangerZoneRange(int min, int max) {
        dangerZoneRange = new PairHeads(min,max);
    }
    
    
}
