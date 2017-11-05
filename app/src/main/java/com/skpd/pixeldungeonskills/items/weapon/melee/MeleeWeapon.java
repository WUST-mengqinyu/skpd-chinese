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
package com.skpd.pixeldungeonskills.items.weapon.melee;

import com.skpd.pixeldungeonskills.Dungeon;
import com.skpd.pixeldungeonskills.items.Item;
import com.skpd.pixeldungeonskills.items.weapon.Weapon;
import com.skpd.pixeldungeonskills.messages.Languages;
import com.skpd.pixeldungeonskills.messages.Messages;
import com.skpd.pixeldungeonskills.utils.Utils;
import com.skpd.utils.Random;

public class MeleeWeapon extends Weapon {
	String a;
	private int tier;
	
	public MeleeWeapon( int tier, float acu, float dly ) {
		super();
		
		this.tier = tier;
		
		ACU = acu;
		DLY = dly;
		
		STR = typicalSTR();
	}
	
	protected int min0() {
		return tier;
	}
	
	protected int max0() {
		return (int)((tier * tier - tier + 10) / ACU * DLY);
	}
	
	@Override
	public int min() {
		return isBroken() ? min0() : min0() + level(); 
	}
	
	@Override
	public int max() {
		return isBroken() ? max0() : max0() + level() * tier;
	}
	
	@Override
	final public Item upgrade() {
		return upgrade( false );
	}
	
	public Item upgrade( boolean enchant ) {
		STR--;		
		return super.upgrade( enchant );
	}
	
	public Item safeUpgrade() {
		return upgrade( enchantment != null );
	}
	
	@Override
	public Item degrade() {		
		STR++;
		return super.degrade();
	}
	
	public int typicalSTR() {
		return 8 + tier * 2;
	}
	
	@Override
	public String info() {
		
		final String p = "\n\n";
		
		StringBuilder info = new StringBuilder( desc() );
		
		int lvl = visiblyUpgraded();
		String quality = lvl != 0 ?
			(lvl > 0 ?
				(isBroken() ? Messages.get(MeleeWeapon.class,"1") : Messages.get(MeleeWeapon.class,"2")) :
				Messages.get(MeleeWeapon.class,"3")) :
			"";
		info.append( p );
		if (Messages.lang()==Languages.ENGLISH)
			info.append( Messages.format(Messages.get(MeleeWeapon.class,"4"),name) + Utils.indefinite( quality ) );
		else
			info.append( Messages.format(Messages.get(MeleeWeapon.class,"4"),name) + quality );
		info.append( Messages.format(Messages.get(MeleeWeapon.class,"5"),tier) );
		
		if (levelKnown) {
			int min = min();
			int max = max();
			int desc = min + (max - min) / 2;
			info.append( Messages.format(Messages.get(MeleeWeapon.class,"6"),desc) );
		} else {
			int min = min0();
			int max = max0();
			int desc =min + (max - min) / 2;
			info.append( 
				Messages.format(Messages.get(MeleeWeapon.class,"7"),desc,typicalSTR()));
			if (typicalSTR() > Dungeon.hero.STR()) {
				info.append( Messages.get(MeleeWeapon.class,"8") );
			}
		}
		
		if (DLY != 1f) {
			if (Messages.lang()== Languages.ENGLISH)
				info.append( "This is a rather " + (DLY < 1f ? "fast" : "slow") );
			else
				info.append( "这是一件相当" + (DLY < 1f ? "快速" : "缓慢") );
			if (ACU != 1f) {
				if ((ACU > 1f) == (DLY < 1f)) {
					if (Messages.lang()== Languages.ENGLISH)
						info.append( " and ");
					else
						info.append( "而且");
				} else {
					if (Messages.lang()== Languages.ENGLISH)
						info.append( " but ");
					else
						info.append( "但是");
				}
				if (Messages.lang()== Languages.ENGLISH)
					info.append( ACU > 1f ? "accurate" : "inaccurate" );
				else
					info.append( ACU > 1f ? "精准的" : "不精准的" );
			}
			if (Messages.lang()== Languages.ENGLISH)
				info.append( " weapon. ");
			else
				info.append( "武器。");
		} else if (ACU != 1f) {
			if (Messages.lang()== Languages.ENGLISH)
				info.append( "This is a rather " + (ACU > 1f ? "accurate" : "inaccurate") + " weapon. " );
			else
				info.append( "这是一件相当" + (ACU > 1f ? "精准的" : "不精准的") + "武器。" );
		}
		switch (imbue) {
		case SPEED:
			info.append( Messages.get(MeleeWeapon.class,"9") );
			break;
		case ACCURACY:
			info.append( Messages.get(MeleeWeapon.class,"0") );
			break;
		case NONE:
		}
		
		if (enchantment != null) {
			info.append( Messages.get(MeleeWeapon.class,"a1") );
		}
		
		if (levelKnown && Dungeon.hero.belongings.backpack.items.contains( this )) {
			if (STR() > Dungeon.hero.STR()) {
				info.append( p );
				info.append( Messages.format(Messages.get(MeleeWeapon.class,"a2"),name));
			}
			if (STR() < Dungeon.hero.STR()) {
				info.append( p );
				info.append( Messages.format(Messages.get(MeleeWeapon.class,"a3"),name) );
			}
		}

		if (Messages.lang()==Languages.ENGLISH)
			a= "You hold the " + name + " at the ready" + (cursed ? ", and because it is cursed, you are powerless to let go." : ".");
		else
			a="你紧紧地握住了" + name + "" + (cursed ? "，由于它被诅咒了你不能将它取下。" : "。");
		if (isEquipped( Dungeon.hero )) {
			info.append( p );
            if(this instanceof MeleeWeapon &&  Dungeon.hero.heroSkills.passiveA1 != null && Dungeon.hero.heroSkills.passiveB3.weaponLevelBonus() > 0) // <--- Warrior Mastery if present
                info.append( Messages.format(Messages.get(MeleeWeapon.class,"a4"), Dungeon.hero.heroSkills.passiveB3.weaponLevelBonus()) );
			info.append( a );
		} else {
			if (cursedKnown && cursed) {
				info.append( p );
				info.append( Messages.format(Messages.get(MeleeWeapon.class,"a5"),name) );
			}
		}
		
		return info.toString();
	}
	
	@Override
	public int price() {
		int price = 20 * (1 << (tier - 1));
		if (enchantment != null) {
			price *= 1.5;
		}
		return considerState( price );
	}
	
	@Override
	public Item random() {
		super.random();
		
		if (Random.Int( 10 + level() ) == 0) {
			enchant();
		}
		
		return this;
	}
}
