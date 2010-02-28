package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.rules.lexical.PREFIXRule;
import br.eti.rslemos.brill.rules.lexical.SUFFIXRule;

@SuppressWarnings("unchecked")
public interface RuleSets {
	static final RuleFactory<String>[] BRILL = new RuleFactory[] {
		CURWDRule.<String>FACTORY(),
		LBIGRAMRule.<String>FACTORY(),
		NEXT1OR2OR3TAGRule.<String>FACTORY1(), NEXT1OR2OR3TAGRule.<String>FACTORY2(), NEXT1OR2OR3TAGRule.<String>FACTORY3(),
		NEXT1OR2TAGRule.<String>FACTORY1(), NEXT1OR2TAGRule.<String>FACTORY2(),
		NEXT1OR2WDRule.<String>FACTORY1(), NEXT1OR2WDRule.<String>FACTORY2(),
		NEXT2TAGRule.<String>FACTORY(),
		NEXT2WDRule.<String>FACTORY(),
		NEXTBIGRAMRule.<String>FACTORY(),
		NEXTTAGRule.<String>FACTORY(),
		NEXTWDRule.<String>FACTORY(),
		PREV1OR2OR3TAGRule.<String>FACTORY1(), PREV1OR2OR3TAGRule.<String>FACTORY2(), PREV1OR2OR3TAGRule.<String>FACTORY3(),
		PREV1OR2TAGRule.<String>FACTORY1(), PREV1OR2TAGRule.<String>FACTORY2(),
		PREV1OR2WDRule.<String>FACTORY1(), PREV1OR2WDRule.<String>FACTORY2(),
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

	static final RuleFactory<String>[] BRILL_EXTENDED = new RuleFactory[] {
		CURWDRule.<String>FACTORY(),
		LBIGRAMRule.<String>FACTORY(),
		NEXT1OR2OR3TAGRule.<String>FACTORY1(), NEXT1OR2OR3TAGRule.<String>FACTORY2(), NEXT1OR2OR3TAGRule.<String>FACTORY3(),
		NEXT1OR2TAGRule.<String>FACTORY1(), NEXT1OR2TAGRule.<String>FACTORY2(),
		NEXT1OR2WDRule.<String>FACTORY1(), NEXT1OR2WDRule.<String>FACTORY2(),
		NEXT2TAGRule.<String>FACTORY(),
		NEXT2WDRule.<String>FACTORY(),
		NEXTBIGRAMRule.<String>FACTORY(),
		NEXTTAGRule.<String>FACTORY(),
		NEXTWDRule.<String>FACTORY(),
		PREV1OR2OR3TAGRule.<String>FACTORY1(), PREV1OR2OR3TAGRule.<String>FACTORY2(), PREV1OR2OR3TAGRule.<String>FACTORY3(),
		PREV1OR2TAGRule.<String>FACTORY1(), PREV1OR2TAGRule.<String>FACTORY2(),
		PREV1OR2WDRule.<String>FACTORY1(), PREV1OR2WDRule.<String>FACTORY2(),
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
		
		NEXT1OR2OR3WDRule.<String>FACTORY1(), NEXT1OR2OR3WDRule.<String>FACTORY2(), NEXT1OR2OR3WDRule.<String>FACTORY3(),
		NEXT1OR2OR3OR4WDRule.<String>FACTORY1(), NEXT1OR2OR3OR4WDRule.<String>FACTORY2(), NEXT1OR2OR3OR4WDRule.<String>FACTORY3(), NEXT1OR2OR3OR4WDRule.<String>FACTORY4(),
		PREV1OR2OR3WDRule.<String>FACTORY1(), PREV1OR2OR3WDRule.<String>FACTORY2(), PREV1OR2OR3WDRule.<String>FACTORY3(),
		PREV1OR2OR3OR4WDRule.<String>FACTORY1(), PREV1OR2OR3OR4WDRule.<String>FACTORY2(), PREV1OR2OR3OR4WDRule.<String>FACTORY3(), PREV1OR2OR3OR4WDRule.<String>FACTORY4(),
		
	};

	static final RuleFactory<String>[] BRILL_LEXICAL = new RuleFactory[] {
		PREFIXRule.FACTORY1, PREFIXRule.FACTORY2, PREFIXRule.FACTORY3, PREFIXRule.FACTORY4,
		SUFFIXRule.FACTORY1, SUFFIXRule.FACTORY2, SUFFIXRule.FACTORY3, SUFFIXRule.FACTORY4,
	};

	static final RuleFactory<String>[] BRILL_LEXICAL_EXTENDED = new RuleFactory[] {
		PREFIXRule.FACTORY1, PREFIXRule.FACTORY2, PREFIXRule.FACTORY3, PREFIXRule.FACTORY4, 
		SUFFIXRule.FACTORY1, SUFFIXRule.FACTORY2, SUFFIXRule.FACTORY3, SUFFIXRule.FACTORY4, 
		
		PREFIXRule.FACTORY5, PREFIXRule.FACTORY6, PREFIXRule.FACTORY7, PREFIXRule.FACTORY8, PREFIXRule.FACTORY9,
		SUFFIXRule.FACTORY5, SUFFIXRule.FACTORY6, SUFFIXRule.FACTORY7, SUFFIXRule.FACTORY8, SUFFIXRule.FACTORY9,
	};
}
