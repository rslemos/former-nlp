package br.eti.rslemos.brill.tracer;

import java.util.List;
import java.util.Queue;

import br.eti.rslemos.brill.Rule;
import br.eti.rslemos.brill.RulesetTrainer.Score;
import br.eti.rslemos.brill.RulesetTrainer.TrainingContext;

public privileged aspect Progress {
	private int bestScore;
	
	private int rules;
	
	private int current;
	private int total;
	
	private long start;
	private long before;

	before(): execution(List<Rule> TrainingContext.discoverRules()) {
		start = System.currentTimeMillis();
		rules = 0;
	}

	after(TrainingContext haltingStrategy) returning(int errorCount):
		execution(int TrainingContext.countErrors()) &&
//		withincode(boolean ThresholdHaltingStrategy.updateAndTest()) &&
		target(haltingStrategy) {
		System.out.printf("Error count dropped from %7d to %7d\n", haltingStrategy.errorCount, errorCount);
	}
	
	Rule around(Queue<Score> rules): call(Rule TrainingContext.selectBestRule(Queue<Score>)) && args(rules) {
		
		System.out.printf("Considering %7d rules...\n", rules.size());
		
		current = bestScore = 0;
		total = rules.size();
		
		before = System.currentTimeMillis();
		Rule bestRule = proceed(rules);
		long after = System.currentTimeMillis();

		this.rules++;
		Frequency freq = computeFrequency(this.rules, after - start);
		ETA eta = computeETA(this.rules, 200, after - start);

		System.out.printf("%3d. Best found (%4ds): %-40s -- % 7.3f rules%-10s -- ETA % 7.3f%-10s\n", this.rules, (after-before)/1000, String.valueOf(bestRule), freq.per_, freq.unit, eta.time, eta.unit);

		return bestRule;
	}
	
	void around(Score score): call(void TrainingContext.computeNegativeScore(Score)) && args(score) {
		current++;
		
		if (current % 10000 == 0) {
			long now = System.currentTimeMillis();
			
			Frequency freq = computeFrequency(current, now - before);
			ETA eta = computeETA(current, total, now - before);
			
			System.out.printf("      % 6d/% 6d (% 3d%%) -- % 7.3f rules%-10s -- ETA % 7.3f%-10s\n", current, total, (current * 100)/total, freq.per_, freq.unit, eta.time, eta.unit);
		}

		//int scoreBefore = score.getScore();
		proceed(score);
		//int scoreAfter = score.getScore();

		//if (round != score.roundComputed)
		//	System.out.printf("Considering rule %-40s [!!!!!!!!! %5d]\n", score.rule.toString(), scoreBefore, scoreAfter);
		//else
		//	System.out.printf("Considering rule %-40s [%5d -> %5d]\n", score.rule.toString(), scoreBefore, scoreAfter);
				
		if (score.getScore() > bestScore) {
			bestScore = score.getScore();
			System.out.printf("    New best so far (%5d): %-40s\n", score.getScore(), score.rule.toString());
		}
	}
	
	private ETA computeETA(long current, long total, long elapsed) {
		final float ms_per = (float)elapsed / current;
		
		final float time;
		final String unit;
		
		final float ms = ms_per * total;
		if (ms < 10) {
			time = ms;
			unit = "ms";
		} else {
			final float s = ms / 1000;
			if (s < 10) {
				time = s;
				unit = "s";
			} else {
				final float m = s / 60;
				if (m < 60) {
					time = m;
					unit = "m";
				} else {
					final float h = m / 60;
					if (h < 24) {
						time = h;
						unit = "h";
					} else {
						final float days = h / 24;
						time = days;
						unit = "days";
					}
				}
			}
		}
		
		return new ETA(time, unit);
	}

	private Frequency computeFrequency(long current, long elapsed) {
		final float per_;
		final String unit;
		
		final float per_ms = (float)current / elapsed;
		if (per_ms > 10) {
			per_ = per_ms;
			unit = "/ms";
		} else {
			final float per_s = per_ms * 1000;
			if (per_s > 10) {
				per_ = per_s;
				unit = "/s";
			} else {
				final float per_m = per_s * 60;
				if (per_m > 10) {
					per_ = per_m;
					unit = "/minute";
				} else {
					final float per_h = per_m * 60;
					if (per_h > 10) {
						per_ = per_h;
						unit = "/hour";
					} else {
						final float per_day = per_h * 24;
						per_ = per_day;
						unit = "/day";
					}
						
				}
			}
		}
		
		return new Frequency(per_, unit);
	}

	private static class Frequency {
		final float per_;
		final String unit;

		protected Frequency(float per_, String unit) {
			this.per_ = per_;
			this.unit = unit;
		}
	}
	
	private static class ETA {
		final float time;
		final String unit;
		
		protected ETA(float time, String unit) {
			this.time = time;
			this.unit = unit;
		}
		
	}
}
