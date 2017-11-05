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
package com.skpd.pixeldungeonskills.items;

import com.skpd.pixeldungeonskills.Dungeon;
import com.skpd.pixeldungeonskills.actors.hero.Hero;
import com.skpd.pixeldungeonskills.items.armor.Armor;
import com.skpd.pixeldungeonskills.items.armor.ClothArmor;
import com.skpd.pixeldungeonskills.items.armor.LeatherArmor;
import com.skpd.pixeldungeonskills.items.armor.MailArmor;
import com.skpd.pixeldungeonskills.items.armor.PlateArmor;
import com.skpd.pixeldungeonskills.items.armor.ScaleArmor;
import com.skpd.pixeldungeonskills.items.bags.Bag;
import com.skpd.pixeldungeonskills.items.food.Food;
import com.skpd.pixeldungeonskills.items.food.MysteryMeat;
import com.skpd.pixeldungeonskills.items.food.Pasty;
import com.skpd.pixeldungeonskills.items.potions.Potion;
import com.skpd.pixeldungeonskills.items.potions.PotionOfExperience;
import com.skpd.pixeldungeonskills.items.potions.PotionOfFrost;
import com.skpd.pixeldungeonskills.items.potions.PotionOfHealing;
import com.skpd.pixeldungeonskills.items.potions.PotionOfInvisibility;
import com.skpd.pixeldungeonskills.items.potions.PotionOfLevitation;
import com.skpd.pixeldungeonskills.items.potions.PotionOfLiquidFlame;
import com.skpd.pixeldungeonskills.items.potions.PotionOfMana;
import com.skpd.pixeldungeonskills.items.potions.PotionOfMight;
import com.skpd.pixeldungeonskills.items.potions.PotionOfMindVision;
import com.skpd.pixeldungeonskills.items.potions.PotionOfParalyticGas;
import com.skpd.pixeldungeonskills.items.potions.PotionOfPurity;
import com.skpd.pixeldungeonskills.items.potions.PotionOfStrength;
import com.skpd.pixeldungeonskills.items.potions.PotionOfToxicGas;
import com.skpd.pixeldungeonskills.items.rings.Ring;
import com.skpd.pixeldungeonskills.items.scrolls.B;
import com.skpd.pixeldungeonskills.items.scrolls.C;
import com.skpd.pixeldungeonskills.items.scrolls.D;
import com.skpd.pixeldungeonskills.items.scrolls.F;
import com.skpd.pixeldungeonskills.items.scrolls.G;
import com.skpd.pixeldungeonskills.items.scrolls.H;
import com.skpd.pixeldungeonskills.items.scrolls.I;
import com.skpd.pixeldungeonskills.items.scrolls.J;
import com.skpd.pixeldungeonskills.items.scrolls.K;
import com.skpd.pixeldungeonskills.items.scrolls.M;
import com.skpd.pixeldungeonskills.items.scrolls.N;
import com.skpd.pixeldungeonskills.items.scrolls.O;
import com.skpd.pixeldungeonskills.items.scrolls.P;
import com.skpd.pixeldungeonskills.items.scrolls.Q;
import com.skpd.pixeldungeonskills.items.scrolls.S;
import com.skpd.pixeldungeonskills.items.scrolls.Scroll;
import com.skpd.pixeldungeonskills.items.wands.Wand;
import com.skpd.pixeldungeonskills.items.wands.WandOfAmok;
import com.skpd.pixeldungeonskills.items.wands.WandOfAvalanche;
import com.skpd.pixeldungeonskills.items.wands.WandOfBlink;
import com.skpd.pixeldungeonskills.items.wands.WandOfDisintegration;
import com.skpd.pixeldungeonskills.items.wands.WandOfFirebolt;
import com.skpd.pixeldungeonskills.items.wands.WandOfFlock;
import com.skpd.pixeldungeonskills.items.wands.WandOfLightning;
import com.skpd.pixeldungeonskills.items.wands.WandOfMagicMissile;
import com.skpd.pixeldungeonskills.items.wands.WandOfPoison;
import com.skpd.pixeldungeonskills.items.wands.WandOfReach;
import com.skpd.pixeldungeonskills.items.wands.WandOfRegrowth;
import com.skpd.pixeldungeonskills.items.wands.WandOfSlowness;
import com.skpd.pixeldungeonskills.items.wands.WandOfTeleportation;
import com.skpd.pixeldungeonskills.items.weapon.Weapon;
import com.skpd.pixeldungeonskills.items.weapon.melee.BattleAxe;
import com.skpd.pixeldungeonskills.items.weapon.melee.Dagger;
import com.skpd.pixeldungeonskills.items.weapon.melee.DualSwords;
import com.skpd.pixeldungeonskills.items.weapon.melee.Glaive;
import com.skpd.pixeldungeonskills.items.weapon.melee.Knuckles;
import com.skpd.pixeldungeonskills.items.weapon.melee.Longsword;
import com.skpd.pixeldungeonskills.items.weapon.melee.Mace;
import com.skpd.pixeldungeonskills.items.weapon.melee.NecroBlade;
import com.skpd.pixeldungeonskills.items.weapon.melee.Quarterstaff;
import com.skpd.pixeldungeonskills.items.weapon.melee.ShortSword;
import com.skpd.pixeldungeonskills.items.weapon.melee.Spear;
import com.skpd.pixeldungeonskills.items.weapon.melee.Sword;
import com.skpd.pixeldungeonskills.items.weapon.melee.WarHammer;
import com.skpd.pixeldungeonskills.items.weapon.missiles.Arrow;
import com.skpd.pixeldungeonskills.items.weapon.missiles.BombArrow;
import com.skpd.pixeldungeonskills.items.weapon.missiles.Boomerang;
import com.skpd.pixeldungeonskills.items.weapon.missiles.Bow;
import com.skpd.pixeldungeonskills.items.weapon.missiles.CurareDart;
import com.skpd.pixeldungeonskills.items.weapon.missiles.Dart;
import com.skpd.pixeldungeonskills.items.weapon.missiles.FlameBow;
import com.skpd.pixeldungeonskills.items.weapon.missiles.FrostBow;
import com.skpd.pixeldungeonskills.items.weapon.missiles.IncendiaryDart;
import com.skpd.pixeldungeonskills.items.weapon.missiles.Javelin;
import com.skpd.pixeldungeonskills.items.weapon.missiles.Shuriken;
import com.skpd.pixeldungeonskills.items.weapon.missiles.Tomahawk;
import com.skpd.pixeldungeonskills.plants.Dreamweed;
import com.skpd.pixeldungeonskills.plants.Earthroot;
import com.skpd.pixeldungeonskills.plants.Fadeleaf;
import com.skpd.pixeldungeonskills.plants.Firebloom;
import com.skpd.pixeldungeonskills.plants.Icecap;
import com.skpd.pixeldungeonskills.plants.Plant;
import com.skpd.pixeldungeonskills.plants.Rotberry;
import com.skpd.pixeldungeonskills.plants.Sorrowmoss;
import com.skpd.pixeldungeonskills.plants.Sungrass;
import com.skpd.utils.Random;

import java.util.HashMap;

public class Generator {

	public static enum Category {
		WEAPON	( 15,	Weapon.class ),
		ARMOR	( 10,	Armor.class ),
		POTION	( 50,	Potion.class ),
		SCROLL	( 40,	Scroll.class ),
		WAND	( 4,	Wand.class ),
		RING	( 2,	Ring.class ),
		SEED	( 5,	Plant.Seed.class ),
		FOOD	( 0,	Food.class ),
		GOLD	( 50,	Gold.class ),
		MISC	( 5,	Item.class );
		
		public Class<?>[] classes;
		public float[] probs;
		
		public float prob;
		public Class<? extends Item> superClass;
		
		private Category( float prob, Class<? extends Item> superClass ) {
			this.prob = prob;
			this.superClass = superClass;
		}
		
		public static int order( Item item ) {
			for (int i=0; i < values().length; i++) {
				if (values()[i].superClass.isInstance( item )) {
					return i;
				}
			}
			
			return item instanceof Bag ? Integer.MAX_VALUE : Integer.MAX_VALUE - 1;
		}
	};
	
	private static HashMap<Category,Float> categoryProbs = new HashMap<Generator.Category, Float>();
	
	static {
		
		Category.GOLD.classes = new Class<?>[]{ 
			Gold.class };
		Category.GOLD.probs = new float[]{ 1 };
		
		Category.SCROLL.classes = new Class<?>[]{ 
			G.class,
			Q.class,
			N.class,
			M.class,
			I.class,
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
                P.class};
		Category.SCROLL.probs = new float[]{ 30, 10, 15, 10, 15, 12, 8, 8, 4, 6, 0, 1, 10, 1, 5, 10 };
		
		Category.POTION.classes = new Class<?>[]{ 
			PotionOfHealing.class, 
			PotionOfExperience.class,
			PotionOfToxicGas.class, 
			PotionOfParalyticGas.class,
			PotionOfLiquidFlame.class,
			PotionOfLevitation.class,
			PotionOfStrength.class,
			PotionOfMindVision.class,
			PotionOfPurity.class,
			PotionOfInvisibility.class,
			PotionOfMight.class,
			PotionOfFrost.class,
            PotionOfMana.class};
		Category.POTION.probs = new float[]{ 45, 4, 15, 10, 15, 10, 0, 20, 12, 10, 0, 10, 20 };
		
		Category.WAND.classes = new Class<?>[]{ 
			WandOfTeleportation.class, 
			WandOfSlowness.class,
			WandOfFirebolt.class,
			WandOfRegrowth.class,
			WandOfPoison.class,
			WandOfBlink.class,
			WandOfLightning.class,
			WandOfAmok.class,
			WandOfReach.class,
			WandOfFlock.class,
			WandOfMagicMissile.class,
			WandOfDisintegration.class,
			WandOfAvalanche.class };
		Category.WAND.probs = new float[]{ 10, 10, 15, 6, 10, 11, 15, 10, 6, 10, 0, 5, 5 };
		
		Category.WEAPON.classes = new Class<?>[]{ 
			Dagger.class, 
			Knuckles.class,
			Quarterstaff.class, 
			Spear.class, 
			Mace.class, 
			Sword.class, 
			Longsword.class,
			BattleAxe.class,
			WarHammer.class, 
			Glaive.class,
			ShortSword.class,
			Dart.class,
			Javelin.class,
			IncendiaryDart.class,
			CurareDart.class,
			Shuriken.class,
			Boomerang.class,
			Tomahawk.class,
                Bow.class,
                FrostBow.class,
                FlameBow.class,
                Arrow.class,
                BombArrow.class,
                NecroBlade.class,
                DualSwords.class
                };
		Category.WEAPON.probs = new float[]{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 0, 1, 0, 1, 1, 3, 1, 3, 3 };
		
		Category.ARMOR.classes = new Class<?>[]{ 
			ClothArmor.class, 
			LeatherArmor.class, 
			MailArmor.class, 
			ScaleArmor.class, 
			PlateArmor.class };
		Category.ARMOR.probs = new float[]{ 1, 1, 1, 1, 1 };
		
		Category.FOOD.classes = new Class<?>[]{ 
			Food.class, 
			Pasty.class,
			MysteryMeat.class };
		Category.FOOD.probs = new float[]{ 4, 1, 0 };
			
		Category.RING.classes = new Class<?>[]{ 
			com.skpd.pixeldungeonskills.items.rings.H.class,
			com.skpd.pixeldungeonskills.items.rings.B.class,
			com.skpd.pixeldungeonskills.items.rings.K.class,
			com.skpd.pixeldungeonskills.items.rings.I.class,
			com.skpd.pixeldungeonskills.items.rings.G.class,
            com.skpd.pixeldungeonskills.items.rings.A.class,
			com.skpd.pixeldungeonskills.items.rings.D.class,
			com.skpd.pixeldungeonskills.items.rings.J.class,
			com.skpd.pixeldungeonskills.items.rings.F.class,
			com.skpd.pixeldungeonskills.items.rings.C.class,
            com.skpd.pixeldungeonskills.items.rings.E.class,
            com.skpd.pixeldungeonskills.items.rings.L.class };
		Category.RING.probs = new float[]{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0 };
		
		Category.SEED.classes = new Class<?>[]{ 
			Firebloom.Seed.class,
			Icecap.Seed.class,
			Sorrowmoss.Seed.class,
			Dreamweed.Seed.class,
			Sungrass.Seed.class,
			Earthroot.Seed.class,
			Fadeleaf.Seed.class,
			Rotberry.Seed.class };
		Category.SEED.probs = new float[]{ 1, 1, 1, 1, 1, 1, 1, 0 };
		
		Category.MISC.classes = new Class<?>[]{ 
			Bomb.class,
			Honeypot.class};
		Category.MISC.probs = new float[]{ 2, 1 };
	}
	
	public static void reset() {
		for (Category cat : Category.values()) {
			categoryProbs.put( cat, cat.prob );
		}
	}
	
	public static Item random() {
		return random( Random.chances( categoryProbs ) );
	}
	
	public static Item random( Category cat ) {
		try {
			
			categoryProbs.put( cat, categoryProbs.get( cat ) / 2 );
			
			switch (cat) {
			case ARMOR:
				return randomArmor();
			case WEAPON:
				return randomWeapon();
			default:
				return ((Item)cat.classes[Random.chances( cat.probs )].newInstance()).random();
			}
			
		} catch (Exception e) {

			return null;
			
		}
	}
	
	public static Item random( Class<? extends Item> cl ) {
		try {
			
			return ((Item)cl.newInstance()).random();
			
		} catch (Exception e) {

			return null;
			
		}
	}
	
	public static Armor randomArmor() throws Exception {
		
		int curStr = Hero.STARTING_STR + Dungeon.potionOfStrength;
		
		Category cat = Category.ARMOR;
		
		Armor a1 = (Armor)cat.classes[Random.chances( cat.probs )].newInstance();
		Armor a2 = (Armor)cat.classes[Random.chances( cat.probs )].newInstance();
		
		a1.random();
		a2.random();
		
		return Math.abs( curStr - a1.STR ) < Math.abs( curStr - a2.STR ) ? a1 : a2;
	}
	
	public static Weapon randomWeapon() throws Exception {
		
		int curStr = Hero.STARTING_STR + Dungeon.potionOfStrength;
		
		Category cat = Category.WEAPON;
		
		Weapon w1 = (Weapon)cat.classes[Random.chances( cat.probs )].newInstance();
		Weapon w2 = (Weapon)cat.classes[Random.chances( cat.probs )].newInstance();
		
		w1.random();
		w2.random();
		
		return Math.abs( curStr - w1.STR ) < Math.abs( curStr - w2.STR ) ? w1 : w2;
	}
}
