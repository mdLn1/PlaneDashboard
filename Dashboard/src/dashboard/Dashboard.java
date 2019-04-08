package dashboard;

import DesignPatterns.Helpers;
import DesignPatterns.ContextStorage;
import Threading.ScriptGaugeThread;
import Threading.SlowValueThread;
import Threading.UpdateGaugeThread;
import Timers.GreenColorTimerTask;
import Interfaces.FrameSetup;
import Interfaces.GaugeAttributes;
import Interfaces.SetPanel;
import Timers.NotifySimulationResultTimerTask;
import UIClassesForGauges.TrafficLightPanel;
import UIClassesForGauges.RegularGauge;
import UIClassesForGauges.SpecialisedGauge;
import UIClassesForGauges.GaugePanel;
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
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import uk.ac.gre.comp1549.dashboard.events.DashBoardEvent;
import uk.ac.gre.comp1549.dashboard.events.DashBoardEventListener;
import uk.ac.gre.comp1549.dashboard.scriptreader.DashboardEventGeneratorFromXML;

public final class Dashboard implements FrameSetup, Runnable {

    public static final String SETGAUGES_SCRIPT = "dashboard_script.xml";
    public static final String SIMULATION_FILE = "simulation_script.xml";
    private String xmlScript = SETGAUGES_SCRIPT;

    // <editor-fold desc="GUI components">
    private JFrame mainFrame;
    private Container rightContainer;
    private Container centerContainer;

    // <editor-fold desc="mainContaienr components">
    private RegularGauge windDir;
    private RegularGauge airPressure;
    private SpecialisedGauge speed;
    private SpecialisedGauge temperature;
    private RegularGauge fuel;
    private TrafficLightPanel trafficLight;

    // </editor-fold>
    // <editor-fold desc="rightContainer JComponents">
    // <editor-fold desc="simulation panel input fields">
    private JButton playSimulationButton;
    private JButton setGaugesValuesButton;
    private JButton helpWithConversionButton;

    private JPanel topRightPanel;

    private JLabel playSpeedLabel;
    private JLabel playAirPressureLabel;
    private JLabel playTemperatureLabel;
    private JLabel playWindDirectionLabel;
    private JLabel playFuelLabel;
    private JLabel distanceLabel;

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

    private JLabel minLimitLabel;
    private JButton minLimitButton;
    private JTextField minLimitTextField;

    private JLabel maxLimitLabel;
    private JButton maxLimitButton;
    private JTextField maxLimitTextField;
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
        addComponentsToFrame(mainFrame.getContentPane());

        mainFrame.pack();
        mainFrame.setVisible(true);
    }
    
    //design main container for frame
    @Override
    public void addComponentsToMainContainer(Container container) {

        ContextStorage context = ContextStorage.getInstance();
        // <editor-fold desc="create gauges and add them to ContextStorage singleton class">
        windDir = new RegularGauge("Wind Direction", Helpers.DIRECTION_DIAL, "Km/h");
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 0.5;
        c.weighty = 0.5;
        context.addGauge(windDir, new PairHeads(0, 0));

        airPressure = new RegularGauge("Air Pressure", Helpers.HALF_DIAL, "mmHg");
        context.addGauge(airPressure, new PairHeads(1, 0));

        speed = new SpecialisedGauge("Speed", Helpers.SIMPLE_RADIAL, "Km/h");
        context.addGauge(speed, new PairHeads(2, 0));

        temperature = new SpecialisedGauge("Temperature", Helpers.LINEAR_BAR, "Celsius");
        context.addGauge(temperature, new PairHeads(0, 1));

        fuel = new RegularGauge("Fuel", Helpers.QUARTER_DIAL, "hectoL");
        context.addGauge(fuel, new PairHeads(1, 1));

        trafficLight = new TrafficLightPanel("Traffic Light", "Traffic Light");

        trafficLight.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                adjustRightContainer((TrafficLightPanel) trafficLight);
            }

        });

        c.gridx = 2;
        c.gridy = 1;

        container.add(trafficLight, c);

        // </editor-fold>
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
            if (g.getKey() instanceof GaugePanel) {
                //SetPanel sp = (SetPanel) g.getKey();
                PairHeads positions = context.getConstraints(g.getKey());
                c.gridx = positions.getStart();
                c.gridy = positions.getEnd();
                container.add(tempGauge, c);
            }

        }
        context.addGauge(trafficLight, new PairHeads(2, 1));
        // </editor-fold>

    }

    //adjust the right container in the frame, depending on the action taken by user
    public void adjustRightContainer(Object gauge) {
        if (editPanel != null) {
            rightContainer.remove(editPanel);
        }
        editPanel = new JPanel();
        editPanel.setLayout(new BoxLayout(editPanel, BoxLayout.Y_AXIS));
        editPanel.setAlignmentX(Component.TOP_ALIGNMENT);
        if (simulationInputsPanel != null) {
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
            ContextStorage context = ContextStorage.getInstance();
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
                    } catch (NumberFormatException infe) {
                        notifyNumberConversionError("danger minimum");
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
                    } catch (NumberFormatException infe) {
                        notifyNumberConversionError("danger maximum");
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
                    } catch (NumberFormatException infe) {
                        notifyNumberConversionError("danger minimum or maximum");
                    }

                }

            });

            editPanel.add(dangerRangeButton);
            // </editor-fold>

            createRegularFormInput((RegularGauge) gauge, editPanel);

        } else if (gauge instanceof RegularGauge) {
            createRegularFormInput((RegularGauge) gauge, editPanel);
        } else if (gauge instanceof TrafficLightPanel) {
            // <editor-fold  desc="build the editing panel for traffic lights">
            TrafficLightPanel traffic = (TrafficLightPanel) gauge;
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
            // </editor-fold>
        }

    }

    //create an input form for editing gauges
    public void createRegularFormInput(RegularGauge gauge, Container container) {
        selectedGaugeValueText.setText(gauge.getGauge().getValue() + "");
        GridBagConstraints c = new GridBagConstraints();

        // <editor-fold desc="setting up title components to edit">
        titleLabel = Helpers.createSmallLabel("Set title for " + gauge.getTitle());
        titleLabel.setPreferredSize(new Dimension(280, 40));
        container.add(titleLabel);

        titleTextField = Helpers.createTextField(gauge.getTitle());

        container.add(titleTextField);

        titleButton = Helpers.createButton("Save title");
        titleButton.addActionListener((ActionEvent e) -> {
            // change title of component
            ContextStorage context = ContextStorage.getInstance();
            SetPanel gauge1 = (SetPanel) context
                    .getGauge(selectedGaugeLabel.getText().trim());
            context.editGaugeTitle(gauge1.getTitle(), titleTextField.getText().trim());
            selectedGaugeLabel.setText(titleTextField.getText().trim());
        });
        container.add(titleButton);
        // </editor-fold>

        // <editor-fold desc="setting up unit of the components for editing when user selects gauge">
        unitLabel = Helpers.createSmallLabel("Set unit for " + gauge.getTitle());
        unitLabel.setPreferredSize(new Dimension(280, 40));
        container.add(unitLabel);

        unitTextField = Helpers.createTextField(gauge.getUnit());
        container.add(unitTextField);

        unitButton = Helpers.createButton("Save unit");
        unitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // setting unit for a dial
                ContextStorage context = ContextStorage.getInstance();
                GaugeAttributes gauge = (GaugeAttributes) context
                        .getGauge(selectedGaugeLabel.getText().trim());
                gauge.setUnit(unitTextField.getText());
            }

        });
        container.add(unitButton);

        minLimitLabel = Helpers.createSmallLabel("Set minimum for " + gauge.getTitle());
        container.add(minLimitLabel);

        minLimitTextField = Helpers.createTextField("" + gauge.getLimitMin());
        container.add(minLimitTextField);

        minLimitButton = Helpers.createButton("Save minimum");
        minLimitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // setting min limit for a dial
                ContextStorage context = ContextStorage.getInstance();
                GaugeAttributes gauge = (GaugeAttributes) context
                        .getGauge(selectedGaugeLabel.getText().trim());
                try {
                    gauge.setLimitMin(minLimitTextField.getText());
                } catch (NumberFormatException nfe) {
                    notifyNumberConversionError("minimum limit");
                } catch (IllegalArgumentException ile) {
                    notifyIllegalArgumentError("minimum limit");
                }
            }

        });
        container.add(minLimitButton);

        maxLimitLabel = Helpers.createSmallLabel("Set maximum for " + gauge.getTitle());
        container.add(maxLimitLabel);

        maxLimitTextField = Helpers.createTextField("" + gauge.getLimitMax());
        container.add(maxLimitTextField);

        maxLimitButton = Helpers.createButton("Save maximum");
        maxLimitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // setting maxLimit for a dial
                ContextStorage context = ContextStorage.getInstance();
                GaugeAttributes gauge = (GaugeAttributes) context
                        .getGauge(selectedGaugeLabel.getText().trim());
                try {
                    gauge.setLimitMax(maxLimitTextField.getText());
                } catch (NumberFormatException nfe) {
                    notifyNumberConversionError("maximum limit");
                } catch (IllegalArgumentException ile) {
                    notifyIllegalArgumentError("maximum limit");
                }
            }

        });
        container.add(maxLimitButton);

        // </editor-fold>
        c = Helpers.addConstraints(0, 2);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.PAGE_START;
        rightContainer.revalidate();
        rightContainer.repaint();
        rightContainer.add(container, c);
    }

    // add and create both, center and east Containers
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

    // run the script, if reading from simulation_script then give feedback 
    // regarding the flight result
    public void doScriptRunning() throws InterruptedException {
        if (xmlScript.equals(SIMULATION_FILE)) {
            Timer timer = new Timer();
            Thread newThread = new Thread(this);
            newThread.start();
            if (checkFlightStatus()) {
                TimerTask notification = new NotifySimulationResultTimerTask(mainFrame,
                        "Plane landed successfully !!", "Success", 2);
                timer.schedule(notification, 21000);
            } else {
                TimerTask notification = new NotifySimulationResultTimerTask(mainFrame, "Plane crashed!"
                        + " Not enough fuel to reach destination.", "Failure", 0);
                timer.schedule(notification, 21000);
            }
        } else {
            Thread newThread = new Thread(this);
            newThread.start();
        }

    }

    // show error message for NumberFormatException
    public void notifyNumberConversionError(String parameter) {
        JOptionPane.showMessageDialog(mainFrame, "Could not convert to number " + parameter,
                 "Error!", 0);
    }

    // show error message for IllegalArgumentException
    public void notifyIllegalArgumentError(String parameter) {
        JOptionPane.showMessageDialog(mainFrame, "The value passed for "
                + parameter + " could not be set",
                "Error!", 0);
    }

    // rebuild the mainFrame together with resetting the values of the dials
    public void reinitializeMainFrame() {

        mainFrame.remove(centerContainer);
        centerContainer.removeAll();
        ContextStorage context = ContextStorage.getInstance();
        context.reinitializeGauges();
        centerContainer = new Container();
        centerContainer.setLayout(new GridBagLayout());

        addComponentsToMainContainer(centerContainer);
        mainFrame.revalidate();
        mainFrame.repaint();
        mainFrame.add(centerContainer, BorderLayout.CENTER);
    }

    // build the EAST panel and add all the components needed
    @Override
    public void addComponentToRightContainer(Container container) {
        playSimulationButton = Helpers.createButton("Play Simulation");
        setGaugesValuesButton = Helpers.createButton("Preset Gauges Values");
        helpWithConversionButton = Helpers.createButton("Convert Distance");
        GridBagConstraints c = new GridBagConstraints();
        topRightPanel = new JPanel();
        topRightPanel.setLayout(new BoxLayout(topRightPanel, BoxLayout.Y_AXIS));
        topRightPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        playSimulationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // show the input Panel for simulation
                reinitializeMainFrame();
                createSimulationInputPanel();
                trafficLight.getGauge().setRedOn(true);
                xmlScript = SIMULATION_FILE;
            }

        });
        
        helpWithConversionButton.addActionListener((ActionEvent e) -> {
            ConverterHelp convHelp = new ConverterHelp();
            convHelp.setLocationRelativeTo(null);
            convHelp.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            convHelp.setVisible(true);
            
        });
        
        setGaugesValuesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // execute dasboard_script.xml to set dials values by reading from script
                reinitializeMainFrame();
                trafficLight.getGauge().setRedOn(true);
                if (editPanel != null) {
                    rightContainer.remove(editPanel);
                }
                rightContainer.revalidate();
                rightContainer.repaint();
                if (gaugeValuePanel.isVisible()) {
                    gaugeValuePanel.setVisible(false);
                }
                if (simulationInputsPanel != null) {
                    rightContainer.remove(simulationInputsPanel);
                }
                xmlScript = SETGAUGES_SCRIPT;
                try {
                    doScriptRunning();
                } catch (InterruptedException ex) {
                    System.out.println("thread was interrupted");
                }
            }
        });

        gaugeValuePanel = new JPanel();
        gaugeValuePanel.setLayout(new GridBagLayout());
        gaugeValuePanel.setAlignmentX(Component.TOP_ALIGNMENT);

        topRightPanel.add(playSimulationButton);
        topRightPanel.add(setGaugesValuesButton);
        topRightPanel.add(helpWithConversionButton);

        selectedGaugeLabel = Helpers.createLabel("Selected Gauge");
        selectedGaugeLabel.setPreferredSize(new Dimension(240, 40));
        c = Helpers.addConstraints(0, 0, 1.0, 0.8);
        c.fill = GridBagConstraints.HORIZONTAL;
        gaugeValuePanel.add(selectedGaugeLabel, c);

        selectedGaugeValueText = Helpers.createTextField("");
        c = Helpers.addConstraints(0, 1);
        c.fill = GridBagConstraints.HORIZONTAL;
        gaugeValuePanel.add(selectedGaugeValueText, c);

        selectedGaugeValueButton = Helpers.createButton("Save value");
        selectedGaugeValueButton.addActionListener((ActionEvent e) -> {
            // set the value of the currently selected dial
            ContextStorage context = ContextStorage.getInstance();
            double newValue = 0.0;

            GaugePanel gauge = (GaugePanel) context
                    .getGauge(selectedGaugeLabel.getText().trim());
            if (gauge != null) {
                try {
                    newValue = Double.parseDouble(selectedGaugeValueText.getText());
                } catch (NumberFormatException npe) {
                    notifyNumberConversionError(selectedGaugeLabel.getText().trim() + " value");
                    return;
                }
                if (gauge.getGauge().getMinValue() <= newValue
                        && gauge.getGauge().getMaxValue() >= newValue) {
                    UpdateGaugeThread updateThread
                            = new UpdateGaugeThread((AbstractGauge) gauge.getGauge(), newValue);
                    updateThread.start();
                } else {
                    JOptionPane.showMessageDialog(mainFrame, "Could not set the "
                            + "given value", "Error!", 0);
                }

            } else if (gauge == null) {
                JOptionPane.showMessageDialog(mainFrame, "You have not selected any gauge,"
                        + " please click on one of the dashboard elements.",
                        "Error!", 0);
            } else {
                JOptionPane.showMessageDialog(mainFrame, "Could not set the "
                        + "given value", "Error!", 0);
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
        container.add(topRightPanel, c);
        c.gridx = 0;
        c.gridy = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        container.add(gaugeValuePanel, c);

    }

    // bottom Panel on the right hand side for inputting values for gauges
    // when running the simulation
    public void createSimulationInputPanel() {

        // JLabels for simulation
        playAirPressureLabel = Helpers.createSmallLabel("Air Pressure Value");
        playFuelLabel = Helpers.createSmallLabel("Fuel Value");
        playSpeedLabel = Helpers.createSmallLabel("Speed Value");
        playTemperatureLabel = Helpers.createSmallLabel("Temperature Value");
        playWindDirectionLabel = Helpers.createSmallLabel("Wind Direction Value");
        playWindDirectionLabel.setPreferredSize(new Dimension(220, 30));
        distanceLabel = Helpers.createSmallLabel("Flight Distance (km)");

        // <editor-fold desc="create Text fields and add input listeners for each">
        playAirPressureText = Helpers.createTextField("");
        DocumentListener airPressureListener = new AirPressureValueListener();
        playAirPressureText.getDocument().addDocumentListener(airPressureListener);

        playSpeedText = Helpers.createTextField("");
        DocumentListener speedListener = new SpeedValueListener();
        playSpeedText.getDocument().addDocumentListener(speedListener);

        playTemperatureText = Helpers.createTextField("");
        DocumentListener temperatureListener = new TemperatureValueListener();
        playTemperatureText.getDocument().addDocumentListener(temperatureListener);

        playFuelText = Helpers.createTextField("");
        DocumentListener fuelListener = new FuelValueListener();
        playFuelText.getDocument().addDocumentListener(fuelListener);

        playWindDirectionText = Helpers.createTextField("");
        DocumentListener windDirectionListener = new WindDirectionValueListener();
        playWindDirectionText.getDocument().addDocumentListener(windDirectionListener);

        distanceText = Helpers.createTextField("");

        // </editor-fold>
        if (simulationInputsPanel != null) {
            rightContainer.remove(simulationInputsPanel);
        }

        runSimulationButton = Helpers.createButton("Run simulator");
        runSimulationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // prepare for running the simulation with preset values
                try {
                    Double.parseDouble(playAirPressureText.getText());
                    Double.parseDouble(playSpeedText.getText());
                    Double.parseDouble(playFuelText.getText());
                    Double.parseDouble(playTemperatureText.getText());
                    Double.parseDouble(playWindDirectionText.getText());
                    Double.parseDouble(distanceText.getText());
                } catch (IllegalArgumentException | NullPointerException ex) {
                    JOptionPane.showMessageDialog(mainFrame, "One or more values could "
                            + "not be converted", "Error", 0);
                    return;
                }
                windDir.getGauge().setValue(40);
                fuel.getGauge().setValue(90);
                speed.getGauge().setValue(0);
                trafficLight.getGauge().setRedOn(true);
                trafficLight.getGauge().setYellowBlinking(true);
                Timer timer = new Timer();
                TimerTask timerTask = new GreenColorTimerTask(trafficLight.getGauge());
                timer.schedule(timerTask, 4000);
                try {
                    doScriptRunning();
                } catch (InterruptedException ex) {
                    System.out.println("thread was interrupted");
                }

            }
        });

        GridBagConstraints c = new GridBagConstraints();

        simulationInputsPanel = new JPanel();
        simulationInputsPanel.setLayout(new BoxLayout(simulationInputsPanel, BoxLayout.Y_AXIS));
        simulationInputsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // <editor-fold desc="add all the created components to the simulationInputPanel">
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

        // </editor-fold>
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
        if (gaugeValuePanel.isVisible()) {
            gaugeValuePanel.setVisible(false);
        }
    }

    // do some calculations using speed, distance and fuel to see whether
    // the plane will succesfully take off and land or crash
    public boolean checkFlightStatus() throws NumberFormatException {
        double fuelValue;
        double distanceValue;
        double speedValue;

        fuelValue = Double.parseDouble(playFuelText.getText());
        distanceValue = Double.parseDouble(distanceText.getText());
        speedValue = Double.parseDouble(playSpeedText.getText());

        double consumePerKm;

        // based on speed calculate fuel consume per hour
        if (speedValue < 55) {
            consumePerKm = 1;
        } else {
            consumePerKm = 1.5;
        }

        double distancePossible = fuelValue / consumePerKm;

        // if distance is reachable return true, else false
        if (distancePossible >= distanceValue) {
            return true;
        } else {
            return false;
        }

    }

    // run method when local thread is started
    @Override
    public void run() {
        runPresetValuesXML();

    }

    // <editor-fold desc="little changes to the runXMLScript method provided in DashboardDemoProject example">
    /**
     * Run the XML script file which generates events for the dashboard
     * indicators
     */
    private void runPresetValuesXML() {
        try {
            DashboardEventGeneratorFromXML dbegXML = new DashboardEventGeneratorFromXML();

            // Register for speed events from the XML script file
            DashBoardEventListener dbelSpeed = (Object originator, DashBoardEvent dbe) -> {
                UpdateGaugeThread speedThread = new UpdateGaugeThread(speed.getGauge(),
                        Double.parseDouble(dbe.getValue()));
                speedThread.start();
            };
            dbegXML.registerDashBoardEventListener("speed", dbelSpeed);

            // Register for airPressure events from the XML script file
            DashBoardEventListener dbelAirPressure = (Object originator, DashBoardEvent dbe) -> {
                Thread speedThread = new ScriptGaugeThread(airPressure.getGauge(),
                        Double.parseDouble(dbe.getValue()));
                speedThread.start();
            };
            dbegXML.registerDashBoardEventListener("airPressure", dbelAirPressure);

            // Register for fuel events from the XML script file
            DashBoardEventListener dbelFuel = (Object originator, DashBoardEvent dbe) -> {
                Thread fuelThread = new Thread(new SlowValueThread(fuel.getGauge(),
                        Double.parseDouble(dbe.getValue())));
                fuelThread.start();
            };
            dbegXML.registerDashBoardEventListener("fuel", dbelFuel);

            // Register for wind direction events from the XML script file
            DashBoardEventListener dbelWindDirection = (Object originator, DashBoardEvent dbe) -> {
                ScriptGaugeThread speedThread = new ScriptGaugeThread(windDir.getGauge(),
                        Double.parseDouble(dbe.getValue()));
                speedThread.start();
            };
            dbegXML.registerDashBoardEventListener("windDirection", dbelWindDirection);

            // Register for temperature events from the XML script file
            DashBoardEventListener dbelTemperature = (Object originator, DashBoardEvent dbe) -> {
                Thread temperatureThread = new Thread(new ScriptGaugeThread(temperature.getGauge(),
                        Double.parseDouble(dbe.getValue())));
                temperatureThread.start();
            };
            dbegXML.registerDashBoardEventListener("temperature", dbelTemperature);

            // Process the script file - it willgenerate events as it runs
            dbegXML.processScriptFile(xmlScript);

        } catch (Exception ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    // </editor-fold>

    // <editor-fold desc="using DashboardDemProject as example for setting up dials values">
    public void setSpeed() {
        try {
            int value = Integer.parseInt(playSpeedText.getText().trim());
            speed.getGauge().setValue(value);
        } catch (NumberFormatException e) {
        }
    }

    public void setFuel() {
        try {
            int value = Integer.parseInt(playFuelText.getText().trim());
            fuel.getGauge().setValue(value);
        } catch (NumberFormatException e) {
        }
    }

    public void setAirPressure() {
        try {
            int value = Integer.parseInt(playAirPressureText.getText().trim());
            airPressure.getGauge().setValue(value);
        } catch (NumberFormatException e) {
        }
    }

    public void setTemperature() {
        try {
            int value = Integer.parseInt(playTemperatureText.getText().trim());
            temperature.getGauge().setValue(value);
        } catch (NumberFormatException e) {
        }
    }

    public void setWindDirection() {
        try {
            int value = Integer.parseInt(playWindDirectionText.getText().trim());
            windDir.getGauge().setValue(value);
        } catch (NumberFormatException e) {
        }
    }

    private class SpeedValueListener implements DocumentListener {

        @Override
        public void insertUpdate(DocumentEvent e) {
            setSpeed();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            setSpeed();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            setSpeed();
        }
    }

    private class AirPressureValueListener implements DocumentListener {

        @Override
        public void insertUpdate(DocumentEvent e) {
            setAirPressure();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            setAirPressure();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            setAirPressure();
        }
    }

    private class FuelValueListener implements DocumentListener {

        @Override
        public void insertUpdate(DocumentEvent e) {
            setFuel();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            setFuel();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            setFuel();
        }
    }

    private class WindDirectionValueListener implements DocumentListener {

        @Override
        public void insertUpdate(DocumentEvent e) {
            setWindDirection();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            setWindDirection();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            setWindDirection();
        }
    }

    private class TemperatureValueListener implements DocumentListener {

        @Override
        public void insertUpdate(DocumentEvent e) {
            setTemperature();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            setTemperature();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            setTemperature();
        }
    }

    // </editor-fold>
    //main method to run application
    public static void main(String[] args) {
        Dashboard dashboard = new Dashboard();
    }

}
