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
package br.eti.rslemos.brill;

import java.util.LinkedList;
import java.util.Map;

import br.eti.rslemos.tagger.Token;

public class DelayedContext implements Context {
	private static class SetObjectCommand {
		protected final Token token;
		private final String name;
		protected final Object value;

		protected SetObjectCommand(Token token, String name, Object value) {
			this.token = token;
			this.name = name;
			this.value = value;
		}
		
		public void setObject() {
			token.put(name, value);
		}
	}
	
	private Context context;
	private LinkedList<SetObjectCommand> commands = new LinkedList<SetObjectCommand>();
	
	public DelayedContext(Context context) {
		this.context = context;
	}

	public void commit() {
		for (SetObjectCommand command : commands) {
			command.setObject();
		}

		commands.clear();
	}

	public Token getToken(int offset) {
		return new DelayedToken(context.getToken(offset));
	}

	public boolean hasNext() {
		return context.hasNext();
	}

	public Token next() {
		return new DelayedToken(context.next());
	}

	public void remove() {
		context.remove();
	}
	
	@Override
	public DelayedContext clone() {
		try {
			DelayedContext result = (DelayedContext) super.clone();
			result.context = context.clone();
			result.commands = new LinkedList<SetObjectCommand>();
			
			return result;
		} catch (CloneNotSupportedException e) {
			throw new Error("Object#clone() threw CloneNotSupportedException", e);
		}
	}

	
	@Override
	public String toString() {
		return "DelayedContext [context=" + context	+ " {+ " + commands.size() + " command(s)}]";
	}


	private final class DelayedToken implements Token {
		private final Token token;

		private DelayedToken(Token token) {
			this.token = token;
		}

		public DelayedToken put(String name, Object value) {
			commands.add(new SetObjectCommand(token, name, value));
			return this;
		}

		public Object get(Object name) {
			return token.get(name);
		}

		public Map<String, Object> getFeatures() {
			return token.getFeatures();
		}
	}

}