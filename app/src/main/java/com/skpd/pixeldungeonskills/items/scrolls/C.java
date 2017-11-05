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
import com.skpd.pixeldungeonskills.actors.buffs.Buff;
import com.skpd.pixeldungeonskills.actors.buffs.Invisibility;
import com.skpd.pixeldungeonskills.actors.buffs.Rage;
import com.skpd.pixeldungeonskills.actors.mobs.Mimic;
import com.skpd.pixeldungeonskills.actors.mobs.Mob;
import com.skpd.pixeldungeonskills.effects.Speck;
import com.skpd.pixeldungeonskills.items.Heap;
import com.skpd.pixeldungeonskills.levels.Level;
import com.skpd.pixeldungeonskills.messages.Messages;
import com.skpd.pixeldungeonskills.utils.GLog;

public class C extends Scroll {

	@Override
	protected void doRead() {

		for (Mob mob : Dungeon.level.mobs.toArray(new Mob[0])) {
			mob.beckon(curUser.pos);
			if (Dungeon.visible[mob.pos]) {
				Buff.affect(mob, Rage.class, Level.distance(curUser.pos, mob.pos));
			}
		}

		for (Heap heap : Dungeon.level.heaps.values()) {
			if (heap.type == Heap.Type.MIMIC) {
				Mimic m = Mimic.spawnAt(heap.pos, heap.items);
				if (m != null) {
					m.beckon(curUser.pos);
					heap.destroy();
				}
			}
		}

		GLog.w(Messages.get(C.class,"1"));
		setKnown();

		curUser.sprite.centerEmitter().start(Speck.factory(Speck.SCREAM), 0.3f, 3);
		Sample.INSTANCE.play(Assets.SND_CHALLENGE);
		Invisibility.dispel();

		readAnimation();
	}
}
