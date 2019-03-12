package dashboard;

public class WindRadial extends GaugeSetup implements GaugeAttributes {

    private String unit;
    private boolean dangerZone = false;
    private PairHeads limits;
    
    public WindRadial(String name)
    {
        super(name, "WindRadial");
    }
    
    public WindRadial()
    {
        super();
    }
    
    @Override
    public String getUnit() {
        return GaugeAttributes.super.getUnit(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setUnit(String unit) {
        System.out.println("Not Coded yet");
    }

    @Override
    public String getLimits() {
        return GaugeAttributes.super.getLimits(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setLimits(int start, int finish) {
        System.out.println("Not Coded yet");
    }

    @Override
    public boolean hasDangerZone() {
        return GaugeAttributes.super.hasDangerZone(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setDangerZone(int dangerLimit) {
        System.out.println("Not Coded yet");
    }
    
    
    
}
