package com.worldline.clic.utils;

import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 * Stores all i18n messages
 * 
 * @author aneveux
 * @version 1.0
 * @since 1.0
 */
public enum Messages {

	// Templates

	POM_TEMPLATE,

	// Commands

	MAVEN_REFERENCE, MAVEN_REFERENCE_DESCRIPTION, MAVEN_REFERENCE_ARG, GENERATE_POM, GENERATE_POM_DESCRIPTION, MAVEN_CMD, MAVEN_CMD_DESCRIPTION, MAVEN_CMD_ARG, JVM_PARAM, JVM_PARAM_DESCRIPTION, JVM_PARAM_ARG,

	;

	/**
	 * ResourceBundle instance
	 */
	private static ResourceBundle resourceBundle = ResourceBundle
			.getBundle("messages");

	/**
	 * @return value of the message
	 */
	public String value() {
		if (Messages.resourceBundle == null
				|| !Messages.resourceBundle.containsKey(name()))
			return "!!" + name() + "!!";
		return Messages.resourceBundle.getString(name());
	}

	/**
	 * @return value of the formatted message
	 */
	public String value(final Object... args) {
		if (Messages.resourceBundle == null
				|| !Messages.resourceBundle.containsKey(name()))
			return "!!" + name() + "!!";
		return MessageFormat.format(Messages.resourceBundle.getString(name()),
				args);
	}

}
