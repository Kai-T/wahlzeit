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

public class SphericCoordinate implements Coordinate {

	private double phi = 0; //azimuth angle
	private double theta = 0; //polar angle
	private double radius = 0;
	
	public SphericCoordinate(double phi, double theta, double radius) {
		this.phi = phi;
		this.theta = theta;
		this.radius = radius;
		assertValidCoordinate(this);
		normalize();
	}
	
	/**
	 * @MethodType assertion
	 */	
	private void assertValidCoordinate(SphericCoordinate coordinate) {
		if (coordinate == null) {
			throw new NullPointerException();
		}
		if (Double.isNaN(coordinate.getPhi()) || Double.isNaN(coordinate.getTheta()) ||Double.isNaN(coordinate.getRadius())) {
			throw new IllegalArgumentException("A Coordinate must not contain NaN!");
		}
		if (Double.isInfinite(coordinate.getPhi()) || Double.isInfinite(coordinate.getTheta()) ||Double.isInfinite(coordinate.getRadius())) {
			throw new IllegalArgumentException("A Coordinate must not contain infinite!");
		}
	}
	
	/**
	 * @MethodType get
	 */	
	public double getRadius() {
		return radius;
	}
	
	/**
	 * @MethodType get
	 */	
	public double getTheta() {
		return theta;
	}
	
	/**
	 * @MethodType get
	 */	
	public double getPhi() {
		return phi;
	}

	/**
	 * Makes sure radius is positive, phi is within [0, 2pi] and theta within [0, pi]
	 * @MethodType helper
	 */
	private void normalize() {
		
		//normalize radius
		if (radius < 0) {
			radius *= -1;
			phi += Math.PI;
			theta = Math.PI - theta;
		}
		
		// make theta to be within [0, 2pi)
		while(theta < 0) {
			theta += (Math.PI * 2);
		}
		while(theta >= Math.PI * 2) {
			theta -= (Math.PI * 2);
		}
		// make theta to be within [0, pi)
		if (theta > Math.PI) {
			theta = 2 * Math.PI - theta;
			phi += Math.PI;
		}
				
		// make phi to be within [0, 2pi)
		while(phi < 0) {
			phi += (Math.PI * 2);
		}
		while(phi >= Math.PI * 2) {
			phi -= (Math.PI * 2);
		}
	}
	
	@Override
	public CartesianCoordinate asCartesianCoordinate() {
		double x = radius * Math.cos(phi) * Math.sin(theta);
		double y = radius * Math.sin(phi) * Math.sin(theta);
		double z = radius * Math.cos(theta);
		
		return new CartesianCoordinate(x, y, z);
	}


	@Override
	public double getCartesianDistance(Coordinate coordinate) {
		return asCartesianCoordinate().getCartesianDistance(coordinate);
	}


	@Override
	public SphericCoordinate asSphericCoordinate() {
		return this;
	}


	@Override
	public double getCentralAngle(Coordinate coordinate) {
		SphericCoordinate c = coordinate.asSphericCoordinate();		
		return doGetCentralAngle(c);
	}

	private double doGetCentralAngle(SphericCoordinate c) {
		if (getRadius() == 0 || c.getRadius() == 0) {
			throw new ArithmeticException("Can not compute central angle with the center of the coordinatesystem");
		}
		
		double phi_diff = Math.abs(phi - c.phi);

		double central_angle = Math.acos(
				Math.cos(theta) * Math.cos(c.theta) 
				+ Math.sin(theta) * Math.sin(c.theta) * Math.cos(phi_diff));
		
		return central_angle;
	}

	/**
	 * Return whether the given coordinate equals this coordinate.
	 */
	@Override
	public boolean isEqual(Coordinate coordinate) {
		double dist = getCartesianDistance(coordinate);
		return Math.abs(dist) <= compare_threshold;
	}

}
