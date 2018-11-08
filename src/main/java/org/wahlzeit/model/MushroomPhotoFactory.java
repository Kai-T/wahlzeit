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

public class MushroomPhotoFactory extends PhotoFactory {

	private static final Logger log = Logger.getLogger(MushroomPhotoFactory.class.getName());
	/**
	 * Hidden singleton instance; needs to be initialized from the outside.
	 */
	private static MushroomPhotoFactory instance = null;
	
	/**
	 *
	 */
	protected MushroomPhotoFactory() {
		// do nothing
	}
	
	/**
	 * Public singleton access method.
	 * @methodtype get
	 */
	public static synchronized PhotoFactory getInstance() {
		if (instance == null) {
			log.config(LogBuilder.createSystemMessage().addAction("setting specialzied MushroomPhotoFactory").toString());
			setInstance(new MushroomPhotoFactory());
		}
		return instance;
	}
	
	/**
	 * Method to set the singleton instance of PhotoFactory.
	 * @methodtype set
	 */
	protected static synchronized void setInstance(MushroomPhotoFactory photoFactory) {
		if (instance != null) {
			throw new IllegalStateException("attempt to initalize MushroomPhotoFactory twice");
		}
		instance = photoFactory;
	}
	
	/**
	 * @methodtype factory
	 */
	public Photo createPhoto() {
		MushroomPhoto photo = new MushroomPhoto();
		return photo;
	}

	/**
	 * Creates a new photo with the specified id
	 * @methodtype factory
	 */
	public Photo createPhoto(PhotoId id) {
		MushroomPhoto photo = new MushroomPhoto(id);
		return photo;
	}
	
}
