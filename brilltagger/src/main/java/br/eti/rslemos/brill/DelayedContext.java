package br.eti.rslemos.brill;

import java.util.LinkedList;

import br.eti.rslemos.tagger.Token;

public class DelayedContext implements Context {
	private static class SetTagCommand {
		private final Token token;
		private final String tag;

		protected SetTagCommand(Token token, String tag) {
			this.token = token;
			this.tag = tag;
		}
		
		public void setTag() {
			token.setTag(tag);
		}
	}
	
	private Context context;
	private LinkedList<SetTagCommand> commands = new LinkedList<SetTagCommand>();
	
	public DelayedContext(Context context) {
		this.context = context;
	}

	public void commit() {
		for (SetTagCommand command : commands) {
			command.setTag();
		}

		commands.clear();
	}

	public Token getToken(int offset) {
		return delayedToken(context.getToken(offset));
	}

	public boolean hasNext() {
		return context.hasNext();
	}

	public Token next() {
		return delayedToken(context.next());
	}

	public void remove() {
		context.remove();
	}
	
	@Override
	public DelayedContext clone() {
		try {
			DelayedContext result = (DelayedContext) super.clone();
			result.context = context.clone();
			result.commands = new LinkedList<SetTagCommand>();
			
			return result;
		} catch (CloneNotSupportedException e) {
			throw new Error("Object#clone() threw CloneNotSupportedException", e);
		}
	}

	private Token delayedToken(final Token token) {
		return new Token() {
			public String getTag() {
				return token.getTag();
			}

			public String getWord() {
				return token.getWord();
			}

			public void setTag(String tag) {
				commands.add(new SetTagCommand(token, tag));
			}
			
		};
	}
}