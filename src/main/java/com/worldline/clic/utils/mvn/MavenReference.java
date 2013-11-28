package com.worldline.clic.utils.mvn;

/**
 * This class aims at representing a Maven Reference using the following
 * representation: <code>groupId:artifactId:version</code>. It allows to create
 * a {@link MavenReference} from this pattern, and to generate it from an
 * existing {@link MavenReference}
 * 
 * @author aneveux
 * @version 1.0
 */
public class MavenReference {
	/**
	 * Creates a MavenReference from a String defined using the format
	 * <code>groupId:artifactId:version</code>
	 * 
	 * @param ref
	 *            <code>groupId:artifactId:version</code> description of a Maven
	 *            refernce
	 */
	public MavenReference(final String ref) {
		groupId = ref.split(":")[0];
		artifactId = ref.split(":")[1];
		version = ref.split(":")[2];
	}

	/**
	 * Maven groupId
	 */
	public String groupId;
	/**
	 * Maven artifactId
	 */
	public String artifactId;
	/**
	 * Maven version
	 */
	public String version;

	/**
	 * Allows to format the reference using the format
	 * <code>groupId:artifactId:version</code>
	 */
	@Override
	public String toString() {
		return groupId + ":" + artifactId + ":" + version;
	}
}
