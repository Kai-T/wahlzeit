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

import static org.junit.Assert.*;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.wahlzeit.testEnvironmentProvider.LocalDatastoreServiceTestConfigProvider;


public class MushroomTest {
	
	@ClassRule
	public static RuleChain ruleChain = RuleChain.
			outerRule(new LocalDatastoreServiceTestConfigProvider());
	
	@Test
	public void testCreation() {
		Mushroom m = new Mushroom(null, null, 1);
		assertNotNull(m);
		assertEquals(1, m.getId());
	}
	
	@Test
	public void testLoacation() {
		Location l = new Location(null);
		Mushroom mushroom = new Mushroom(null, l, 1);
		assertEquals(l, mushroom.getLocation());
	}
	
	
	@Test
	public void testManager() {
		Mushroom mushroom = new Mushroom(null, null, 1);
		assertEquals(MushroomManager.getInstance(), mushroom.getManager());
	}
	
	@Test
	public void testGetType() {
		String type_name = "porcini";
		Mushroom mushroom = MushroomManager.getInstance().createMushroom(type_name, null);
		assertEquals(mushroom.getType(), MushroomManager.getInstance().getMushroomType(type_name));
		assertEquals(type_name, mushroom.getType().getName());
	}

	@Test
	public void testEdible() {
		
		Mushroom mushroom = MushroomManager.getInstance().createMushroom("porcini", null);
		assertEquals(mushroom.getEdible(), "unknown");
		MushroomManager.getInstance().getMushroomType("porcini").setEdible("yes");
		assertEquals(mushroom.getEdible(), "yes");
	}
	
	
}
