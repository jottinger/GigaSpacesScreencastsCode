package com.gigaspaces.inventory.model;

import com.gigaspaces.annotation.pojo.SpaceClass;
import com.gigaspaces.annotation.pojo.SpaceId;
import com.gigaspaces.annotation.pojo.SpaceProperty;
import com.gigaspaces.annotation.pojo.SpaceRouting;

import javax.persistence.Entity;
import javax.persistence.Id;

@SpaceClass
@Entity
public class ScanEvent {
    String id;
    String itemId;

    public ScanEvent() {
    }

    public ScanEvent(String itemId) {
        this.itemId = itemId;
    }

    @Id
    @SpaceId(autoGenerate = true)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @SpaceProperty
    @SpaceRouting
    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ScanEvent scanEvent = (ScanEvent) o;

        if (id != null ? !id.equals(scanEvent.id) : scanEvent.id != null) return false;
        if (itemId != null ? !itemId.equals(scanEvent.itemId) : scanEvent.itemId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (itemId != null ? itemId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("ScanEvent");
        sb.append("{id='").append(id).append('\'');
        sb.append(", itemId='").append(itemId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
