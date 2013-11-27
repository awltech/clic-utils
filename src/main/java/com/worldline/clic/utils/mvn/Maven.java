package com.worldline.clic.utils.mvn;

import static com.worldline.clic.utils.Messages.GENERATE_POM;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import joptsimple.util.KeyValuePair;

import org.apache.maven.shared.invoker.DefaultInvoker;
import org.apache.maven.shared.invoker.InvocationOutputHandler;
import org.apache.maven.shared.invoker.InvocationRequest;
import org.apache.maven.shared.invoker.Invoker;
import org.apache.maven.shared.invoker.MavenInvocationException;

public class Maven {

	public void executeCommandLine(final OptionSet options,
			final OptionSpec<KeyValuePair> mavenParameters,
			final OptionSpec<String> mavenReference,
			final OptionSpec<String> mavenCommand) throws IOException,
			MavenInvocationException {
		final Reference reference = new Reference(
				options.valueOf(mavenReference));

		InvocationRequest request;

		if (options.has(GENERATE_POM.value()))
			request = Command.generatePomCommand(
					Pom.generate(reference, "tmp"), options
							.valueOf(mavenCommand), CommandLine
							.computeMavenParameters(options, mavenParameters));
		else
			request = Command.generateCommand(reference, options
					.valueOf(mavenCommand), CommandLine.computeMavenParameters(
					options, mavenParameters));
		execute(request);
	}

	public void execute(final InvocationRequest request)
			throws MavenInvocationException {
		final Invoker invoker = new DefaultInvoker();
		invoker.execute(request);
	}

	public List<String> execute(final InvocationRequest request,
			final String... outputPatterns) throws MavenInvocationException {
		final PatternOutputHandler handler = new PatternOutputHandler(
				outputPatterns);
		execute(request, handler);
		return handler.outputs;
	}

	public void execute(final InvocationRequest request,
			final InvocationOutputHandler outputHandler)
			throws MavenInvocationException {
		final Invoker invoker = new DefaultInvoker();
		if (outputHandler != null)
			invoker.setOutputHandler(outputHandler);
		invoker.execute(request);
	}

	static class PatternOutputHandler implements InvocationOutputHandler {

		String[] patterns;
		List<String> outputs;

		public PatternOutputHandler(final String... patterns) {
			this.patterns = patterns;
			outputs = new ArrayList<String>();
		}

		@Override
		public void consumeLine(final String line) {
			for (final String pattern : patterns)
				if (line.matches(pattern) || line.contains(pattern))
					outputs.add(line);
		}
	}

}
