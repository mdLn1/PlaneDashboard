package dashboard;

import eu.hansolo.steelseries.extras.TrafficLight;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public final class Dashboard implements FrameSetup {

    ContextStorage context;
    JFrame mainFrame;
    Container rightContainer;
    Container centerContainer;
    JLabel selectedGaugeLabel;
    
    
    public Dashboard(){
        createGUI();
    }

    @Override
    public void createGUI() {
        mainFrame = new JFrame("Plane Dashboard");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(2000, 1000);
        mainFrame.setPreferredSize(mainFrame.getSize());
        mainFrame.setLayout(new BorderLayout());
        
        context = ContextStorage.getInstance();
        
        addComponentsToFrame(mainFrame.getContentPane());

        mainFrame.pack();
        mainFrame.setVisible(true);
    }

    @Override
    public void addComponentsToMainContainer(Container container) {

        RegularGauge windDir = new RegularGauge("Wind Direction", Helpers.DIRECTION_DIAL);
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.NONE;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.8;
        c.weighty = 0.8;
        //windDir.getGauge().addMouseListener(new GaugesListener(selectedGaugeLabel));
        
        context.addGauge(windDir, c);

        RegularGauge halfdial = new RegularGauge("Air Pressure", Helpers.HALF_DIAL);
        c.fill = GridBagConstraints.NONE;
        c.gridx = 1;
        c.gridy = 0;
        
        context.addGauge(halfdial, c);

        TrafficLight trafficLight = new TrafficLight();
        trafficLight.setPreferredSize(new Dimension(300, 300));
        c.gridx = 2;
        c.gridy = 0;
        c.weightx = 0.5;
        c.weighty = 0.5;
        //container.add(trafficLight, c);

        SpecialisedGauge temperature = new SpecialisedGauge("Temperature", Helpers.LINEAR_BAR);
        c.gridx = 0;
        c.gridy = 1;
        
        context.addGauge(temperature, c);
        
        SpecialisedGauge radial = new SpecialisedGauge("Speed", Helpers.SIMPLER_RADIAL);
        c.gridx = 2;
        c.gridy = 1;
        
        context.addGauge(radial, c);
        
        RegularGauge quarterDial = new RegularGauge("Fuel", Helpers.QUARTER_DIAL);
        c.fill = GridBagConstraints.NONE;
        c.gridx = 1;
        c.gridy = 1;
        
        context.addGauge(quarterDial, c);
        
        for(Map.Entry<GaugeSetup, GridBagConstraints> g: context.getGauges().entrySet())
        {
            g.getKey().getGauge().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                selectedGaugeLabel.setText(g.getKey().getTitle());
                System.out.println("Got clicked");
            }
            
        });
            c = g.getValue();
        }
        
        container.add(windDir, context.getConstraints(windDir));
        container.add(halfdial, context.getConstraints(halfdial));
        container.add(temperature, context.getConstraints(temperature));
        container.add(radial, context.getConstraints(radial));
        container.add(quarterDial, context.getConstraints(quarterDial));

    }
   

    @Override
    public void addComponentsToFrame(Container container) {
        //edit container on the right
        rightContainer = new Container();
        addComponentToRightContainer(rightContainer);
        rightContainer.setLayout(new GridBagLayout());

        centerContainer = new Container();
        centerContainer.setLayout(new GridBagLayout());
        addComponentsToMainContainer(centerContainer);
        container.add(centerContainer, BorderLayout.CENTER);
        container.add(rightContainer, BorderLayout.EAST);

    }
    
    @Override
    public void addComponentToRightContainer(Container container) {
        JPanel boxedPanel = new JPanel();
        boxedPanel.setLayout(new GridBagLayout());
        //boxedPanel.setPreferredSize(new Dimension(250,400));
        
        GridBagConstraints c = new GridBagConstraints();
        
        selectedGaugeLabel = Helpers.createLabel("Selected Gauge");
        selectedGaugeLabel.setPreferredSize(new Dimension(240,40));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1.0;
        c.weighty = 0.8;
        boxedPanel.add(selectedGaugeLabel,c);
        
        JTextField selectedGaugeText = Helpers.createTextField("");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        boxedPanel.add(selectedGaugeText,c);
        
        JButton saveDetailsGauge = Helpers.createButton("Save");
        c.fill = GridBagConstraints.NONE;
        c.gridx = 0;
        c.gridy = 2;
        boxedPanel.add(saveDetailsGauge,c);
        
        c.gridx = 0;
        c.gridy = 0;
        container.add(boxedPanel,c);
        
    }


    public static void main(String[] args) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Dashboard dashboard = new Dashboard();
            }
        });
    }


   
}
