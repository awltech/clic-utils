package com.worldline.clic.utils.mvn;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.apache.maven.shared.invoker.DefaultInvocationRequest;
import org.apache.maven.shared.invoker.InvocationRequest;

import com.google.common.io.Files;

/**
 * This class aims at providing helpers in order to generate Maven commands,
 * both for execution on pom.xml files, or plugins executions.
 * 
 * @author aneveux
 * @version 1.0
 */
public class MavenCommand {
	/**
	 * Generates a pom.xml and the associated {@link InvocationRequest}.
	 * 
	 * @param reference
	 *            {@link MavenReference} to be used as a parent
	 * @param artifactId
	 *            to be used by the generated pom
	 * @param goals
	 *            the goals to be invoked
	 * @param properties
	 *            {@link Properties} containing all the parameters to be
	 *            processed by Maven
	 * @return the {@link InvocationRequest} to be executed
	 * @throws IOException
	 *             if pom cannot be generated
	 */
	public static InvocationRequest generatePomAndCommand(
			final MavenReference reference, final String artifactId,
			final List<String> goals, final Properties properties)
			throws IOException {
		File pom = MavenPom.generate(reference, artifactId);
		return generatePomCommand(pom, goals, properties);
	}

	/**
	 * Generates an {@link InvocationRequest} on a specific pom.xml
	 * 
	 * @param pom
	 *            the pom.xml file to be used for the Maven execution
	 * @param goals
	 *            the goals to be invoked
	 * @return the {@link InvocationRequest} to be executed
	 */
	public static InvocationRequest generatePomCommand(final File pom,
			final List<String> goals) {
		return generatePomCommand(pom, goals, null);
	}

	/**
	 * Generates an {@link InvocationRequest} on a specific pom.xml with some
	 * parameters
	 * 
	 * @param pom
	 *            the pom.xml file to be used for the Maven execution
	 * @param goals
	 *            the goals to be invoked
	 * @param properties
	 *            {@link Properties} containing all the parameters to be
	 *            processed by Maven
	 * @return the {@link InvocationRequest} to be executed
	 */
	public static InvocationRequest generatePomCommand(final File pom,
			final List<String> goals, final Properties properties) {
		final InvocationRequest request = new DefaultInvocationRequest();
		request.setBaseDirectory(pom.getParentFile());
		request.setPomFile(pom);
		request.setGoals(goals);
		if (properties != null)
			request.setProperties(properties);
		return request;
	}

	/**
	 * Generates an {@link InvocationRequest} on a specific pom.xml with an
	 * unique goal
	 * 
	 * @param pom
	 *            the pom.xml file to be used for the Maven execution
	 * @param goal
	 *            the unique goal to be executed
	 * @return the {@link InvocationRequest} to be executed
	 */
	public static InvocationRequest generatePomCommand(final File pom,
			final String goal) {
		return generatePomCommand(pom, Collections.singletonList(goal), null);
	}

	/**
	 * Generates an {@link InvocationRequest} on a specific pom.xml with an
	 * unique goal, and specifying some parameters for Maven
	 * 
	 * @param pom
	 *            the pom.xml file to be used for the Maven execution
	 * @param goal
	 *            the unique goal to be executed
	 * @param properties
	 *            {@link Properties} contaning the parameters to be processed by
	 *            Maven
	 * @return the {@link InvocationRequest} to be executed
	 */
	public static InvocationRequest generatePomCommand(final File pom,
			final String goal, final Properties properties) {
		return generatePomCommand(pom, Collections.singletonList(goal),
				properties);
	}

	/**
	 * Generates an {@link InvocationRequest} executing a particular command
	 * from a {@link MavenReference}
	 * 
	 * @param reference
	 *            the {@link MavenReference} of the plugin you'd like to execute
	 * @param command
	 *            the command you need to execute
	 * @return the {@link InvocationRequest} to be executed
	 */
	public static InvocationRequest generateCommand(
			final MavenReference reference, final String command) {
		return generateCommand(Files.createTempDir(), reference.groupId,
				reference.artifactId, reference.version, command, null);
	}

	/**
	 * Generates an {@link InvocationRequest} executing a particular command
	 * from a {@link MavenReference} in a particular path
	 * 
	 * @param path
	 *            the path where the command should be executed
	 * @param reference
	 *            the {@link MavenReference} of the plugin you'd like to execute
	 * @param command
	 *            the command you need to execute
	 * @return the {@link InvocationRequest} to be executed
	 */
	public static InvocationRequest generateCommand(final File path,
			final MavenReference reference, final String command) {
		return generateCommand(path, reference.groupId, reference.artifactId,
				reference.version, command, null);
	}

	/**
	 * Generates an {@link InvocationRequest} executing a particular command
	 * from a {@link MavenReference} with parameters
	 * 
	 * @param reference
	 *            the {@link MavenReference} of the plugin you'd like to execute
	 * @param command
	 *            the command you need to execute
	 * @param properties
	 *            {@link Properties} containing the parameters to be processed
	 *            by Maven
	 * @return the {@link InvocationRequest} to be executed
	 */
	public static InvocationRequest generateCommand(
			final MavenReference reference, final String command,
			final Properties properties) {
		return generateCommand(Files.createTempDir(), reference.groupId,
				reference.artifactId, reference.version, command, properties);
	}

	/**
	 * Generates an {@link InvocationRequest} executing a particular command
	 * from a {@link MavenReference} in a particular path with parameters
	 * 
	 * @param path
	 *            the path where the command should be executed
	 * @param reference
	 *            the {@link MavenReference} of the plugin you'd like to execute
	 * @param command
	 *            the command you need to execute
	 * @param properties
	 *            {@link Properties} containing the parameters to be processed
	 *            by Maven
	 * @return the {@link InvocationRequest} to be executed
	 */
	public static InvocationRequest generateCommand(final File path,
			final MavenReference reference, final String command,
			final Properties properties) {
		return generateCommand(path, reference.groupId, reference.artifactId,
				reference.version, command, properties);
	}

	/**
	 * Generates an {@link InvocationRequest} executing a particular command
	 * from a plugin specified by its coordinates
	 * 
	 * @param groupId
	 *            plugin's groupId
	 * @param artifactId
	 *            plugin's artifactId
	 * @param version
	 *            plugin's version
	 * @param command
	 *            the command to be executed
	 * @return the {@link InvocationRequest} to be executed
	 */
	public static InvocationRequest generateCommand(final String groupId,
			final String artifactId, final String version, final String command) {
		return generateCommand(Files.createTempDir(), groupId, artifactId,
				version, command, null);
	}

	/**
	 * Generates an {@link InvocationRequest} executing a particular command
	 * from a plugin specified by its coordinates with parameters
	 * 
	 * @param groupId
	 *            plugin's groupId
	 * @param artifactId
	 *            plugin's artifactId
	 * @param version
	 *            plugin's version
	 * @param command
	 *            the command to be executed
	 * @param properties
	 *            {@link Properties} containing the parameters to be computed by
	 *            Maven
	 * @return the {@link InvocationRequest} to be executed
	 */
	public static InvocationRequest generateCommand(final String groupId,
			final String artifactId, final String version,
			final String command, final Properties properties) {
		return generateCommand(Files.createTempDir(), groupId, artifactId,
				version, command, properties);
	}

	/**
	 * Generates an {@link InvocationRequest} executing a particular command
	 * from a plugin specified by its coordinates in a specific path
	 * 
	 * @param path
	 *            the path where the command should be executed
	 * @param groupId
	 *            plugin's groupId
	 * @param artifactId
	 *            plugin's artifactId
	 * @param version
	 *            plugin's version
	 * @param command
	 *            the command to be executed
	 * @return the {@link InvocationRequest} to be executed
	 */
	public static InvocationRequest generateCommand(final File path,
			final String groupId, final String artifactId,
			final String version, final String command) {
		return generateCommand(path, groupId, artifactId, version, command,
				null);
	}

	/**
	 * Generates an {@link InvocationRequest} executing a particular command
	 * from a plugin specified by its coordinates in a specific path with
	 * parameters
	 * 
	 * @param path
	 *            the path where the command should be executed
	 * @param groupId
	 *            plugin's groupId
	 * @param artifactId
	 *            plugin's artifactId
	 * @param version
	 *            plugin's version
	 * @param command
	 *            the command to be executed
	 * @param properties
	 *            {@link Properties} containing the parameters to be computed by
	 *            Maven
	 * @return the {@link InvocationRequest} to be executed
	 */
	public static InvocationRequest generateCommand(final File path,
			final String groupId, final String artifactId,
			final String version, final String command,
			final Properties properties) {
		return generateCommand(
				path,
				Collections.singletonList(groupId + ":" + artifactId + ":"
						+ version + ":" + command), properties);
	}

	/**
	 * Generates an {@link InvocationRequest} executing various goals on a
	 * specific path using parameters
	 * 
	 * @param path
	 *            the path where the command should be executed
	 * @param goals
	 *            all the goals to be executed
	 * @param properties
	 *            {@link Properties} containing the parameters to be computed by
	 *            Maven
	 * @return the {@link InvocationRequest} to be executed
	 */
	public static InvocationRequest generateCommand(final File path,
			final List<String> goals, final Properties properties) {
		final InvocationRequest request = new DefaultInvocationRequest();
		request.setBaseDirectory(path);
		request.setGoals(goals);
		if (properties != null)
			request.setProperties(properties);
		return request;
	}

}
