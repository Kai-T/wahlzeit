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

public class MushroomManagerTest {
	
	@ClassRule
	public static RuleChain ruleChain = RuleChain.
			outerRule(new LocalDatastoreServiceTestConfigProvider());
	
	@Test
	public void testSingelton() {
		assertEquals(MushroomManager.getInstance(), MushroomManager.getInstance());
	}
	
	@Test
	public void testCreateMushroom() {
		MushroomManager mm = MushroomManager.getInstance();
		Mushroom m1 = mm.createMushroom("a", null);
		Location l = new Location(null);
		Mushroom m2 = mm.createMushroom("b", l);
		Mushroom m3 = mm.createMushroom("a", null);


		assertEquals("a", m1.getType().getName());
		assertEquals(null, m1.getLocation());
	
		assertEquals(l, m2.getLocation());
		
		assertEquals(m1.getType(), m3.getType());
	
		assertNotEquals(m1.getId(), m2.getId());
		assertNotEquals(m1.getType(), m2.getType());
		assertFalse(m1.getType().isSubtype(m2.getType()));
		assertFalse(m2.getType().isSubtype(m1.getType()));

	}
	
	@Test (expected=IllegalArgumentException.class)
	public void testCreateMushroomInvalidName1() {
		MushroomManager.getInstance().createMushroom(null, null);
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void testCreateMushroomInvalidName2() {
		MushroomManager.getInstance().createMushroom("", null);
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void testCreateMushroomInvalidName3() {
		MushroomManager.getInstance().createMushroom(
				"Waaaaaaaaayy tooooo looooooooooong naaaaaaaaaaaaaaaaaaaaaaaaaame "
					+ "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"
					+ "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!", 
				null);
	}
	
	@Test
	public void testGetMushroomType() {
		MushroomManager mm = MushroomManager.getInstance();
		Mushroom m1 = mm.createMushroom("a", null);
		Mushroom m2 = mm.createMushroom("b", null);

		assertEquals(m1.getType(), mm.getMushroomType("a"));
		assertEquals(m2.getType(), mm.getMushroomType("b"));
	}
	
	
	
	
	
	
	
	
	
	@Test
	public void testGetEdible() {
		MushroomPhoto foto = new MushroomPhoto();
		assertEquals(foto.getEdible(), "unknown");
		
		Mushroom mushroom= MushroomManager.getInstance().createMushroom("porcini", null);
		MushroomManager.getInstance().getMushroomType("porcini").setEdible("yes");
		foto.setMushroom(mushroom);
		assertEquals(foto.getEdible(), "yes");
	}
	
	@Test
	public void setAndGetMushroom() {
		MushroomPhoto foto = new MushroomPhoto();
		Mushroom mushroom= MushroomManager.getInstance().createMushroom("porcini", null);
		foto.setMushroom(mushroom);
		assertEquals(mushroom, foto.getMushroom());
	}
	
}
