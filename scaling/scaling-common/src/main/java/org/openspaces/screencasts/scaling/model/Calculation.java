package org.openspaces.screencasts.scaling.model;

import com.gigaspaces.annotation.pojo.SpaceClass;
import com.gigaspaces.annotation.pojo.SpaceDynamicProperties;
import com.gigaspaces.annotation.pojo.SpaceId;
import com.gigaspaces.annotation.pojo.SpaceProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.math.BigInteger;
import java.util.Set;

@SpaceClass
@Entity
public class Calculation {
    String id;
    BigInteger source;
    Set<BigInteger> factors;
    Boolean processed;
    String eventClass;

    @Column
    @SpaceProperty
    public String getEventClass() {
        return eventClass;
    }

    public void setEventClass(String eventClass) {
        this.eventClass = eventClass;
    }

    public Calculation() {
    }

    public Calculation(BigInteger source) {
        this.source = source;
    }

    @SpaceDynamicProperties
    @OneToMany
    public Set<BigInteger> getFactors() {
        return factors;
    }

    public void setFactors(Set<BigInteger> factors) {
        this.factors = factors;
    }

    @SpaceId(autoGenerate = true)
    @Id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @SpaceProperty
    @Column
    public BigInteger getSource() {
        return source;
    }

    public void setSource(BigInteger source) {
        this.source = source;
    }

    @Column
    @SpaceProperty
    public Boolean getProcessed() {
        return processed;
    }

    public void setProcessed(Boolean processed) {
        this.processed = processed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Calculation)) return false;

        Calculation that = (Calculation) o;

        if (factors != null ? !factors.equals(that.factors) : that.factors != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (processed != null ? !processed.equals(that.processed) : that.processed != null) return false;
        if (source != null ? !source.equals(that.source) : that.source != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (source != null ? source.hashCode() : 0);
        result = 31 * result + (factors != null ? factors.hashCode() : 0);
        result = 31 * result + (processed != null ? processed.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Calculation{");
        sb.append("id='").append(id).append('\'');
        sb.append(", source=").append(source);
        sb.append(", processed=").append(processed);
        sb.append(",factors=").append(factors);
        sb.append('}');
        return sb.toString();
    }
}
