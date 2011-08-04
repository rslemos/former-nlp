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
