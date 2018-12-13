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

import java.lang.Math;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.wahlzeit.services.LogBuilder;

/**
 * A Coordinate class that represents a position via three cartesian coordinates.
 */
public class CartesianCoordinate extends AbstractCoordinate {
	

	private final double x;
	private final double y;
	private final double z;
	
	private static Map<Integer, CartesianCoordinate> allCartesianCoordinates = new ConcurrentHashMap<>();

	private CartesianCoordinate(double x, double y, double z) {
		assertValidDouble(x);
		assertValidDouble(y);
		assertValidDouble(z);

		this.x = x;
		this.y = y;
		this.z = z;
		
		assertClassInvariants();
	}
	
	public static CartesianCoordinate getCartesianCoordinate(double x, double y, double z) {
		int hash = createHashCode(x, y, z);
		CartesianCoordinate coordinate = allCartesianCoordinates.get(hash);
		if (coordinate == null) {
			synchronized (allCartesianCoordinates){
				coordinate = allCartesianCoordinates.get(hash);
				if (coordinate == null) {
					coordinate = new CartesianCoordinate(x, y, z);
					allCartesianCoordinates.put(hash, coordinate);
				}
			}
		}
		return coordinate;		
	}
	
	/**
	 * @MethodType assertion
	 */	
	private void assertClassInvariants() {
		try {
			assertValidDouble(getX());
			assertValidDouble(getY());
			assertValidDouble(getZ());
		} catch (IllegalArgumentException e) {
			log.warning(
					LogBuilder.createSystemMessage().addException("Class invariant for CartesianCoordinate was violated!", e).toString());
			throw new IllegalStateException("Class invariant for CartesianCoordinate was violated!", e);
		}
	}

	
	/**
	 * @MethodType get
	 */	
	public double getX() {
		return x;
	}
	
	
	/**
	 * @MethodType get
	 */	
	public double getY() {
		return y;
	}
	
	
	/**
	 * @MethodType get
	 */	
	public double getZ() {
		return z;
	}


	/**
	 * 	@Methodtype conversion
	 */
	@Override
	public CartesianCoordinate doAsCartesianCoordinate() {
		return this;
	}


	/**
	 * 	@Methodtype conversion
	 */
	@Override
	public SphericCoordinate doAsSphericCoordinate() {
		double radius  = Math.sqrt(getX() * getX() + getY() * getY() + getZ() * getZ());
		double phi = 0;
		if (x!=0) {
			phi = Math.atan(y / x);
			if (x < 0) {
				phi += Math.PI;
			}
		} else {
			if (y > 0) {
				phi = Math.PI / 2;
			} else {
				phi = - Math.PI / 2;
			}
		}
		double theta = 0;
		if (radius != 0) {
			theta = Math.acos(z / radius);
		}
		
		return SphericCoordinate.getSphericCoordinate(phi, theta, radius);
	}
	
	
	@Override
	public int hashCode() {
		return createHashCode(getX(), getY(), getZ());
	}
	
	
	private static int createHashCode(double x, double y, double z) {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(z);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}


	/**
	 * @Methodtype boolean query
	 */
	@Override
	public boolean isExactlyEqual(Coordinate coordinate) {
		if (coordinate == null) {
			return false;
		}
		CartesianCoordinate other = coordinate.asCartesianCoordinate();
		return getX() == other.getX() && getY() == other.getY() && getZ() == other.getZ();
	}

}
