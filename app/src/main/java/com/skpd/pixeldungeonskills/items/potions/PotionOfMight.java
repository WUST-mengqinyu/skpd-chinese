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
package com.skpd.pixeldungeonskills.items.potions;

import com.skpd.pixeldungeonskills.Badges;
import com.skpd.pixeldungeonskills.actors.hero.Hero;
import com.skpd.pixeldungeonskills.messages.Messages;
import com.skpd.pixeldungeonskills.sprites.CharSprite;
import com.skpd.pixeldungeonskills.utils.GLog;

public class PotionOfMight extends PotionOfStrength {
	
	@Override
	protected void apply( Hero hero ) {
		setKnown();
		
		hero.STR++;
		hero.HT += 5;
		hero.HP += 5;
		hero.sprite.showStatus( CharSprite.POSITIVE, Messages.get(PotionOfMight.class,"1") );
		GLog.p(Messages.get(PotionOfMight.class,"2"));
		
		Badges.validateStrengthAttained();
	}

	@Override
	public int price() {
		return isKnown() ? 200 * quantity : super.price();
	}
}
