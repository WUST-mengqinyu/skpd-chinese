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

import com.skpd.noosa.audio.Sample;
import com.skpd.pixeldungeonskills.Assets;
import com.skpd.pixeldungeonskills.Badges;
import com.skpd.pixeldungeonskills.Dungeon;
import com.skpd.pixeldungeonskills.Statistics;
import com.skpd.pixeldungeonskills.actors.hero.Hero;
import com.skpd.pixeldungeonskills.messages.Messages;
import com.skpd.pixeldungeonskills.scenes.GameScene;
import com.skpd.pixeldungeonskills.sprites.CharSprite;
import com.skpd.pixeldungeonskills.sprites.ItemSpriteSheet;
import com.skpd.pixeldungeonskills.utils.Utils;
import com.skpd.utils.Bundle;
import com.skpd.utils.Random;

import java.util.ArrayList;

public class Gold extends Item {

	private static final String TXT_COLLECT	= Messages.get(Gold.class,"1");
	private static final String TXT_INFO	= Messages.get(Gold.class,"2") + TXT_COLLECT;
	private static final String TXT_INFO_1	= Messages.get(Gold.class,"3") + TXT_COLLECT;
	private static final String TXT_VALUE	= "%+d";
    private static final String TXT_VALUE_LOOT	= Messages.get(Gold.class,"4");
	
	{
		image = ItemSpriteSheet.GOLD;
		stackable = true;
	}
	
	public Gold() {
		this( 1 );
	}
	
	public Gold( int value ) {
		this.quantity = value;
	}
	
	@Override
	public ArrayList<String> actions( Hero hero ) {
		return new ArrayList<String>();
	}
	
	@Override
	public boolean doPickUp( Hero hero ) {
		
		Dungeon.gold += quantity + hero.heroSkills.passiveA1.lootBonus(quantity); // <--- Rogue bandit if present
		Statistics.goldCollected += quantity  + hero.heroSkills.passiveA1.lootBonus(quantity);
		Badges.validateGoldCollected();
		
		GameScene.pickUp( this );
        if(hero.heroSkills.passiveA1.lootBonus(quantity) == 0)
		    hero.sprite.showStatus( CharSprite.NEUTRAL, TXT_VALUE, quantity );
        else
            hero.sprite.showStatus( CharSprite.NEUTRAL, TXT_VALUE_LOOT, quantity, hero.heroSkills.passiveA1.lootBonus(quantity));

		hero.spendAndNext( TIME_TO_PICK_UP );
		
		Sample.INSTANCE.play( Assets.SND_GOLD, 1, 1, Random.Float( 0.9f, 1.1f ) );
		
		return true;
	}
	
	@Override
	public boolean isUpgradable() {
		return false;
	}
	
	@Override
	public boolean isIdentified() {
		return true;
	}
	
	@Override
	public String info() {
		switch (quantity) {
		case 0:
			return TXT_COLLECT;
		case 1:
			return TXT_INFO_1;
		default:
			return Utils.format( TXT_INFO, quantity );
		}
	}
	
	@Override
	public Item random() {
		quantity = Random.Int( 20 + Dungeon.depth * 10, 40 + Dungeon.depth * 20 );
		return this;
	}
	
	private static final String VALUE	= "value";
	
	@Override
	public void storeInBundle( Bundle bundle ) {
		super.storeInBundle( bundle );
		bundle.put( VALUE, quantity );
	}
	
	@Override
	public void restoreFromBundle( Bundle bundle ) {
		super.restoreFromBundle(bundle);
		quantity = bundle.getInt( VALUE );
	}
}
