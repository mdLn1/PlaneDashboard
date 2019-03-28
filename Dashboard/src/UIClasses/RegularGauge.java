package UIClasses;

import UIClasses.GaugeSetup;
import Interfaces.GaugeAttributes;

public class RegularGauge extends GaugeSetup implements GaugeAttributes {

    private String unit;
    private int limitMin;
    private int limitMax;
    
    public RegularGauge(String name, String type, String unit)
    {
        super(name, type);
        setUnit(unit);
    }
    
    public RegularGauge()
    {
        super();
    }
    
    public int getLimitMin() {
        return limitMin;
    }

    public void setLimitMin(int limitMin) {
        this.limitMin = limitMin;
    }

    public int getLimitMax() {
        return limitMax;
    }

    public void setLimitMax(int limitMax) {
        this.limitMax = limitMax;
    }
    
    @Override
    public String getUnit() {
        return unit;
    }

    @Override
    public void setUnit(String unit) {
        this.unit = unit;
        setGaugeUnit(unit);
    }
}
