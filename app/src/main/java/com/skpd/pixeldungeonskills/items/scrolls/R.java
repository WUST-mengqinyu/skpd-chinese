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
package com.skpd.pixeldungeonskills.items.scrolls;

import com.skpd.noosa.audio.Sample;
import com.skpd.pixeldungeonskills.Assets;
import com.skpd.pixeldungeonskills.Dungeon;
import com.skpd.pixeldungeonskills.actors.buffs.Buff;
import com.skpd.pixeldungeonskills.actors.buffs.Invisibility;
import com.skpd.pixeldungeonskills.actors.buffs.Terror;
import com.skpd.pixeldungeonskills.actors.mobs.Mob;
import com.skpd.pixeldungeonskills.effects.Flare;
import com.skpd.pixeldungeonskills.levels.Level;
import com.skpd.pixeldungeonskills.messages.Messages;
import com.skpd.pixeldungeonskills.utils.GLog;

public class R extends Scroll {

	
	@Override
	protected void doRead() {
		
		new Flare( 5, 32 ).color( 0xFF0000, true ).show( curUser.sprite, 2f );
		Sample.INSTANCE.play( Assets.SND_READ );
		Invisibility.dispel();
		
		int count = 0;
		Mob affected = null;
		for (Mob mob : Dungeon.level.mobs.toArray( new Mob[0] )) {
			if (Level.fieldOfView[mob.pos]) {
				Buff.affect( mob, Terror.class, Terror.DURATION ).object = curUser.id();
				
				count++;
				affected = mob;
			}
		}
		
		switch (count) {
		case 0:
			GLog.i(Messages.get(R.class,"1"));
			break;
		case 1:
			GLog.i( Messages.format(Messages.get(R.class,"2"),affected.name) );
			break;
		default:
			GLog.i( Messages.get(R.class,"3") );
		}
		setKnown();
		
		readAnimation();
	}

	@Override
	public int price() {
		return isKnown() ? 50 * quantity : super.price();
	}
}
