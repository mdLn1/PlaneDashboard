package UIClassesForGauges;

import UIClassesForGauges.GaugeSetup;
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

    public void setLimitMin(String limitMin) throws NumberFormatException, IllegalArgumentException
    {
        this.gauge.setMinValue(Double.parseDouble(limitMin));
        this.limitMin = Integer.parseInt(limitMin);
    }

    public int getLimitMax() {
        return limitMax;
    }

    public void setLimitMax(String limitMax) throws NumberFormatException {
        this.gauge.setMaxValue(Double.parseDouble(limitMax));
        this.limitMax = Integer.parseInt(limitMax);
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
