package UIClasses;

import UIClassesForGauges.RegularGauge;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class RegularGaugeTest {
    
    private RegularGauge instance;
    
    public RegularGaugeTest() {
        instance = new RegularGauge();
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

    // test get limit on a newly instantiated RegularGauge()
    @org.junit.Test
    public void testGetLimitMin() {
        int expResult = 0;
        int result = instance.getLimitMin();
        assertEquals(expResult, result);
    }
    
    // test setLimitMin() with an empty string
    @Test(expected = NumberFormatException.class)
    public void testSetLimitMin() {
        String limitMin = "";
        instance.setLimitMin(limitMin);
    }
    
    // test setLimitMin() with null as parameter
    @Test(expected = NullPointerException.class)
    public void testSetLimitMin1() {
        String limitMin = null;
        instance.setLimitMin(limitMin);
    }
    
    // test setLimitMin() with value bigger than limitMax
    @org.junit.Test
    public void testSetLimitMin2() {
        String limitMin = "500";
        instance.setLimitMin(limitMin);
        
        assertEquals(instance.getLimitMin(), 500);
    }

    // test getLimitMax() if not set previously
    @org.junit.Test
    public void testGetLimitMax() {
        int expResult = 0;
        int result = instance.getLimitMax();
        assertEquals(expResult, result);
    }

    
    // test setLimitMax() if Empty String is passed
     @Test(expected = NumberFormatException.class)
    public void testSetLimitMax() {
        String limitMax = "";
        instance.setLimitMax(limitMax);
    }
    
    // test setLimitMax() if null pased
    @Test(expected = NullPointerException.class)
    public void testSetLimitMax1() {
        String limitMax = null;
        instance.setLimitMax(limitMax);
    }
    
    // test setLimitMax() if null pased
    @org.junit.Test
    public void testSetLimitMax2() {
        String limitMax = "-20";
        instance.setLimitMax(limitMax);
        
        assertEquals(instance.getLimitMax(), -20);
    }

    
}
