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
			token.setFeature(name, value);
		}
	}

	@Deprecated
	private static class OldSetObjectCommand extends SetObjectCommand {
		protected OldSetObjectCommand(Token token, Object tag) {
			super(token, null, tag);
		}

		public void setObject() {
			token.setTag(value);
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

		@Deprecated
		public Object getTag() {
			return token.getTag();
		}

		@Deprecated
		public String getWord() {
			return token.getWord();
		}

		public DelayedToken setFeature(String name, Object value) {
			commands.add(new SetObjectCommand(token, name, value));
			return this;
		}

		@Deprecated
		public DelayedToken setTag(Object value) {
			commands.add(new OldSetObjectCommand(token, value));
			return this;
		}

		public Object getFeature(String name) {
			return token.getFeature(name);
		}

		public Map<String, Object> getFeatures() {
			return token.getFeatures();
		}
	}

}