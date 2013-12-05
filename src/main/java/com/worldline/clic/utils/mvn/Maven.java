package com.worldline.clic.utils.mvn;

import static com.worldline.clic.utils.Messages.GENERATE_POM;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import joptsimple.util.KeyValuePair;

import org.apache.maven.shared.invoker.DefaultInvoker;
import org.apache.maven.shared.invoker.InvocationOutputHandler;
import org.apache.maven.shared.invoker.InvocationRequest;
import org.apache.maven.shared.invoker.InvocationResult;
import org.apache.maven.shared.invoker.Invoker;
import org.apache.maven.shared.invoker.MavenInvocationException;

/**
 * This class provides helper methods allowing to execute Maven commands. It can
 * both deal with CLiC command lines computed from jopt-simple, and
 * {@link Invoker} requests using Maven shared library.
 * 
 * Various methods are provided allowing to execute simply a command, or to
 * interact with the outputs and get results from a Maven execution.
 * 
 * @author aneveux
 * @version 1.0
 */
public class Maven {

	private Maven() {
	} // prevents instantation

	/**
	 * Simple Maven execution for a Claudia plugin.
	 * 
	 * @param reference
	 *            group:artifact:version of the plugin
	 * @param goal
	 * @param arguments
	 *            properties to pass -D to mvn
	 * @param outputPatterns
	 *            to filter the stdout of the mvn invocation
	 * @return stdout lines matching one of the provided patterns
	 * @throws IOException
	 *             if a pom.xml needs to be generated and there's an error
	 *             during that process
	 * @throws MavenInvocationException
	 *             if anything's wrong while executing Maven
	 */
	public static List<String> execute(String reference, String goal,
			Properties arguments, String... outputPatterns)
			throws MavenInvocationException, IOException {
		InvocationRequest pomCommand = MavenCommand.generatePomAndCommand(
				new MavenReference(reference), "execution-from-java",
				Collections.singletonList(goal), arguments);
		return Maven.execute(pomCommand, outputPatterns);
	}

	/**
	 * Executes a Maven command line which has been computed through CLiC using
	 * jopt-simple. You can use the result of a
	 * {@link OptionParser#parse(String...)} which gives the required
	 * {@link OptionSet}, and get the proper configuration from
	 * {@link MavenClicCommandLine#configureParser(OptionParser)}.
	 * 
	 * @param options
	 *            jopt-simple computed options from
	 *            {@link OptionParser#parse(String...)} using the configuration
	 *            provided by
	 *            {@link MavenClicCommandLine#configureParser(OptionParser)}
	 * @param mavenParameters
	 *            {@link OptionSpec} coming from the configuration using
	 *            {@link MavenClicCommandLine#configureParser(OptionParser)},
	 *            can be retrieved in
	 *            {@link MavenClicCommandLine#getMavenParameters()} after
	 *            computing the options
	 * @param mavenReference
	 *            {@link OptionSpec} coming from the configuration using
	 *            {@link MavenClicCommandLine#configureParser(OptionParser)},
	 *            can be retrieved in
	 *            {@link MavenClicCommandLine#getMavenReference()} after
	 *            computing the options
	 * @param mavenCommand
	 *            {@link OptionSpec} coming from the configuration using
	 *            {@link MavenClicCommandLine#configureParser(OptionParser)},
	 *            can be retrieved in
	 *            {@link MavenClicCommandLine#getMavenCommand()} after computing
	 *            the options
	 * @throws IOException
	 *             if a pom.xml needs to be generated and there's an error
	 *             during that process
	 * @throws MavenInvocationException
	 *             if anything's wrong while executing Maven
	 */
	public static void executeCommandLine(final OptionSet options,
			final OptionSpec<KeyValuePair> mavenParameters,
			final OptionSpec<String> mavenReference,
			final OptionSpec<String> mavenCommand) throws IOException,
			MavenInvocationException {
		final MavenReference reference = new MavenReference(
				options.valueOf(mavenReference));

		InvocationRequest request;

		if (options.has(GENERATE_POM.value()))
			request = MavenCommand.generatePomCommand(MavenPom.generate(
					reference, "tmp"), options.valueOf(mavenCommand),
					MavenClicCommandLine.computeMavenParameters(options,
							mavenParameters));
		else
			request = MavenCommand.generateCommand(reference, options
					.valueOf(mavenCommand), MavenClicCommandLine
					.computeMavenParameters(options, mavenParameters));
		execute(request);
	}

	/**
	 * Executes a Maven invocation defined in a {@link InvocationRequest} which
	 * can be created through helpers using {@link MavenCommand}.
	 * 
	 * @param request
	 *            {@link InvocationRequest} to be executed
	 * @return the {@link InvocationResult} computed from the execution
	 * @throws MavenInvocationException
	 *             if anything went wrong while executing Maven
	 */
	public static InvocationResult execute(final InvocationRequest request)
			throws MavenInvocationException {
		final Invoker invoker = new DefaultInvoker();
		final InvocationResult result = invoker.execute(request);
		return result;
	}

	/**
	 * Executes a Maven invocation defined in a {@link InvocationRequest} which
	 * can be created through helpers using {@link MavenCommand}. It allows also
	 * to get results while filtering the output using the provided patterns.
	 * 
	 * @param request
	 *            {@link InvocationRequest} to be executed
	 * @param outputPatterns
	 *            an array of patterns (regex, or plain Strings) you'd like to
	 *            use to filter the results. Filetering will be done using
	 *            {@link PatternOutputHandler}.
	 * @return a {@link List} of all the strings coming from the outputs
	 *         matching or containing the provided patterns
	 * @throws MavenInvocationException
	 *             if anything went wrong while executing Maven
	 */
	public static List<String> execute(final InvocationRequest request,
			final String... outputPatterns) throws MavenInvocationException {
		final PatternOutputHandler handler = new PatternOutputHandler(
				outputPatterns);
		execute(request, handler);
		return handler.outputs;
	}

	/**
	 * Executes a Maven invocation defined in a {@link InvocationRequest} which
	 * can be created through helpers using {@link MavenCommand}. It allows to
	 * provide your own specific implementation of a
	 * {@link InvocationOutputHandler} in order to deal with the execution
	 * outputs.
	 * 
	 * @param request
	 *            {@link InvocationRequest} to be executed
	 * @param outputHandler
	 *            your own implementation of a {@link InvocationOutputHandler}
	 *            in order to interact with the outputs
	 * @throws MavenInvocationException
	 *             if anything went wrong while executing Maven
	 */
	public static void execute(final InvocationRequest request,
			final InvocationOutputHandler outputHandler)
			throws MavenInvocationException {
		final Invoker invoker = new DefaultInvoker();
		if (outputHandler != null) {
			invoker.setOutputHandler(outputHandler);
			invoker.setErrorHandler(outputHandler);
		}
		invoker.execute(request);
	}

	/**
	 * Simple implementation of an {@link InvocationOutputHandler} allowing to
	 * retrieve results based on provided patterns.
	 * 
	 * @author aneveux
	 * @version 1.0
	 * @since 1.0
	 */
	static class PatternOutputHandler implements InvocationOutputHandler {

		/**
		 * Patterns to be used for retrieving results
		 */
		String[] patterns;
		/**
		 * Results of the output scanning
		 */
		List<String> outputs;

		/**
		 * Default constructor
		 * 
		 * @param patterns
		 *            patterns to be used in order to search for the results
		 */
		public PatternOutputHandler(final String... patterns) {
			this.patterns = patterns;
			outputs = new ArrayList<String>();
		}

		/**
		 * Allows to scan the outputs using {@link String#matches(String)} &
		 * {@link String#contains(CharSequence)} for each provided pattern
		 * 
		 * @see InvocationOutputHandler#consumeLine(String)
		 */
		@Override
		public void consumeLine(final String line) {
			for (final String pattern : patterns)
				if (line.matches(pattern) || line.contains(pattern))
					outputs.add(line);
		}
	}

}
