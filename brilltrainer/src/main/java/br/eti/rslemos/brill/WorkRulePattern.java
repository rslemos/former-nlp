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

import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;
import java.util.Formatter;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

class WorkRulePattern {

	public static Collection<WorkRulePattern> toWorkPatterns(Collection<RulePattern> originalPatterns, String[] featureNames) {
		WorkRulePattern[] result = new WorkRulePattern[originalPatterns.size()];
		
		Iterator<RulePattern> it = originalPatterns.iterator();
		for (int i = 0; i < result.length; i++) {
			result[i] = toWorkPattern(it.next(), featureNames);
		}
		
		return Arrays.asList(result);
	}

	public static String[] collectFeatures(Collection<RulePattern> originalPatterns) {
		TreeSet<String> featureNames = new TreeSet<String>();
		for (RulePattern rulePattern : originalPatterns) {
			featureNames.addAll(rulePattern.sets);
			for (List<String> features : rulePattern.matches) {
				featureNames.addAll(features);
			}
		}
		return featureNames.toArray(new String[featureNames.size()]);
	}

	BitSet[] matches;
	BitSet sets;

	private static WorkRulePattern toWorkPattern(RulePattern pattern, String[] featureNames) {
		WorkRulePattern result = new WorkRulePattern();
		
		result.matches = new BitSet[pattern.matches.length];
		for (int i = 0; i < result.matches.length; i++) {
			if (pattern.matches[i] != null) {
				result.matches[i] = new BitSet(featureNames.length);
				for (String feature : pattern.matches[i]) {
					int idx = Arrays.binarySearch(featureNames, feature);
					if (idx >= 0)
						result.matches[i].set(idx);
				}
			}
		}
		
		result.sets = new BitSet(featureNames.length);
		for (String feature : pattern.sets) {
			int idx = Arrays.binarySearch(featureNames, feature);
			if (idx >= 0)
				result.sets.set(idx);
		}
		
		return result;
	}

	public String toString() {
		@SuppressWarnings("resource")
		Formatter result = new Formatter();
		
		result.format("matches:\n");
		for (int i = (matches.length - 2) | 1; i > 0; i -= 2) {
			result.format("%2d. %s\n", (i + 1) / -2, matches[i]); 
		}
		
		for (int i = 0; i < matches.length; i += 2) {
			result.format("%2d. %s\n", i/2, matches[i]);
		}
		
		result.format("sets: %s\n", sets);
		
		return result.toString();
	}
}