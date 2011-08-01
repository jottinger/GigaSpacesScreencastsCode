package org.openspaces.screencasts.scaling.model;

import com.gigaspaces.annotation.pojo.*;
import com.gigaspaces.metadata.index.SpaceIndexType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@SpaceClass
@Entity
public class CalculationEventCounter {
    String id;
    Long timeSlice;
    Long counter;
    Integer digit;

    @SpaceProperty
    @SpaceRouting
    @Column
    public Integer getDigit() {
        return digit;
    }

    public void setDigit(Integer digit) {
        this.digit = digit;
    }

    @SpaceProperty
    @Column
    public Long getCounter() {
        return counter;
    }

    public void setCounter(Long counter) {
        this.counter = counter;
    }

    @SpaceId(autoGenerate = true)
    @Id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column
    @SpaceProperty
    @SpaceIndex(type = SpaceIndexType.EXTENDED)
    public Long getTimeSlice() {
        return timeSlice;
    }

    public void setTimeSlice(Long timeSlice) {
        this.timeSlice = timeSlice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CalculationEventCounter)) return false;

        CalculationEventCounter that = (CalculationEventCounter) o;

        if (counter != null ? !counter.equals(that.counter) : that.counter != null) return false;
        if (digit != null ? !digit.equals(that.digit) : that.digit != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (timeSlice != null ? !timeSlice.equals(that.timeSlice) : that.timeSlice != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (timeSlice != null ? timeSlice.hashCode() : 0);
        result = 31 * result + (counter != null ? counter.hashCode() : 0);
        result = 31 * result + (digit != null ? digit.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("CalculationEventCounter");
        sb.append("{counter=").append(counter);
        sb.append(", id='").append(id).append('\'');
        sb.append(", timeSlice=").append(timeSlice);
        sb.append(", digit=").append(digit);
        sb.append('}');
        return sb.toString();
    }
}
