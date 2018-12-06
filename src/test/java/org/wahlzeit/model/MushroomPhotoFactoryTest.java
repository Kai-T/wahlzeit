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
import org.junit.Test;

public class MushroomPhotoFactoryTest {

	@Test
	public void testInitialize() {
		MushroomPhotoFactory.initialize();
		assertNotNull(MushroomPhotoFactory.getInstance());
		assertTrue(MushroomPhotoFactory.getInstance() instanceof MushroomPhotoFactory);
		assertTrue(MushroomPhotoFactory.getInstance() instanceof PhotoFactory);
	}
	
	@Test
	public void testCreatePhoto() {
		MushroomPhotoFactory.initialize();
		PhotoFactory factory = MushroomPhotoFactory.getInstance();
		assertTrue(factory.createPhoto() instanceof MushroomPhoto);
		assertTrue(factory.createPhoto(PhotoId.getRandomId()) instanceof MushroomPhoto);
	}
	
	@Test
	public void testCreatePhotoNullArgument() {
		MushroomPhotoFactory.initialize();
		PhotoFactory factory = MushroomPhotoFactory.getInstance();
		assertNotNull(factory.createPhoto(null));
	}
	
	@Test (expected=NullPointerException.class)
	public void testSetInstaceNullArgument() {
		MushroomPhotoFactory.setInstance(null);
	}

}
