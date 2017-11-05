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
import com.skpd.pixeldungeonskills.actors.buffs.Invisibility;
import com.skpd.pixeldungeonskills.actors.hero.Hero;
import com.skpd.pixeldungeonskills.items.wands.WandOfBlink;
import com.skpd.pixeldungeonskills.messages.Messages;
import com.skpd.pixeldungeonskills.utils.GLog;

public class Q extends Scroll {

	public static final String TXT_TELEPORTED = 
		Messages.get(Q.class,"1");
	
	public static final String TXT_NO_TELEPORT =
			Messages.get(Q.class,"2");

	@Override
	protected void doRead() {

		Sample.INSTANCE.play( Assets.SND_READ );
		Invisibility.dispel();
		
		teleportHero( curUser );
		setKnown();
		
		readAnimation();
	}
	
	public static void teleportHero( Hero  hero ) {

		int count = 10;
		int pos;
		do {
			pos = Dungeon.level.randomRespawnCell();
			if (count-- <= 0) {
				break;
			}
		} while (pos == -1);
		
		if (pos == -1) {
			
			GLog.w( TXT_NO_TELEPORT );
			
		} else {

			WandOfBlink.appear( hero, pos );
			Dungeon.level.press( pos, hero );
			Dungeon.observe();
			
			GLog.i( TXT_TELEPORTED );
			
		}
	}
	
	@Override
	public int price() {
		return isKnown() ? 40 * quantity : super.price();
	}
}
