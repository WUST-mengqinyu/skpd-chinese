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
import com.skpd.pixeldungeonskills.items.wands.WandOfBlink;
import com.skpd.pixeldungeonskills.messages.Messages;
import com.skpd.pixeldungeonskills.utils.GLog;

public class F extends Scroll {
	
	@Override
	protected void doRead() {

		Sample.INSTANCE.play( Assets.SND_READ );

        if(Dungeon.bossLevel() == false)
        {
            WandOfBlink.appear( curUser, Dungeon.level.entrance );
            GLog.i(Messages.get(F.class,"1"));
        }
        else
            GLog.w(Messages.get(F.class,"2"));

        Dungeon.observe();

		setKnown();
		
		curUser.spendAndNext( TIME_TO_READ );

	}

	@Override
	public int price() {
		return isKnown() ? 40 * quantity : super.price();
	}
}
