package org.openspaces.screencasts.scaling.model;

import com.gigaspaces.annotation.pojo.SpaceClass;
import com.gigaspaces.annotation.pojo.SpaceProperty;
import com.gigaspaces.annotation.pojo.SpaceRouting;

@SpaceClass
public class CalculationEvent {
    Long timeSlice;
    Integer digit;

    @SpaceProperty
    @SpaceRouting
    public Long getTimeSlice() {
        return timeSlice;
    }

    public void setTimeSlice(Long timeSlice) {
        this.timeSlice = timeSlice;
    }

    public Integer getDigit() {
        return digit;
    }

    public void setDigit(Integer digit) {
        this.digit = digit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CalculationEvent)) return false;

        CalculationEvent that = (CalculationEvent) o;

        if (digit != null ? !digit.equals(that.digit) : that.digit != null) return false;
        if (timeSlice != null ? !timeSlice.equals(that.timeSlice) : that.timeSlice != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = timeSlice != null ? timeSlice.hashCode() : 0;
        result = 31 * result + (digit != null ? digit.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("CalculationEvent");
        sb.append("{digit=").append(digit);
        sb.append(", timeSlice=").append(timeSlice);
        sb.append('}');
        return sb.toString();
    }
}
