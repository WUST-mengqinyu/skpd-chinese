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

import com.skpd.pixeldungeonskills.Badges;
import com.skpd.pixeldungeonskills.actors.buffs.Blindness;
import com.skpd.pixeldungeonskills.actors.hero.Hero;
import com.skpd.pixeldungeonskills.items.Item;
import com.skpd.pixeldungeonskills.items.ItemStatusHandler;
import com.skpd.pixeldungeonskills.messages.Languages;
import com.skpd.pixeldungeonskills.messages.Messages;
import com.skpd.pixeldungeonskills.sprites.HeroSprite;
import com.skpd.pixeldungeonskills.sprites.ItemSpriteSheet;
import com.skpd.pixeldungeonskills.utils.GLog;
import com.skpd.utils.Bundle;

import java.util.ArrayList;
import java.util.HashSet;

public abstract class Scroll extends Item {

	private static final String TXT_BLINDED	= Messages.get(Scroll.class,"1");
	
	public static final String AC_READ	= Messages.get(Scroll.class,"2");
	
	protected static final float TIME_TO_READ	= 1f;
	
	private static final Class<?>[] scrolls = {
		G.class,
		I.class,
		M.class,
		N.class,
		Q.class,
		C.class,
		com.skpd.pixeldungeonskills.items.scrolls.R.class,
		H.class,
		K.class,
		J.class,
		S.class,
		D.class,
            F.class,
            O.class,
            B.class,
            P.class,
            E.class,
            T.class

    };
	private static final String[] runes = 
		{"KAUNAN", "SOWILO", "LAGUZ", "YNGVI", "GYFU", "RAIDO", "ISAZ", "MANNAZ", "NAUDIZ", "BERKANAN", "ODAL", "TIWAZ", "", "", "", "", "", ""};
	private static final Integer[] images = {
		ItemSpriteSheet.SCROLL_KAUNAN, 
		ItemSpriteSheet.SCROLL_SOWILO, 
		ItemSpriteSheet.SCROLL_LAGUZ, 
		ItemSpriteSheet.SCROLL_YNGVI, 
		ItemSpriteSheet.SCROLL_GYFU, 
		ItemSpriteSheet.SCROLL_RAIDO, 
		ItemSpriteSheet.SCROLL_ISAZ, 
		ItemSpriteSheet.SCROLL_MANNAZ, 
		ItemSpriteSheet.SCROLL_NAUDIZ, 
		ItemSpriteSheet.SCROLL_BERKANAN, 
		ItemSpriteSheet.SCROLL_ODAL, 
		ItemSpriteSheet.SCROLL_TIWAZ,
            ItemSpriteSheet.SCROLL_GOHOME,
            ItemSpriteSheet.SCROLL_SACRIFICE,
            ItemSpriteSheet.SCROLL_BLOODY,
            ItemSpriteSheet.SCROLL_SKILLPOINT,
            ItemSpriteSheet.SCROLL_FROST,
            ItemSpriteSheet.SCROLL_WIPE_OUT
    };
	
	private static ItemStatusHandler<Scroll> handler;
	
	private String rune;
	
	{
		stackable = true;		
		defaultAction = AC_READ;
	}
	
	@SuppressWarnings("unchecked")
	public static void initLabels() {
		handler = new ItemStatusHandler<Scroll>( (Class<? extends Scroll>[])scrolls, runes, images, 6 );
	}
	
	public static void save( Bundle bundle ) {
		handler.save( bundle );
	}


	public static void saveSelectively( Bundle bundle, ArrayList<Item> items ) {
		handler.saveSelectively( bundle, items );
	}

	@SuppressWarnings("unchecked")
	public static void restore( Bundle bundle ) {
		handler = new ItemStatusHandler<Scroll>( (Class<? extends Scroll>[])scrolls, runes, images, bundle );
	}
	
	public Scroll() {
		super();
		image = handler.image( this );
		rune = handler.label( this );
	}
	
	@Override
	public ArrayList<String> actions( Hero hero ) {
		ArrayList<String> actions = super.actions( hero );
		actions.add( AC_READ );
		return actions;
	}
	
	@Override
	public void execute( Hero hero, String action ) {
		if (action.equals( AC_READ )) {
			
			if (hero.buff( Blindness.class ) != null) {
				GLog.w( TXT_BLINDED );
			} else {
				curUser = hero;
				curItem = detach( hero.belongings.backpack );
				doRead();
			}
			
		} else {
		
			super.execute( hero, action );
			
		}
	}
	
	abstract protected void doRead();
	
	protected void readAnimation() {
		curUser.spend( TIME_TO_READ );
		curUser.busy();
		((HeroSprite)curUser.sprite).read();
	}
	
	public boolean isKnown() {
		return handler.isKnown( this );
	}
	
	public void setKnown() {
		if (!isKnown()) {
			handler.know( this );
		}
		
		Badges.validateAllScrollsIdentified();
	}
	
	@Override
	public Item identify() {
		setKnown();
		return super.identify();
	}
	
	@Override
	public String name() {
		if(Messages.lang()== Languages.ENGLISH)
		return isKnown() ? name : "scroll \"" + rune + "\"";
		else return isKnown() ? name : "\"" + rune + "\"卷轴";
	}
	
	@Override
	public String info() {
		return isKnown() ?
			desc() : Messages.format(Messages.get(Scroll.class,"3"),rune );
	}
	
	@Override
	public boolean isUpgradable() {
		return false;
	}
	
	@Override
	public boolean isIdentified() {
		return isKnown();
	}
	
	public static HashSet<Class<? extends Scroll>> getKnown() {
		return handler.known();
	}
	
	public static HashSet<Class<? extends Scroll>> getUnknown() {
		return handler.unknown();
	}
	
	public static boolean allKnown() {
		return handler.known().size() == scrolls.length;
	}
	
	@Override
	public int price() {
		return 15 * quantity;
	}
}
