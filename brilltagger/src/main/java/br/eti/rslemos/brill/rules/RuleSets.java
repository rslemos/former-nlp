package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.rules.lexical.PREFIXRule;
import br.eti.rslemos.brill.rules.lexical.SUFFIXRule;

@SuppressWarnings("unchecked")
public interface RuleSets {
	static final RuleFactory<String>[] BRILL = new RuleFactory[] {
		CURWDRule.<String>FACTORY(),
		LBIGRAMRule.<String>FACTORY(),
		NEXT1OR2OR3TAGRule.<String>FACTORY(),
		NEXT1OR2TAGRule.<String>FACTORY(),
		NEXT1OR2WDRule.<String>FACTORY(),
		NEXT2TAGRule.<String>FACTORY(),
		NEXT2WDRule.<String>FACTORY(),
		NEXTBIGRAMRule.<String>FACTORY(),
		NEXTTAGRule.<String>FACTORY(),
		NEXTWDRule.<String>FACTORY(),
		PREV1OR2OR3TAGRule.<String>FACTORY(),
		PREV1OR2TAGRule.<String>FACTORY(),
		PREV1OR2WDRule.<String>FACTORY(),
		PREV2TAGRule.<String>FACTORY(),
		PREV2WDRule.<String>FACTORY(),
		PREVBIGRAMRule.<String>FACTORY(),
		PREVTAGRule.<String>FACTORY(),
		PREVWDRule.<String>FACTORY(),
		RBIGRAMRule.<String>FACTORY(),
		SURROUNDTAGRule.<String>FACTORY(),
		WDAND2AFTRule.<String>FACTORY(),
		WDAND2BFRRule.<String>FACTORY(),
		WDAND2TAGAFTRule.<String>FACTORY(),
		WDAND2TAGBFRRule.<String>FACTORY(),
		WDNEXTTAGRule.<String>FACTORY(),
		WDPREVTAGRule.<String>FACTORY(),		
	};

	static final RuleFactory<String>[] BRILL_LEXICAL = new RuleFactory[] {
		PREFIXRule.FACTORY(), SUFFIXRule.FACTORY()
	};
}
