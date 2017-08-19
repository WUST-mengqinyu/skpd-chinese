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
package com.skpd.pixeldungeonskills.actors.mobs;

import com.skpd.pixeldungeonskills.Dungeon;
import com.skpd.pixeldungeonskills.actors.Char;
import com.skpd.pixeldungeonskills.actors.mobs.npcs.Ghost;
import com.skpd.pixeldungeonskills.items.food.MysteryMeat;
import com.skpd.pixeldungeonskills.messages.Messages;
import com.skpd.pixeldungeonskills.sprites.CrabSprite;
import com.skpd.utils.Random;

public class Crab extends Mob {

	{
		name = Messages.get(this,"1");
		spriteClass = CrabSprite.class;
		
		HP = HT = 15;
		defenseSkill = 5;
		baseSpeed = 2f;
		
		EXP = 3;
		maxLvl = 9;
		
		loot = new MysteryMeat();
		lootChance = 0.167f;

        name = Dungeon.currentDifficulty.mobPrefix() + name;
        HT *= Dungeon.currentDifficulty.mobHPModifier();
        HP = HT;
	}
	
	@Override
	public int damageRoll() {
		return Random.NormalIntRange( 3, 6 );
	}
	
	@Override
	public int attackSkill( Char target ) {
		return 12;
	}
	
	@Override
	public int dr() {
		return 4;
	}
	
	@Override
	public String defenseVerb() {
		return Messages.get(this,"2");
	}
	
	@Override
	public void die( Object cause ) {
		Ghost.Quest.processSewersKill( pos );
		super.die( cause );
	}

    @Override
    public int attackProc( Char enemy, int damage ) {
        champEffect(enemy, damage);
        return damage;
    }
	
	@Override
	public String description() {
		return
				Messages.get(this,"3");
	}
}
