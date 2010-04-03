package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.rules.lexical.PREFIXRule;
import br.eti.rslemos.brill.rules.lexical.SUFFIXRule;


public interface RuleSets {
	static final RuleFactory[] BRILL = new RuleFactory[] {
		CURWDRule.FACTORY(),
		LBIGRAMRule.FACTORY(),
		NEXT1OR2OR3TAGRule.FACTORY(),
		NEXT1OR2TAGRule.FACTORY(),
		NEXT1OR2WDRule.FACTORY(),
		NEXT2TAGRule.FACTORY(),
		NEXT2WDRule.FACTORY(),
		NEXTBIGRAMRule.FACTORY(),
		NEXTTAGRule.FACTORY(),
		NEXTWDRule.FACTORY(),
		PREV1OR2OR3TAGRule.FACTORY(),
		PREV1OR2TAGRule.FACTORY(),
		PREV1OR2WDRule.FACTORY(),
		PREV2TAGRule.FACTORY(),
		PREV2WDRule.FACTORY(),
		PREVBIGRAMRule.FACTORY(),
		PREVTAGRule.FACTORY(),
		PREVWDRule.FACTORY(),
		RBIGRAMRule.FACTORY(),
		SURROUNDTAGRule.FACTORY(),
		WDAND2AFTRule.FACTORY(),
		WDAND2BFRRule.FACTORY(),
		WDAND2TAGAFTRule.FACTORY(),
		WDAND2TAGBFRRule.FACTORY(),
		WDNEXTTAGRule.FACTORY(),
		WDPREVTAGRule.FACTORY(),		
	};

	static final RuleFactory[] BRILL_LEXICAL = new RuleFactory[] {
		PREFIXRule.FACTORY(), SUFFIXRule.FACTORY()
	};
}
