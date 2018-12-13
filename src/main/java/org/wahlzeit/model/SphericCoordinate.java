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

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.wahlzeit.services.LogBuilder;

public class SphericCoordinate extends AbstractCoordinate {

	private final double phi; //azimuth angle
	private final double theta; //polar angle
	private final double radius;
	
	private static Map<Integer, SphericCoordinate> allSphericCoordinates = new ConcurrentHashMap<>();
	
	private SphericCoordinate(double phi, double theta, double radius) {
		assertValidDouble(phi);
		assertValidDouble(theta);
		assertValidDouble(radius);
		Double [] ret = normalize(phi, theta, radius);
		this.phi = ret[0];
		this.theta = ret[1];
		this.radius = ret[2];
		assertClassInvariants();
	}
	
	public static SphericCoordinate getSphericCoordinate(double phi, double theta, double radius) {
		int hash = createHashCode(phi, theta, radius);
		SphericCoordinate coordinate = allSphericCoordinates.get(hash);
		if (coordinate == null) {
			synchronized (allSphericCoordinates){
				coordinate = allSphericCoordinates.get(hash);
				if (coordinate == null) {
					coordinate = new SphericCoordinate(phi, theta, radius);
					allSphericCoordinates.put(hash, coordinate);
				}
			}
		}
		return coordinate;		
	}
	
	
	/**
	 * @MethodType assertion
	 */	
	private void assertValidCoordinate(SphericCoordinate coordinate) {
		try {
			if (coordinate == null) {
				throw new NullPointerException();
			}
			assertValidDouble(coordinate.getPhi());
			assertValidDouble(coordinate.getRadius());
			assertValidDouble(coordinate.getTheta());
		} catch (Exception e) {
			log.warning(
					LogBuilder.createSystemMessage().addException("Invalid values in SphericCoordinate!", e).toString());
			throw e;
		}
	}
	
	
	/**
	 * @MethodType assertion
	 */	
	private void assertClassInvariants() {
		Exception ex = null;
		
		try {
			assertValidCoordinate(this);
		} catch (Exception e) {
			ex = e;
		}
		if (radius < 0) {
			ex = new IllegalStateException("Radius in a SphericCoordinate must not be smaller than 0!");
		}
		if (phi < 0 || phi > 2 * Math.PI) {
			ex = new IllegalStateException("Phi in a SphericCoordinate must be within [0, 2pi]!");
		}
		if (theta < 0 || theta > Math.PI) {
			ex = new IllegalStateException("Theta in a SphericCoordinate must be within [0, pi]!");
		}
		if (ex != null) {
			log.warning(
					LogBuilder.createSystemMessage().addException("Class invarint for SphericCoordinate was violated!", ex).toString());
			throw new IllegalStateException("Class invarint for SphericCoordinate was violated!", ex);
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
	private Double[] normalize(double phi, double theta, double radius) {
		
		assertValidCoordinate(this);
		
		Double[] ret  = new Double[3];
		
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
		
		ret[0] = phi;
		ret[1] = theta;
		ret[2] = radius;
		
		return ret;
	}
	
	
	/**
	 * 	@Methodtype conversion
	 */
	@Override
	public CartesianCoordinate doAsCartesianCoordinate() {
		double x = radius * Math.cos(phi) * Math.sin(theta);
		double y = radius * Math.sin(phi) * Math.sin(theta);
		double z = radius * Math.cos(theta);
		
		return CartesianCoordinate.getCartesianCoordinate(x, y, z);
	}
	

	/**
	 * 	@Methodtype conversion
	 */
	@Override
	public SphericCoordinate doAsSphericCoordinate() {
		return this;
	}


	@Override
	public int hashCode() {
		return createHashCode(getPhi(), getTheta(), getRadius());
	}
	
	
	private static int createHashCode(double phi, double theta, double radius) {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(phi);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(radius);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(theta);
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
		SphericCoordinate other = coordinate.asSphericCoordinate();
		return getPhi() == other.getPhi() && getRadius() == other.getRadius() && getTheta() == other.getTheta();
	}
	
	
}
