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

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.wahlzeit.model.persistence.DatastoreAdapter;
import org.wahlzeit.model.persistence.ImageStorage;
import org.wahlzeit.testEnvironmentProvider.LocalDatastoreServiceTestConfigProvider;
import org.wahlzeit.testEnvironmentProvider.RegisteredOfyEnvironmentProvider;

import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.util.Closeable;

public class MushroomPhotoMangerTest {
	
	private Closeable closeable;
	
	@Before
    public void setUp() {
        ImageStorage.setInstance(new DatastoreAdapter());
        closeable = ObjectifyService.begin();
    }

    @After
    public void tearDown() {
        closeable.close();
    }
	
	@ClassRule
	public static TestRule chain = RuleChain.
			outerRule(new LocalDatastoreServiceTestConfigProvider()).
			around(new RegisteredOfyEnvironmentProvider());
	
	@Test
	public void testGetInstance() {
		assertTrue(MushroomPhotoManager.getInstance() instanceof MushroomPhotoManager);
	}
	
	@Test
	public void testSaveAndLoad() {
		User user = new User("testId", "testnick", "test@test.de");
		
		PhotoManager manager = MushroomPhotoManager.getInstance();
		PhotoId id = new PhotoId(1);
		PhotoFactory factory = MushroomPhotoFactory.getInstance();
		Photo savedPhoto = factory.createPhoto(id);
		//set width and height to verify that the same Photo is loaded
		savedPhoto.setWidthAndHeight(1234, 23312);
		user.addPhoto(savedPhoto);		
		manager.savePhoto(savedPhoto);
		manager.loadPhotos();
		Photo loadedPhoto = manager.getPhoto(id);
		assertNotNull(savedPhoto);
		assertNotNull(loadedPhoto);
		
		//compare creation time, width and height to verify the Photos are the same
		assertEquals(savedPhoto.getCreationTime(), loadedPhoto.getCreationTime());
		assertEquals(savedPhoto.getWidth(), loadedPhoto.getWidth());
		assertEquals(savedPhoto.getHeight(), loadedPhoto.getHeight());
	}

}
