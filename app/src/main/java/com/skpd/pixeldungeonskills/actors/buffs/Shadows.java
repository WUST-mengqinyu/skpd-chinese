/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package com.skpd.pixeldungeonskills.actors.buffs;

import com.skpd.noosa.audio.Sample;
import com.skpd.pixeldungeonskills.Assets;
import com.skpd.pixeldungeonskills.Dungeon;
import com.skpd.pixeldungeonskills.actors.Char;
import com.skpd.pixeldungeonskills.messages.Messages;
import com.skpd.pixeldungeonskills.ui.BuffIndicator;
import com.skpd.utils.Bundle;

public class Shadows extends Invisibility {
	
	protected float left;
	
	private static final String LEFT	= Messages.get(Poison.class,"1");
	
	@Override
	public void storeInBundle( Bundle bundle ) {
		super.storeInBundle( bundle );
		bundle.put( LEFT, left );
		
	}
	
	@Override
	public void restoreFromBundle( Bundle bundle ) {
		super.restoreFromBundle( bundle );
		left = bundle.getFloat( LEFT );
	}
	
	@Override
	public boolean attachTo( Char target ) {
		if (super.attachTo( target )) {
			Sample.INSTANCE.play( Assets.SND_MELD );
			Dungeon.observe();
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public void detach() {
		super.detach();
		Dungeon.observe();
	}
	
	@Override
	public boolean act() {
		if (target.isAlive()) {
			
			spend( TICK * 2 );
			
			if (--left <= 0 || Dungeon.hero.visibleEnemies() > 0) {
				detach();
			}
			
		} else {
			
			detach();
			
		}

		return true;
	}
	
	public void prolong() {
		left = 2;
	}
	
	@Override
	public int icon() {
		return BuffIndicator.SHADOWS;
	}
	
	@Override
	public String toString() {
		return Messages.get(this,"");
	}
	
}
