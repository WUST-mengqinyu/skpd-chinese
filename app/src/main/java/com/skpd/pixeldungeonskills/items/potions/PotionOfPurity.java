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

import com.skpd.noosa.audio.Sample;
import com.skpd.pixeldungeonskills.Assets;
import com.skpd.pixeldungeonskills.Dungeon;
import com.skpd.pixeldungeonskills.actors.blobs.Blob;
import com.skpd.pixeldungeonskills.actors.blobs.ParalyticGas;
import com.skpd.pixeldungeonskills.actors.blobs.ToxicGas;
import com.skpd.pixeldungeonskills.actors.buffs.Buff;
import com.skpd.pixeldungeonskills.actors.buffs.GasesImmunity;
import com.skpd.pixeldungeonskills.actors.hero.Hero;
import com.skpd.pixeldungeonskills.effects.CellEmitter;
import com.skpd.pixeldungeonskills.effects.Speck;
import com.skpd.pixeldungeonskills.levels.Level;
import com.skpd.pixeldungeonskills.messages.Messages;
import com.skpd.pixeldungeonskills.utils.BArray;
import com.skpd.pixeldungeonskills.utils.GLog;
import com.skpd.utils.PathFinder;

public class PotionOfPurity extends Potion {

	private static final String TXT_FRESHNESS	= Messages.get(PotionOfPurity.class,"1");
	private static final String TXT_NO_SMELL	=  Messages.get(PotionOfPurity.class,"2");
	
	private static final int DISTANCE	= 2;

	@Override
	public void shatter( int cell ) {
		
		PathFinder.buildDistanceMap( cell, BArray.not( Level.losBlocking, null ), DISTANCE );
		
		boolean procd = false;
		
		Blob[] blobs = {
			Dungeon.level.blobs.get( ToxicGas.class ), 
			Dungeon.level.blobs.get( ParalyticGas.class )
		};
		
		for (int j=0; j < blobs.length; j++) {
			
			Blob blob = blobs[j];
			if (blob == null) {
				continue;
			}
			
			for (int i=0; i < Level.LENGTH; i++) {
				if (PathFinder.distance[i] < Integer.MAX_VALUE) {
					
					int value = blob.cur[i]; 
					if (value > 0) {
						
						blob.cur[i] = 0;
						blob.volume -= value;
						procd = true;
						
						if (Dungeon.visible[i]) {
							CellEmitter.get( i ).burst( Speck.factory( Speck.DISCOVER ), 1 );
						}
					}

				}
			}
		}
		
		boolean heroAffected = PathFinder.distance[Dungeon.hero.pos] < Integer.MAX_VALUE;
		
		if (procd) {
			
			if (Dungeon.visible[cell]) {
				splash( cell );
				Sample.INSTANCE.play( Assets.SND_SHATTER );
			}
			
			setKnown();
			
			if (heroAffected) {
				GLog.p( TXT_FRESHNESS );
			}
			
		} else {
			
			super.shatter( cell );
			
			if (heroAffected) {
				GLog.i( TXT_FRESHNESS );
				setKnown();
			}
			
		}
	}
	
	@Override
	protected void apply( Hero hero ) {
		GLog.w( TXT_NO_SMELL );
		Buff.prolong( hero, GasesImmunity.class, GasesImmunity.DURATION );
		setKnown();
	}

	@Override
	public int price() {
		return isKnown() ? 50 * quantity : super.price();
	}
}
