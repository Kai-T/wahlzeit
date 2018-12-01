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

public class SphericCoordinate extends AbstractCoordinate {

	private double phi = 0; //azimuth angle
	private double theta = 0; //polar angle
	private double radius = 0;
	
	public SphericCoordinate(double phi, double theta, double radius) {
		this.phi = phi;
		this.theta = theta;
		this.radius = radius;
		normalize();
		assertClassInvariants();
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
	 * @MethodType assertion
	 */	
	private void assertClassInvariants() {
		assertValidCoordinate(this);
		if (radius < 0) {
			throw new IllegalStateException("radius in a SphericCoordinate must not be smaller than 0!");
		}
		if (phi < 0 || phi > 2 * Math.PI) {
			throw new IllegalStateException("phi in a SphericCoordinate must be within [0, 2pi]!");
		}
		if (theta < 0 || theta > Math.PI) {
			throw new IllegalStateException("radius in a SphericCoordinate must be within [0, pi]!");
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
	 * @MethodType mutation
	 */
	private void normalize() {
		
		assertValidCoordinate(this);
		
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
		assertClassInvariants();
	}
	
	
	/**
	 * 	@Methodtype conversion
	 */
	@Override
	public CartesianCoordinate doAsCartesianCoordinate() {
		double x = radius * Math.cos(phi) * Math.sin(theta);
		double y = radius * Math.sin(phi) * Math.sin(theta);
		double z = radius * Math.cos(theta);
		
		return new CartesianCoordinate(x, y, z);
	}
	

	/**
	 * 	@Methodtype conversion
	 */
	@Override
	public SphericCoordinate doAsSphericCoordinate() {
		return this;
	}
	
}
