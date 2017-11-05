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

import com.skpd.pixeldungeonskills.Badges;
import com.skpd.pixeldungeonskills.effects.Identification;
import com.skpd.pixeldungeonskills.items.Item;
import com.skpd.pixeldungeonskills.messages.Languages;
import com.skpd.pixeldungeonskills.messages.Messages;
import com.skpd.pixeldungeonskills.utils.GLog;
import com.skpd.pixeldungeonskills.windows.WndBag;

public class G extends A {

	{
		inventoryTitle = Messages.get(G.class,"1");
		mode = WndBag.Mode.UNIDENTIFED;
	}
	
	@Override
	protected void onItemSelected( Item item ) {
		
		curUser.sprite.parent.add( new Identification( curUser.sprite.center().offset( 0, -16 ) ) );
		
		item.identify();
		if (Messages.lang()== Languages.ENGLISH)
		GLog.i( "It is " + item );
		else
			GLog.i("它是"+item);
		
		Badges.validateItemLevelAquired( item );
	}

	@Override
	public int price() {
		return isKnown() ? 30 * quantity : super.price();
	}
}
