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
import com.skpd.pixeldungeonskills.actors.buffs.Sleep;
import com.skpd.pixeldungeonskills.actors.mobs.Mob;
import com.skpd.pixeldungeonskills.effects.Speck;
import com.skpd.pixeldungeonskills.levels.Level;
import com.skpd.pixeldungeonskills.messages.Messages;
import com.skpd.pixeldungeonskills.utils.GLog;

public class H extends Scroll {

	@Override
	protected void doRead() {
		
		curUser.sprite.centerEmitter().start( Speck.factory( Speck.NOTE ), 0.3f, 5 );
		Sample.INSTANCE.play( Assets.SND_LULLABY );
		Invisibility.dispel();
		
		int count = 0;
		Mob affected = null;
		for (Mob mob : Dungeon.level.mobs.toArray( new Mob[0] )) {
			if (Level.fieldOfView[mob.pos]) {
				Buff.affect( mob, Sleep.class );
				if (mob.buff( Sleep.class ) != null) {
					affected = mob;
					count++;
				}
			}
		}
		
		switch (count) {
		case 0:
			GLog.i( Messages.get(H.class,"1") );
			break;
		case 1:
			GLog.i(Messages.format(Messages.get(H.class,"2"),affected.name));
			break;
		default:
			GLog.i(Messages.get(H.class,"3"));
		}
		setKnown();
		
		readAnimation();
	}

	@Override
	public int price() {
		return isKnown() ? 50 * quantity : super.price();
	}
}
