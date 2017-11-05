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
import com.skpd.pixeldungeonskills.actors.buffs.Invisibility;
import com.skpd.pixeldungeonskills.actors.hero.Hero;
import com.skpd.pixeldungeonskills.effects.SpellSprite;
import com.skpd.pixeldungeonskills.effects.particles.EnergyParticle;
import com.skpd.pixeldungeonskills.messages.Messages;
import com.skpd.pixeldungeonskills.utils.GLog;

public class M extends Scroll {

	@Override
	protected void doRead() {
		
		int count = curUser.belongings.charge( true );		
		charge( curUser );
		
		Sample.INSTANCE.play( Assets.SND_READ );
		Invisibility.dispel();
		
		if (count > 0) {
			GLog.i(Messages.get(M.class,"1"));
			SpellSprite.show( curUser, SpellSprite.CHARGE );
		} else {
			GLog.i( Messages.get(M.class,"2") );
		}
		setKnown();
		
		readAnimation();
	}

	public static void charge( Hero hero ) {
		hero.sprite.centerEmitter().burst( EnergyParticle.FACTORY, 15 );
	}
	
	@Override
	public int price() {
		return isKnown() ? 40 * quantity : super.price();
	}
}
