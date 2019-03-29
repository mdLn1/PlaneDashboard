package parameterscalculator;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

public class calculatorBeanInfo extends SimpleBeanInfo{
    public PropertyDescriptor[] getPropertyDescriptors() {
        try {
            PropertyDescriptor unit1 = new PropertyDescriptor("unit1", calculator.class,
                    "getUnit1", "setUnit1");
            PropertyDescriptor unit2 = new PropertyDescriptor("unit2", calculator.class,
                    "getUnit2", "setUnit2");
            PropertyDescriptor converterName = new PropertyDescriptor("converterName", calculator.class,
                    "getConverterName", "setConverterName");
            PropertyDescriptor scale = new PropertyDescriptor("scale", calculator.class,
                    "getScale", "setScale");
            PropertyDescriptor[] pds = new PropertyDescriptor[]{
                unit1, unit2, converterName, scale};
            return pds;

        } catch (IntrospectionException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
