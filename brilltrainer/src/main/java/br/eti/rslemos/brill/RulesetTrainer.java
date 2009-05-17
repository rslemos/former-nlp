package br.eti.rslemos.brill;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.collections15.Bag;
import org.apache.commons.collections15.bag.HashBag;

import br.eti.rslemos.brill.rules.CURWDRule;

public class RulesetTrainer {

	private final BaseTagger baseTagger;

	public RulesetTrainer(BaseTagger baseTagger) {
		this.baseTagger = baseTagger;
	}

	public List<Rule> train(List<List<Token>> sentences) {
		DefaultToken[][] workSentences = getBaseTaggedSentences(sentences);
		
		LinkedList<Rule> rules = new LinkedList<Rule>();
		
		Bag<TagCorrection> corrections = compareSentences(sentences, workSentences);
		while(corrections.size() > 0) {
			TagCorrection correction = corrections.iterator().next();

			Rule newRule = produceRuleForCorrection(sentences, workSentences, correction);
			rules.add(newRule);
			
			applyRule(workSentences, newRule);
			
			corrections = compareSentences(sentences, workSentences);
		}
		
		return rules;
	}

	private void applyRule(DefaultToken[][] workSentences, Rule newRule) {
		for (DefaultToken[] workSentence : workSentences) {
			Context context = new Context(workSentence);
			while(context.isValidPosition()) {
				newRule.apply(context);
				context.advance();
			}
		}
	}

	private Rule produceRuleForCorrection(List<List<Token>> sentences, DefaultToken[][] workSentences, TagCorrection correction) {
		for (int i = 0; i < workSentences.length; i++) {
			DefaultToken[] workSentence = workSentences[i];
			List<Token> sentence = sentences.get(i);
			
			for (int j = 0; j < workSentence.length; j++) {
				DefaultToken workToken = workSentence[j];
				Token token = sentence.get(j);
				
				String workTag = workToken.getTag();
				String tag = token.getTag();

				if (correction.equals(new TagCorrection(workTag, tag))) {
					return new CURWDRule(workTag, tag, workToken.getWord());
				}
			}
		}
		
		return null;
	}

	private Bag<TagCorrection> compareSentences(List<List<Token>> sentences, DefaultToken[][] workSentences) {
		Bag<TagCorrection> corrections = new HashBag<TagCorrection>();
		
		for (int i = 0; i < workSentences.length; i++) {
			DefaultToken[] workSentence = workSentences[i];
			List<Token> sentence = sentences.get(i);
			
			for (int j = 0; j < workSentence.length; j++) {
				DefaultToken workToken = workSentence[j];
				Token token = sentence.get(j);
				
				String workTag = workToken.getTag();
				String tag = token.getTag();
				if (tag != null ? !tag.equals(workTag) : workTag != null)
					corrections.add(new TagCorrection(workTag, tag));
			}
		}
		
		return corrections;
	}

	private DefaultToken[][] getBaseTaggedSentences(List<List<Token>> sentences) {
		DefaultToken[][] baseTaggedSentences = new DefaultToken[sentences.size()][];
		
		for (int i = 0; i < baseTaggedSentences.length; i++) {
			List<Token> sentence = sentences.get(i);
			
			DefaultToken[] baseTaggedSentence = baseTaggedSentences[i] = new DefaultToken[sentence.size()];
			for (int j = 0; j < baseTaggedSentence.length; j++) {
				DefaultToken token = baseTaggedSentence[j] = new DefaultToken(sentence.get(j).getWord());
				baseTagger.tag(token);
			}
		}
		
		return baseTaggedSentences;
	}

	private static final class TagCorrection {

		public final String from;
		public final String to;

		public TagCorrection(String from, String to) {
			this.from = from;
			this.to = to;
		}

		@Override
		public boolean equals(Object o) {
			if (o == null)
				return false;
			
			if (o == this)
				return true;
			
			if (!(o instanceof TagCorrection))
				return false;
			
			TagCorrection other = (TagCorrection) o;
			return (from != null ? from.equals(other.from) : other.from == null) &&
				(to != null ? to.equals(other.to) : other.to == null);
		}

		@Override
		public int hashCode() {
			int hashCode = 0;
			
			hashCode += from != null ? from.hashCode() : 0;
			hashCode *= 3;
			hashCode += to != null ? to.hashCode() : 0;
			
			return hashCode;
		}

		@Override
		public String toString() {
			return from + "->" + to;
		}
	}
}
