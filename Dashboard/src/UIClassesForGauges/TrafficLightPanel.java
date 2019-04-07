package UIClassesForGauges;

import DesignPatterns.GaugeFactory;
import DesignPatterns.Helpers;
import Interfaces.SetPanel;
// <editor-fold desc="component of SteelSeries">
import eu.hansolo.steelseries.extras.TrafficLight;
// </editor-fold>
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

// pnale for traffic light component
public class TrafficLightPanel extends JPanel implements SetPanel {
    
    // title property
    private String title;
    
    //GUI components of the panel
    private TrafficLight gauge;
    private JLabel gaugeName;
    
    // constructor with two parameters
     public TrafficLightPanel(String name, String type) {
         // if nothing is passed in the constructor then do the same as the no-parameter constructor
        if (!name.isEmpty() && !type.isEmpty()) {
            buildPanel(name, type);
            
        }
        else
        {
            initialiseEmptyPanel();
        }
        
    }
    
     // no parameters constructor
    public TrafficLightPanel() {
        initialiseEmptyPanel();
    }
    
    //create dial and label defined by name and type
    @Override
    public void buildPanel(String name, String type) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        setAlignmentX(Component.CENTER_ALIGNMENT);
        
        gaugeName = Helpers.createLabel(name);
        gaugeName.setAlignmentX(LEFT_ALIGNMENT);
        gauge = (TrafficLight) GaugeFactory.createDial(type);
        gauge.setPreferredSize(new Dimension(300,300));
        title = name;
        setTitle(name);
        
        add(gaugeName);
        add(gauge);
    }

    @Override
    public void initialiseEmptyPanel() {
        gaugeName = Helpers.createLabel("Default");
        
        gauge = new TrafficLight();
        gauge.setPreferredSize(new Dimension(300,300));
        setTitle("Default");
        add(gaugeName);
        add(gauge);
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
        gaugeName.setText(title);
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public TrafficLight getGauge() {
        return gauge;
    }
    
}
