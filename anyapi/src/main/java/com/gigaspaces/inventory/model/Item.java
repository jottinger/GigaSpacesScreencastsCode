package com.gigaspaces.inventory.model;

import com.gigaspaces.annotation.pojo.SpaceClass;
import com.gigaspaces.annotation.pojo.SpaceId;
import com.gigaspaces.annotation.pojo.SpaceProperty;
import com.gigaspaces.annotation.pojo.SpaceRouting;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@SpaceClass
public class Item {
    String id;
    String description;
    Long count;

    public Item() {
    }

    public Item(Long count, String id, String description) {
        this.count = count;
        this.id = id;
        this.description=description;
    }

    @Column
    @SpaceProperty
    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @SpaceId(autoGenerate = true)
    @Id
    @SpaceRouting
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Item");
        sb.append("{count=").append(count);
        sb.append(", id='").append(id).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;

        if (count != null ? !count.equals(item.count) : item.count != null) return false;
        if (description != null ? !description.equals(item.description) : item.description != null) return false;
        if (id != null ? !id.equals(item.id) : item.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (count != null ? count.hashCode() : 0);
        return result;
    }
}
