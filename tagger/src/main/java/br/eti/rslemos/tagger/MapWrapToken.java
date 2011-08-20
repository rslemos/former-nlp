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
package br.eti.rslemos.tagger;

import java.util.Map;

import com.google.common.collect.ForwardingMap;

public class MapWrapToken extends ForwardingMap<String, Object> implements Token {

	private final Map<String, Object> map;

	public MapWrapToken(Map<String, Object> map) {
		this.map = map;
	}

	@Override
	protected Map<String, Object> delegate() {
		return map;
	}
	
	public static MapWrapToken wrap(Map<String, Object> map) {
		return new MapWrapToken(map);
	}
}
