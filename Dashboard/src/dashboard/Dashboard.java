package dashboard;

import Interfaces.FrameSetup;
import Interfaces.SetPanel;
import UIClasses.TrafficLightSetup;
import UIClasses.RegularGauge;
import UIClasses.SpecialisedGauge;
import UIClasses.GaugeSetup;
import eu.hansolo.steelseries.gauges.AbstractGauge;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public final class Dashboard implements FrameSetup {

    private ContextStorage context;
    
    // <editor-fold desc="GUI components">
    
    private JFrame mainFrame;
    private Container rightContainer;
    private Container centerContainer;
    
    // <editor-fold desc="mainContaienr components">
    RegularGauge windDir;
    RegularGauge airPressure;
    SpecialisedGauge speed;
    SpecialisedGauge temperature;
    RegularGauge fuel;
    TrafficLightSetup trafficLight;
    
    // </editor-fold>
    
    // <editor-fold desc="rightContainer JComponents">
    
    // <editor-fold desc="simulation panel input fields">
    private JButton playSimulationButton;
    
    private JLabel  playSpeedLabel;
    private JLabel  playAirPressureLabel;
    private JLabel  playTemperatureLabel;
    private JLabel  playWindDirectionLabel;
    private JLabel  playFuelLabel;
    private JLabel  distanceLabel;
    
    private JTextField playSpeedText;
    private JTextField playAirPressureText;
    private JTextField playTemperatureText;
    private JTextField playWindDirectionText;
    private JTextField playFuelText;
    private JTextField distanceText;
    
    private JButton runSimulationButton;
    
    // </editor-fold>
    
    
    // <editor-fold dexc="set Value for selected gauge">
    private JLabel selectedGaugeLabel;
    private JTextField selectedGaugeValueText;
    private JButton selectedGaugeValueButton;
    
    // </editor-fold>
    
    // <editor-fold desc="traffic light components">
    private JCheckBox redLightCheckBox;
    private JCheckBox yellowLightCheckBox;
    private JCheckBox greenLightCheckBox;
    
    // </editor-fold>
    
    // <editor-fold desc="dynamically generated panel for gauge details editing">
    
    // <editor-fold desc="specialised gauge JComponents">
    private JLabel dangerLabel;
    private JTextField dangerMinText;
    private JTextField dangerMaxText;
    private JLabel dangerMinLabel;
    private JLabel dangerMaxLabel;
    private JButton dangerMinButton;
    private JButton dangerMaxButton;
    private JButton dangerRangeButton;
    // </editor-fold>
    
    private JPanel editPanel;
    private JPanel gaugeValuePanel;
    private JPanel simulationInputsPanel;
    
    private JLabel unitLabel;
    private JButton unitButton;
    private JTextField unitTextField;
    
    private JLabel titleLabel;
    private JButton titleButton;
    private JTextField titleTextField;
    // </editor-fold>
    
    // </editor-fold>
    
    
    // constructor
    public Dashboard() {
        createGUI();
    }

    //method that designs mainFrame
    @Override
    public void createGUI() {
        
        // <editor-fold desc="frame settings">
        
        mainFrame = new JFrame("Plane Dashboard");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(2000, 1000);
        mainFrame.setPreferredSize(mainFrame.getSize());
        mainFrame.setLayout(new BorderLayout());
        
        // </editor-fold>
        
        context = ContextStorage.getInstance();

        addComponentsToFrame(mainFrame.getContentPane());

        mainFrame.pack();
        mainFrame.setVisible(true);
    }

    //design main container for frame
    @Override
    public void addComponentsToMainContainer(Container container) {

        context = ContextStorage.getInstance();
        // <editor-fold desc="create gauges and add them to ContextStorage singleton class">
        windDir = new RegularGauge("Wind Direction", Helpers.DIRECTION_DIAL, "Km/h");
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 0.5;
        c.weighty = 0.5;
        context.addGauge(windDir, new PairHeads(0, 0));
        

        airPressure = new RegularGauge("Air Pressure", Helpers.HALF_DIAL, "mmHg");
        context.addGauge(airPressure, new PairHeads(1, 0));

        speed = new SpecialisedGauge("Speed", Helpers.SIMPLER_RADIAL, "Km/h");
        context.addGauge(speed, new PairHeads(2, 0));

        temperature = new SpecialisedGauge("Temperature", Helpers.LINEAR_BAR, "Celsius");
        context.addGauge(temperature, new PairHeads(0, 1));

        fuel = new RegularGauge("Fuel", Helpers.QUARTER_DIAL, "hectoL");
        context.addGauge(fuel, new PairHeads(1, 1));
        // </editor-fold>
        
        
        trafficLight = new TrafficLightSetup("Traffic Light", "Traffic Light");
        
        trafficLight.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent evt) {
                    adjustRightContainer((TrafficLightSetup) trafficLight);
                }

            });
        
        c.gridx = 2;
        c.gridy = 1;
       
        container.add(trafficLight, c);

        // <editor-fold desc="add mouse listener for every gauge + add every gauge to the main container">
        for (Map.Entry<Object, PairHeads> g : context.getGauges().entrySet()) {
            JComponent tempGauge = (JComponent) g.getKey(); 
            tempGauge.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent evt) {
                    SetPanel sg = (SetPanel) g.getKey();
                    selectedGaugeLabel.setText(sg.getTitle());
                    adjustRightContainer(sg);
                }

            });
            if (g.getKey() instanceof GaugeSetup) {
                //SetPanel sp = (SetPanel) g.getKey();
                PairHeads positions = context.getConstraints(g.getKey());
                c.gridx = positions.getStart();
                c.gridy = positions.getEnd();
                container.add(tempGauge, c);
            }
           
        }
         context.addGauge(trafficLight, new PairHeads(2,1));
         // </editor-fold>

    }
    
    //adjust the right container in the frame
    public void adjustRightContainer(Object gauge) {
        if (editPanel != null) {
            rightContainer.remove(editPanel);
        }
        editPanel = new JPanel();
        editPanel.setLayout(new BoxLayout(editPanel, BoxLayout.Y_AXIS));
        editPanel.setAlignmentX(Component.TOP_ALIGNMENT);
        if (simulationInputsPanel != null) 
        {
            rightContainer.remove(simulationInputsPanel);
        }
        
        if (!gaugeValuePanel.isVisible()) {
            gaugeValuePanel.setVisible(true);
        }
        
        SetPanel selectedGauge = (SetPanel) gauge;
        dangerLabel = Helpers.createLabel("Edit Panel for " + selectedGauge.getTitle());
        dangerLabel.setPreferredSize(new Dimension(280, 40));
        editPanel.add(dangerLabel);
        
        if (gauge instanceof SpecialisedGauge) {
            SpecialisedGauge specialGauge = (SpecialisedGauge) gauge;
            
            // <editor-fold desc="additional components for a specialised gauge">
            editPanel.add(Helpers.createSeparatorYaxis());
            
            dangerMinLabel = Helpers.createSmallLabel("Danger min limit for "
                    + specialGauge.getTitle());
            dangerMinLabel.setPreferredSize(new Dimension(280, 40));
            editPanel.add(dangerMinLabel);
            
            dangerMinText = Helpers.createTextField(specialGauge.getDangerZoneMin() + "");
            editPanel.add(dangerMinText);
            
            dangerMinButton = Helpers.createButton("Save minimum");
            dangerMinButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    SpecialisedGauge gauge = (SpecialisedGauge) context
                            .getGauge(selectedGaugeLabel.getText().trim());
                    try {
                    gauge.setDangerZoneMin(Double.parseDouble(dangerMinText.getText()));
                    } catch (InvalidLimitsException ile) {
                        dangerMinText.setText(gauge.getDangerZoneMin() + "");
                        JOptionPane.showMessageDialog(mainFrame, ile.toString(), "Error", 0);
                    }
                }

            });
            editPanel.add(dangerMinButton);
            
            dangerMaxLabel = Helpers.createSmallLabel("Danger max limit for "
                    + specialGauge.getTitle());
            dangerMaxLabel.setPreferredSize(new Dimension(280, 40));
            editPanel.add(dangerMaxLabel);
            
            dangerMaxText = Helpers.createTextField(specialGauge.getDangerZoneMax() + "");
            editPanel.add(dangerMaxText);
            
            dangerMaxButton = Helpers.createButton("Save maximum");
            dangerMaxButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    SpecialisedGauge gauge = (SpecialisedGauge) context
                            .getGauge(selectedGaugeLabel.getText().trim());
                    try {
                    gauge.setDangerZoneMax(Double.parseDouble(dangerMaxText.getText()));
                    } catch (InvalidLimitsException ile) {
                       dangerMaxText.setText(gauge.getDangerZoneMax() + "");
                       JOptionPane.showMessageDialog(mainFrame, ile.toString(), "Error", 0);
                    }
                }

            });
            editPanel.add(dangerMaxButton);
            
            dangerRangeButton = Helpers.createButton("Save both");
            dangerRangeButton.setToolTipText("Works differently as the color"
                    + " changes when the interval is reached");
            dangerRangeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    SpecialisedGauge gauge = (SpecialisedGauge) context
                            .getGauge(selectedGaugeLabel.getText().trim());
                    
                    try {
                    gauge.setDangerZoneRange(Double.parseDouble(dangerMinText.getText()),
                            Double.parseDouble(dangerMaxText.getText()));
                    } catch (InvalidLimitsException ile) {
                       dangerMinText.setText(gauge.getDangerZoneMin() + "");
                       dangerMaxText.setText(gauge.getDangerZoneMax() + "");
                       JOptionPane.showMessageDialog(mainFrame, ile.toString(), "Error", 0);
                    }
                            
                }

            });
            
            editPanel.add(dangerRangeButton);
            // </editor-fold>
            
            createRegularFormInput((RegularGauge) gauge, editPanel);
            
        } else if (gauge instanceof RegularGauge) {
            createRegularFormInput((RegularGauge) gauge, editPanel);
        } else if (gauge instanceof TrafficLightSetup) {
            TrafficLightSetup traffic = (TrafficLightSetup) gauge;
            editPanel.add(Helpers.createSeparatorYaxis());
            
            redLightCheckBox = new JCheckBox("Red Light", traffic.getGauge().isRedOn());
            redLightCheckBox.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    traffic.getGauge().setRedOn(!traffic.getGauge().isRedOn());
                }
            });
            yellowLightCheckBox = new JCheckBox("Yellow Light", traffic.getGauge().isYellowOn());
            yellowLightCheckBox.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    traffic.getGauge().setYellowOn(!traffic.getGauge().isYellowOn());
                }
            });
            greenLightCheckBox = new JCheckBox("Green Light", traffic.getGauge().isGreenOn());
            greenLightCheckBox.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    traffic.getGauge().setGreenOn(!traffic.getGauge().isGreenOn());
                }
            });
            
            editPanel.add(Helpers.createSeparatorYaxis());
            editPanel.add(redLightCheckBox);
            editPanel.add(yellowLightCheckBox);
            editPanel.add(greenLightCheckBox);
            
            gaugeValuePanel.setVisible(false);
            
            GridBagConstraints c = new GridBagConstraints();
            c = Helpers.addConstraints(0, 2);
            c.fill = GridBagConstraints.HORIZONTAL;
            c.anchor = GridBagConstraints.PAGE_START;
            rightContainer.revalidate();
            rightContainer.repaint();
            rightContainer.add(editPanel, c);
        }

    }
    
    //create an input form for editing gauges
    public void createRegularFormInput(RegularGauge gauge, Container container)
    {
        String selected = selectedGaugeLabel.getText();
        selectedGaugeValueText.setText(gauge.getGauge().getValue() + "");
        GridBagConstraints c = new GridBagConstraints();
        
        // <editor-fold desc="setting up title components to edit">
        titleLabel = Helpers.createSmallLabel("Set title for " + gauge.getTitle());
        titleLabel.setPreferredSize(new Dimension(280, 40));
        container.add(titleLabel);

        titleTextField = Helpers.createTextField(gauge.getTitle());
        
        container.add(titleTextField);

        titleButton = Helpers.createButton("Save title");
        titleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SetPanel gauge = (SetPanel) context
                        .getGauge(selectedGaugeLabel.getText().trim());
                context.editGaugeTitle(gauge.getTitle(), titleTextField.getText().trim());
                selectedGaugeLabel.setText(titleTextField.getText().trim());
                
            }
            
        });
        container.add(titleButton);
        // </editor-fold>
        
        // <editor-fold desc="setting up unit of the components for editing">
        unitLabel = Helpers.createSmallLabel("Set unit for " + gauge.getTitle());
        unitLabel.setPreferredSize(new Dimension(280, 40));
        container.add(unitLabel);

        unitTextField = Helpers.createTextField(gauge.getUnit());
        container.add(unitTextField);

        unitButton = Helpers.createButton("Save unit");
        unitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegularGauge gauge = (RegularGauge) context
                        .getGauge(selectedGaugeLabel.getText().trim());
                gauge.getGauge().setUnitString(unitTextField.getText());
            }
            
        });
        container.add(unitButton);

        // </editor-fold>
        
        //rebuild the whole container (EAST)
        c = Helpers.addConstraints(0, 2);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.PAGE_START;
        rightContainer.revalidate();
        rightContainer.repaint();
        rightContainer.add(container, c);
    }

    @Override
    public void addComponentsToFrame(Container container) {
       
        centerContainer = new Container();
        centerContainer.setLayout(new GridBagLayout());
        addComponentsToMainContainer(centerContainer);
        
         //edit container on the right
        rightContainer = new Container();
        rightContainer.setLayout(new GridBagLayout());
        addComponentToRightContainer(rightContainer);
        
        
        container.add(centerContainer, BorderLayout.CENTER);
        container.add(rightContainer, BorderLayout.EAST);

    }

    @Override
    public void addComponentToRightContainer(Container container) {
        playSimulationButton = Helpers.createButton("Play Simulation");
        GridBagConstraints c = new GridBagConstraints();
        
        playSimulationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                mainFrame.remove(centerContainer);
                centerContainer.removeAll();
                context = ContextStorage.getInstance();
                context.reinitializeGauges();
                centerContainer = new Container();
                centerContainer.setLayout(new GridBagLayout());
                addComponentsToMainContainer(centerContainer);

                mainFrame.revalidate();
                mainFrame.repaint();
                mainFrame.add(centerContainer, BorderLayout.CENTER);
                createSimulationInputPanel();
            }

        });
        
        
        gaugeValuePanel = new JPanel();
        gaugeValuePanel.setLayout(new GridBagLayout());
        gaugeValuePanel.setAlignmentX(Component.TOP_ALIGNMENT);
        

        selectedGaugeLabel = Helpers.createLabel("Selected Gauge");
        selectedGaugeLabel.setPreferredSize(new Dimension(240, 40));
        c = Helpers.addConstraints(0, 0,1.0,0.8);
        c.fill = GridBagConstraints.HORIZONTAL;
        gaugeValuePanel.add(selectedGaugeLabel, c);

        selectedGaugeValueText = Helpers.createTextField("");
        c = Helpers.addConstraints(0, 1);
        c.fill = GridBagConstraints.HORIZONTAL;
        gaugeValuePanel.add(selectedGaugeValueText, c);

        selectedGaugeValueButton = Helpers.createButton("Save value");
        selectedGaugeValueButton.addActionListener(new ActionListener() {   
            @Override
            public void actionPerformed(ActionEvent e) {
                double newValue = Double.parseDouble(selectedGaugeValueText.getText());
                GaugeSetup gauge = (GaugeSetup) context
                        .getGauge(selectedGaugeLabel.getText().trim());
                if (gauge != null) {
                    UpdateGaugeThread updateThread = 
                            new UpdateGaugeThread((AbstractGauge) gauge.getGauge(), newValue);
                    updateThread.start();
                }
            }
        });
        c.gridx = 0;
        c.gridy = 2;
        c.fill = GridBagConstraints.NONE;
        gaugeValuePanel.add(selectedGaugeValueButton, c);
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.PAGE_START;
        container.add(playSimulationButton, c);
        c.gridx = 0;
        c.gridy = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        container.add(gaugeValuePanel, c);
        

    }
    
    
    public void createSimulationInputPanel() {
        playAirPressureLabel = Helpers.createSmallLabel("Air Pressure Value");
        playFuelLabel = Helpers.createSmallLabel("Fuel Value");
        playSpeedLabel = Helpers.createSmallLabel("Speed Value");
        playTemperatureLabel = Helpers.createSmallLabel("Temperature Value");
        playWindDirectionLabel = Helpers.createSmallLabel("Wind Direction Value");
        playWindDirectionLabel.setPreferredSize(new Dimension(220,30));
        distanceLabel = Helpers.createSmallLabel("Flight Distance");
        
        playAirPressureText = Helpers.createTextField("");
        playSpeedText = Helpers.createTextField("");
        playTemperatureText = Helpers.createTextField("");
        playFuelText = Helpers.createTextField("");
        playWindDirectionText = Helpers.createTextField("");
        distanceText = Helpers.createTextField("");
        if (simulationInputsPanel != null) 
        {
            rightContainer.remove(simulationInputsPanel);
        }
        
        
        runSimulationButton = Helpers.createButton("Run simulator");
        runSimulationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               windDir.getGauge().setValue(Double.parseDouble(playWindDirectionText.getText().trim()));
               fuel.getGauge().setValue(Double.parseDouble(playFuelText.getText().trim()));
               trafficLight.getGauge().setRedOn(true);
               
               
               
               playAirPressureText.getText().trim();
               playSpeedText.getText().trim();
               playTemperatureText.getText().trim();
               distanceText.getText().trim(); 
                       
            }
        });
        
        GridBagConstraints c = new GridBagConstraints();
        
        simulationInputsPanel = new JPanel();
        simulationInputsPanel.setLayout(new BoxLayout(simulationInputsPanel, BoxLayout.Y_AXIS));
        simulationInputsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        simulationInputsPanel.add(playAirPressureLabel);
        simulationInputsPanel.add(playAirPressureText);
        simulationInputsPanel.add(playSpeedLabel);
        simulationInputsPanel.add(playSpeedText);
        simulationInputsPanel.add(playFuelLabel);
        simulationInputsPanel.add(playFuelText);
        simulationInputsPanel.add(playWindDirectionLabel);
        simulationInputsPanel.add(playWindDirectionText);
        simulationInputsPanel.add(playTemperatureLabel);
        simulationInputsPanel.add(playTemperatureText);
        simulationInputsPanel.add(distanceLabel);
        simulationInputsPanel.add(distanceText);
        simulationInputsPanel.add(Helpers.createSeparatorYaxis());
        simulationInputsPanel.add(runSimulationButton);
       
        c.gridx = 0;
        c.gridy = 2;
        c.anchor = GridBagConstraints.PAGE_START;
        c.fill = GridBagConstraints.HORIZONTAL;
        simulationInputsPanel.add(Helpers.createSeparatorYaxis());
        rightContainer.add(simulationInputsPanel, c);
        
         if (editPanel != null) {
            rightContainer.remove(editPanel);
        }
        rightContainer.revalidate();
        rightContainer.repaint();
         if (gaugeValuePanel.isVisible())
        {
            gaugeValuePanel.setVisible(false);
        }
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
