package br.eti.rslemos.brill.stats;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.eti.rslemos.brill.CompositeTagger;
import br.eti.rslemos.brill.ConstantTokenTagger;
import br.eti.rslemos.brill.DefaultToken;
import br.eti.rslemos.brill.LookupTokenTagger;
import br.eti.rslemos.brill.Tagger;
import br.eti.rslemos.brill.Token;

public class Test {

	public static void main(String[] args) {
		new Test().test();
	}

	public void test() {
		test0();
	}

	private void test0() {
		Map lexicon = new HashMap();
		Tagger tagger1 = new LookupTokenTagger(lexicon);
		Tagger tagger0 = new ConstantTokenTagger("tag");
		
		Token t = new DefaultToken("word");
		List taggers = Arrays.asList(new Tagger[] {tagger1, tagger0});
		
		List sentence = Collections.singletonList(t);
		new CompositeTagger(taggers).tagSentence(sentence);
	}
}
