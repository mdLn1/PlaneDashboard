package UIClassesForGauges;

import DesignPatterns.GaugeFactory;
import DesignPatterns.Helpers;
import Interfaces.SetPanel;
import eu.hansolo.steelseries.extras.TrafficLight;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class TrafficLightPanel extends JPanel implements SetPanel {
    private String title;
    private TrafficLight gauge;
    private JLabel gaugeName;
    
    
     public TrafficLightPanel(String name, String type) {
        if (!name.isEmpty() && !type.isEmpty()) {
            buildPanel(name, type);
            
        }
        else
        {
            initialiseEmptyPanel();
        }
        
    }
    
    public TrafficLightPanel() {
        initialiseEmptyPanel();
    }
    
    
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
