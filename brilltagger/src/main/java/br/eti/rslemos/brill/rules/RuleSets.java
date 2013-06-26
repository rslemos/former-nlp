/*******************************************************************************
 * BEGIN COPYRIGHT NOTICE
 * 
 * This file is part of program "Natural Language Processing"
 * Copyright 2011  Rodrigo Lemos
 * Copyright 2013  Rodrigo Lemos
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

import br.eti.rslemos.brill.rules.lexical.PREFIX;
import br.eti.rslemos.brill.rules.lexical.SUFFIX;


public interface RuleSets {
	RuleFactory[] BRILL = {
		CURWD.Factory.INSTANCE,
		LBIGRAM.Factory.INSTANCE,
		NEXT1OR2OR3TAG.Factory.INSTANCE,
		NEXT1OR2TAG.Factory.INSTANCE,
		NEXT1OR2WD.Factory.INSTANCE,
		NEXT2TAG.Factory.INSTANCE,
		NEXT2WD.Factory.INSTANCE,
		NEXTBIGRAM.Factory.INSTANCE,
		NEXTTAG.Factory.INSTANCE,
		NEXTWD.Factory.INSTANCE,
		PREV1OR2OR3TAG.Factory.INSTANCE,
		PREV1OR2TAG.Factory.INSTANCE,
		PREV1OR2WD.Factory.INSTANCE,
		PREV2TAG.Factory.INSTANCE,
		PREV2WD.Factory.INSTANCE,
		PREVBIGRAM.Factory.INSTANCE,
		PREVTAG.Factory.INSTANCE,
		PREVWD.Factory.INSTANCE,
		RBIGRAM.Factory.INSTANCE,
		SURROUNDTAG.Factory.INSTANCE,
		WDAND2AFT.Factory.INSTANCE,
		WDAND2BFR.Factory.INSTANCE,
		WDAND2TAGAFT.Factory.INSTANCE,
		WDAND2TAGBFR.Factory.INSTANCE,
		WDNEXTTAG.Factory.INSTANCE,
		WDPREVTAG.Factory.INSTANCE,		
	};

	RuleFactory[] BRILL_LEXICAL = {
		PREFIX.Factory.INSTANCE, SUFFIX.Factory.INSTANCE
	};
}
