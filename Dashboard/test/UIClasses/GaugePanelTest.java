package UIClasses;

import UIClassesForGauges.GaugePanel;
import eu.hansolo.steelseries.extras.WindDirection;
import eu.hansolo.steelseries.gauges.AbstractGauge;
import eu.hansolo.steelseries.gauges.Radial;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class GaugePanelTest {
    
    public GaugePanelTest() {//
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    // test if the panel creates the expected dial with the desired specification
    @Test
    public void testBuildPanel() {
        String name = "Wind";
        String type = "Direction Radial";
        GaugePanel instance = new GaugePanel();
        instance.buildPanel(name, type);
        
        assertEquals(instance.getGauge().getClass(), WindDirection.class);
    }
    
    // test what happens when name is an Empty String
    @Test
    public void testBuildPane1l() {
        String name = "";
        String type = "Direction Radial";
        GaugePanel instance = new GaugePanel();
        instance.buildPanel(name, type);
        
        assertEquals(instance.getGauge().getClass(), WindDirection.class);
    }
    
    // test what happens when name is an Empty String and type is not a valid argument
    @Test
    public void testBuildPane12() {
        String name = "";
        String type = "Direction";
        GaugePanel instance = new GaugePanel();
        instance.buildPanel(name, type);
        
        assertEquals(instance.getGauge().getClass(), Radial.class);
    }
    
    // test what happens if type is an Empty String and name has a value
    @Test
    public void testBuildPane13() {
        String name = "Wind";
        String type = "";
        GaugePanel instance = new GaugePanel();
        instance.buildPanel(name, type);
        assertEquals(instance.getGauge().getClass(), Radial.class);
    }
    
    // test the outcome when both, name and type, are null
    @Test
    public void testBuildPane14() {
        String name = null;
        String type = null;
        GaugePanel instance = new GaugePanel();
        instance.buildPanel(name, type);
        
        assertEquals(instance.getGauge().getClass(), Radial.class);
    }

    // test method InitialiseEmptyPanel()
    @Test
    public void testInitialiseEmptyPanel() {
        GaugePanel instance = new GaugePanel();
        instance.initialiseEmptyPanel();
        
        assertEquals(instance.getGauge().getClass(), Radial.class);
    }

    // test setter SetGaugeUnit() with empty string
    @Test
    public void testSetGaugeUnit() {
        String unit = "";
        GaugePanel instance = new GaugePanel();
        instance.setGaugeUnit(unit);
        assertEquals(unit, "");
    }
    
    @Test
    public void testSetGaugeUnit1() {
        String unit = "123";
        GaugePanel instance = new GaugePanel();
        instance.setGaugeUnit(unit);
        assertEquals(unit, "123");
    }
    
    // test setter SetGaugeUnit() with null string
    @Test
    public void testSetGaugeUnit2() {
        String unit = null;
        GaugePanel instance = new GaugePanel();
        instance.setGaugeUnit(unit);
        assertEquals(unit, null);
    }

    // test getTitle() after creating a simple GaugeSetup
    @Test
    public void testGetTitle() {
        GaugePanel instance = new GaugePanel();
        String expResult = "Default";
        String result = instance.getTitle();
        assertEquals(expResult, result);
    }

    // test setTitle() by changing if the right title is received using the getter
    @Test
    public void testSetTitle() {
        String title = "title";
        GaugePanel instance = new GaugePanel();
        instance.setTitle(title);
        
        assertEquals(instance.getTitle(), "title");
    }

    // test getGauge() when a simple GaugeSetup() is created
    @Test
    public void testGetGauge() {
        GaugePanel instance = new GaugePanel();
        AbstractGauge expResult = new Radial();
        AbstractGauge result = instance.getGauge();
        assertEquals(expResult.getClass(), result.getClass());
    }
    
}
