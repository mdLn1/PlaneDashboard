package dashboard;

public class RegularGauge extends GaugeSetup implements GaugeAttributes {

    private String unit;
    private PairHeads limits;
    
    public RegularGauge(String name, String type)
    {
        super(name, type);
        setUnit("unit");
    }
    
    public RegularGauge()
    {
        super();
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

    @Override
    public String getLimits() {
        return limits.toString();
    }

    @Override
    public void setLimits(int start, int finish) {
        limits = new PairHeads(start, finish);
    }
    
    
    
}
