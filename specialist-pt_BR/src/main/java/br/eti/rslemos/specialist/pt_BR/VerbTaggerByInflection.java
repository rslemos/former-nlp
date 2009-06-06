package br.eti.rslemos.specialist.pt_BR;

import br.eti.rslemos.brill.AbstractTokenTagger;
import br.eti.rslemos.brill.Token;

public class VerbTaggerByInflection extends AbstractTokenTagger {

	@Override
	public void tag(Token token) {
		String word = token.getWord().toLowerCase();

		for (VerbMatcher matcher : matchers) {
			if (matcher.matches(word)) {
				token.setTag(matcher.getTag());
				break;
			}
		}
	}

	private static interface VerbMatcher {
		boolean matches(String word);
		String getTag();
	}
	
	private static class SuffixMatcher implements VerbMatcher {

		private final String suffix;
		private final String tag;

		protected SuffixMatcher(String suffix, String tag) {
			this.suffix = suffix;
			this.tag = tag;
		}

		public boolean matches(String word) {
			return word.endsWith(suffix) && !word.equals(suffix);
		}
		
		public String getTag() {
			return tag;
		}
	}

	private static class StringMatcher implements VerbMatcher {

		private final String word;
		private final String tag;

		protected StringMatcher(String word, String tag) {
			this.word = word;
			this.tag = tag;
		}

		public boolean matches(String word) {
			return this.word.equals(word);
		}
		
		public String getTag() {
			return tag;
		}
	}

	private static final VerbMatcher[] matchers = {
		// verbo ser
		new StringMatcher("sendo", "v-ger"),
		new StringMatcher("sido", "v-pcp"),

		new StringMatcher("sou", "v-fin"),
		new StringMatcher("és", "v-fin"),
		new StringMatcher("é", "v-fin"),
		new StringMatcher("somos", "v-fin"),
		new StringMatcher("sois", "v-fin"),
		new StringMatcher("são", "v-fin"),
		new StringMatcher("fui", "v-fin"),
		new StringMatcher("foste", "v-fin"),
		new StringMatcher("foi", "v-fin"),
		new StringMatcher("fomos", "v-fin"),
		new StringMatcher("fostes", "v-fin"),
		new StringMatcher("foram", "v-fin"),
		new StringMatcher("era", "v-fin"),
		new StringMatcher("eras", "v-fin"),
		new StringMatcher("era", "v-fin"),
		new StringMatcher("éramos", "v-fin"),
		new StringMatcher("éreis", "v-fin"),
		new StringMatcher("eram", "v-fin"),
		new StringMatcher("fora", "v-fin"),
		new StringMatcher("foras", "v-fin"),
		new StringMatcher("fora", "v-fin"),
		new StringMatcher("fôramos", "v-fin"),
		new StringMatcher("fôreis", "v-fin"),
		new StringMatcher("foram", "v-fin"),
		new StringMatcher("serei", "v-fin"),
		new StringMatcher("sereis", "v-fin"),
		new StringMatcher("será", "v-fin"),
		new StringMatcher("seremos", "v-fin"),
		new StringMatcher("sereis", "v-fin"),
		new StringMatcher("serão", "v-fin"),
		new StringMatcher("seria", "v-fin"),
		new StringMatcher("serias", "v-fin"),
		new StringMatcher("seria", "v-fin"),
		new StringMatcher("seríamos", "v-fin"),
		new StringMatcher("seríeis", "v-fin"),
		new StringMatcher("seriam", "v-fin"),
		new StringMatcher("seja", "v-fin"),
		new StringMatcher("sejas", "v-fin"),
		new StringMatcher("seja", "v-fin"),
		new StringMatcher("sejamos", "v-fin"),
		new StringMatcher("sejais", "v-fin"),
		new StringMatcher("sejam", "v-fin"),
		new StringMatcher("fosse", "v-fin"),
		new StringMatcher("fosses", "v-fin"),
		new StringMatcher("fosse", "v-fin"),
		new StringMatcher("fôssemos", "v-fin"),
		new StringMatcher("fôsseis", "v-fin"),
		new StringMatcher("fossem", "v-fin"),
		new StringMatcher("for", "v-fin"),
		new StringMatcher("fores", "v-fin"),
		new StringMatcher("for", "v-fin"),
		new StringMatcher("formos", "v-fin"),
		new StringMatcher("fordes", "v-fin"),
		new StringMatcher("forem", "v-fin"),
		new StringMatcher("sê", "v-fin"),
		new StringMatcher("seja", "v-fin"),
		new StringMatcher("sejamos", "v-fin"),
		new StringMatcher("sede", "v-fin"),
		new StringMatcher("sejam", "v-fin"),
		new StringMatcher("sejas", "v-fin"),
		new StringMatcher("seja", "v-fin"),
		new StringMatcher("sejamos", "v-fin"),
		new StringMatcher("sejais", "v-fin"),
		new StringMatcher("sejam", "v-fin"),

		new StringMatcher("ser", "v-inf"),
		new StringMatcher("seres", "v-inf"),
		new StringMatcher("ser", "v-inf"),
		new StringMatcher("sermos", "v-inf"),
		new StringMatcher("serdes", "v-inf"),
		new StringMatcher("serem", "v-inf"),

		// verbos regulares
		new SuffixMatcher("ando", "v-ger"),
		new SuffixMatcher("endo", "v-ger"),
		new SuffixMatcher("indo", "v-ger"),
	
		new SuffixMatcher("ado", "v-pcp"),
		new SuffixMatcher("ido", "v-pcp"),
	
		new SuffixMatcher("íssemos", "v-fin"),
		new SuffixMatcher("iríamos", "v-fin"),
		new SuffixMatcher("êssemos", "v-fin"),
		new SuffixMatcher("eríamos", "v-fin"),
		new SuffixMatcher("ássemos", "v-fin"),
		new SuffixMatcher("aríamos", "v-fin"),
		new SuffixMatcher("ísseis", "v-fin"),
		new SuffixMatcher("iríeis", "v-fin"),
		new SuffixMatcher("iremos", "v-fin"),
		new SuffixMatcher("íramos", "v-fin"),
		new SuffixMatcher("êsseis", "v-fin"),
		new SuffixMatcher("eríeis", "v-fin"),
		new SuffixMatcher("eremos", "v-fin"),
		new SuffixMatcher("êramos", "v-fin"),
		new SuffixMatcher("ávamos", "v-fin"),
		new SuffixMatcher("ásseis", "v-fin"),
		new SuffixMatcher("aríeis", "v-fin"),
		new SuffixMatcher("aremos", "v-fin"),
		new SuffixMatcher("áramos", "v-fin"),
		new SuffixMatcher("istes", "v-fin"),
		new SuffixMatcher("isses", "v-fin"),
		new SuffixMatcher("issem", "v-fin"),
		new SuffixMatcher("irmos", "v-fin"),
		new SuffixMatcher("irias", "v-fin"),
		new SuffixMatcher("iriam", "v-fin"),
		new SuffixMatcher("íreis", "v-fin"),
		new SuffixMatcher("ireis", "v-fin"),
		new SuffixMatcher("irdes", "v-fin"),
		new SuffixMatcher("íamos", "v-fin"),
		new SuffixMatcher("estes", "v-fin"),
		new SuffixMatcher("esses", "v-fin"),
		new SuffixMatcher("essem", "v-fin"),
		new SuffixMatcher("ermos", "v-fin"),
		new SuffixMatcher("erias", "v-fin"),
		new SuffixMatcher("eriam", "v-fin"),
		new SuffixMatcher("êreis", "v-fin"),
		new SuffixMatcher("ereis", "v-fin"),
		new SuffixMatcher("erdes", "v-fin"),
		new SuffixMatcher("áveis", "v-fin"),
		new SuffixMatcher("astes", "v-fin"),
		new SuffixMatcher("asses", "v-fin"),
		new SuffixMatcher("assem", "v-fin"),
		new SuffixMatcher("armos", "v-fin"),
		new SuffixMatcher("arias", "v-fin"),
		new SuffixMatcher("ariam", "v-fin"),
		new SuffixMatcher("áreis", "v-fin"),
		new SuffixMatcher("areis", "v-fin"),
		new SuffixMatcher("ardes", "v-fin"),
		new SuffixMatcher("iste", "v-fin"),
		new SuffixMatcher("isse", "v-fin"),
		new SuffixMatcher("iria", "v-fin"),
		new SuffixMatcher("ires", "v-fin"),
		new SuffixMatcher("irem", "v-fin"),
		new SuffixMatcher("irei", "v-fin"),
		new SuffixMatcher("iras", "v-fin"),
		new SuffixMatcher("irás", "v-fin"),
		new SuffixMatcher("irão", "v-fin"),
		new SuffixMatcher("iram", "v-fin"),
		new SuffixMatcher("imos", "v-fin"),
		new SuffixMatcher("íeis", "v-fin"),
		new SuffixMatcher("este", "v-fin"),
		new SuffixMatcher("esse", "v-fin"),
		new SuffixMatcher("eria", "v-fin"),
		new SuffixMatcher("eres", "v-fin"),
		new SuffixMatcher("erem", "v-fin"),
		new SuffixMatcher("erei", "v-fin"),
		new SuffixMatcher("eras", "v-fin"),
		new SuffixMatcher("erás", "v-fin"),
		new SuffixMatcher("erão", "v-fin"),
		new SuffixMatcher("eram", "v-fin"),
		new SuffixMatcher("emos", "v-fin"),
		new SuffixMatcher("avas", "v-fin"),
		new SuffixMatcher("avam", "v-fin"),
		new SuffixMatcher("aste", "v-fin"),
		new SuffixMatcher("asse", "v-fin"),
		new SuffixMatcher("aria", "v-fin"),
		new SuffixMatcher("ares", "v-fin"),
		new SuffixMatcher("arem", "v-fin"),
		new SuffixMatcher("arei", "v-fin"),
		new SuffixMatcher("aras", "v-fin"),
		new SuffixMatcher("arás", "v-fin"),
		new SuffixMatcher("arão", "v-fin"),
		new SuffixMatcher("aram", "v-fin"),
		new SuffixMatcher("amos", "v-fin"),
		new SuffixMatcher("ira", "v-fin"),
		new SuffixMatcher("irá", "v-fin"),
		new SuffixMatcher("ias", "v-fin"),
		new SuffixMatcher("iam", "v-fin"),
		new SuffixMatcher("era", "v-fin"),
		new SuffixMatcher("erá", "v-fin"),
		new SuffixMatcher("eis", "v-fin"),
		new SuffixMatcher("ava", "v-fin"),
		new SuffixMatcher("ara", "v-fin"),
		new SuffixMatcher("ará", "v-fin"),
		new SuffixMatcher("ais", "v-fin"),
		new SuffixMatcher("ou", "v-fin"),
		new SuffixMatcher("iu", "v-fin"),
		new SuffixMatcher("is", "v-fin"),
//		new SuffixMatcher("ir", "v-fin"),
		new SuffixMatcher("ia", "v-fin"),
		new SuffixMatcher("eu", "v-fin"),
		new SuffixMatcher("es", "v-fin"),
//		new SuffixMatcher("er", "v-fin"),
		new SuffixMatcher("em", "v-fin"),
		new SuffixMatcher("ei", "v-fin"),
		new SuffixMatcher("as", "v-fin"),
//		new SuffixMatcher("ar", "v-fin"),
		new SuffixMatcher("am", "v-fin"),
		new SuffixMatcher("ai", "v-fin"),
//		new SuffixMatcher("o", "v-fin"),
//		new SuffixMatcher("i", "v-fin"),
//		new SuffixMatcher("e", "v-fin"),
//		new SuffixMatcher("a", "v-fin"),
	
		new SuffixMatcher("ar", "v-inf"),
		new SuffixMatcher("er", "v-inf"),
		new SuffixMatcher("ir", "v-inf"),
	
	};
}
