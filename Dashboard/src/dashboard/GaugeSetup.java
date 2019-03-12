package dashboard;

import eu.hansolo.steelseries.gauges.AbstractRadial;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GaugeSetup extends JPanel{

    private String title;

    //private final String[] descriptions = {"WindRadial"};
    
    private JLabel gaugeName;
    
    //private JPanel mainPanel;

    private AbstractRadial gauge;

    public GaugeSetup(String name, String type) {
        if (!name.isEmpty() && !type.isEmpty()) {
            buildPanel(name, type);
        }
        else
        {
            initialiseEmptyPanel();
        }
        
    }
    
    public GaugeSetup() {
        initialiseEmptyPanel();
    }
    
    //create gauge and label defined by name and type
    private void buildPanel(String name, String type)
    {
        setTitle(name);
        gaugeName = new JLabel(name);
        add(gaugeName);
//        for (String s : descriptions) {
//                if (s.equals(type)) 
//                {
//                    
//                }
//                    
//        }
        gauge = GaugeFactory.createRadialGauge(type);
        add(gauge);
    }
    
    //create gauge and label if no parameters defined
    private void initialiseEmptyPanel()
    {
        gaugeName = new JLabel("Default");
        setTitle("Default");
        gauge = GaugeFactory.createRadialGauge("");
        add(gaugeName);
        add(gauge);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
