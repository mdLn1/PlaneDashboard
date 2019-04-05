package anythingconverter;

import java.awt.Image;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

public class ConverterBeanInfo extends SimpleBeanInfo{
    public PropertyDescriptor[] getPropertyDescriptors() {
        try {
            PropertyDescriptor unit1 = new PropertyDescriptor("unit1", Converter.class,
                    "getUnit1", "setUnit1");
            PropertyDescriptor unit2 = new PropertyDescriptor("unit2", Converter.class,
                    "getUnit2", "setUnit2");
            PropertyDescriptor converterName = new PropertyDescriptor("converterName", Converter.class,
                    "getConverterName", "setConverterName");
            PropertyDescriptor scale = new PropertyDescriptor("scale", Converter.class,
                    "getScale", "setScale");
            PropertyDescriptor[] pds = new PropertyDescriptor[]{
                unit1, unit2, converterName, scale};
            return pds;

        } catch (IntrospectionException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
     public Image getIcon(int iconKind) {
        switch (iconKind) {
            case BeanInfo.ICON_COLOR_16x16:
                return loadImage("conv16.png");
            case BeanInfo.ICON_COLOR_32x32:
                return loadImage("conv16.png");
            case BeanInfo.ICON_MONO_16x16:
                return loadImage("conv16.png");
            case BeanInfo.ICON_MONO_32x32:
                return loadImage("conv16.png");
        }
        return null;
    }
}
