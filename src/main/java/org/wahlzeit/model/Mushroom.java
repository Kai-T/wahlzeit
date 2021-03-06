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

import org.wahlzeit.model.Location;
import org.wahlzeit.services.DataObject;
import org.wahlzeit.utils.PatternInstance;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;



@PatternInstance(
		patternName = "TypeObject",
		participants = {
				"Object"
		}
	)
public class Mushroom {
		
	private final Location location;
	private final MushroomType type;
	private MushroomManager manager = MushroomManager.getInstance();
	private final long id;

	
	public Mushroom(MushroomType type, Location location, long id) {
		this.type = type;
		this.location = location;
		this.id = id;
	}


	/**
	 * @methodtype get
	 */
	public Location getLocation() {
		return this.location;
	}
	
	/**
	 * @methodtype get
	 */
	public MushroomType getType() {
		return this.type;
	}
	
	/**
	 * @methodtype get
	 */
	public MushroomManager getManager() {
		return this.manager;
	}
	
	/**
	 * @methodtype get
	 */
	public long getId() {
		return this.id;
	}
	

	/**
	 * @Methodtype getter
	 */
	public String getEdible() {
		if (type == null) {
			return "unknown";
		}
		return type.getEdible();
	}

}
