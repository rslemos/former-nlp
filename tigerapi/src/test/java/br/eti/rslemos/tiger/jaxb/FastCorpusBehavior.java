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
package br.eti.rslemos.tiger.jaxb;

import java.io.UnsupportedEncodingException;

import br.eti.rslemos.tiger.Corpus;
import br.eti.rslemos.tiger.CorpusBehavior;
import br.eti.rslemos.tiger.TigerException;
import br.eti.rslemos.tiger.TigerInputMother;

public class FastCorpusBehavior extends CorpusBehavior {

	@Override
	public Corpus getStraightCorpus()
	throws UnsupportedEncodingException, TigerException {
		return new FastCorpus(TigerInputMother.getInputStraightTiger());
	}

	@Override
	public void shouldFullyReadFeatures() throws Throwable {
		// TODO Auto-generated method stub
		super.shouldFullyReadFeatures();
	}

	@Override
	public void shouldHaveFeatures() throws Throwable {
		// TODO Auto-generated method stub
		super.shouldHaveFeatures();
	}

	@Override
	public void shouldHaveValuedFeature() throws Throwable {
		// TODO Auto-generated method stub
		super.shouldHaveValuedFeature();
	}

	@Override
	public void shouldReadId() throws Throwable {
		// TODO Auto-generated method stub
		super.shouldReadId();
	}

	@Override
	public void shouldReadMetadata() throws Throwable {
		// TODO Auto-generated method stub
		super.shouldReadMetadata();
	}
}
