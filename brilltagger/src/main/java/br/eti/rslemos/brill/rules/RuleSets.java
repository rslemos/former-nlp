package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.rules.lexical.PREFIXRule;
import br.eti.rslemos.brill.rules.lexical.SUFFIXRule;

public interface RuleSets {
	static final RuleFactory[] BRILL = {
		CURWDRule.FACTORY,
		LBIGRAMRule.FACTORY,
		NEXT1OR2OR3TAGRule.FACTORY1, NEXT1OR2OR3TAGRule.FACTORY2, NEXT1OR2OR3TAGRule.FACTORY3,
		NEXT1OR2TAGRule.FACTORY1, NEXT1OR2TAGRule.FACTORY2,
		NEXT1OR2WDRule.FACTORY1, NEXT1OR2WDRule.FACTORY2,
		NEXT2TAGRule.FACTORY,
		NEXT2WDRule.FACTORY,
		NEXTBIGRAMRule.FACTORY,
		NEXTTAGRule.FACTORY,
		NEXTWDRule.FACTORY,
		PREV1OR2OR3TAGRule.FACTORY1, PREV1OR2OR3TAGRule.FACTORY2, PREV1OR2OR3TAGRule.FACTORY3,
		PREV1OR2TAGRule.FACTORY1, PREV1OR2TAGRule.FACTORY2,
		PREV1OR2WDRule.FACTORY1, PREV1OR2WDRule.FACTORY2,
		PREV2TAGRule.FACTORY,
		PREV2WDRule.FACTORY,
		PREVBIGRAMRule.FACTORY,
		PREVTAGRule.FACTORY,
		PREVWDRule.FACTORY,
		RBIGRAMRule.FACTORY,
		SURROUNDTAGRule.FACTORY,
		WDAND2AFTRule.FACTORY,
		WDAND2BFRRule.FACTORY,
		WDAND2TAGAFTRule.FACTORY,
		WDAND2TAGBFRRule.FACTORY,
		WDNEXTTAGRule.FACTORY,
		WDPREVTAGRule.FACTORY,		
	};

	static final RuleFactory[] BRILL_EXTENDED = {
		CURWDRule.FACTORY,
		LBIGRAMRule.FACTORY,
		NEXT1OR2OR3TAGRule.FACTORY1, NEXT1OR2OR3TAGRule.FACTORY2, NEXT1OR2OR3TAGRule.FACTORY3,
		NEXT1OR2TAGRule.FACTORY1, NEXT1OR2TAGRule.FACTORY2,
		NEXT1OR2WDRule.FACTORY1, NEXT1OR2WDRule.FACTORY2,
		NEXT2TAGRule.FACTORY,
		NEXT2WDRule.FACTORY,
		NEXTBIGRAMRule.FACTORY,
		NEXTTAGRule.FACTORY,
		NEXTWDRule.FACTORY,
		PREV1OR2OR3TAGRule.FACTORY1, PREV1OR2OR3TAGRule.FACTORY2, PREV1OR2OR3TAGRule.FACTORY3,
		PREV1OR2TAGRule.FACTORY1, PREV1OR2TAGRule.FACTORY2,
		PREV1OR2WDRule.FACTORY1, PREV1OR2WDRule.FACTORY2,
		PREV2TAGRule.FACTORY,
		PREV2WDRule.FACTORY,
		PREVBIGRAMRule.FACTORY,
		PREVTAGRule.FACTORY,
		PREVWDRule.FACTORY,
		RBIGRAMRule.FACTORY,
		SURROUNDTAGRule.FACTORY,
		WDAND2AFTRule.FACTORY,
		WDAND2BFRRule.FACTORY,
		WDAND2TAGAFTRule.FACTORY,
		WDAND2TAGBFRRule.FACTORY,
		WDNEXTTAGRule.FACTORY,
		WDPREVTAGRule.FACTORY,
		
		NEXT1OR2OR3WDRule.FACTORY1, NEXT1OR2OR3WDRule.FACTORY2, NEXT1OR2OR3WDRule.FACTORY3,
		NEXT1OR2OR3OR4WDRule.FACTORY1, NEXT1OR2OR3OR4WDRule.FACTORY2, NEXT1OR2OR3OR4WDRule.FACTORY3, NEXT1OR2OR3OR4WDRule.FACTORY4,
		PREV1OR2OR3WDRule.FACTORY1, PREV1OR2OR3WDRule.FACTORY2, PREV1OR2OR3WDRule.FACTORY3,
		PREV1OR2OR3OR4WDRule.FACTORY1, PREV1OR2OR3OR4WDRule.FACTORY2, PREV1OR2OR3OR4WDRule.FACTORY3, PREV1OR2OR3OR4WDRule.FACTORY4,
		
	};

	static final RuleFactory[] BRILL_LEXICAL = {
		PREFIXRule.FACTORY1, PREFIXRule.FACTORY2, PREFIXRule.FACTORY3, PREFIXRule.FACTORY4,
		SUFFIXRule.FACTORY1, SUFFIXRule.FACTORY2, SUFFIXRule.FACTORY3, SUFFIXRule.FACTORY4,
	};

	static final RuleFactory[] BRILL_LEXICAL_EXTENDED = {
		PREFIXRule.FACTORY1, PREFIXRule.FACTORY2, PREFIXRule.FACTORY3, PREFIXRule.FACTORY4, 
		SUFFIXRule.FACTORY1, SUFFIXRule.FACTORY2, SUFFIXRule.FACTORY3, SUFFIXRule.FACTORY4, 
		
		PREFIXRule.FACTORY5, PREFIXRule.FACTORY6, PREFIXRule.FACTORY7, PREFIXRule.FACTORY8, PREFIXRule.FACTORY9,
		SUFFIXRule.FACTORY5, SUFFIXRule.FACTORY6, SUFFIXRule.FACTORY7, SUFFIXRule.FACTORY8, SUFFIXRule.FACTORY9,
	};
}
