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

import com.skpd.pixeldungeonskills.actors.Char;
import com.skpd.pixeldungeonskills.actors.buffs.Bleeding;
import com.skpd.pixeldungeonskills.actors.buffs.Buff;
import com.skpd.pixeldungeonskills.items.Item;
import com.skpd.pixeldungeonskills.sprites.ItemSpriteSheet;
import com.skpd.utils.Random;

public class Tomahawk extends MissileWeapon {

	{
		image = ItemSpriteSheet.TOMAHAWK;
		
		STR = 17;
	}
	
	public Tomahawk() {
		this( 1 );
	}
	
	public Tomahawk(int number ) {
		super();
		quantity = number;
	}
	
	@Override
	public int min() {
		return 4;
	}
	
	@Override
	public int max() {
		return 20;
	}
	
	@Override
	public void proc( Char attacker, Char defender, int damage ) {
		super.proc( attacker, defender, damage );
		Buff.affect( defender, Bleeding.class ).set( damage );
	}	

	@Override
	public Item random() {
		quantity = Random.Int( 5, 12 );
		return this;
	}
	
	@Override
	public int price() {
		return 20 * quantity;
	}
}
