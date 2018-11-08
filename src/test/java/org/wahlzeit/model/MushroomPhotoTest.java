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

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.wahlzeit.services.OfyService;
import org.wahlzeit.testEnvironmentProvider.LocalDatastoreServiceTestConfigProvider;
import org.wahlzeit.testEnvironmentProvider.RegisteredOfyEnvironmentProvider;
import org.wahlzeit.testEnvironmentProvider.SysConfigProvider;
import org.wahlzeit.testEnvironmentProvider.UserServiceProvider;
import org.wahlzeit.testEnvironmentProvider.UserSessionProvider;

import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesServiceFactory;

public class MushroomPhotoTest {

	@ClassRule
	public static RuleChain ruleChain = RuleChain.
			outerRule(new LocalDatastoreServiceTestConfigProvider()).
			around(new RegisteredOfyEnvironmentProvider()).
			around(new SysConfigProvider()).
			around(new UserServiceProvider()).
			around(new UserSessionProvider());
	
	@Test
	public void testLoad() throws Exception {
		final String PICTURES_PATH = "pictures";

		URL url = getClass().getClassLoader().getResource(PICTURES_PATH);
		String photoDir = new File(url.getPath()).getAbsolutePath();

		PhotoManager photoManager = MushroomPhotoManager.getInstance();
		File photoDirFile = new File(photoDir);
		FileFilter photoFileFilter = file -> file.getName().endsWith(".jpg");
		File[] photoFiles = photoDirFile.listFiles(photoFileFilter);

		for (File photo : photoFiles) {
			String photoPath = photo.getAbsolutePath();
			Image image = ImagesServiceFactory.makeImage(Files.readAllBytes(Paths.get(photoPath)));
			Photo newPhoto = photoManager.createPhoto(photo.getName(), image);
			assertTrue(newPhoto instanceof MushroomPhoto);
		}
	}
}
