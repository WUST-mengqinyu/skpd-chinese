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
package com.skpd.pixeldungeonskills.items.weapon.missiles;

import com.skpd.pixeldungeonskills.Dungeon;
import com.skpd.pixeldungeonskills.actors.Char;
import com.skpd.pixeldungeonskills.actors.buffs.Buff;
import com.skpd.pixeldungeonskills.actors.buffs.Charm;
import com.skpd.pixeldungeonskills.actors.hero.Hero;
import com.skpd.pixeldungeonskills.actors.mobs.Bestiary;
import com.skpd.pixeldungeonskills.effects.Speck;
import com.skpd.pixeldungeonskills.items.Item;
import com.skpd.pixeldungeonskills.sprites.ItemSpriteSheet;
import com.skpd.utils.Random;

import java.util.ArrayList;

public class CupidArrow extends Arrow {

	{
		image = ItemSpriteSheet.CupidArrow;

        stackable = true;
	}

	public CupidArrow() {
		this( 1 );
	}

	public CupidArrow(int number) {
		super();
		quantity = number;
	}

    @Override
    public Item random() {
        quantity = Random.Int(3, 5);
        return this;
    }

    @Override
    public void arrowEffect(Char attacker, Char defender)
    {
        if(Bestiary.isBoss(defender))
            return;
        int duration = Random.IntRange( 5, 10 );
        Buff.affect( defender, Charm.class, Charm.durationFactor( defender ) * duration ).object = attacker.id();
        defender.sprite.centerEmitter().start( Speck.factory(Speck.HEART), 0.2f, 5 );
    }

	@Override
	public int price() {
		return quantity * 15;
	}

    @Override
    public ArrayList<String> actions( Hero hero ) {
        ArrayList<String> actions = super.actions( hero );
        if(Dungeon.hero.belongings.bow != null) {
            if(actions.contains(AC_THROW) == false)
            actions.add(AC_THROW);
        }
        else
            actions.remove( AC_THROW );
        actions.remove(AC_EQUIP);

        return actions;
    }
}
