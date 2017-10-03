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
package com.skpd.pixeldungeonskills.actors.mobs.npcs;

import com.skpd.pixeldungeonskills.Dungeon;
import com.skpd.pixeldungeonskills.effects.CellEmitter;
import com.skpd.pixeldungeonskills.effects.Speck;
import com.skpd.pixeldungeonskills.effects.particles.ElmoParticle;
import com.skpd.pixeldungeonskills.items.Heap;
import com.skpd.pixeldungeonskills.messages.Messages;
import com.skpd.pixeldungeonskills.sprites.ImpSprite;
import com.skpd.pixeldungeonskills.utils.Utils;

public class ImpShopkeeper extends Shopkeeper {

	private static final String TXT_GREETINGS = Messages.get(ImpShopkeeper.class,"2");
	
	{
		name = Messages.get(ImpShopkeeper.class,"1");
		spriteClass = ImpSprite.class;
	}
	
	private boolean seenBefore = false;
	
	@Override
	protected boolean act() {

		if (!seenBefore && Dungeon.visible[pos]) {
			yell( Utils.format( TXT_GREETINGS ) );
			seenBefore = true;
		}
		
		return super.act();
	}
	
	@Override
	protected void flee() {
		for (Heap heap: Dungeon.level.heaps.values()) {
			if (heap.type == Heap.Type.FOR_SALE) {
				CellEmitter.get( heap.pos ).burst( ElmoParticle.FACTORY, 4 );
				heap.destroy();
			}
		}
		
		destroy();
		
		sprite.emitter().burst( Speck.factory( Speck.WOOL ), 15 );
		sprite.killAndErase();
	}
	
	@Override
	public String description() {
		return Messages.get(ImpShopkeeper.class,"3");
	}
}
