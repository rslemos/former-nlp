package br.eti.rslemos.specialist.pt_BR;

import br.eti.rslemos.brill.AbstractTokenTagger;
import br.eti.rslemos.brill.Token;

public class VerbTaggerByInflection extends AbstractTokenTagger {

	@Override
	public void tag(Token token) {
		String word = token.getWord().toLowerCase();
		
		if (testWordForSuffix(word, "ando", "endo", "indo"))
			token.setTag("v-ger");
		
		if (testWordForSuffix(word, "ado", "ido"))
			token.setTag("v-pcp");
		
		// "íssemos", "iríamos", "êssemos", "eríamos", "ássemos", "aríamos", "ísseis", "iríeis", "iremos", "íramos", "êsseis", "eríeis", "eremos", "êramos", "ávamos", "ásseis", "aríeis", "aremos", "áramos", "istes", "isses", "issem", "irmos", "irias", "iriam", "íreis", "ireis", "irdes", "íamos", "estes", "esses", "essem", "ermos", "erias", "eriam", "êreis", "ereis", "erdes", "áveis", "astes", "asses", "assem", "armos", "arias", "ariam", "áreis", "areis", "ardes", "iste", "isse", "iria", "ires", "irem", "irei", "iras", "irás", "irão", "iram", "imos", "íeis", "este", "esse", "eria", "eres", "erem", "erei", "eras", "erás", "erão", "eram", "emos", "avas", "avam", "aste", "asse", "aria", "ares", "arem", "arei", "aras", "arás", "arão", "aram", "amos", "ira", "irá", "ias", "iam", "era", "erá", "eis", "ava", "ara", "ará", "ais", "ou", "iu", "is", "ir", "ia", "eu", "es", "er", "em", "ei", "as", "ar", "am", "ai", "o", "i", "e", "a"
		
		if (testWordForSuffix(word, "íssemos", "iríamos", "êssemos", "eríamos", "ássemos", "aríamos", "ísseis", "iríeis", "iremos", "íramos", "êsseis", "eríeis", "eremos", "êramos", "ávamos", "ásseis", "aríeis", "aremos", "áramos", "istes", "isses", "issem", "irmos", "irias", "iriam", "íreis", "ireis", "irdes", "íamos", "estes", "esses", "essem", "ermos", "erias", "eriam", "êreis", "ereis", "erdes", "áveis", "astes", "asses", "assem", "armos", "arias", "ariam", "áreis", "areis", "ardes", "iste", "isse", "iria", "ires", "irem", "irei", "iras", "irás", "irão", "iram", "imos", "íeis", "este", "esse", "eria", "eres", "erem", "erei", "eras", "erás", "erão", "eram", "emos", "avas", "avam", "aste", "asse", "aria", "ares", "arem", "arei", "aras", "arás", "arão", "aram", "amos", "ira", "irá", "ias", "iam", "era", "erá", "eis", "ava", "ara", "ará", "ais", "ou", "iu", "is", "ir", "ia", "eu", "es", "er", "em", "ei", "as", "ar", "am", "ai"))
			token.setTag("v-fin");

		if (testWordForSuffix(word, "ar", "er", "ir"))
			token.setTag("v-inf");

	}

	private boolean testWordForSuffix(String word, String... suffixes) {
		for (String suffix : suffixes) {
			if (word.endsWith(suffix) && !word.equals(suffix))
				return true;
		}
		
		return false;
	}

}
