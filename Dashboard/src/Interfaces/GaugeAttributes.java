package Interfaces;

public interface GaugeAttributes {
    
   
    public default String getUnit()
    {
        return "No unit set yet";
    }
    public void setUnit(String unit);
    public int getLimitMin();

    public void setLimitMin(int limitMin);

    public int getLimitMax();

    public void setLimitMax(int limitMax);
    
}
