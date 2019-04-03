/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UIClasses;

import eu.hansolo.steelseries.extras.WindDirection;
import eu.hansolo.steelseries.gauges.AbstractGauge;
import eu.hansolo.steelseries.gauges.Radial;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mp7857c
 */
public class GaugeSetupTest {
    
    public GaugeSetupTest() {
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

    /**
     * Test of buildPanel method, of class GaugeSetup.
     */
    @Test
    public void testBuildPanel() {
        String name = "Wind";
        String type = "Direction Radial";
        GaugeSetup instance = new GaugeSetup();
        instance.buildPanel(name, type);
        
        assertEquals(instance.getGauge().getClass(), WindDirection.class);
    }
    
    @Test
    public void testBuildPane1l() {
        String name = "";
        String type = "Direction Radial";
        GaugeSetup instance = new GaugeSetup();
        instance.buildPanel(name, type);
        
        assertEquals(instance.getGauge().getClass(), WindDirection.class);
    }
    
     @Test
    public void testBuildPane12() {
        String name = "";
        String type = "Direction";
        GaugeSetup instance = new GaugeSetup();
        instance.buildPanel(name, type);
        
        assertEquals(instance.getGauge().getClass(), Radial
                .class);
    }

    /**
     * Test of initialiseEmptyPanel method, of class GaugeSetup.
     */
    @Test
    public void testInitialiseEmptyPanel() {
        GaugeSetup instance = new GaugeSetup();
        instance.initialiseEmptyPanel();
        
        assertEquals(instance.getGauge().getClass(), Radial.class);
    }

    /**
     * Test of setGaugeUnit method, of class GaugeSetup.
     */
    @Test
    public void testSetGaugeUnit() {
        String unit = "";
        GaugeSetup instance = new GaugeSetup();
        instance.setGaugeUnit(unit);
        assertEquals(unit, "");
    }
    
    @Test
    public void testSetGaugeUnit1() {
        String unit = "123";
        GaugeSetup instance = new GaugeSetup();
        instance.setGaugeUnit(unit);
        assertEquals(unit, "123");
    }

    /**
     * Test of getTitle method, of class GaugeSetup.
     */
    @Test
    public void testGetTitle() {
        GaugeSetup instance = new GaugeSetup();
        String expResult = "Default";
        String result = instance.getTitle();
        assertEquals(expResult, result);
    }

    /**
     * Test of setTitle method, of class GaugeSetup.
     */
    @Test
    public void testSetTitle() {
        String title = "title";
        GaugeSetup instance = new GaugeSetup();
        instance.setTitle(title);
        
        assertEquals(instance.getTitle(), "title");
    }

    /**
     * Test of getGauge method, of class GaugeSetup.
     */
    @Test
    public void testGetGauge() {
        GaugeSetup instance = new GaugeSetup();
        AbstractGauge expResult = new Radial();
        AbstractGauge result = instance.getGauge();
        assertEquals(expResult.getClass(), result.getClass());
    }
    
}
