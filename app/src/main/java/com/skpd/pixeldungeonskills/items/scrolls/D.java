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

import com.skpd.pixeldungeonskills.Dungeon;
import com.skpd.pixeldungeonskills.effects.Enchanting;
import com.skpd.pixeldungeonskills.effects.Speck;
import com.skpd.pixeldungeonskills.items.Item;
import com.skpd.pixeldungeonskills.items.armor.Armor;
import com.skpd.pixeldungeonskills.items.weapon.Weapon;
import com.skpd.pixeldungeonskills.items.weapon.missiles.Bow;
import com.skpd.pixeldungeonskills.messages.Messages;
import com.skpd.pixeldungeonskills.utils.GLog;
import com.skpd.pixeldungeonskills.windows.WndBag;

public class D extends A {

	private static final String TXT_GLOWS	= Messages.get(D.class,"1");
    private static final String TXT_BOW	= Messages.get(D.class,"2");
	{
		inventoryTitle = Messages.get(D.class,"3");
		mode = WndBag.Mode.ENCHANTABLE;
	}
	
	@Override
	protected void onItemSelected( Item item ) {

		N.uncurse( Dungeon.hero, item );


        if (item instanceof Bow) {

            Bow newBow =  ((Bow)item).enchant();
            if(curUser.belongings.bow != null)
                curUser.belongings.bow = newBow;
            else
            {
                item.detach(Dungeon.hero.belongings.backpack);
                newBow.collect();
            }

            GLog.w(TXT_BOW, newBow.name());
        }
        else if (item instanceof Weapon) {
			
			((Weapon)item).enchant();
			
		} else {

			((Armor)item).inscribe();
		
		}
		
		item.fix();
		
		curUser.sprite.emitter().start( Speck.factory( Speck.LIGHT ), 0.1f, 5 );
        if (!(item instanceof Bow)) {
            Enchanting.show(curUser, item);
            GLog.w(TXT_GLOWS, item.name());
        }
	}

}
