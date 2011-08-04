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
package br.eti.rslemos.tiger.stax;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import br.eti.rslemos.tiger.Domain;
import br.eti.rslemos.tiger.Feature;

public class StAXFeature implements Feature {

	private final String name;
	private final Domain domain;

	private final Map<String, StAXFeatureValue> values = new LinkedHashMap<String, StAXFeatureValue>();

	public StAXFeature(String name, Domain domain) {
		this.name = name;
		this.domain = domain;
	}

	public String getName() {
		return name;
	}

	public Domain getDomain() {
		return domain;
	}

	public List<StAXFeatureValue> getValues() {
		return new LinkedList<StAXFeatureValue>(values.values());
	}

	public void add(StAXFeatureValue featureValue) {
		values.put(featureValue.getName(), featureValue);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if (obj == null)
			return false;

		if (!(obj instanceof StAXFeature))
			return false;

		StAXFeature other = (StAXFeature)obj;

		return
		(name != null ? name.equals(other.name) : other.name == null) &&
		(domain != null ? domain.equals(other.domain) : other.domain == null) &&
		(values.equals(other.values));
	}

	@Override
	public int hashCode() {
		int hashCode = 0;

		hashCode += name != null ? name.hashCode() : 0;
		hashCode *= 3;
		hashCode += domain != null ? domain.hashCode() : 0;
		hashCode *= 5;
		hashCode += values.hashCode();

		return hashCode;
	}

	@Override
	public String toString() {
		return name + "(" + domain + ")";
	}

}
