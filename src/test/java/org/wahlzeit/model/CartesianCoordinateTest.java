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

import org.junit.Before;
import org.junit.Test;

/**
 * Test cases for the CartesianCoordinate class.
 */
public class CartesianCoordinateTest {
	private Coordinate coordinate1 = null;

	@Before
	public void setUp() {
		coordinate1 = CartesianCoordinate.getCartesianCoordinate(0, 0, 0);
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void testNaNInInitialization() {
		CartesianCoordinate.getCartesianCoordinate(0, 0, Double.NaN);
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void testPositiveInfinityInInitialization() {
		CartesianCoordinate.getCartesianCoordinate(0, 0, Double.POSITIVE_INFINITY);
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void testNegativeInfinityInInitialization() {
		CartesianCoordinate.getCartesianCoordinate(0, 0, Double.NEGATIVE_INFINITY);
	}
	
	@Test
	public void testIdentity() {
		CartesianCoordinate c1 = CartesianCoordinate.getCartesianCoordinate(0, 0, 0);
		CartesianCoordinate c2 = CartesianCoordinate.getCartesianCoordinate(0, 0, 0);
		assertTrue(c1 ==c2);
	}

	@Test
	public void testIsEqualSameCoordinate() {
		assertTrue(coordinate1.isEqual(coordinate1));
	}
	
	@Test
	public void testIsEqualSameValues() {
		assertTrue(coordinate1.isEqual(CartesianCoordinate.getCartesianCoordinate(0, 0, 0)));
	}
	
	@Test
	public void testIsEqualDifferentValues() {
		assertFalse(coordinate1.isEqual(CartesianCoordinate.getCartesianCoordinate(1, 0, 0)));
	}
	
	@Test
	public void testGetDistanceSameCoordinate() {
		assertEquals(0, coordinate1.getCartesianDistance(coordinate1), 0.0);
	}
	
	@Test
	public void testGetDistanceSameValue() {
		assertEquals(0, coordinate1.getCartesianDistance(CartesianCoordinate.getCartesianCoordinate(0, 0, 0)), 0.0);
	}
	
	@Test
	public void testGetDistanceSimple() {
		assertEquals(1, coordinate1.getCartesianDistance(CartesianCoordinate.getCartesianCoordinate(1, 0, 0)), 0.0);
	}
	
	@Test
	public void testGetDistanceComplex() {
		double result = coordinate1.getCartesianDistance(CartesianCoordinate.getCartesianCoordinate(12, 2, 63.72810));
		assertEquals(64.87889278964307, result, 1e-20);
	}
	
	@Test (expected = Exception.class)
	public void testGetDistanceOverflow() throws Exception {
		Coordinate coordinate2 = CartesianCoordinate.getCartesianCoordinate(Double.MAX_VALUE, 0, 0);
		Coordinate coordinate3 = CartesianCoordinate.getCartesianCoordinate(-1, 0, 0);
		
		coordinate2.getCartesianDistance(coordinate3);
	}
}
