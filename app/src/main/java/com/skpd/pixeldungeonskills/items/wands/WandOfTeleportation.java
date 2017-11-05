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
package com.skpd.pixeldungeonskills.items.wands;

import com.skpd.noosa.audio.Sample;
import com.skpd.pixeldungeonskills.Assets;
import com.skpd.pixeldungeonskills.Dungeon;
import com.skpd.pixeldungeonskills.actors.Actor;
import com.skpd.pixeldungeonskills.actors.Char;
import com.skpd.pixeldungeonskills.effects.MagicMissile;
import com.skpd.pixeldungeonskills.items.scrolls.Q;
import com.skpd.pixeldungeonskills.messages.Languages;
import com.skpd.pixeldungeonskills.messages.Messages;
import com.skpd.pixeldungeonskills.utils.GLog;
import com.skpd.utils.Callback;

public class WandOfTeleportation extends Wand {

	@Override
	protected void onZap( int cell ) {
		
		Char ch = Actor.findChar( cell );
		
		if (ch == curUser) {
			
			setKnown();
			Q.teleportHero( curUser );
			
		} else if (ch != null) {
			
			int count = 10;
			int pos;
			do {
				pos = Dungeon.level.randomRespawnCell();
				if (count-- <= 0) {
					break;
				}
			} while (pos == -1);
			
			if (pos == -1) {
				
				GLog.w( Q.TXT_NO_TELEPORT );
				
			} else {
			
				ch.pos = pos;
				ch.sprite.place( ch.pos );
				ch.sprite.visible = Dungeon.visible[pos];
				if(Messages.lang()== Languages.ENGLISH)
					GLog.i( curUser.name + " teleported " + ch.name + " to somewhere" );
				else
					GLog.i(curUser.name+"将"+ch.name+"传送到了某处。");

			}

		} else {
			
			GLog.i(Messages.get(WandOfMagicCasting.class,"4"));
			
		}
	}
	
	protected void fx( int cell, Callback callback ) {
		MagicMissile.coldLight( curUser.sprite.parent, curUser.pos, cell, callback );
		Sample.INSTANCE.play( Assets.SND_ZAP );
	}

}
