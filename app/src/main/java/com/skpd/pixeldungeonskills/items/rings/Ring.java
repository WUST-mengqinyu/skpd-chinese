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
package com.skpd.pixeldungeonskills.items.rings;

import com.skpd.pixeldungeonskills.Badges;
import com.skpd.pixeldungeonskills.Dungeon;
import com.skpd.pixeldungeonskills.PixelDungeon;
import com.skpd.pixeldungeonskills.actors.Char;
import com.skpd.pixeldungeonskills.actors.buffs.Buff;
import com.skpd.pixeldungeonskills.actors.hero.Hero;
import com.skpd.pixeldungeonskills.actors.hero.HeroClass;
import com.skpd.pixeldungeonskills.items.EquipableItem;
import com.skpd.pixeldungeonskills.items.Item;
import com.skpd.pixeldungeonskills.items.ItemStatusHandler;
import com.skpd.pixeldungeonskills.messages.Messages;
import com.skpd.pixeldungeonskills.sprites.ItemSpriteSheet;
import com.skpd.pixeldungeonskills.utils.GLog;
import com.skpd.pixeldungeonskills.utils.Utils;
import com.skpd.pixeldungeonskills.windows.WndOptions;
import com.skpd.utils.Bundle;
import com.skpd.utils.Random;

import java.util.ArrayList;

public class Ring extends EquipableItem {

	private static final int TICKS_TO_KNOW	= 200;
	
	private static final float TIME_TO_EQUIP = 1f;
	
	private static final String TXT_IDENTIFY 		=	Messages.get(Ring.class,"1");
	
	private static final String TXT_UNEQUIP_TITLE 	= 	Messages.get(Ring.class,"2");
	private static final String TXT_UNEQUIP_MESSAGE = 	Messages.get(Ring.class,"3");
	
	protected Buff buff;
	
	private static final Class<?>[] rings = { 
		H.class,
		B.class,
		K.class,
		I.class,
		G.class,
		A.class,
		D.class,
		J.class,
		F.class,
		E.class,
		C.class,
		L.class
	};
	private static final String[] gems = 
		{Messages.get(Ring.class,"4"),
				Messages.get(Ring.class,"5"),
				Messages.get(Ring.class,"6"),
				Messages.get(Ring.class,"7"),
				Messages.get(Ring.class,"8"),
				Messages.get(Ring.class,"9"),
				Messages.get(Ring.class,"0"),
				Messages.get(Ring.class,"a1"),
				Messages.get(Ring.class,"a2"),
				Messages.get(Ring.class,"a3"),
				Messages.get(Ring.class,"a4"),
				Messages.get(Ring.class,"a5")};
	private static final Integer[] images = {
		ItemSpriteSheet.RING_DIAMOND, 
		ItemSpriteSheet.RING_OPAL, 
		ItemSpriteSheet.RING_GARNET, 
		ItemSpriteSheet.RING_RUBY, 
		ItemSpriteSheet.RING_AMETHYST, 
		ItemSpriteSheet.RING_TOPAZ, 
		ItemSpriteSheet.RING_ONYX, 
		ItemSpriteSheet.RING_TOURMALINE, 
		ItemSpriteSheet.RING_EMERALD, 
		ItemSpriteSheet.RING_SAPPHIRE, 
		ItemSpriteSheet.RING_QUARTZ, 
		ItemSpriteSheet.RING_AGATE};
	
	private static ItemStatusHandler<Ring> handler;
	
	private String gem;
	
	private int ticksToKnow = TICKS_TO_KNOW;
	
	@SuppressWarnings("unchecked")
	public static void initGems() {
		handler = new ItemStatusHandler<Ring>( (Class<? extends Ring>[])rings, gems, images );
	}
	
	public static void save( Bundle bundle ) {
		handler.save( bundle );
	}

	public static void saveSelectively( Bundle bundle, ArrayList<Item> items ) {
		handler.saveSelectively( bundle, items );
	}

	@SuppressWarnings("unchecked")
	public static void restore( Bundle bundle ) {
		handler = new ItemStatusHandler<Ring>( (Class<? extends Ring>[])rings, gems, images, bundle );
	}
	
	public Ring() {
		super();
		syncGem();
	}
	
	public void syncGem() {
		image	= handler.image( this );
		gem		= handler.label( this );
	}
	
	@Override
	public ArrayList<String> actions( Hero hero ) {
		ArrayList<String> actions = super.actions( hero );
		actions.add( isEquipped( hero ) ? AC_UNEQUIP : AC_EQUIP );
		return actions;
	}
	
	@Override
	public boolean doEquip( final Hero hero ) {
		
		if (hero.belongings.ring1 != null && hero.belongings.ring2 != null) {
			
			final Ring r1 = hero.belongings.ring1;
			final Ring r2 = hero.belongings.ring2;
			
			PixelDungeon.scene().add( 
				new WndOptions( TXT_UNEQUIP_TITLE, TXT_UNEQUIP_MESSAGE, 
					Utils.capitalize( r1.toString() ), 
					Utils.capitalize( r2.toString() ) ) {
					
					@Override
					protected void onSelect( int index ) {
						
						detach( hero.belongings.backpack );
						
						Ring equipped = (index == 0 ? r1 : r2);
						if (equipped.doUnequip( hero, true, false )) {
							doEquip( hero );
						} else {
							collect( hero.belongings.backpack );
						}
					}
				} );
			
			return false;
			
		} else {
			
			if (hero.belongings.ring1 == null) {
				hero.belongings.ring1 = this;
			} else {
				hero.belongings.ring2 = this;
			}
			
			detach( hero.belongings.backpack );
			
			activate( hero );
			
			cursedKnown = true;
			if (cursed) {
				equipCursed( hero );
				GLog.n(Messages.get(Ring.class,"a6"));
			}
			
			hero.spendAndNext( TIME_TO_EQUIP );
			return true;
			
		}

	}
	
	public void activate( Char ch ) {
		buff = buff();
		buff.attachTo( ch );
	}

	@Override
	public boolean doUnequip( Hero hero, boolean collect, boolean single ) {
		if (super.doUnequip( hero, collect, single )) {
			
			if (hero.belongings.ring1 == this) {
				hero.belongings.ring1 = null;
			} else {
				hero.belongings.ring2 = null;
			}
			
			hero.remove( buff );
			buff = null;
			
			return true;
			
		} else {
			
			return false;
			
		}
	}
	
	@Override
	public boolean isEquipped( Hero hero ) {
		return hero.belongings.ring1 == this || hero.belongings.ring2 == this;
	}
	
	@Override
	public int effectiveLevel() {
		return isBroken() ? 1 : level();
	}
	
	private void renewBuff() {
		if (buff != null) {
			Char owner = buff.target;
			buff.detach();
			if ((buff = buff()) != null) {
				buff.attachTo( owner );
			}
		}
	}
	
	@Override
	public void getBroken() {
		renewBuff();
		super.getBroken();
	}
	
	@Override
	public void fix() {
		super.fix();
		renewBuff();
	}
	
	@Override
	public int maxDurability( int lvl ) {
		if (lvl <= 1) {
			return Integer.MAX_VALUE;
		} else {
			return 100 * (lvl < 16 ? 16 - lvl : 1);
		}
	}
	
	public boolean isKnown() {
		return handler.isKnown( this );
	}
	
	protected void setKnown() {
		if (!isKnown()) {
			handler.know( this );
		}
		
		Badges.validateAllRingsIdentified();
	}
	
	@Override
	public String toString() {
		return 
			levelKnown && isBroken() ? 
				Messages.get(Ring.class,"a7") + super.toString() :
				super.toString();
	}
	
	@Override
	public String name() {
		return isKnown() ? name : gem + Messages.get(Ring.class,"a8");
	}
	
	@Override
	public String info() {
		String desc=isKnown()?desc():Messages.format(Messages.get(Ring.class,"a9"),gem);
		if (isEquipped( Dungeon.hero )) {
			
			desc += "\n\n" + Messages.format(Messages.get(Ring.class,"a0"),name())+
				(cursed ? Messages.get(Ring.class,"b1") : "" );
			
		} else if (cursed && cursedKnown) {
			
			desc += "\n\n" + Messages.get(Ring.class,"b2") + name();
			
		}
		return desc;
	}
	
	@Override
	public boolean isIdentified() {
		return super.isIdentified() && isKnown();
	}
	
	@Override
	public Item identify() {
		setKnown();
		return super.identify();
	}
	
	@Override
	public Item random() {
		int lvl = Random.Int( 1, 3 );
		if (Random.Float() < 0.3f) {
			degrade( lvl );
			cursed = true;
		} else {
			upgrade( lvl );
		}
		return this;
	}
	
	public static boolean allKnown() {
		return handler.known().size() == rings.length - 2;
	}
	
	@Override
	public int price() {
		return considerState( 80 );
	}
	
	protected RingBuff buff() {
		return null;
	}
	
	private static final String UNFAMILIRIARITY	= "unfamiliarity";
	
	@Override
	public void storeInBundle( Bundle bundle ) {
		super.storeInBundle( bundle );
		bundle.put( UNFAMILIRIARITY, ticksToKnow );
	}
	
	@Override
	public void restoreFromBundle( Bundle bundle ) {
		super.restoreFromBundle( bundle );
		if ((ticksToKnow = bundle.getInt( UNFAMILIRIARITY )) == 0) {
			ticksToKnow = TICKS_TO_KNOW;
		}
	}
	
	public class RingBuff extends Buff {
		
		private final String TXT_KNOWN = Messages.get(Ring.class,"b3");
		
		public int level;
		public RingBuff() {
			level = Ring.this.effectiveLevel();
		}
		
		@Override
		public boolean attachTo( Char target ) {

			if (target instanceof Hero && ((Hero)target).heroClass == HeroClass.ROGUE && !isKnown()) {
				setKnown();
				GLog.i( TXT_KNOWN, name() );
				Badges.validateItemLevelAquired( Ring.this );
			}
			
			return super.attachTo(target);
		}
		
		@Override
		public boolean act() {
			
			if (!isIdentified() && --ticksToKnow <= 0) {
				String gemName = name();
				identify();
				GLog.w( TXT_IDENTIFY, gemName, Ring.this.toString() );
				Badges.validateItemLevelAquired( Ring.this );
			}
			
			use();
			
			spend( TICK );
			
			return true;
		}
	}
}
