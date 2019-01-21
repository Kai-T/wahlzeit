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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.wahlzeit.services.ObjectManager;
import org.wahlzeit.utils.PatternInstance;


@PatternInstance(
		patternName = "Singleton",
		participants = {
				"Singleton"
		}
	)
public class MushroomManager {
	
	private static final MushroomManager instance = new MushroomManager();
	
	private final Map<String, MushroomType> types = new HashMap<String, MushroomType>();
	private final Map<Long, Mushroom> mushrooms = new HashMap<Long, Mushroom>();
	
	private long lastid = 0;


	private MushroomManager() {}
	
	/**
	 * @Methodtype getter
	 */
	public static MushroomManager getInstance() {
		return instance;
	}
	
	
	public Mushroom createMushroom(String typeName, Location location) {
		MushroomType.assertIsValidMushroomTypeName(typeName);
		MushroomType mt = getMushroomType(typeName);
		if (mt == null) {
			mt = new MushroomType(typeName, "unknown");
			types.put(typeName, mt);
		}
		Mushroom mushroom = mt.createInstance(location, getNetId());
		mushrooms.put(mushroom.getId(), mushroom);
		return mushroom;
	}


	public MushroomType getMushroomType(String typeName) {
		MushroomType.assertIsValidMushroomTypeName(typeName);
		MushroomType type = types.get(typeName);
		return type;		
	}
	
	

	private synchronized long getNetId() {
		lastid++;
		return lastid;
	}
	
	
}
