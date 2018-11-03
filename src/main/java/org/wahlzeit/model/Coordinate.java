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
 * A Coordinate represents a position via three cartesian coordinates.
 */
public class Coordinate {

	private double x = 0;
	private double y = 0;
	private double z = 0;
	
	/**
	 * @methodtype constructor
	 */
	public Coordinate(double x, double y, double z) {
		/*
		 * A Coordinate with one of the values being NaN or infinite might lead to problems later. 
		 * That's why I prevent this situation from happening. 
		 */
		if (Double.isNaN(x) || Double.isNaN(y) ||Double.isNaN(z)) {
			throw new IllegalArgumentException("A Coordinate must not contain NaN!");
		}
		if (Double.isInfinite(x) || Double.isInfinite(y) ||Double.isInfinite(z)) {
			throw new IllegalArgumentException("A Coordinate must not contain infinite!");
		}
		
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * Calculate the direct cartesian distance between the coordinates.
	 * @param coordinate	the second coordinate
	 * @return				the calculated distance
	 */
	public double getDistance(Coordinate coordinate) {
		/* 
		 * None of the values in both coordinates can be NaN or infinite, 
		 * because the constructor prevents that.  
		 * */
		double x_diff = this.x - coordinate.x;
		double y_diff = this.y - coordinate.y;
		double z_diff = this.z - coordinate.z;
		
		double radicand = x_diff * x_diff + y_diff * y_diff + z_diff * z_diff;
		
		/* If the values where too big for a double, throw an exception. */
		if (Double.isInfinite(radicand)) {
			throw new ArithmeticException("In the calculation the values got bigger than Double.MAX_VALUE.");
		}
		
		return Math.sqrt(radicand);
	}
	
	/**
	 * Return whether the given coordinate equals this coordinate.
	 * @param 	coordinate the coordinate this coordinate is compared to
	 * @return	the result of the comparison
	 */
	public boolean isEqual(Coordinate coordinate) {
		return (this.x == coordinate.x) 
			   && (this.y == coordinate.y) 
			   && (this.z == coordinate.z);
	}	
	
	
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (!(obj.getClass().equals(Coordinate.class))){
			return false;
		}
		return this.isEqual((Coordinate)obj);
	}
}
