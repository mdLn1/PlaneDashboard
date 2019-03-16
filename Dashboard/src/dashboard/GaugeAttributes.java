package dashboard;

public interface GaugeAttributes {
    
   
    public default String getUnit()
    {
        return "No unit set yet";
    }
    public void setUnit(String unit);
    public default String getLimits()
    {
        return "No limits set yet";
    }
    public void setLimits(int start, int finish);
    
}
