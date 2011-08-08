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
package br.eti.rslemos.brill.rules.lexical;

import java.util.ArrayList;
import java.util.Collection;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;
import br.eti.rslemos.brill.rules.AbstractRuleFactory;
import br.eti.rslemos.tagger.Token;

public abstract class AbstractAFFIXRuleFactory extends AbstractRuleFactory {

	public AbstractAFFIXRuleFactory() {
		super();
	}

	protected abstract Rule create(Object from, Object to, String word, int length);

	@Override
	public Collection<Rule> create(Object from, Object to, Context context) {
		return create(from, to, (String)context.getToken(0).get(Token.WORD));
	}

	private Collection<Rule> create(Object from, Object to, String word) {
		Collection<Rule> result = new ArrayList<Rule>(word.length() - 1);
	
		for (int i = 1; i < word.length(); i++) {
			result.add(create(from, to, word, i));
		}
	
		return result;
	}

}