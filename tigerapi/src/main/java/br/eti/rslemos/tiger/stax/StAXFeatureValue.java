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

import br.eti.rslemos.tiger.FeatureValue;

public class StAXFeatureValue implements FeatureValue {

	private final String name;
	private final String comment;

	public StAXFeatureValue(String name, String comment) {
		this.name = name;
		this.comment = comment;
	}

	public StAXFeatureValue(String name) {
		this(name, "");
	}

	public String getName() {
		return name;
	}

	public String getComment() {
		return comment;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if (obj == null)
			return false;

		if (!(obj instanceof StAXFeatureValue))
			return false;

		StAXFeatureValue other = (StAXFeatureValue)obj;

		return
		(name != null ? name.equals(other.name) : other.name == null) &&
		(comment != null ? comment.equals(other.comment) : other.comment == null);
	}

	@Override
	public int hashCode() {
		int hashCode = 0;

		hashCode += name != null ? name.hashCode() : 0;
		hashCode *= 3;
		hashCode += comment != null ? comment.hashCode() : 0;

		return hashCode;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public String getValue() {
		return getComment();
	}


}
