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
package com.skpd.pixeldungeonskills.windows;

import com.skpd.pixeldungeonskills.Dungeon;
import com.skpd.pixeldungeonskills.actors.hero.Hero;
import com.skpd.pixeldungeonskills.actors.mobs.npcs.Wandmaker;
import com.skpd.pixeldungeonskills.items.Item;
import com.skpd.pixeldungeonskills.messages.Messages;
import com.skpd.pixeldungeonskills.utils.GLog;
import com.skpd.pixeldungeonskills.utils.Utils;

public class WndWandmaker extends WndQuest {
	
	private static final String TXT_MESSAGE		= Messages.get(WndWandmaker.class,"1");
	private static final String TXT_BATTLE		= Messages.get(WndWandmaker.class,"2");
	private static final String TXT_NON_BATTLE	= Messages.get(WndWandmaker.class,"3");
	
	private static final String TXT_FARAWELL	= Messages.get(WndWandmaker.class,"4");
	
	private Wandmaker wandmaker;
	private Item questItem;
	
	
	public WndWandmaker( final Wandmaker wandmaker, final Item item ) {
		
		super( wandmaker, TXT_MESSAGE, TXT_BATTLE, TXT_NON_BATTLE );
		
		this.wandmaker = wandmaker;
		questItem = item;
	}
	
	@Override
	protected void onSelect( int index ) {

		questItem.detach( Dungeon.hero.belongings.backpack );
		
		Item reward = index == 0 ? Wandmaker.Quest.wand1 : Wandmaker.Quest.wand2;
		reward.identify();
		if (reward.doPickUp( Dungeon.hero )) {
			GLog.i( Hero.TXT_YOU_NOW_HAVE, reward.name() );
		} else {
			Dungeon.level.drop( reward, wandmaker.pos ).sprite.drop();
		}
		
		wandmaker.yell( Utils.format( TXT_FARAWELL, Dungeon.hero.className() ) );
		wandmaker.destroy();
		
		wandmaker.sprite.die();
		
		Wandmaker.Quest.complete();
	}
}
