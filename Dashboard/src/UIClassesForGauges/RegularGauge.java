package UIClassesForGauges;

import Interfaces.GaugeAttributes;

public class RegularGauge extends GaugePanel implements GaugeAttributes {

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
    
    @Override
    public int getLimitMin() {
        return limitMin;
    }

    @Override
    public void setLimitMin(String limitMin) throws NumberFormatException, IllegalArgumentException
    {
        if (this.gauge.getMaxValue() > 1 + Double.parseDouble(limitMin)
                && Double.parseDouble(limitMin) > -7000) {
        this.gauge.setMinValue(Double.parseDouble(limitMin));
        this.limitMin = Integer.parseInt(limitMin);
         return;
        } 
        throw new IllegalArgumentException();
    }

    @Override
    public int getLimitMax() {
        return limitMax;
    }

    @Override
    public void setLimitMax(String limitMax) throws NumberFormatException, IllegalArgumentException {
        if (this.gauge.getMinValue() + 1 < Double.parseDouble(limitMax)
                && Double.parseDouble(limitMax) < 8000) {
            this.gauge.setMaxValue(Double.parseDouble(limitMax));
            this.limitMax = Integer.parseInt(limitMax);
            return;
        } 
        throw new IllegalArgumentException();
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
