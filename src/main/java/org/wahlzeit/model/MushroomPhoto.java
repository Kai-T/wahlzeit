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

import java.util.logging.Logger;

import org.wahlzeit.services.LogBuilder;
import org.wahlzeit.utils.PatternInstance;

import com.googlecode.objectify.annotation.Ignore;
import com.googlecode.objectify.annotation.Subclass;

@Subclass
@PatternInstance(
		patternName = "Abstract Factory",
		participants = {
				"ConcreteProduct"
		}
	)
public class MushroomPhoto extends Photo {
	
	@Ignore
	private Mushroom mushroom = null;
	
	public MushroomPhoto() {
		super();
	}
	
	public MushroomPhoto(PhotoId id) {
		super(id);
	}
	
	/**
	 * @Methodtype getter
	 */
	public Mushroom getMushroom() {
		return mushroom;
	}

	/**
	 * @Methodtype setter
	 */
	public void setMushroom(Mushroom mushroom) {
		this.mushroom = mushroom;
	}

	/**
	 * @Methodtype getter
	 */
	public String getEdible() {
		if (mushroom == null) {
			return "unknown";
		}
		return mushroom.getEdible();
	}
	
}
