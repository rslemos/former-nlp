package br.eti.rslemos.nlp.maven;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Formatter;
import java.util.Map;
import java.util.TreeMap;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;
import org.codehaus.plexus.util.DirectoryScanner;

import br.eti.rslemos.ad.ADCorpus;
import br.eti.rslemos.ad.Analysis;
import br.eti.rslemos.ad.Extract;
import br.eti.rslemos.ad.Node;
import br.eti.rslemos.ad.Sentence;
import br.eti.rslemos.ad.SentenceSet;

/**
 * @goal stats
 */
public class CorpusStatisticsCollectorMojo extends AbstractMojo {

	/**
	 * Directory containing the filtered resource files
	 *
	 * @parameter expression="${project.build.outputDirectory}"
	 * @required
	 */
	private File classesDirectory;
	
	/**
	 * Base directory where all statistics are written to.
	 *
	 * @parameter expression="${project.build.directory}/corpus-stats"
	 * @required
	 */
	private File statsDirectory;
	
	public void execute() throws MojoExecutionException, MojoFailureException {
		DirectoryScanner directoryScanner = new DirectoryScanner();
		directoryScanner.setBasedir(classesDirectory);
		directoryScanner.addDefaultExcludes();
		directoryScanner.scan();
		
		for (String fileName : directoryScanner.getIncludedFiles()) {
			File srcFile = new File(classesDirectory, fileName);
			try {
				getLog().info("Processing " + srcFile);
				
				ADCorpus corpus = new ADCorpus(new FileReader(srcFile));
				int numExtracts = 0;
				int numSentenceSets = 0;
				int numSentences = 0;
				int numAnalises = 0;
				for (Extract extract : corpus) {
					numExtracts++;
					for (SentenceSet sentenceSet : extract) {
						numSentenceSets++;
						for (Sentence sentence : sentenceSet) {
							numSentences++;
							for (Analysis analysis : sentence) {
								numAnalises++;
								for (Node node : analysis) {
									processNode(node);
								}
							}
						}
					}
				}
				
				getLog().info("Extracts: " + numExtracts);
				getLog().info("Sentence sets: " + numSentenceSets);
				getLog().info("Sentences: " + numSentences);
				getLog().info("Analises: " + numAnalises);
				getLog().info("Functions: " + functions.size());
				printMap(getLog(), "    ", functions);
				getLog().info("Forms: " + forms.size());
				printMap(getLog(), "    ", forms);
				
				//File dstFile = new File(statsDirectory, fileName + ".xml");
			} catch (FileNotFoundException e) {
				throw new MojoExecutionException("File not found", e);
			}
			
		}
	}

	private static void printMap(Log log, String indent, Map<String, Integer> map) {
		int maxkey = 0;
		for (String key : map.keySet()) {
			if (key.length() > maxkey)
				maxkey = key.length();
		}
		maxkey += 2;
		
		StringBuilder builder = new StringBuilder();
		Formatter formatter = new Formatter(builder);
		
		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			formatter.format(indent + "%-" + maxkey + "s : %5d", entry.getKey(), entry.getValue());
			log.info(builder.toString());
			builder.setLength(0);
		}
	}

	private Map<String, Integer> functions = new TreeMap<String, Integer>();
	private Map<String, Integer> forms = new TreeMap<String, Integer>();
	
	private void processNode(Node node) {
		String function = node.getFunction();
		String form = node.getForm();
		
		increment(functions, function);
		increment(forms, form);
		for (Node child : node) {
			processNode(child);
		}
	}

	private static void increment(Map<String, Integer> map, String key) {
		if (key == null)
			key = "";
		
		Integer value = map.get(key);
		if (value == null)
			value = 0;
		
		value++;
		map.put(key, value);
	}

}
