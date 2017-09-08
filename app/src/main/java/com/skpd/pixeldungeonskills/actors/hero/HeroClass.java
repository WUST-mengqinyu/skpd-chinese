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
package com.skpd.pixeldungeonskills.actors.hero;

import com.skpd.pixeldungeonskills.Assets;
import com.skpd.pixeldungeonskills.Badges;
import com.skpd.pixeldungeonskills.Dungeon;
import com.skpd.pixeldungeonskills.skills.CurrentSkills;
import com.skpd.pixeldungeonskills.skills.Skill;
import com.skpd.pixeldungeonskills.items.SoulCrystalFilled;
import com.skpd.pixeldungeonskills.items.TomeOfMastery;
import com.skpd.pixeldungeonskills.items.armor.ClothArmor;
import com.skpd.pixeldungeonskills.items.bags.Keyring;
import com.skpd.pixeldungeonskills.items.food.Food;
import com.skpd.pixeldungeonskills.items.potions.PotionOfHealing;
import com.skpd.pixeldungeonskills.items.potions.PotionOfMana;
import com.skpd.pixeldungeonskills.items.potions.PotionOfStrength;
import com.skpd.pixeldungeonskills.items.rings.RingOfShadows;
import com.skpd.pixeldungeonskills.items.scrolls.ScrollOfBloodyRitual;
import com.skpd.pixeldungeonskills.items.scrolls.ScrollOfFrostLevel;
import com.skpd.pixeldungeonskills.items.scrolls.ScrollOfHome;
import com.skpd.pixeldungeonskills.items.scrolls.ScrollOfIdentify;
import com.skpd.pixeldungeonskills.items.scrolls.ScrollOfMagicMapping;
import com.skpd.pixeldungeonskills.items.scrolls.ScrollOfSacrifice;
import com.skpd.pixeldungeonskills.items.scrolls.ScrollOfSkill;
import com.skpd.pixeldungeonskills.items.wands.WandOfMagicMissile;
import com.skpd.pixeldungeonskills.items.weapon.melee.Dagger;
import com.skpd.pixeldungeonskills.items.weapon.melee.DualSwords;
import com.skpd.pixeldungeonskills.items.weapon.melee.Knuckles;
import com.skpd.pixeldungeonskills.items.weapon.melee.ShortSword;
import com.skpd.pixeldungeonskills.items.weapon.missiles.Arrow;
import com.skpd.pixeldungeonskills.items.weapon.missiles.Boomerang;
import com.skpd.pixeldungeonskills.items.weapon.missiles.Bow;
import com.skpd.pixeldungeonskills.items.weapon.missiles.CupidArrow;
import com.skpd.pixeldungeonskills.items.weapon.missiles.Dart;
import com.skpd.pixeldungeonskills.items.weapon.missiles.Shuriken;
import com.skpd.pixeldungeonskills.items.weapon.missiles.SoulCrystal;
import com.skpd.pixeldungeonskills.messages.Messages;
import com.skpd.pixeldungeonskills.sprites.EyeSprite;
import com.skpd.pixeldungeonskills.ui.QuickSlot;
import com.skpd.utils.Bundle;

public enum HeroClass {

	WARRIOR( "warrior" ), MAGE( "mage" ), ROGUE( "rogue" ), HUNTRESS( "huntress" ), HATSUNE("hatsune");
	
	private String title;
	
	private HeroClass( String title ) {
		this.title = title;
	}
	
	public void initHero( Hero hero ) {
		
		hero.heroClass = this;
		
		initCommon( hero );
		
		switch (this) {
		case WARRIOR:
			initWarrior( hero );
			break;
			
		case MAGE:
			initMage( hero );
			break;
			
		case ROGUE:
			initRogue( hero );
			break;
			
		case HUNTRESS:
			initHuntress( hero );
			break;
		}
		
		if (Badges.isUnlocked( masteryBadge() )) {
			new TomeOfMastery().collect();
		}
		
		hero.updateAwareness();
	}
	
	private static void initCommon( Hero hero ) {
		(hero.belongings.armor = new ClothArmor()).identify();
		new Food().identify().collect();
		new Keyring().collect();

        Dungeon.hero.HP -= Dungeon.currentDifficulty.difficultyHPStartPenalty();
        Dungeon.hero.HT -= Dungeon.currentDifficulty.difficultyHPStartPenalty();
        Dungeon.currentDifficulty.difficultyStartItemBonus();

        Skill.availableSkill = Skill.STARTING_SKILL;

        Bow tmp = new Bow(1);
        tmp.collect();
        tmp.doEquip(hero);
        new Arrow(15).collect();
        new CupidArrow(5).collect();

        new ScrollOfHome().setKnown();
        new ScrollOfSacrifice().setKnown();
        new ScrollOfBloodyRitual().setKnown();
        new ScrollOfSkill().setKnown();
        new ScrollOfFrostLevel().setKnown();


        new ScrollOfHome().collect();
       // new ScrollOfSacrifice().collect();
       // new ScrollOfBloodyRitual().collect();
        new ScrollOfSkill().collect();
       // new ScrollOfMagicMapping().identify().collect();
       // new ScrollOfFrostLevel().collect();

       // new Ankh().collect();
        new PotionOfHealing().setKnown();
        new PotionOfMana().setKnown();

        new PotionOfMana().collect();
        new PotionOfMana().collect();
        new PotionOfMana().collect();

       // new ScrollOfEnchantment().identify().collect();

        new SoulCrystal(3).collect();
        new SoulCrystalFilled(EyeSprite.class, 50, 20, "Captured Evil Eye").collect();
       // new PotionOfMindVision().collect();
        //new ArmorKit().collect();
       // new ScrollHolder().collect();
    }
	
	public Badges.Badge masteryBadge() {
		switch (this) {
		case WARRIOR:
			return Badges.Badge.MASTERY_WARRIOR;
		case MAGE:
			return Badges.Badge.MASTERY_MAGE;
		case ROGUE:
			return Badges.Badge.MASTERY_ROGUE;
		case HUNTRESS:
			return Badges.Badge.MASTERY_HUNTRESS;
		}
		return null;
	}
	
	private static void initWarrior( Hero hero ) {
		hero.STR = hero.STR + 1;
		hero.MP = hero.MMP = 20;
		(hero.belongings.weapon = new ShortSword()).identify();
		new Dart( 8 ).identify().collect();
		
		QuickSlot.primaryValue = Dart.class;
		
		new PotionOfStrength().setKnown();

        hero.heroSkills = CurrentSkills.WARRIOR;
        hero.heroSkills.init();
	}
	
	private static void initMage( Hero hero ) {
        hero.MP = hero.MMP = 40;

		(hero.belongings.weapon = new Knuckles()).identify();
		
		WandOfMagicMissile wand = new WandOfMagicMissile();
		wand.identify().collect();
		
		QuickSlot.primaryValue = wand;
		
		new ScrollOfIdentify().setKnown();

        hero.heroSkills = CurrentSkills.MAGE;
        hero.heroSkills.init();
	}
	
	private static void initRogue( Hero hero ) {
        hero.MP = hero.MMP = 30;
		//(hero.belongings.weapon = new Dagger()).identify();
        (hero.belongings.weapon = new DualSwords()).identify();
		(hero.belongings.ring1 = new RingOfShadows()).upgrade().identify();
		new Dart( 8 ).identify().collect();
		new Shuriken(10).identify().collect();
		hero.belongings.ring1.activate( hero );
		
		QuickSlot.primaryValue = Dart.class;
		
		new ScrollOfMagicMapping().setKnown();

        hero.heroSkills = CurrentSkills.ROGUE;
        hero.heroSkills.init();
	}
	
	private static void initHuntress( Hero hero ) {
        hero.MP = hero.MMP = 35;
		hero.HP = (hero.HT -= 5);
		
		(hero.belongings.weapon = new Dagger()).identify();
		Boomerang boomerang = new Boomerang();
		boomerang.identify().collect();
		
		QuickSlot.primaryValue = boomerang;

        hero.heroSkills = CurrentSkills.HUNTRESS;
        hero.heroSkills.init();
	}
	
	public String title() {
		return title;
	}
	
	public String spritesheet() {
		
		switch (this) {
		case WARRIOR:
			return Assets.WARRIOR;
		case MAGE:
			return Assets.MAGE;
		case ROGUE:
			return Assets.ROGUE;
		case HUNTRESS:
			return Assets.HUNTRESS;
        case HATSUNE:
            return Assets.LEGEND;
		}
		
		return null;
	}

	public String[] perks() {

		switch (this) {
			case WARRIOR:
				return new String[]{
						Messages.get(HeroClass.class, "warrior_perk1"),
						Messages.get(HeroClass.class, "warrior_perk2"),
						Messages.get(HeroClass.class, "warrior_perk3"),
						Messages.get(HeroClass.class, "warrior_perk4"),
						Messages.get(HeroClass.class, "warrior_perk5"),
				};
			case MAGE:
				return new String[]{
						Messages.get(HeroClass.class, "mage_perk1"),
						Messages.get(HeroClass.class, "mage_perk2"),
						Messages.get(HeroClass.class, "mage_perk3"),
						Messages.get(HeroClass.class, "mage_perk4"),
						Messages.get(HeroClass.class, "mage_perk5"),
				};
			case ROGUE:
				return new String[]{
						Messages.get(HeroClass.class, "rogue_perk1"),
						Messages.get(HeroClass.class, "rogue_perk2"),
						Messages.get(HeroClass.class, "rogue_perk3"),
						Messages.get(HeroClass.class, "rogue_perk4"),
						Messages.get(HeroClass.class, "rogue_perk5"),
						Messages.get(HeroClass.class, "rogue_perk6"),
				};
			case HUNTRESS:
				return new String[]{
						Messages.get(HeroClass.class, "huntress_perk1"),
						Messages.get(HeroClass.class, "huntress_perk2"),
						Messages.get(HeroClass.class, "huntress_perk3"),
						Messages.get(HeroClass.class, "huntress_perk4"),
						Messages.get(HeroClass.class, "huntress_perk5"),
				};
			case HATSUNE:
				return new String[] {
						Messages.get(HeroClass.class, "hatsume_perk1"),
						Messages.get(HeroClass.class, "hatsume_perk2"),
						Messages.get(HeroClass.class, "hatsume_perk3"),
						Messages.get(HeroClass.class, "hatsume_perk4"),
						Messages.get(HeroClass.class, "hatsume_perk5"),
				};
		}

		return null;
	}

	private static final String CLASS	= "class";
	
	public void storeInBundle( Bundle bundle ) {
		bundle.put( CLASS, toString() );
	}
	
	public static HeroClass restoreInBundle( Bundle bundle ) {
		String value = bundle.getString( CLASS );
		return value.length() > 0 ? valueOf( value ) : ROGUE;
	}
}
