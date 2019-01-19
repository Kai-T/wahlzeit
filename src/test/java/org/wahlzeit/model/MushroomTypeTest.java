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

public class MushroomTypeTest {
	
	@ClassRule
	public static RuleChain ruleChain = RuleChain.
			outerRule(new LocalDatastoreServiceTestConfigProvider());
	
	@Test
	public void testCreation() {
		MushroomType m = new MushroomType("a", "no");
		assertNotNull(m);
		assertEquals("a", m.getName());
		assertEquals("no", m.getEdible());
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void testInvalidCreation1() {
		MushroomType m = new MushroomType("", "no");
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void testInvalidCreation2() {
		MushroomType m = new MushroomType(null, "no");
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void testInvalidCreation3() {
		MushroomType m = new MushroomType(
				"Waaaaaaaaayy tooooo looooooooooong naaaaaaaaaaaaaaaaaaaaaaaaaame "
				+ "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"
				+ "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!", 
				"no");
	}
	
	@Test
	public void testGetManager() {
		MushroomType m = new MushroomType("a", "no");
		assertEquals(MushroomManager.getInstance(), m.getManager());
	}
	
	@Test
	public void testSetEdible() {
		MushroomType m = new MushroomType("a", "no");
		assertEquals("no", m.getEdible());
		m.setEdible("yes");
		assertEquals("yes", m.getEdible());
	}
	
	@Test
	public void testSetParentType() {
		MushroomType m1 = new MushroomType("a", "no");
		MushroomType m2 = new MushroomType("b", "no");
		m1.setParentType(m2);
		
		assertEquals(m1.getParentType(), m2);
	}
	
	
	@Test
	public void testIsSubtype() {
		MushroomType m1 = new MushroomType("a", "no");
		MushroomType m2 = new MushroomType("b", "no");
		MushroomType m3 = new MushroomType("c", "no");

		assertTrue(m1.isSubtype(m1));
		assertTrue(m2.isSubtype(m2));
		assertTrue(m3.isSubtype(m3));
		
		assertFalse(m1.isSubtype(null));
		assertFalse(m2.isSubtype(null));
		assertFalse(m3.isSubtype(null));

		
		m1.setParentType(m2);
		m2.setParentType(m3);
		
		assertTrue(m1.isSubtype(m2));
		assertTrue(m1.isSubtype(m3));
		assertTrue(m2.isSubtype(m3));	
	}
	
	@Test
	public void testCreateInstance() {
		MushroomType mt = new MushroomType("a", "no");
		Location location = new Location(null);
		
		Mushroom m = mt.createInstance(location, 1);
		
		assertEquals(mt, m.getType());
		assertEquals(location, m.getLocation());
		assertEquals("no", m.getEdible());
	}

}
