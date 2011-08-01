package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.rules.lexical.PREFIXRuleFactory;
import br.eti.rslemos.brill.rules.lexical.SUFFIXRuleFactory;


public interface RuleSets {
	RuleFactory[] BRILL = {
		CURWDRuleFactory.INSTANCE,
		LBIGRAMRuleFactory.INSTANCE,
		NEXT1OR2OR3TAGRuleFactory.INSTANCE,
		NEXT1OR2TAGRuleFactory.INSTANCE,
		NEXT1OR2WDRuleFactory.INSTANCE,
		NEXT2TAGRuleFactory.INSTANCE,
		NEXT2WDRuleFactory.INSTANCE,
		NEXTBIGRAMRuleFactory.INSTANCE,
		NEXTTAGRuleFactory.INSTANCE,
		NEXTWDRuleFactory.INSTANCE,
		PREV1OR2OR3TAGRuleFactory.INSTANCE,
		PREV1OR2TAGRuleFactory.INSTANCE,
		PREV1OR2WDRuleFactory.INSTANCE,
		PREV2TAGRuleFactory.INSTANCE,
		PREV2WDRuleFactory.INSTANCE,
		PREVBIGRAMRuleFactory.INSTANCE,
		PREVTAGRuleFactory.INSTANCE,
		PREVWDRuleFactory.INSTANCE,
		RBIGRAMRuleFactory.INSTANCE,
		SURROUNDTAGRuleFactory.INSTANCE,
		WDAND2AFTRuleFactory.INSTANCE,
		WDAND2BFRRuleFactory.INSTANCE,
		WDAND2TAGAFTRuleFactory.INSTANCE,
		WDAND2TAGBFRRuleFactory.INSTANCE,
		WDNEXTTAGRuleFactory.INSTANCE,
		WDPREVTAGRuleFactory.INSTANCE,		
	};

	RuleFactory[] BRILL_LEXICAL = {
		PREFIXRuleFactory.INSTANCE, SUFFIXRuleFactory.INSTANCE
	};
}
