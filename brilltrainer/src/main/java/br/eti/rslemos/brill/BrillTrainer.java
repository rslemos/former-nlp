/*******************************************************************************
 * BEGIN COPYRIGHT NOTICE
 * 
 * This file is part of program "Natural Language Processing"
 * Copyright 2013  Rodrigo Lemos
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import br.eti.rslemos.tagger.Corpus;

public class BrillTrainer {
	private static final Object ANY = new Object() { 
		public boolean equals(Object o) { return true; }
		public String toString() { return "ANY"; }
	};

	private static final Object SKIP = new Object() {
		public String toString() { return "SKIP"; }
	};
	
	private WorkCorpus base;
	private WorkCorpus proof;

	private String[] patternsFeatureNames;
	private Collection<WorkRulePattern> patterns;
	
	private ArrayList<WorkRule> rules;
	private BitSet dirty;
	private ArrayList<WorkRule> candidates;
	private int[] sentence;

	public BrillTrainer() {
	}

	public BrillTrainer setRulePatterns(Collection<RulePattern> patterns) {
		if (patterns == null)
			throw new NullPointerException();
		
		this.patternsFeatureNames = WorkRulePattern.collectFeatures(patterns);
		this.patterns = WorkRulePattern.toWorkPatterns(patterns, this.patternsFeatureNames);
		
		return this;
	}
	
	public BrillTrainer setCorpora(Corpus base, Corpus proof) {
		if (base == null || proof == null)
			throw new NullPointerException();
		
		this.proof = new WorkCorpus(proof);
		this.base = new WorkCorpus(base, this.proof);
		
		return this;
	}

	public List<Rule> getRules() {
		ArrayList<Rule> result = new ArrayList<Rule>(rules.size());
		
		for (WorkRule workRule : rules) {
			Rule rule = new Rule();
			rule.matches = newHashMapArray(workRule.matches.length);
			
			for (int i = 0; i < rule.matches.length; i++) {
				rule.matches[i] = new HashMap<String, Object>();
				for (int j = 0; j < workRule.matches[i].length; j++) {
					if (workRule.matches[i][j] != ANY) {
						rule.matches[i].put(patternsFeatureNames[j], workRule.matches[i][j]);
					}
				}
			}
			
			rule.sets = new HashMap<String, Object>();
			for (int j = 0; j < workRule.sets.length; j++) {
				if (workRule.sets[j] != SKIP)
					rule.sets.put(patternsFeatureNames[j], workRule.sets[j]);
			}
			
			result.add(rule);
		}
		
		return result;
	}

	@SuppressWarnings("unchecked")
	private <K, V> HashMap<K, V>[] newHashMapArray(int size) {
		return new HashMap[size];
	}
	
	public List<Rule> train() {
		rules = new ArrayList<WorkRule>();
	
		dirty = new BitSet(base.tokens());
		dirty.set(0, base.tokens());

		candidates = new ArrayList<WorkRule>();
		
		do {
			buildCandidates();
			selectBestCandidate();
		} while (!candidates.isEmpty());
		
		return getRules();
	}

	private void selectBestCandidate() {
		if (!candidates.isEmpty()) {
			WorkRule rule;
			rules.add(rule = candidates.remove(0));
			
			sentence = new int[] { -1, -1 };
			for (int i = 0; i < base.tokens(); i++) {
				if (match(rule, i)) {
					apply(rule, i);
				}
			}
		}
	}

	private void apply(WorkRule rule, int i) {
		dirty.set(i);

		for (int j = 0; j < rule.sets.length; j++) {
			if (rule.sets[j] != SKIP) {
				int idx = Arrays.binarySearch(base.featureNames, patternsFeatureNames[j]);
				
				if (idx >= 0) {
					base.features[i][idx] = rule.sets[j];
				}
			}
		}
	}

	private boolean match(WorkRule rule, int i) {
		if (i >= sentence[1])
			sentence = base.getSentenceForToken(i);
		
		for (int k = 0; k < rule.matches.length; k++) {
			Object[] match = rule.matches[k];
			
			if (match != null) {
				int pos = k % 2 == 0 ? k / 2 : (k + 1) / -2;

				for (int j = 0; j < match.length; j++) {
					int idx = Arrays.binarySearch(base.featureNames, patternsFeatureNames[j]);
					Object value;
					
					if (idx >= 0 && (pos + i) >= sentence[0] && (pos + i) < sentence[1])
						value = base.features[pos + i][idx];
					else
						value = null;
					
					if (!eq(match[j], value))
						return false;
				}
			}
		}
		
		return true;
	}

	private void buildCandidates() {
		sentence = new int[] { -1, -1 };
		// iterate over dirty positions
		for (int i = dirty.nextSetBit(0); i >= 0; i = dirty.nextSetBit(i+1)) {
			buildCandidates(i);
		}
	}

	private void buildCandidates(int i) {
		if (i >= sentence[1])
			sentence = base.getSentenceForToken(i);
		
		for (WorkRulePattern pattern : patterns) {
			WorkRule rule = new WorkRule();
			
			rule.sets = new Object[patternsFeatureNames.length];
			Arrays.fill(rule.sets, SKIP);
			
			boolean change = false;
			
			for (int j = pattern.sets.nextSetBit(0); j >= 0; j = pattern.sets.nextSetBit(j+1)) {
				int idxProof = Arrays.binarySearch(proof.featureNames, patternsFeatureNames[j]);
				int idxBase = Arrays.binarySearch(base.featureNames, patternsFeatureNames[j]);
				
				if (idxProof >= 0) {
					rule.sets[j] = proof.features[i][idxProof];
				} else {
					rule.sets[j] = null;
				}
				
				if (idxProof >=0 && idxBase >=0 && !eq(proof.features[i][idxProof], base.features[i][idxBase]))
					change = true;
			}

			if (change) {
				rule.matches = new Object[pattern.matches.length][patternsFeatureNames.length];
				
				for (int k = 0; k < pattern.matches.length; k++) {
					BitSet match = pattern.matches[k];
					
					if (match != null) {
						int pos = k % 2 == 0 ? k / 2 : (k + 1) / -2;
						
						Arrays.fill(rule.matches[k], ANY);
						
						for (int j = match.nextSetBit(0); j >= 0; j = match.nextSetBit(j+1)) {
							int idx = Arrays.binarySearch(base.featureNames, patternsFeatureNames[j]);
							
							if (idx >= 0 && (pos + i) >= sentence[0] && (pos + i) < sentence[1]) {
								rule.matches[k][j] = base.features[pos + i][idx];
							} else {
								rule.matches[k][j] = null;
							}
						}
					}
				}
				
				candidates.add(rule);
			}
		}
		
		dirty.clear(i);
	}

	private boolean eq(Object o1, Object o2) {
		return o1 != null ? o1.equals(o2) : o2 == null;
	}

}