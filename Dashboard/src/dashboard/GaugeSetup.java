package dashboard;

import javax.swing.JPanel;

public class GaugeSetup extends JPanel {
    
    private String title;
    
    public GaugeSetup(String name)
    {
        if (!name.isEmpty())
        {
            setTitle(name);
        }
    }
    
    public GaugeSetup()
    {
        this.title = "Default";
    }
    
    public String getTitle()
    {
        return title;
    }
    
    public void setTitle(String title)
    {
        this.title = title;
    }
    
}
