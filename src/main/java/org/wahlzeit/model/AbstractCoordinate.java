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

import java.util.logging.Logger;
import org.wahlzeit.services.LogBuilder;

public abstract class AbstractCoordinate implements Coordinate {
	
	protected static final Logger log = Logger.getLogger(AbstractCoordinate.class.getName());
	
	private static final double compare_threshold = 0.000001;
	
	/**
	 * 	@Methodtype conversion
	 */
	@Override
	public CartesianCoordinate asCartesianCoordinate() {
		CartesianCoordinate ret = doAsCartesianCoordinate();
		assertIsNotNull(ret);		
		if (! valueCompare(ret.doAsSphericCoordinate(), this.doAsSphericCoordinate())) {
			IllegalStateException ex = new IllegalStateException("The coordinate was not converted correctly!");
			log.warning(
					LogBuilder.createSystemMessage().addException("Coordinate conversion to CartesianCooridnate failed!", ex).toString());
			throw ex;
		}

		return ret;
	}	
	
	/**
	 * @Methodtype helper
	 */
	private boolean valueCompare(SphericCoordinate c1, SphericCoordinate c2) {
		double phi_diff = Math.abs(c1.getPhi() - c2.getPhi());
		double theta_diff = Math.abs(c1.getTheta()- c2.getTheta());
		double r_diff = Math.abs(c1.getRadius() - c2.getRadius());

		boolean zero_radius = c1.getRadius() == 0 && c2.getRadius() == 0;
		return zero_radius 
				|| (phi_diff < compare_threshold 
					&& theta_diff < compare_threshold 
					&& r_diff < compare_threshold);
	}
	
	
	/**
	 * 	@Methodtype conversion
	 */
	protected abstract CartesianCoordinate doAsCartesianCoordinate();

	
	/**
	 * 	@Methodtype conversion
	 */
	@Override
	public SphericCoordinate asSphericCoordinate() {
		SphericCoordinate ret = doAsSphericCoordinate();
		assertIsNotNull(ret);
		if (!valueCompare(ret.doAsCartesianCoordinate(), this.doAsCartesianCoordinate())) {
			IllegalStateException ex = new IllegalStateException("The coordinate was not converted correctly!");
			log.warning(
					LogBuilder.createSystemMessage().addException("Coordinate conversion to SphericCooridnate failed!", ex).toString());
			throw ex;
		}

		return ret;
	}	
	
	/**
	 * @Methodtype helper
	 */
	private boolean valueCompare(CartesianCoordinate c1, CartesianCoordinate c2) {
		double x_diff = Math.abs(c1.getX() - c2.getX());
		double y_diff = Math.abs(c1.getY() - c2.getY());
		double z_diff = Math.abs(c1.getZ() - c2.getZ());

		return x_diff < compare_threshold && y_diff < compare_threshold && z_diff < compare_threshold;
	}

	
	/**
	 * 	@Methodtype conversion
	 */
	public abstract SphericCoordinate doAsSphericCoordinate();

	
	/**
	 * @MethodType assertion
	 */	
	protected void assertIsNotNull(Coordinate c) {
		if (c == null) {
			throw new NullPointerException();
		}
	}
	
	
	@Override
	public double getCartesianDistance(Coordinate coordinate) {
		assertIsNotNull(coordinate);
		double dist = doGetCartesianDistance(this.asCartesianCoordinate(), coordinate.asCartesianCoordinate());
		if (dist < 0) {		
			IllegalStateException ex = new IllegalStateException("The calculated distance is smaller than 0. This has to be an implementation error");
			log.warning(
					LogBuilder.createSystemMessage().addException("getCartesianDistance failed", ex).toString());
			throw ex;
		}
		return dist;
	}
	
	
	private double doGetCartesianDistance(CartesianCoordinate coordinate1, CartesianCoordinate coordinate2) {
		/* 
		 * None of the values in both coordinates can be NaN or infinite, 
		 * because the class invariant.
		 * */
		
		double x_diff = coordinate2.getX() - coordinate1.getX();
		double y_diff = coordinate2.getY() - coordinate1.getY();
		double z_diff = coordinate2.getZ() - coordinate1.getZ();
		
		double radicand = x_diff * x_diff + y_diff * y_diff + z_diff * z_diff;
		
		/* If the values where too big for a double, throw an exception. */
		if (Double.isInfinite(radicand)) {
			ArithmeticException ex = new ArithmeticException("In the calculation the values got bigger than Double.MAX_VALUE.");
			log.warning(
					LogBuilder.createSystemMessage().addException("doGetCartesianDistance failed", ex).toString());
			throw ex;
		}
		
		return Math.sqrt(radicand);
	}

	
	@Override
	public double getCentralAngle(Coordinate coordinate) {
		assertIsNotNull(coordinate);
		SphericCoordinate c1 = this.asSphericCoordinate();
		SphericCoordinate c2 = coordinate.asSphericCoordinate();
		if (c1.getRadius() == 0 || c2.getRadius() == 0) {
			IllegalArgumentException ex = new IllegalArgumentException("Can not compute central angle with the center of the coordinatesystem.");
			log.warning(
					LogBuilder.createSystemMessage().addException("getCentralAngle failed", ex).toString());
			throw ex;
		}
		
		double dist = doGetCentralAngle(c1, c2);
		if (dist < 0 || dist > Math.PI) {
			IllegalStateException ex = new IllegalStateException("the calculated central angle is not in the valid range. This has to be an implementation error.");
			log.warning(
					LogBuilder.createSystemMessage().addException("getCentralAngle failed", ex).toString());
			throw ex;
		}
		return dist;
	}

	
	private double doGetCentralAngle(SphericCoordinate c1, SphericCoordinate c2) {
		double phi_diff = Math.abs(c2.getPhi() - c1.getPhi());

		double central_angle = Math.acos(
				Math.cos(c2.getTheta()) * Math.cos(c1.getTheta()) 
				+ Math.sin(c2.getTheta()) * Math.sin(c1.getTheta()) * Math.cos(phi_diff));
		
		return central_angle;
	}

	
	/**
	 * @Methodtype boolean query
	 */
	@Override
	public boolean isEqual(Coordinate coordinate) {
		assertIsNotNull(coordinate);
		double dist = getCartesianDistance(coordinate);
		return Math.abs(dist) <= compare_threshold;
	}
	

	/**
	 * @Methodtype boolean query
	 */	
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
	
	
	/**
	 * @MethodType assertion
	 */	
	protected void assertValidDouble(double d) {
		if (Double.isNaN(d)) {
			IllegalArgumentException ex = new IllegalArgumentException("This value must not be NaN!");
			log.warning(
					LogBuilder.createSystemMessage().addException("Got and invlid double as an argument!", ex).toString());
			throw ex;
		}
		if (Double.isInfinite(d)) {
			IllegalArgumentException ex = new IllegalArgumentException("This value must not be infinte!");
			log.warning(
					LogBuilder.createSystemMessage().addException("Got and invlid double as an argument!", ex).toString());
			throw ex;
		}
	}

}
