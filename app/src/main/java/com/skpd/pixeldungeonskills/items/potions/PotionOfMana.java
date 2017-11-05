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

import com.skpd.pixeldungeonskills.actors.hero.Hero;
import com.skpd.pixeldungeonskills.messages.Messages;
import com.skpd.pixeldungeonskills.ui.StatusPane;
import com.skpd.pixeldungeonskills.utils.GLog;

public class PotionOfMana extends Potion {

	@Override
	protected void apply( Hero hero ) {
		setKnown();

        GLog.p(Messages.get(PotionOfMana.class,"1"));
        hero.MP = hero.MMP;
        StatusPane.manaDropping = 0;
	}
	
	@Override
	public int price() {
		return isKnown() ? 30 * quantity : super.price();
	}
}
