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

/**
 * A Coordinate class that represents a position via three cartesian coordinates.
 */
public class CartesianCoordinate extends AbstractCoordinate {
	

	private final double x;
	private final double y;
	private final double z;
	

	public CartesianCoordinate(double x, double y, double z) {
		assertValidDouble(x);
		assertValidDouble(y);
		assertValidDouble(z);

		this.x = x;
		this.y = y;
		this.z = z;
		
		assertClassInvariants();
	}
	
	
	/**
	 * @MethodType assertion
	 */	
	private void assertClassInvariants() {
		if (Double.isNaN(getX()) || Double.isNaN(getY()) ||Double.isNaN(getZ())) {
			throw new IllegalArgumentException("A Coordinate must not contain NaN!");
		}
		if (Double.isInfinite(getX()) || Double.isInfinite(getY()) ||Double.isInfinite(getZ())) {
			throw new IllegalArgumentException("A Coordinate must not contain infinite!");
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
		
		return new SphericCoordinate(phi, theta, radius);
	}

}
