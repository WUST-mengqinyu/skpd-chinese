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

import com.skpd.noosa.audio.Sample;
import com.skpd.pixeldungeonskills.Assets;
import com.skpd.pixeldungeonskills.actors.buffs.Invisibility;
import com.skpd.pixeldungeonskills.items.Item;
import com.skpd.pixeldungeonskills.messages.Messages;
import com.skpd.pixeldungeonskills.scenes.GameScene;
import com.skpd.pixeldungeonskills.windows.WndBag;
import com.skpd.pixeldungeonskills.windows.WndOptions;

public abstract class A extends Scroll {

	protected String inventoryTitle = Messages.get(A.class,"1");
	protected WndBag.Mode mode = WndBag.Mode.ALL;
	
	private static final String TXT_WARNING	= Messages.get(A.class,"2");
	private static final String TXT_YES		= Messages.get(A.class,"3");
	private static final String TXT_NO		= Messages.get(A.class,"4");
	
	@Override
	protected void doRead() {
		
		if (!isKnown()) {
			setKnown();
			identifiedByUse = true;
		} else {
			identifiedByUse = false;
		}
		
		GameScene.selectItem( itemSelector, mode, inventoryTitle );
	}
	
	private void confirmCancelation() {
		GameScene.show( new WndOptions( name(), TXT_WARNING, TXT_YES, TXT_NO ) {
			@Override
			protected void onSelect( int index ) {
				switch (index) {
				case 0:
					curUser.spendAndNext( TIME_TO_READ );
					identifiedByUse = false;
					break;
				case 1:
					GameScene.selectItem( itemSelector, mode, inventoryTitle );
					break;
				}
			}
			public void onBackPressed() {};
		} );
	}
	
	protected abstract void onItemSelected( Item item );
	
	protected static boolean identifiedByUse = false;
	protected static WndBag.Listener itemSelector = new WndBag.Listener() {
		@Override
		public void onSelect( Item item ) {
			if (item != null) {

				((A)curItem).onItemSelected( item );
				((A)curItem).readAnimation();
				
				Sample.INSTANCE.play( Assets.SND_READ );
				Invisibility.dispel();
				
			} else if (identifiedByUse) {
				
				((A)curItem).confirmCancelation();
				
			} else {

				curItem.collect( curUser.belongings.backpack );
				
			}
		}
	};
}
