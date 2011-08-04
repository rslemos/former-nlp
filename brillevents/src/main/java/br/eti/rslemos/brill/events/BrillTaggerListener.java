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
package br.eti.rslemos.brill.events;

import java.util.EventListener;

public interface BrillTaggerListener extends EventListener {

	void taggingStart(BrillTaggerEvent event);

	void taggingFinish(BrillTaggerEvent event);

	void ruleApplicationStart(BrillTaggerEvent event);

	void ruleApplicationFinish(BrillTaggerEvent event);

	void contextAdvanced(BrillTaggerEvent event);

	void contextCommitted(BrillTaggerEvent event);

	void ruleApplied(BrillTaggerEvent event);

}
