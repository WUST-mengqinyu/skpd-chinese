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

import com.skpd.pixeldungeonskills.messages.Messages;
import com.skpd.utils.Bundle;

public enum HeroSubClass {

	NONE( null, null ),
	
	GLADIATOR(Messages.get(HeroSubClass.class,"a1"),
			Messages.get(HeroSubClass.class,"1") ),
	BERSERKER( Messages.get(HeroSubClass.class,"a2"),
			Messages.get(HeroSubClass.class,"2")),
	
	WARLOCK( Messages.get(HeroSubClass.class,"b1"),
			Messages.get(HeroSubClass.class,"3") ),
	BATTLEMAGE( Messages.get(HeroSubClass.class,"b2"),
			Messages.get(HeroSubClass.class,"4") ),
	
	ASSASSIN( Messages.get(HeroSubClass.class,"c1"),
			Messages.get(HeroSubClass.class,"5") ),
	FREERUNNER( Messages.get(HeroSubClass.class,"c2"),
			Messages.get(HeroSubClass.class,"6")),
		
	SNIPER( Messages.get(HeroSubClass.class,"d1"),
			Messages.get(HeroSubClass.class,"7") ),
	WARDEN( Messages.get(HeroSubClass.class,"d2"),
			Messages.get(HeroSubClass.class,"8") );
	
	private String title;
	private String desc;
	
	private HeroSubClass( String title, String desc ) {
		this.title = title;
		this.desc = desc;
	}
	
	public String title() {
		return title;
	}
	
	public String desc() {
		return desc;
	}
	
	private static final String SUBCLASS	= "subClass";
	
	public void storeInBundle( Bundle bundle ) {
		bundle.put( SUBCLASS, toString() );
	}
	
	public static HeroSubClass restoreInBundle( Bundle bundle ) {
		String value = bundle.getString( SUBCLASS );
		try {
			return valueOf( value );
		} catch (Exception e) {
			return NONE;
		}
	}
	
}
