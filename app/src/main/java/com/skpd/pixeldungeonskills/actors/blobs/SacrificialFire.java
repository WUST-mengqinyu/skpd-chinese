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
package com.skpd.pixeldungeonskills.actors.blobs;

import com.skpd.noosa.audio.Sample;
import com.skpd.pixeldungeonskills.Assets;
import com.skpd.pixeldungeonskills.Dungeon;
import com.skpd.pixeldungeonskills.DungeonTilemap;
import com.skpd.pixeldungeonskills.Journal;
import com.skpd.pixeldungeonskills.Journal.Feature;
import com.skpd.pixeldungeonskills.actors.Actor;
import com.skpd.pixeldungeonskills.actors.Char;
import com.skpd.pixeldungeonskills.actors.buffs.Buff;
import com.skpd.pixeldungeonskills.actors.buffs.FlavourBuff;
import com.skpd.pixeldungeonskills.actors.hero.Hero;
import com.skpd.pixeldungeonskills.actors.mobs.Mob;
import com.skpd.pixeldungeonskills.effects.BlobEmitter;
import com.skpd.pixeldungeonskills.effects.Flare;
import com.skpd.pixeldungeonskills.effects.Wound;
import com.skpd.pixeldungeonskills.effects.particles.SacrificialParticle;
import com.skpd.pixeldungeonskills.items.scrolls.T;
import com.skpd.pixeldungeonskills.messages.Messages;
import com.skpd.pixeldungeonskills.scenes.GameScene;
import com.skpd.pixeldungeonskills.ui.BuffIndicator;
import com.skpd.pixeldungeonskills.utils.GLog;
import com.skpd.utils.Bundle;
import com.skpd.utils.Random;

public class SacrificialFire extends Blob {
	
	private static final String TXT_WORTHY		= Messages.get(SacrificialFire.class,"1");
	private static final String TXT_UNWORTHY	= Messages.get(SacrificialFire.class,"2");
	private static final String TXT_REWARD		= Messages.get(SacrificialFire.class,"3");
	
	protected int pos;
	
	@Override
	public void restoreFromBundle( Bundle bundle ) {
		super.restoreFromBundle( bundle );
		
		for (int i=0; i < LENGTH; i++) {
			if (cur[i] > 0) {
				pos = i;
				break;
			}
		}
	}
	
	@Override
	protected void evolve() {
		volume = off[pos] = cur[pos];
		Char ch = Actor.findChar( pos );
		if (ch != null) {
			if (Dungeon.visible[pos] && ch.buff( Marked.class ) == null) {
				ch.sprite.emitter().burst( SacrificialParticle.FACTORY, 20 );
				Sample.INSTANCE.play( Assets.SND_BURNING );
			}
			Buff.prolong( ch, Marked.class, Marked.DURATION );
		}
		if (Dungeon.visible[pos]) {
			Journal.add( Feature.SACRIFICIAL_FIRE );
		}
	}
	
	@Override
	public void seed( int cell, int amount ) {
		cur[pos] = 0;
		pos = cell;
		volume = cur[pos] = amount;
	}
	
	@Override
	public void use( BlobEmitter emitter ) {
		super.use( emitter );
		
		emitter.pour( SacrificialParticle.FACTORY, 0.04f );
	}
	
	public static void sacrifice( Char ch ) {
		
		Wound.hit( ch );
		
		SacrificialFire fire = (SacrificialFire)Dungeon.level.blobs.get( SacrificialFire.class );
		if (fire != null) {
			
			int exp = 0;
			if (ch instanceof Mob) {
				exp = ((Mob)ch).exp() * Random.IntRange( 1, 3 );
			} else if (ch instanceof Hero) {
				exp = ((Hero)ch).maxExp();
			}
			
			if (exp > 0) {
				
				int volume = fire.volume - exp;
				if (volume > 0) {
					fire.seed( fire.pos, volume );
					GLog.w( TXT_WORTHY );
				} else {
					fire.seed( fire.pos, 0 );
					Journal.remove( Feature.SACRIFICIAL_FIRE );
					
					GLog.w( TXT_REWARD );
					GameScene.effect( new Flare( 7, 32 ).color( 0x66FFFF, true ).show( ch.sprite.parent, DungeonTilemap.tileCenterToWorld( fire.pos ), 2f ) );
					Dungeon.level.drop( new T(), fire.pos ).sprite.drop();
				}
			} else {
				
				GLog.w( TXT_UNWORTHY );
				
			}
		}
	}
	
	@Override
	public String tileDesc() {
		return Messages.get(SacrificialFire.class,"4");
	}
	
	public static class Marked extends FlavourBuff {

		public static final float DURATION	= 5f;
		
		@Override
		public int icon() {
			return BuffIndicator.SACRIFICE;
		}
		
		@Override
		public String toString() {
			return Messages.get(SacrificialFire.class,"5");
		}
		
		@Override
		public void detach() {
			if (!target.isAlive()) {
				sacrifice( target );
			}
			super.detach();
		}
	}

}
