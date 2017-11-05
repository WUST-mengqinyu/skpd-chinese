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
import com.skpd.pixeldungeonskills.actors.mobs.Mob;
import com.skpd.pixeldungeonskills.levels.Level;
import com.skpd.pixeldungeonskills.messages.Messages;
import com.skpd.pixeldungeonskills.scenes.GameScene;
import com.skpd.pixeldungeonskills.ui.StatusPane;
import com.skpd.pixeldungeonskills.utils.GLog;
import com.skpd.utils.Random;

public class B extends Scroll {

	@Override
	protected void doRead() {
		
		GameScene.flash( 0xFF0000 );
		
		Sample.INSTANCE.play( Assets.SND_BLAST );
		Invisibility.dispel();

        int damage = (int)Math.round(Dungeon.hero.HT * 0.2);

		for (Mob mob : Dungeon.level.mobs.toArray( new Mob[0] )) {
			if (Level.fieldOfView[mob.pos]) {
				mob.damage( Random.IntRange( damage , damage ), this );
			}
		}

        Dungeon.hero.HT -= damage;
        Dungeon.hero.HP = Dungeon.hero.HT;
        StatusPane.takingDamage = 0;

        GLog.n(Messages.format(Messages.get(B.class,"1"),damage));
        GLog.p(Messages.get(B.class,"2"));
		Dungeon.observe();
		
		setKnown();
		
		curUser.spendAndNext( TIME_TO_READ );
	}

	@Override
	public int price() {
		return isKnown() ? 80 * quantity : super.price();
	}
}
