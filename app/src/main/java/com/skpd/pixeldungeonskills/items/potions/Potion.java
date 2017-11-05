
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
 */package com.skpd.pixeldungeonskills.items.potions;

import com.skpd.noosa.audio.Sample;
import com.skpd.pixeldungeonskills.Assets;
import com.skpd.pixeldungeonskills.Badges;
import com.skpd.pixeldungeonskills.Dungeon;
import com.skpd.pixeldungeonskills.actors.hero.Hero;
import com.skpd.pixeldungeonskills.effects.Splash;
import com.skpd.pixeldungeonskills.items.Item;
import com.skpd.pixeldungeonskills.items.ItemStatusHandler;
import com.skpd.pixeldungeonskills.levels.Level;
import com.skpd.pixeldungeonskills.levels.Terrain;
import com.skpd.pixeldungeonskills.messages.Messages;
import com.skpd.pixeldungeonskills.scenes.GameScene;
import com.skpd.pixeldungeonskills.sprites.ItemSprite;
import com.skpd.pixeldungeonskills.sprites.ItemSpriteSheet;
import com.skpd.pixeldungeonskills.utils.GLog;
import com.skpd.pixeldungeonskills.windows.WndOptions;
import com.skpd.utils.Bundle;

import java.util.ArrayList;
import java.util.HashSet;

public class Potion extends Item {
	
	public static final String AC_DRINK	= Messages.get(Potion.class,"1");
	
	private static final String TXT_HARMFUL		= Messages.get(Potion.class,"2");
	private static final String TXT_BENEFICIAL	= Messages.get(Potion.class,"3");
	private static final String TXT_YES			= Messages.get(Potion.class,"4");
	private static final String TXT_NO			= Messages.get(Potion.class,"5");
	private static final String TXT_R_U_SURE_DRINK = Messages.get(Potion.class,"6");
	private static final String TXT_R_U_SURE_THROW = Messages.get(Potion.class,"7");
	
	private static final float TIME_TO_DRINK = 1f;
	
	private static final Class<?>[] potions = {
		PotionOfHealing.class, 
		PotionOfExperience.class, 
		PotionOfToxicGas.class, 
		PotionOfLiquidFlame.class,
		PotionOfStrength.class,
		PotionOfParalyticGas.class,
		PotionOfLevitation.class,
		PotionOfMindVision.class, 
		PotionOfPurity.class,
		PotionOfInvisibility.class,
		PotionOfMight.class,
		PotionOfFrost.class,
        PotionOfMana.class
	};
	private static final String[] colors = {
			Messages.get(Potion.class,"8"),
			Messages.get(Potion.class,"9"),
			Messages.get(Potion.class,"0"),
			Messages.get(Potion.class,"a1"),
			Messages.get(Potion.class,"a2"),
			Messages.get(Potion.class,"a3"),
			Messages.get(Potion.class,"a4"),
			Messages.get(Potion.class,"a5"),
			Messages.get(Potion.class,"a6"),
			Messages.get(Potion.class,"a7"),
			Messages.get(Potion.class,"a8"),
			Messages.get(Potion.class,"a9"),
			""};
	private static final Integer[] images = {
		ItemSpriteSheet.POTION_TURQUOISE, 
		ItemSpriteSheet.POTION_CRIMSON, 
		ItemSpriteSheet.POTION_AZURE, 
		ItemSpriteSheet.POTION_JADE, 
		ItemSpriteSheet.POTION_GOLDEN, 
		ItemSpriteSheet.POTION_MAGENTA, 
		ItemSpriteSheet.POTION_CHARCOAL, 
		ItemSpriteSheet.POTION_IVORY, 
		ItemSpriteSheet.POTION_AMBER, 
		ItemSpriteSheet.POTION_BISTRE, 
		ItemSpriteSheet.POTION_INDIGO, 
		ItemSpriteSheet.POTION_SILVER,
        ItemSpriteSheet.POTION_MANA};
	
	private static ItemStatusHandler<Potion> handler;
	
	private String color;
	
	{	
		stackable = true;		
		defaultAction = AC_DRINK;
	}
	
	@SuppressWarnings("unchecked")
	public static void initColors() {
		handler = new ItemStatusHandler<Potion>( (Class<? extends Potion>[])potions, colors, images, 1 );
	}
	
	public static void save( Bundle bundle ) {
		handler.save( bundle );
	}

	public static void saveSelectively( Bundle bundle, ArrayList<Item> items ) {
		handler.saveSelectively( bundle, items );
	}

	@SuppressWarnings("unchecked")
	public static void restore( Bundle bundle ) {
		handler = new ItemStatusHandler<Potion>( (Class<? extends Potion>[])potions, colors, images, bundle );
	}
	
	public Potion() {
		super();
		image = handler.image( this );
		color = handler.label( this );
	}
	
	@Override
	public ArrayList<String> actions( Hero hero ) {
		ArrayList<String> actions = super.actions( hero );
		actions.add( AC_DRINK );
		return actions;
	}
	
	@Override
	public void execute( final Hero hero, String action ) {
		if (action.equals( AC_DRINK )) {
			
			if (isKnown() && (
					this instanceof PotionOfLiquidFlame || 
					this instanceof PotionOfToxicGas || 
					this instanceof PotionOfParalyticGas)) {
				
					GameScene.show( 
						new WndOptions( TXT_HARMFUL, TXT_R_U_SURE_DRINK, TXT_YES, TXT_NO ) {
							@Override
							protected void onSelect(int index) {
								if (index == 0) {
									drink( hero );
								}
							};
						}
					);
					
				} else {
					drink( hero );
				}
			
		} else {
			
			super.execute( hero, action );
			
		}
	}
	
	@Override
	public void doThrow( final Hero hero ) {

		if (isKnown() && (
			this instanceof PotionOfExperience || 
			this instanceof PotionOfHealing || 
			this instanceof PotionOfLevitation ||
			this instanceof PotionOfMindVision ||
			this instanceof PotionOfStrength ||
			this instanceof PotionOfInvisibility || 
			this instanceof PotionOfMight)) {
		
			GameScene.show( 
				new WndOptions( TXT_BENEFICIAL, TXT_R_U_SURE_THROW, TXT_YES, TXT_NO ) {
					@Override
					protected void onSelect(int index) {
						if (index == 0) {
							Potion.super.doThrow( hero );
						}
					};
				}
			);
			
		} else {
			super.doThrow( hero );
		}
	}
	
	protected void drink( Hero hero ) {
		
		detach( hero.belongings.backpack );
		
		hero.spend( TIME_TO_DRINK );
		hero.busy();
		onThrow( hero.pos );
		
		Sample.INSTANCE.play( Assets.SND_DRINK );
		
		hero.sprite.operate( hero.pos );
	}
	
	@Override
	protected void onThrow( int cell ) {
		if (Dungeon.hero.pos == cell) {
			
			apply( Dungeon.hero );
			
		} else if (Dungeon.level.map[cell] == Terrain.WELL || Level.pit[cell]) {
			
			super.onThrow( cell );
			
		} else  {
			
			shatter( cell );
			
		}
	}
	
	protected void apply( Hero hero ) {
		shatter( hero.pos );
	}
	
	public void shatter( int cell ) {
		if (Dungeon.visible[cell]) {
			GLog.i( Messages.format(Messages.get(Potion.class,"a0"),color) );
			Sample.INSTANCE.play( Assets.SND_SHATTER );
			splash( cell );
		}
	}
	
	public boolean isKnown() {
		return handler.isKnown( this );
	}
	
	public void setKnown() {
		if (!isKnown()) {
			handler.know( this );
		}
		
		Badges.validateAllPotionsIdentified();
	}
	
	@Override
	public Item identify() {
		setKnown();
		return this;
	}
	
	protected String color() {
		return color;
	}
	
	@Override
	public String name() {
		return isKnown() ? name : color + Messages.get(Potion.class,"b1");
	}
	
	@Override
	public String info() {
		return isKnown() ?
			desc() : Messages.format(Messages.get(Potion.class,"b2"),color);
	}
	
	@Override
	public boolean isIdentified() {
		return isKnown();
	}
	
	@Override
	public boolean isUpgradable() {
		return false;
	}
	
	public static HashSet<Class<? extends Potion>> getKnown() {
		return handler.known();
	}
	
	public static HashSet<Class<? extends Potion>> getUnknown() {
		return handler.unknown();
	}
	
	public static boolean allKnown() {
		return handler.known().size() == potions.length;
	}
	
	protected void splash( int cell ) {		
		final int color = ItemSprite.pick( image, 8, 10 );
		Splash.at( cell, color, 5 );
	}
	
	@Override
	public int price() {
		return 20 * quantity;
	}
}
