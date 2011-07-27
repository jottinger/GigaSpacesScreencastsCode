package com.gigaspaces.anyapi;

import com.gigaspaces.annotation.pojo.SpaceClass;
import com.gigaspaces.annotation.pojo.SpaceDynamicProperties;
import com.gigaspaces.annotation.pojo.SpaceId;
import com.gigaspaces.annotation.pojo.SpaceProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Map;

@Entity
@SpaceClass
public class Programmer {

	private Integer id;
	private String name;
	private String language;
	private Map<String, Object> dynamicProperties;

	public Programmer(){}

	public Programmer(Integer id){
		this.id = id;
	}

	public Programmer(Integer id, String name, String language){
		this.id = id;
		this.name = name;
		this.language = language;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Id
	@SpaceId
	public Integer getId() {
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}

    @Column
    @SpaceProperty
	public String getName() {
		return name;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

    @Column
    @SpaceProperty
	public String getLanguage() {
		return language;
	}

	public void setDynamicProperties(Map<String, Object> dynamicProperties) {
		this.dynamicProperties = dynamicProperties;
	}

	@SuppressWarnings({"JpaAttributeTypeInspection"})
    @SpaceDynamicProperties
	public Map<String, Object> getDynamicProperties() {
		return dynamicProperties;
	}

	@Override
	public String toString() {
		String string =
			" programmer: id=" + getId() +
			" name=" + getName() +
			" language=" + getLanguage();

		if(dynamicProperties != null){
			string += " " + dynamicProperties;
		}
		return string;
	}

}

