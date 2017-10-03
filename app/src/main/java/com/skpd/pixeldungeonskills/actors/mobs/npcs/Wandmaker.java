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
import com.skpd.pixeldungeonskills.Journal;
import com.skpd.pixeldungeonskills.actors.Actor;
import com.skpd.pixeldungeonskills.actors.Char;
import com.skpd.pixeldungeonskills.actors.buffs.Buff;
import com.skpd.pixeldungeonskills.items.Heap;
import com.skpd.pixeldungeonskills.items.Item;
import com.skpd.pixeldungeonskills.items.quest.CorpseDust;
import com.skpd.pixeldungeonskills.items.quest.PhantomFish;
import com.skpd.pixeldungeonskills.items.wands.Wand;
import com.skpd.pixeldungeonskills.items.wands.WandOfAmok;
import com.skpd.pixeldungeonskills.items.wands.WandOfAvalanche;
import com.skpd.pixeldungeonskills.items.wands.WandOfBlink;
import com.skpd.pixeldungeonskills.items.wands.WandOfDisintegration;
import com.skpd.pixeldungeonskills.items.wands.WandOfFirebolt;
import com.skpd.pixeldungeonskills.items.wands.WandOfLightning;
import com.skpd.pixeldungeonskills.items.wands.WandOfPoison;
import com.skpd.pixeldungeonskills.items.wands.WandOfReach;
import com.skpd.pixeldungeonskills.items.wands.WandOfRegrowth;
import com.skpd.pixeldungeonskills.items.wands.WandOfSlowness;
import com.skpd.pixeldungeonskills.levels.Level;
import com.skpd.pixeldungeonskills.levels.PrisonLevel;
import com.skpd.pixeldungeonskills.levels.Room;
import com.skpd.pixeldungeonskills.levels.Terrain;
import com.skpd.pixeldungeonskills.messages.Messages;
import com.skpd.pixeldungeonskills.plants.Rotberry;
import com.skpd.pixeldungeonskills.scenes.GameScene;
import com.skpd.pixeldungeonskills.sprites.WandmakerSprite;
import com.skpd.pixeldungeonskills.utils.Utils;
import com.skpd.pixeldungeonskills.windows.WndQuest;
import com.skpd.pixeldungeonskills.windows.WndWandmaker;
import com.skpd.utils.Bundle;
import com.skpd.utils.Random;

import java.util.ArrayList;

public class Wandmaker extends NPC {

	{	
		name = Messages.get(Wandmaker.class,"1");
		spriteClass = WandmakerSprite.class;
	}
	
	@Override
	protected boolean act() {
		throwItem();
		return super.act();
	}
	
	@Override
	public int defenseSkill( Char enemy ) {
		return 1000;
	}
	
	@Override
	public String defenseVerb() {
		return Messages.get(Wandmaker.class,"2");
	}
	
	@Override
	public void damage( int dmg, Object src ) {
	}
	
	@Override
	public void add( Buff buff ) {
	}
	
	@Override
	public boolean reset() {
		return true;
	}
	
	@Override
	public void interact() {
		sprite.turnTo( pos, Dungeon.hero.pos );
		Quest.type.handler.interact( this );
	}
	
	private void tell( String format, Object...args ) {
		GameScene.show( new WndQuest( this, Utils.format( format, args ) ) );
	}
	
	@Override
	public String description() {
		return Messages.get(Wandmaker.class,"3");
	}
	
	public static class Quest {
		
		enum Type {
			ILLEGAL( null ), BERRY( berryQuest ), DUST( dustQuest ), FISH( fishQuest );
			
			public QuestHandler handler;
			private Type( QuestHandler handler ) {
				this.handler = handler;
			}
		}
		
		private static Type type;
		
		private static boolean spawned;
		private static boolean given;
		
		public static Wand wand1;
		public static Wand wand2;
		
		public static void reset() {
			spawned = false;

			wand1 = null;
			wand2 = null;
		}
		
		private static final String NODE		= "wandmaker";
		
		private static final String SPAWNED		= "spawned";
		private static final String TYPE		= "type";
		private static final String ALTERNATIVE	= "alternative";
		private static final String GIVEN		= "given";
		private static final String WAND1		= "wand1";
		private static final String WAND2		= "wand2";
		
		public static void storeInBundle( Bundle bundle ) {
			
			Bundle node = new Bundle();
			
			node.put( SPAWNED, spawned );
			
			if (spawned) {
				
				node.put( TYPE, type.toString() );
				
				node.put( GIVEN, given );
				
				node.put( WAND1, wand1 );
				node.put( WAND2, wand2 );
			}
			
			bundle.put( NODE, node );
		}
		
		public static void restoreFromBundle( Bundle bundle ) {

			Bundle node = bundle.getBundle( NODE );
			
			if (!node.isNull() && (spawned = node.getBoolean( SPAWNED ))) {
				
				type = node.getEnum( TYPE, Type.class );
				if (type == Type.ILLEGAL) {
					type = node.getBoolean( ALTERNATIVE ) ? Type.DUST : Type.BERRY;
				}
				
				given = node.getBoolean( GIVEN );
				
				wand1 = (Wand)node.get( WAND1 );
				wand2 = (Wand)node.get( WAND2 );
			} else {
				reset();
			}
		}
		
		public static void spawn( PrisonLevel level, Room room ) {
			if (!spawned && Dungeon.depth > 6 && Random.Int( 10 - Dungeon.depth ) == 0) {
				
				Wandmaker npc = new Wandmaker();
				do {
					npc.pos = room.random();
				} while (level.map[npc.pos] == Terrain.ENTRANCE || level.map[npc.pos] == Terrain.SIGN);
				level.mobs.add( npc );
				Actor.occupyCell( npc );
				
				spawned = true;
				switch (Random.Int( 3 )) {
				case 0:
					type = Type.BERRY;
					break;
				case 1:
					type = Type.DUST;
					break;
				case 2:
					type = Type.FISH;
					int water = 0;
					for (int i=0; i < Level.LENGTH; i++) {
						if (Level.water[i]) {
							if (++water > Level.LENGTH / 16) {
								type = Random.Int( 2 ) == 0 ? Type.BERRY : Type.DUST;
								break;
							}
						}
					}
					break;
				}
				
				given = false;
				
				switch (Random.Int( 5 )) {
				case 0:
					wand1 = new WandOfAvalanche();
					break;
				case 1:
					wand1 = new WandOfDisintegration();
					break;
				case 2:
					wand1 = new WandOfFirebolt();
					break;
				case 3:
					wand1 = new WandOfLightning();
					break;
				case 4:
					wand1 = new WandOfPoison();
					break;
				}
				wand1.random().upgrade();
				
				switch (Random.Int( 5 )) {
				case 0:
					wand2 = new WandOfAmok();
					break;
				case 1:
					wand2 = new WandOfBlink();
					break;
				case 2:
					wand2 = new WandOfRegrowth();
					break;
				case 3:
					wand2 = new WandOfSlowness();
					break;
				case 4:
					wand2 = new WandOfReach();
					break;
				}
				wand2.random().upgrade();
			}
		}
		
		public static void complete() {
			wand1 = null;
			wand2 = null;
			
			Journal.remove( Journal.Feature.WANDMAKER );
		}
	}
	
	abstract public static class QuestHandler {
			
		protected String txtQuest1;
		protected String txtQuest2;
		
		public void interact( Wandmaker wandmaker ) {
			if (Quest.given) {
				
				Item item = checkItem();
				if (item != null) {
					GameScene.show( new WndWandmaker( wandmaker, item ) );
				} else {
					wandmaker.tell( txtQuest2, Dungeon.hero.className() );
				}
				
			} else {
				wandmaker.tell( txtQuest1 );
				Quest.given = true;
				
				placeItem();
				
				Journal.add( Journal.Feature.WANDMAKER );
			}
		}
		
		abstract protected Item checkItem();
		abstract protected void placeItem();
	}
	
	private static final QuestHandler berryQuest = new QuestHandler() {
		{
			txtQuest1 = Messages.get(Wandmaker.class,"4");
			txtQuest2 = Messages.get(Wandmaker.class,"5");
		}

		@Override
		protected Item checkItem() {
			return Dungeon.hero.belongings.getItem( Rotberry.Seed.class );
		}

		@Override
		protected void placeItem() {
			int shrubPos = Dungeon.level.randomRespawnCell();
			while (Dungeon.level.heaps.get( shrubPos ) != null) {
				shrubPos = Dungeon.level.randomRespawnCell();
			}
			Dungeon.level.plant( new Rotberry.Seed(), shrubPos );
		}
	};
	
	private static final QuestHandler dustQuest = new QuestHandler() {
		{
			txtQuest1 = Messages.get(Wandmaker.class,"6");
			txtQuest2 = Messages.get(Wandmaker.class,"7");
		}
		
		@Override
		protected Item checkItem() {
			return Dungeon.hero.belongings.getItem( CorpseDust.class );
		}

		@Override
		protected void placeItem() {
			ArrayList<Heap> candidates = new ArrayList<Heap>();
			for (Heap heap : Dungeon.level.heaps.values()) {
				if (heap.type == Heap.Type.SKELETON && !Dungeon.visible[heap.pos]) {
					candidates.add( heap );
				}
			}
			
			if (candidates.size() > 0) {
				Random.element( candidates ).drop( new CorpseDust() );
			} else {
				int pos = Dungeon.level.randomRespawnCell();
				while (Dungeon.level.heaps.get( pos ) != null) {
					pos = Dungeon.level.randomRespawnCell();
				}
				
				Heap heap = Dungeon.level.drop( new CorpseDust(), pos );
				heap.type = Heap.Type.SKELETON;
				heap.sprite.link();
			}
		}
	};
	
	private static final QuestHandler fishQuest = new QuestHandler() {
		{
			txtQuest1 = Messages.get(Wandmaker.class,"8");

			txtQuest2 = Messages.get(Wandmaker.class,"9");
		}
		
		@Override
		protected Item checkItem() {
			return Dungeon.hero.belongings.getItem( PhantomFish.class );
		}

		@Override
		protected void placeItem() {
			Heap heap = null;
			for (int i=0; i < 100; i++) {
				int pos = Random.Int( Level.LENGTH );
				if (Level.water[pos]) {
					heap = Dungeon.level.drop( new PhantomFish(), pos );
					heap.type = Heap.Type.HIDDEN;
					heap.sprite.link();
					return;
				}
			}
			if (heap == null) {
				int pos = Dungeon.level.randomRespawnCell();
				while (Dungeon.level.heaps.get( pos ) != null) {
					pos = Dungeon.level.randomRespawnCell();
				}
				
				Dungeon.level.drop( new PhantomFish(), pos );
			}
		}
	};
}
