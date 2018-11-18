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

public class SphericCoordinateTest {
	
	private static final double double_threshold = 1e-10;

	private Coordinate coordinate1 = null;

	@Before
	public void setUp() {
		coordinate1 = new SphericCoordinate(0, 0, 1);
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void testNaNInInitialization() {
		new SphericCoordinate(0, 0, Double.NaN);
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void testPositiveInfinityInInitialization() {
		new SphericCoordinate(0, 0, Double.POSITIVE_INFINITY);
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void testNegativeInfinityInInitialization() {
		new SphericCoordinate(0, 0, Double.NEGATIVE_INFINITY);
	}
	
	@Test
	public void testNegativeRadiusConversion() {
		double r = -2131.121;
		double theta = Math.PI / 4;
		double phi = Math.PI;
		
		SphericCoordinate c = new SphericCoordinate(phi, theta, r);
		assertEquals(c.getRadius(), -r, double_threshold);
		assertEquals(c.getPhi(), 0, double_threshold);
		assertEquals(c.getTheta(), 3*theta, double_threshold);
	}

	@Test 
	public void testAngleNormalization() {
		double theta = 0.02423;
		double phi = 0.123324325;
		double r = 1;
		SphericCoordinate c = new SphericCoordinate(phi + Math.PI * 4, theta - Math.PI *2, r);
		
		assertEquals(phi, c.getPhi(), double_threshold);
		assertEquals(theta, c.getTheta(), double_threshold);	
	}
	
	@Test
	public void testAsCartesianCoordinate() {
		double r = 1;
		double theta = Math.PI / 2;
		double phi = Math.PI / 2;		
		CartesianCoordinate c = new SphericCoordinate(phi, theta, r).asCartesianCoordinate();		
		assertEquals(0, c.getX(), double_threshold);
		assertEquals(1, c.getY(), double_threshold);
		assertEquals(0, c.getZ(), double_threshold);
		
		r = 2;
		theta = Math.PI / 2;
		phi = 0;		
		c = new SphericCoordinate(phi, theta, r).asCartesianCoordinate();		
		assertEquals(2, c.getX(), double_threshold);
		assertEquals(0, c.getY(), double_threshold);
		assertEquals(0, c.getZ(), double_threshold);
		
		r = 1;
		theta = 3 * Math.PI / 4;	
		phi = Math.PI;
		c = new SphericCoordinate(phi, theta, r).asCartesianCoordinate();		
		assertEquals(-1/Math.sqrt(2), c.getX(), double_threshold);
		assertEquals(0, c.getY(), double_threshold);
		assertEquals(-1/Math.sqrt(2), c.getZ(), double_threshold);
	}
	
	@Test
	public void testGetCentralAngle() {
		double r = 1;
		double theta = Math.PI / 2;
		double phi = 0;		
		SphericCoordinate c = new SphericCoordinate(phi, theta, r);
		
		assertEquals(Math.PI/2, c.getCentralAngle(coordinate1), double_threshold);
		assertEquals(c.getCentralAngle(coordinate1), coordinate1.getCentralAngle(c), double_threshold);
	}
	
}
