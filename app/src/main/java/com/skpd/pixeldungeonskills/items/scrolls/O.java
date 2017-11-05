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
import com.skpd.pixeldungeonskills.messages.Messages;
import com.skpd.pixeldungeonskills.utils.GLog;

public class O extends Scroll {
	
	@Override
	protected void doRead() {

        Dungeon.hero.STR++;
        if(Dungeon.hero.HT > 3) {

            Dungeon.hero.HT-= 3;
        }
        else
        {
            Dungeon.hero.HT = 1;
        }

        if(Dungeon.hero.HP > Dungeon.hero.HT)
            Dungeon.hero.HP = Dungeon.hero.HT;

        GLog.w(Messages.get(O.class,"1"));
        GLog.p( Messages.get(O.class,"2") );
        GLog.n( Messages.get(O.class,"3") );
		setKnown();

		curUser.spendAndNext( TIME_TO_READ );
	}

}
