package com.worldline.clic.utils.mvn;

import static com.worldline.clic.utils.Messages.*;

import java.io.File;
import java.io.IOException;

import com.google.common.io.Files;

public class Pom {

	public static File generate(final Reference reference,
			final String artifactId) throws IOException {
		return generate(Files.createTempDir(), reference.groupId,
				reference.artifactId, reference.version, artifactId);
	}

	public static File generate(final File path, final Reference reference,
			final String artifactId) throws IOException {
		return generate(path, reference.groupId, reference.artifactId,
				reference.version, artifactId);
	}

	public static File generate(final String parentGroupId,
			final String parentArtifactId, final String parentVersion,
			final String artifactId) throws IOException {
		return generate(Files.createTempDir(), parentGroupId, parentArtifactId,
				parentVersion, artifactId);
	}

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
