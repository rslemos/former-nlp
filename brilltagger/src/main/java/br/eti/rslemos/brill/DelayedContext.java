package br.eti.rslemos.brill;

import java.util.LinkedList;
import java.util.List;

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
	
	private final SentenceContext context;
	private final List<SetTagCommand> commands = new LinkedList<SetTagCommand>();
	
	DelayedContext(SentenceContext context) {
		this.context = context;
	}

	public void commit() {
		for (DelayedContext.SetTagCommand command : commands) {
			command.setTag();
		}

		commands.clear();
		
		context.pointer = -1;
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
			return (DelayedContext) super.clone();
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