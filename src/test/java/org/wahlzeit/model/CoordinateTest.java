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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CoordinateTest {
	private static final double double_threshold = 1e-10;

	
	private Coordinate[] coordinates_cartesian = new Coordinate [5];
	private Coordinate[] coordinates_spheric = new Coordinate [5];

	@Before
	public void setUp() {
		coordinates_cartesian[0] = CartesianCoordinate.getCartesianCoordinate(0, 0, 0);
		coordinates_cartesian[1] = CartesianCoordinate.getCartesianCoordinate(1, 0, 0);
		coordinates_cartesian[2] = CartesianCoordinate.getCartesianCoordinate(0, -2, 0);
		coordinates_cartesian[3] = CartesianCoordinate.getCartesianCoordinate(0, 3, 4);
		coordinates_cartesian[4] = CartesianCoordinate.getCartesianCoordinate(-1/Math.sqrt(2), 0, -1/Math.sqrt(2));
		
		coordinates_spheric[0] = SphericCoordinate.getSphericCoordinate(0, 0, 0);
		coordinates_spheric[1] = SphericCoordinate.getSphericCoordinate(0, Math.PI/2, 1);
		coordinates_spheric[2] = SphericCoordinate.getSphericCoordinate(-Math.PI/2, Math.PI/2, 2);
		coordinates_spheric[3] = SphericCoordinate.getSphericCoordinate(Math.PI/2, Math.asin(3. / 5.), 5);	
		coordinates_spheric[4] = SphericCoordinate.getSphericCoordinate(Math.PI,  3 * Math.PI / 4, 1);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testIsEqual() {
		for (int i=0; i < coordinates_cartesian.length; i++) {
			assertTrue(coordinates_cartesian[i].isEqual(coordinates_cartesian[i]));
			assertTrue(coordinates_spheric[i].isEqual(coordinates_spheric[i]));
			assertTrue(coordinates_cartesian[i].isEqual(coordinates_spheric[i]));
			assertTrue(coordinates_spheric[i].isEqual(coordinates_cartesian[i]));
		}
	}
	
	@Test
	public void testAsCartesian() {
		for (int i=0; i < coordinates_cartesian.length; i++) {
			assertTrue(coordinates_cartesian[i].isEqual(coordinates_spheric[i].asCartesianCoordinate()));
			assertTrue(coordinates_spheric[i].isEqual(coordinates_cartesian[i].asCartesianCoordinate()));
		}
	}
	
	@Test
	public void testAsSpheric() {
		for (int i=0; i < coordinates_cartesian.length; i++) {
			assertTrue(coordinates_cartesian[i].isEqual(coordinates_spheric[i].asSphericCoordinate()));
			assertTrue(coordinates_spheric[i].isEqual(coordinates_cartesian[i].asSphericCoordinate()));

		}
	}
	
	@Test
	public void testGetCentralAngleWorking() {
		for (int i=1; i < coordinates_cartesian.length; i++) {
			assertEquals(0, coordinates_cartesian[i].getCentralAngle(coordinates_spheric[i]), double_threshold);
			assertEquals(0, coordinates_cartesian[i].getCentralAngle(coordinates_spheric[i]), double_threshold);
		}
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void testGetCentralAngleError() {
		assertEquals(0, coordinates_cartesian[0].getCentralAngle(coordinates_spheric[0]), double_threshold);
		assertEquals(0, coordinates_cartesian[0].getCentralAngle(coordinates_spheric[0]), double_threshold);
	}
}
