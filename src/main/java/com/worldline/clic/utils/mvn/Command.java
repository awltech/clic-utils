package com.worldline.clic.utils.mvn;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.apache.maven.shared.invoker.DefaultInvocationRequest;
import org.apache.maven.shared.invoker.InvocationRequest;

import com.google.common.io.Files;

public class Command {

	public static InvocationRequest generatePomCommand(final File pom,
			final List<String> goals) {
		return generatePomCommand(pom, goals, null);
	}

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

	public static InvocationRequest generatePomCommand(final File pom,
			final String goal) {
		return generatePomCommand(pom, Collections.singletonList(goal), null);
	}

	public static InvocationRequest generatePomCommand(final File pom,
			final String goal, final Properties properties) {
		return generatePomCommand(pom, Collections.singletonList(goal),
				properties);
	}

	public static InvocationRequest generateCommand(final Reference reference,
			final String command) {
		return generateCommand(Files.createTempDir(), reference.groupId,
				reference.artifactId, reference.version, command, null);
	}

	public static InvocationRequest generateCommand(final File path,
			final Reference reference, final String command) {
		return generateCommand(path, reference.groupId, reference.artifactId,
				reference.version, command, null);
	}

	public static InvocationRequest generateCommand(final Reference reference,
			final String command, final Properties properties) {
		return generateCommand(Files.createTempDir(), reference.groupId,
				reference.artifactId, reference.version, command, properties);
	}

	public static InvocationRequest generateCommand(final File path,
			final Reference reference, final String command,
			final Properties properties) {
		return generateCommand(path, reference.groupId, reference.artifactId,
				reference.version, command, properties);
	}

	public static InvocationRequest generateCommand(final String groupId,
			final String artifactId, final String version, final String command) {
		return generateCommand(Files.createTempDir(), groupId, artifactId,
				version, command, null);
	}

	public static InvocationRequest generateCommand(final String groupId,
			final String artifactId, final String version,
			final String command, final Properties properties) {
		return generateCommand(Files.createTempDir(), groupId, artifactId,
				version, command, properties);
	}

	public static InvocationRequest generateCommand(final File path,
			final String groupId, final String artifactId,
			final String version, final String command) {
		return generateCommand(path, groupId, artifactId, version, command,
				null);
	}

	public static InvocationRequest generateCommand(final File path,
			final String groupId, final String artifactId,
			final String version, final String command,
			final Properties properties) {
		return generateCommand(
				path,
				Collections.singletonList(groupId + ":" + artifactId + ":"
						+ version + ":" + command), properties);
	}

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
