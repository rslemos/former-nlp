package br.eti.rslemos.brill.tracer;

import java.util.List;
import java.util.Queue;

import br.eti.rslemos.brill.Rule;
import br.eti.rslemos.brill.RulesetTrainer;

public privileged aspect Progress {
	private int bestScore;
	
	private int rules;
	
	private long start;
	private long before;

	before(): execution(List RulesetTrainer.discoverRules()) {
		start = System.currentTimeMillis();
		rules = 0;
	}

	RulesetTrainer.Score around(Queue rules): call(RulesetTrainer.Score RulesetTrainer.selectBestRule(Queue)) && args(rules) {
		
		System.out.printf("Considering %7d rules...\n", rules.size());
		
		bestScore = 0;
		
		before = System.currentTimeMillis();
		RulesetTrainer.Score bestScore = proceed(rules);
		long after = System.currentTimeMillis();

		this.rules++;
		Frequency freq = computeFrequency(this.rules, after - start);
		ETA eta = computeETA(this.rules, 200, after - start);

		System.out.printf("%3d. Best found (%4ds): %-40s -- % 7.3f rules%-10s -- ETA % 7.3f%-10s\n", this.rules, (after-before)/1000, String.valueOf(bestScore.rule), freq.per_, freq.unit, eta.time, eta.unit);

		return bestScore;
	}
	
	void around(RulesetTrainer.Score score): call(void RulesetTrainer.computeNegativeScore(RulesetTrainer.Score)) && args(score) {
		proceed(score);
				
		if (score.getScore() > bestScore) {
			bestScore = score.getScore();
			System.out.printf("    New best so far (%5d): %-40s\n", score.getScore(), score.rule.toString());
		}
	}
	
	private ETA computeETA(long current, long total, long elapsed) {
		final float ms_per = (float)elapsed / current;
		
		final float time;
		final String unit;
		
		final float ms = ms_per * (total - current);
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
