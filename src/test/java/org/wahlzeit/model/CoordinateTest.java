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

	
	private Coordinate[] coordinates_cartesian = new Coordinate [4];
	private Coordinate[] coordinates_spheric = new Coordinate [4];

	@Before
	public void setUp() {
		coordinates_cartesian[0] = new CartesianCoordinate(0, 0, 0);
		coordinates_cartesian[1] = new CartesianCoordinate(1, 0, 0);
		coordinates_cartesian[2] = new CartesianCoordinate(0, -2, 0);
		coordinates_cartesian[3] = new CartesianCoordinate(0, 3, 4);
		
		coordinates_spheric[0] = new SphericCoordinate(0, 0, 0);
		coordinates_spheric[1] = new SphericCoordinate(0, Math.PI/2, 1);
		coordinates_spheric[2] = new SphericCoordinate(-Math.PI/2, Math.PI/2, 2);
		coordinates_spheric[3] = new SphericCoordinate(Math.PI/2, Math.asin(3. / 5.), 5);		
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
	public void testGetCentralAngle() {
		for (int i=0; i < coordinates_cartesian.length; i++) {
			assertEquals(0, coordinates_cartesian[i].getCentralAngle(coordinates_spheric[i]), double_threshold);
			assertEquals(0, coordinates_cartesian[i].getCentralAngle(coordinates_spheric[i]), double_threshold);
		}
	}
	/*
	 * 		System.out.println(coordinates_spheric[i].asCartesianCoordinate().getX());
			System.out.println(coordinates_spheric[i].asCartesianCoordinate().getY());
			System.out.println(coordinates_spheric[i].asCartesianCoordinate().getZ());
			System.out.println(coordinates_cartesian[i].getCartesianDistance(coordinates_spheric[i]));
			System.out.println();
	 */
	private void print(SphericCoordinate c) {
		System.out.println(c.getPhi());
		System.out.println(c.getTheta());
		System.out.println(c.getRadius());
	}

}
