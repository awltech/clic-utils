package com.worldline.clic.utils.mvn;

import static com.worldline.clic.utils.Messages.POM_TEMPLATE;

import java.io.File;
import java.io.IOException;

import com.google.common.io.Files;

/**
 * This class aims at providing helper methods in order to generate pom.xml
 * files allowing to invoke Maven commands.
 * 
 * @author aneveux
 * @version 1.0
 */
public class MavenPom {

	/**
	 * Generates a Maven pom.xml with specified {@link MavenReference} as a
	 * parent, in a temporary directory
	 * 
	 * @param reference
	 *            {@link MavenReference} to be used as a parent
	 * @param artifactId
	 *            to be used by the generated pom
	 * @return the generated {@link File}
	 * @throws IOException
	 *             if anything went wrong
	 */
	public static File generate(final MavenReference reference,
			final String artifactId) throws IOException {
		return generate(Files.createTempDir(), reference.groupId,
				reference.artifactId, reference.version, artifactId);
	}

	/**
	 * Generates a Maven pom.xml with specified {@link MavenReference} as a
	 * parent, in the specified path
	 * 
	 * @param path
	 *            the path where the generated file should be located
	 * @param reference
	 *            {@link MavenReference} to be used as a parent
	 * @param artifactId
	 *            to be used by the generated pom
	 * @return the generated {@link File}
	 * @throws IOException
	 *             if anything went wrong
	 */
	public static File generate(final File path,
			final MavenReference reference, final String artifactId)
			throws IOException {
		return generate(path, reference.groupId, reference.artifactId,
				reference.version, artifactId);
	}

	/**
	 * Generates a Maven pom.xml with specified parent coordinates
	 * 
	 * @param parentGroupId
	 *            parent's groupId
	 * @param parentArtifactId
	 *            parent's artifactId
	 * @param parentVersion
	 *            parent's version
	 * @param artifactId
	 *            to be used by the generated pom
	 * @return the generated {@link File}
	 * @throws IOException
	 *             if anything went wrong
	 */
	public static File generate(final String parentGroupId,
			final String parentArtifactId, final String parentVersion,
			final String artifactId) throws IOException {
		return generate(Files.createTempDir(), parentGroupId, parentArtifactId,
				parentVersion, artifactId);
	}

	/**
	 * Generates a Maven pom.xml with specified parent coordinates in a
	 * specified path
	 * 
	 * @param path
	 *            the path where the generated file should be located
	 * @param parentGroupId
	 *            parent's groupId
	 * @param parentArtifactId
	 *            parent's artifactId
	 * @param parentVersion
	 *            parent's version
	 * @param artifactId
	 *            to be used by the generated pom
	 * @return the generated {@link File}
	 * @throws IOException
	 *             if anything went wrong
	 */
	public static File generate(final File path, final String parentGroupId,
			final String parentArtifactId, final String parentVersion,
			final String artifactId) throws IOException {
		final File pom = new File(path.getAbsolutePath() + "/pom.xml");
		final String pomContent = POM_TEMPLATE.value(parentGroupId,
				parentArtifactId, parentVersion, artifactId);

		Files.newOutputStreamSupplier(pom).getOutput()
				.write(pomContent.getBytes());

		return pom;
	}

}
