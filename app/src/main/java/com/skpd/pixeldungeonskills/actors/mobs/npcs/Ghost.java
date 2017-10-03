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

import com.skpd.noosa.audio.Sample;
import com.skpd.noosa.tweeners.AlphaTweener;
import com.skpd.pixeldungeonskills.Assets;
import com.skpd.pixeldungeonskills.Challenges;
import com.skpd.pixeldungeonskills.Dungeon;
import com.skpd.pixeldungeonskills.Journal;
import com.skpd.pixeldungeonskills.actors.Actor;
import com.skpd.pixeldungeonskills.actors.Char;
import com.skpd.pixeldungeonskills.actors.buffs.Buff;
import com.skpd.pixeldungeonskills.actors.buffs.Paralysis;
import com.skpd.pixeldungeonskills.actors.buffs.Roots;
import com.skpd.pixeldungeonskills.actors.mobs.CursePersonification;
import com.skpd.pixeldungeonskills.actors.mobs.FetidRat;
import com.skpd.pixeldungeonskills.actors.mobs.Mob;
import com.skpd.pixeldungeonskills.effects.CellEmitter;
import com.skpd.pixeldungeonskills.effects.Speck;
import com.skpd.pixeldungeonskills.effects.particles.ShadowParticle;
import com.skpd.pixeldungeonskills.items.Generator;
import com.skpd.pixeldungeonskills.items.Item;
import com.skpd.pixeldungeonskills.items.armor.Armor;
import com.skpd.pixeldungeonskills.items.armor.ClothArmor;
import com.skpd.pixeldungeonskills.items.quest.DriedRose;
import com.skpd.pixeldungeonskills.items.quest.RatSkull;
import com.skpd.pixeldungeonskills.items.weapon.Weapon;
import com.skpd.pixeldungeonskills.items.weapon.missiles.MissileWeapon;
import com.skpd.pixeldungeonskills.levels.SewerLevel;
import com.skpd.pixeldungeonskills.messages.Messages;
import com.skpd.pixeldungeonskills.scenes.GameScene;
import com.skpd.pixeldungeonskills.sprites.GhostSprite;
import com.skpd.pixeldungeonskills.utils.Utils;
import com.skpd.pixeldungeonskills.windows.WndQuest;
import com.skpd.pixeldungeonskills.windows.WndSadGhost;
import com.skpd.utils.Bundle;
import com.skpd.utils.Random;

import java.util.HashSet;

public class Ghost extends NPC {

	{
		name = Messages.get(Ghost.class,"name");
		spriteClass = GhostSprite.class;
		
		flying = true;
		
		state = WANDERING;
	}
	
	public Ghost() {
		super();

		Sample.INSTANCE.load( Assets.SND_GHOST );
	}
	
	@Override
	public int defenseSkill( Char enemy ) {
		return 1000;
	}
	
	@Override
	public String defenseVerb() {
		return "evaded";
	}
	
	@Override
	public float speed() {
		return 0.5f;
	}
	
	@Override
	protected Char chooseEnemy() {
		return null;
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
		Sample.INSTANCE.play( Assets.SND_GHOST );
		
		Quest.type.handler.interact( this );
	}
	
	@Override
	public String description() {
		return Messages.get(Ghost.class,"desc");
	}
	
	private static final HashSet<Class<?>> IMMUNITIES = new HashSet<Class<?>>();
	static {
		IMMUNITIES.add( Paralysis.class );
		IMMUNITIES.add( Roots.class );
	}
	
	@Override
	public HashSet<Class<?>> immunities() {
		return IMMUNITIES;
	}
	
	public static void replace( final Mob a, final Mob b ) {
		final float FADE_TIME = 0.5f;
		
		a.destroy();
		a.sprite.parent.add( new AlphaTweener( a.sprite, 0, FADE_TIME ) {
			protected void onComplete() {
				a.sprite.killAndErase();
				parent.erase( this );
			};
		} );
		
		b.pos = a.pos;
		GameScene.add( b );
		
		b.sprite.flipHorizontal = a.sprite.flipHorizontal;
		b.sprite.alpha( 0 );
		b.sprite.parent.add( new AlphaTweener( b.sprite, 1, FADE_TIME ) );
	}

	public static class Quest {

		enum Type {
			ILLEGAL( null ), ROSE( roseQuest ), RAT( ratQuest ), CURSE( curseQuest );
			
			public QuestHandler handler;
			private Type( QuestHandler handler ) {
				this.handler = handler;
			}
		}
		
		private static Type type;
		
		private static boolean spawned;
		private static boolean given;
		private static boolean processed;
		
		private static int depth;
		
		private static int left2kill;
		
		public static Weapon weapon;
		public static Armor armor;
		
		public static void reset() {
			spawned = false; 
			
			weapon = null;
			armor = null;
		}
		
		private static final String NODE		= "sadGhost";
		
		private static final String SPAWNED		= "spawned";
		private static final String TYPE		= "type";
		private static final String ALTERNATIVE	= "alternative";
		private static final String LEFT2KILL	= "left2kill";
		private static final String GIVEN		= "given";
		private static final String PROCESSED	= "processed";
		private static final String DEPTH		= "depth";
		private static final String WEAPON		= "weapon";
		private static final String ARMOR		= "armor";
		
		public static void storeInBundle( Bundle bundle ) {
			
			Bundle node = new Bundle();
			
			node.put( SPAWNED, spawned );
			
			if (spawned) {
				
				node.put( TYPE, type.toString() );
				if (type == Type.ROSE) {
					node.put( LEFT2KILL, left2kill );
				}
				
				node.put( GIVEN, given );
				node.put( DEPTH, depth );
				node.put( PROCESSED, processed );
				
				node.put( WEAPON, weapon );
				node.put( ARMOR, armor );
			}
			
			bundle.put( NODE, node );
		}
		
		public static void restoreFromBundle( Bundle bundle ) {
			
			Bundle node = bundle.getBundle( NODE );
			
			if (!node.isNull() && (spawned = node.getBoolean( SPAWNED ))) {
				
				type = node.getEnum( TYPE, Type.class );
				if (type == Type.ILLEGAL) {
					type = node.getBoolean( ALTERNATIVE ) ? Type.RAT : Type.ROSE;
				}
				if (type == Type.ROSE) {
					left2kill = node.getInt( LEFT2KILL );
				}
				
				given	= node.getBoolean( GIVEN );
				depth	= node.getInt( DEPTH );
				processed	= node.getBoolean( PROCESSED );
				
				weapon	= (Weapon)node.get( WEAPON );
				armor	= (Armor)node.get( ARMOR );
			} else {
				reset();
			}
		}
		
		public static void spawn( SewerLevel level ) {
			if (!spawned && Dungeon.depth > 1 && Random.Int( 5 - Dungeon.depth ) == 0) {
				
				Ghost ghost = new Ghost();
				do {
					ghost.pos = level.randomRespawnCell();
				} while (ghost.pos == -1);
				level.mobs.add( ghost );
				Actor.occupyCell( ghost );
				
				spawned = true;
				switch (Random.Int( 3 )) {
				case 0:
					type = Type.ROSE;
					left2kill = 8;
					break;
				case 1:
					type = Type.RAT;
					break;
				case 2:
					type = Type.CURSE;
					break;
				}
				
				given = false;
				processed = false;
				depth = Dungeon.depth;
				
				for (int i=0; i < 4; i++) {
					Item another;
					do {
						another = (Weapon)Generator.random( Generator.Category.WEAPON );
					} while (another instanceof MissileWeapon);
					
					if (weapon == null || another.level() > weapon.level()) {
						weapon = (Weapon)another;
					}
				}
				
				if (Dungeon.isChallenged( Challenges.NO_ARMOR )) {
					armor = (Armor)new ClothArmor().degrade();
				} else {
					armor = (Armor)Generator.random( Generator.Category.ARMOR );
					for (int i=0; i < 3; i++) {
						Item another = Generator.random( Generator.Category.ARMOR );
						if (another.level() > armor.level()) {
							armor = (Armor)another;
						}
					}
				}
				
				weapon.identify();
				armor.identify();
			}
		}

		public static void processSewersKill( int pos ) {
			if (spawned && given && !processed && (depth == Dungeon.depth)) {
				switch (type) {
				case ROSE:
					if (Random.Int( left2kill ) == 0) {
						Dungeon.level.drop( new DriedRose(), pos ).sprite.drop();
						processed = true;
					} else {
						left2kill--;
					}
					break;
				case RAT:
					FetidRat rat = new FetidRat();
					rat.pos = Dungeon.level.randomRespawnCell();
					if (rat.pos != -1) {
						GameScene.add( rat );
						processed = true;
					}
					break;
				default:
				}
			}
		}
		
		public static void complete() {
			weapon = null;
			armor = null;
			
			Journal.remove( Journal.Feature.GHOST );
		}
	}

	abstract public static class QuestHandler {
		
		abstract public void interact(  Ghost ghost ); 
		
		protected void relocate( Ghost ghost ) {
			int newPos = -1;
			for (int i=0; i < 10; i++) {
				newPos = Dungeon.level.randomRespawnCell();
				if (newPos != -1) {
					break;
				}
			}
			if (newPos != -1) {
				
				Actor.freeCell( ghost.pos );
				
				CellEmitter.get( ghost.pos ).start( Speck.factory( Speck.LIGHT ), 0.2f, 3 );
				ghost.pos = newPos;
				ghost.sprite.place( ghost.pos );
				ghost.sprite.visible = Dungeon.visible[ghost.pos];
			}
		}
	}
	
	private static final QuestHandler roseQuest = new QuestHandler() {
		private final String TXT_ROSE1	= Messages.get(Ghost.class,"1");
		
		private final String TXT_ROSE2	= Messages.get(Ghost.class,"2");
		
		private final String TXT_ROSE3	= Messages.get(Ghost.class,"3");

		public void interact( Ghost ghost ) {
			if (Quest.given) {

				Item item = Dungeon.hero.belongings.getItem( DriedRose.class );	
				if (item != null) {
					GameScene.show( new WndSadGhost( ghost, item, TXT_ROSE3 ) );
				} else {
					GameScene.show( new WndQuest( ghost, TXT_ROSE2 ) );
					relocate( ghost );
				}
				
			} else {
				GameScene.show( new WndQuest( ghost, TXT_ROSE1 ) );
				Quest.given = true;
				
				Journal.add( Journal.Feature.GHOST );
			}
		}
	};
	
	private static final QuestHandler ratQuest = new QuestHandler() {
		private final String TXT_RAT1	= Messages.get(Ghost.class,"4");
			
		private final String TXT_RAT2	= Messages.get(Ghost.class,"5");
		
		private final String TXT_RAT3	= Messages.get(Ghost.class,"6");
		
		public void interact( Ghost ghost ) {
			if (Quest.given) {

				Item item = Dungeon.hero.belongings.getItem( RatSkull.class );	
				if (item != null) {
					GameScene.show( new WndSadGhost( ghost, item, TXT_RAT3 ) );
				} else {
					GameScene.show( new WndQuest( ghost, TXT_RAT2 ) );
					relocate( ghost );
				}
				
			} else {
				GameScene.show( new WndQuest( ghost, TXT_RAT1 ) );
				Quest.given = true;
				
				Journal.add( Journal.Feature.GHOST );
			}
		}
	};
	
	private static final QuestHandler curseQuest = new QuestHandler() {
		private final String TXT_CURSE1 =Messages.get(Ghost.class,"7");
		private final String TXT_CURSE2 =Messages.get(Ghost.class,"8");
		
		private final String TXT_YES	= Messages.get(Ghost.class,"9");
		private final String TXT_NO	= Messages.get(Ghost.class,"0");
		
		public void interact( final Ghost ghost ) {
			if (Quest.given) {

				GameScene.show( new WndSadGhost( ghost, null, Utils.format( TXT_CURSE2, Dungeon.hero.className() ) ) );
				
			} else {
				GameScene.show( new WndQuest( ghost, TXT_CURSE1, TXT_YES, TXT_NO ) {
					protected void onSelect( int index ) {
						if (index == 0) {
							Quest.given = true;
							
							CursePersonification d = new CursePersonification();
							Ghost.replace( ghost, d );
							
							d.sprite.emitter().burst( ShadowParticle.CURSE, 5 );
							Sample.INSTANCE.play( Assets.SND_GHOST );
							
							Dungeon.hero.next();
						} else {
							relocate( ghost );
						}
					};
				} );
				
				Journal.add( Journal.Feature.GHOST );
			}
		}
	};
}
