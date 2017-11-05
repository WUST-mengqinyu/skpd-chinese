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
package com.skpd.pixeldungeonskills.items;

import com.skpd.pixeldungeonskills.Dungeon;
import com.skpd.pixeldungeonskills.actors.hero.Hero;
import com.skpd.pixeldungeonskills.messages.Messages;
import com.skpd.pixeldungeonskills.sprites.ItemSprite.Glowing;
import com.skpd.pixeldungeonskills.sprites.ItemSpriteSheet;
import com.skpd.pixeldungeonskills.utils.GLog;

import java.util.ArrayList;

public class BombTriggerBeacon extends Item {


	
	public static final float TIME_TO_USE = 1;
	


	
	{
		image = ItemSpriteSheet.BEACON;
		unique = true;
	}


    @Override
    public ArrayList<String> actions( Hero hero ) {
        ArrayList<String> actions = super.actions( hero );
        actions.remove( AC_THROW );
//// FIXME: 2017/10/10 
            if(actions.contains(Messages.get(BombTriggerBeacon.class,"1")) == false)
                actions.add(Messages.get(BombTriggerBeacon.class,"1"));

        return actions;
    }
	
	@Override
	public void execute( Hero hero, String action ) {

        if (action == Messages.get(BombTriggerBeacon.class,"1"))
        {
            GLog.i(Messages.get(BombTriggerBeacon.class,"2"));
            int key = 0;
            for(int i = 0; i < Dungeon.level.heaps.size(); i++) {
                key = Dungeon.level.heaps.keyAt(i);
                Heap heap = Dungeon.level.heaps.get(key);

                for(Item item: heap.items)
                {
                    if(item instanceof RemoteBombGround)
                    {
                        ((RemoteBombGround)item).explode();

                    }
                }

                //heap.removeRemoteBombs();
                if (heap.isEmpty()) {
                    heap.destroy();
                } else if (heap.sprite != null) {
                    heap.sprite.view( heap.image(), heap.glowing() );
                }
            }
            Dungeon.observe();
            hero.spend(5);
			
		} else {
			
			super.execute( hero, action );
			
		}
	}
	

	
	@Override
	public boolean isUpgradable() {
		return false;
	}
	
	@Override
	public boolean isIdentified() {
		return true;
	}
	
	private static final Glowing WHITE = new Glowing( 0xFFFFFF );

}
