/*
 * Copyright (c) 2006-2009 by Dirk Riehle, http://dirkriehle.com
 *
 * This file is part of the Wahlzeit photo rating application.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package org.wahlzeit.model;

import org.wahlzeit.services.DataObject;
import org.wahlzeit.utils.PatternInstance;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@PatternInstance(
		patternName = "TypeObject",
		participants = {
				"ObjectType"
		}
	)
public class MushroomType {

	
	private String name = "";
	private String edible = "no";
	private MushroomType superType = null;


	private MushroomManager manager = MushroomManager.getInstance();
	
	public MushroomType(String name, String edible) {
		MushroomType.assertIsValidMushroomTypeName(name);
		this.name = name;
		this.edible = edible;
	}
	
	
	public Mushroom createInstance(Location location, long id) {
		return new Mushroom(this, location, id);
	}
	
	/**
	 * @Methodtype getter
	 */
	public MushroomType getSuperType() {
		return superType;
	}


	/**
	 * @Methodtype setter
	 */
	public void setSuperType(MushroomType superType) {
		this.superType = superType;
	}
	
	/**
	 * @Methodtype boolean query
	 */
	public boolean isSubtype(MushroomType t) {
		if (t == null) {
			return false;
		}
		if (t == this) {
			return true;
		}
		if (superType == null) {
			return false;
		}
		return superType.isSubtype(t);
	}
	
	
	/**
	 * @Methodtype getter
	 */
	public String getName() {
		return name;
	}
	
	
	/**
	 * @Methodtype getter
	 */
	public String getEdible() {
		return edible;
	}
	
	/**
	 * @Methodtype setter
	 */
	public void setEdible(String edible) {
		this.edible = edible;
	}


	
	/**
	 * @Methodtype getter
	 */
	public MushroomManager getManager() {
		return manager;
	}

	
	/**
	 * @Methodtype assertion
	 */
	public static void assertIsValidMushroomTypeName(String typeName) {
		if (typeName == null || typeName == "") {
			throw new IllegalArgumentException(typeName + " is no valid MushroomType name.");
		}
		if (typeName.length() > 200) {
			throw new IllegalArgumentException("The MushroomType name is too long!");
		}
	}
	
}
