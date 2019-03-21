package dashboard;

import eu.hansolo.steelseries.tools.Section;
import java.awt.Color;

public final class SpecialisedGauge extends RegularGauge {

    private double dangerZoneMin = 0.0;
    private double dangerZoneMax = 0.0;

    public SpecialisedGauge(String name, String type) {
        super(name, type);
    }


    public double getDangerZoneMin() {
        return dangerZoneMin;
    }

    public double getDangerZoneMax() {
        return dangerZoneMax;
    }

    public void setDangerZoneMin(double min) throws InvalidLimitsException {

        if (Math.abs(dangerZoneMax - min) > 1 && dangerZoneMax > min && min >= getGauge().getMinValue() && min < getGauge().getMaxValue()) {
            dangerZoneMin = min;
            rebuildGaugeWithLimits(dangerZoneMin, dangerZoneMax);
            return;
        }

        throw new InvalidLimitsException(min, dangerZoneMax, getGauge().getMinValue(), getGauge().getMaxValue());

    }

    public void setDangerZoneMax(double max) throws InvalidLimitsException {

        if (Math.abs(max - dangerZoneMin) > 1 && max > dangerZoneMin 
                && max <= getGauge().getMaxValue() && max > getGauge().getMinValue()) {
            dangerZoneMax = max;
            rebuildGaugeWithLimits(dangerZoneMin, dangerZoneMax);
            return;
        }
        throw new InvalidLimitsException(dangerZoneMin, max, getGauge().getMinValue(), getGauge().getMaxValue());

    }

    public void setDangerZoneRange(double min, double max) throws InvalidLimitsException {

        if (Math.abs(max - min) > 1 && max > min 
                && min >= getGauge().getMinValue() && max <= getGauge().getMaxValue()
                && min < getGauge().getMaxValue() && max > getGauge().getMinValue()) {
            getGauge().addSection(new Section(min, max, Color.GREEN));
            getGauge().setSectionsVisible(true);
            return;
        }

        throw new InvalidLimitsException(min, max, getGauge().getMinValue(), getGauge().getMaxValue());

    }

}
