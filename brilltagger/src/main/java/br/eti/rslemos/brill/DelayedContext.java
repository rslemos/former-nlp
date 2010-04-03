package br.eti.rslemos.brill;

import java.util.LinkedList;

import br.eti.rslemos.tagger.Token;

public class DelayedContext implements Context {
	private static class SetObjectCommand {
		private final Token token;
		private final Object tag;

		protected SetObjectCommand(Token token, Object tag) {
			this.token = token;
			this.tag = tag;
		}
		
		public void setObject() {
			token.setTag(tag);
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


	private class DelayedToken implements Token {
		private final Token token;

		private DelayedToken(Token token) {
			this.token = token;
		}

		public Object getTag() {
			return token.getTag();
		}

		public String getWord() {
			return token.getWord();
		}

		public void setTag(Object tag) {
			commands.add(new SetObjectCommand(token, tag));
		}
	}

}