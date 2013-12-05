package com.worldline.clic.utils.mvn;

import java.util.List;

/**
 * Wraps the results from a call : stdout line and stderr lines.
 * 
 * @author M Daviot
 */
public class StandardOutputError {
	public final List<String> stdout;
	public final List<String> stderr;
	public final int exitCode;

	public StandardOutputError(List<String> stdout, List<String> stderr,
			int exitCode) {
		this.stdout = stdout;
		this.stderr = stderr;
		this.exitCode = exitCode;
	}
}
