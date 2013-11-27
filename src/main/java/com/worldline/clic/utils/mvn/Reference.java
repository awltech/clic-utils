package com.worldline.clic.utils.mvn;

public class Reference {
	public Reference(final String ref) {
		groupId = ref.split(":")[0];
		artifactId = ref.split(":")[1];
		version = ref.split(":")[2];
	}

	public String groupId;
	public String artifactId;
	public String version;

	@Override
	public String toString() {
		return groupId + ":" + artifactId + ":" + version;
	}
}
