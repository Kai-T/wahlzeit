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

public abstract class AbstractCoordinate implements Coordinate {
	
	private static final double compare_threshold = 0.000001;

	@Override
	public abstract CartesianCoordinate asCartesianCoordinate();

	@Override
	public abstract SphericCoordinate asSphericCoordinate();
	
	@Override
	public double getCartesianDistance(Coordinate coordinate) {
		CartesianCoordinate c = coordinate.asCartesianCoordinate();
		//assertValidCoordinate(c); not needed, is done in asCartesianCoordinate anyways
		return doGetCartesianDistance(c);
	}
	
	/**
	 * Calculate the direct cartesian distance between the coordinates.
	 * @param coordinate	the second coordinate
	 * @return				the calculated distance
	 */
	private double doGetCartesianDistance(CartesianCoordinate coordinate) {
		/* 
		 * None of the values in both coordinates can be NaN or infinite, 
		 * because the assertion method in getCartesianDistance
		 * */
		CartesianCoordinate this_cartesian = this.asCartesianCoordinate();
		
		double x_diff = this_cartesian.getX() - coordinate.getX();
		double y_diff = this_cartesian.getY() - coordinate.getY();
		double z_diff = this_cartesian.getZ() - coordinate.getZ();
		
		double radicand = x_diff * x_diff + y_diff * y_diff + z_diff * z_diff;
		
		/* If the values where too big for a double, throw an exception. */
		if (Double.isInfinite(radicand)) {
			throw new ArithmeticException("In the calculation the values got bigger than Double.MAX_VALUE.");
		}
		
		return Math.sqrt(radicand);
	}

	@Override
	public double getCentralAngle(Coordinate coordinate) {
		SphericCoordinate c = coordinate.asSphericCoordinate();		
		return doGetCentralAngle(c);
	}

	private double doGetCentralAngle(SphericCoordinate c) {
		SphericCoordinate this_spheric = this.asSphericCoordinate();
		if (this_spheric.getRadius() == 0 || c.getRadius() == 0) {
			throw new ArithmeticException("Can not compute central angle with the center of the coordinatesystem");
		}
		
		double phi_diff = Math.abs(this_spheric.getPhi() - c.getPhi());

		double central_angle = Math.acos(
				Math.cos(this_spheric.getTheta()) * Math.cos(c.getTheta()) 
				+ Math.sin(this_spheric.getTheta()) * Math.sin(c.getTheta()) * Math.cos(phi_diff));
		
		return central_angle;
	}

	@Override
	public boolean isEqual(Coordinate coordinate) {
		double dist = getCartesianDistance(coordinate);
		return Math.abs(dist) <= compare_threshold;
	}
	
	
	@Override
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
