/*******************************************************************************
 * BEGIN COPYRIGHT NOTICE
 * 
 * This file is part of program "Natural Language Processing"
 * Copyright 2011  Rodrigo Lemos
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * END COPYRIGHT NOTICE
 ******************************************************************************/
package br.eti.rslemos.tiger.jaxb;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.xml.namespace.QName;

import br.eti.rslemos.tiger.Annotation;
import br.eti.rslemos.tiger.Feature;
import br.eti.rslemos.tiger.Head;
import br.eti.rslemos.tiger.Meta;
import br.eti.rslemos.tiger.Sentence;
import br.eti.rslemos.tiger.Terminal;

@SuppressWarnings("unchecked")
public class Test {
	private static final QName POS = new QName("pos");
	private static final QName WORD = new QName("word");

	public static void main(String[] args) throws Throwable {
		InputStream stream = new URL("file:///home/rslemos/work/Bosque_CF_8.0.TigerXML.xml").openStream();

		FastCorpus corpus = new FastCorpus(stream);

		System.out.printf("CORPUS id = %s, version = %s\n\n", corpus.getId(), corpus.getVersion());

		Head head = corpus.getHead();

		if (head != null) {
			Meta meta = head.getMeta();
			System.out.printf("METADATA\n" +
					"name = %s\n" +
					"author = %s\n" +
					"description = %s\n" +
					"date = %s\n" +
					"history = %s\n" +
					"format = %s\n\n",
					meta.getName(), meta.getAuthor(), meta.getDescription(),
					meta.getDate(), meta.getHistory(), meta.getFormat());


			Annotation annotation = head.getAnnotation();
			System.out.printf("ANNOTATION:\n");
			for (Feature feature : annotation.getFeatures()) {
				System.out.printf("FEATURE\n" +
						"name = %s\n" +
						"domain = %s\n" +
						"values = %s\n\n",
						feature.getName(),
						feature.getDomain(),
						feature.getValues());
			}
		} else
			System.out.printf("No head\n");

		final TreeMap<String, Map<String, Integer>> collect = new TreeMap<String, Map<String, Integer>>();

		Iterator<Sentence> it = (Iterator<Sentence>) corpus.getBody().sentences();
		while(it.hasNext()) {
			Sentence sentence = it.next();
			List<? extends Terminal> terminals = sentence.getGraph().getTerminals();
			for (Terminal terminal : terminals) {
				Map<QName, String> features = terminal.getFeatures();

				String word = features.get(WORD);
				if (word != null) {
					String pos = features.get(POS);

					Map<String, Integer> w = collect.get(word);
					if (w == null) {
						w = new HashMap<String, Integer>();
						collect.put(word, w);
					}

					Integer count = w.get(pos);
					if (count == null)
						count = Integer.valueOf(0);

					w.put(pos, ++count);
				}
			}
		}

		List<String> words = new ArrayList<String>(collect.keySet());
		Collections.sort(words, new Comparator<String>() {

			public int compare(String w1, String w2) {
				return count(collect.get(w2)) - count(collect.get(w1));
			}

			private int count(Map<String, Integer> c1) {
				return sum(c1.values());
			}

			private int sum(Collection<Integer> values) {
				int sum = 0;

				for (Integer v : values)
					sum += v;

				return sum;
			}
		});

		File out = new File("/home/rslemos/work/Linguística Computacional/contratos/lexicon");
		FileOutputStream fos = new FileOutputStream(out);
		OutputStreamWriter osw = new OutputStreamWriter(fos, Charset.forName("UTF-8"));
		PrintWriter pw = new PrintWriter(osw);
		try {
			for (String word : words) {
				pw.printf("%s", word);

				Map<String, Integer> map = collect.get(word);

				Entry<String, Integer>[] w = map.entrySet().toArray(new Entry[map.size()]);
				Arrays.sort(w, new Comparator<Entry<String, Integer>>() {

					public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
						return o2.getValue() - o1.getValue();
					}
				});

				for (Entry<String, Integer> pos : w) {
					pw.printf(" %s", pos.getKey().toUpperCase());
				}

				pw.printf("\n");
			}
		} finally {
			pw.close();
			osw.close();
			fos.close();
		}
	}
}
