package com.tgfcodes.bores.event.util;

import java.util.Properties;

import org.apache.velocity.app.VelocityEngine;

public class VelocityEngineUtil {

	public static VelocityEngine getVelocityEngine() {
		Properties propert = new Properties();
		propert.setProperty("resource.loaders", "class");
		propert.setProperty("resource.loader.class.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		return new VelocityEngine(propert);
	}

}
