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
package com.skpd.pixeldungeonskills.windows;

import com.skpd.pixeldungeonskills.items.Heap;
import com.skpd.pixeldungeonskills.items.Heap.Type;
import com.skpd.pixeldungeonskills.items.Item;
import com.skpd.pixeldungeonskills.messages.Messages;
import com.skpd.pixeldungeonskills.scenes.PixelScene;
import com.skpd.pixeldungeonskills.sprites.ItemSprite;
import com.skpd.pixeldungeonskills.ui.ItemSlot;
import com.skpd.pixeldungeonskills.ui.RenderedTextMultiline;
import com.skpd.pixeldungeonskills.ui.Window;
import com.skpd.pixeldungeonskills.utils.Utils;

public class WndInfoItem extends Window {
	
	private final String TXT_CHEST			= Messages.get(WndInfoItem.class,"1");
	private final String TXT_LOCKED_CHEST	= Messages.get(WndInfoItem.class,"2");
	private final String TXT_CRYSTAL_CHEST	= Messages.get(WndInfoItem.class,"3");
	private final String TXT_TOMB			= Messages.get(WndInfoItem.class,"4");
	private final String TXT_SKELETON		= Messages.get(WndInfoItem.class,"5");
	private final String TXT_WONT_KNOW		= Messages.get(WndInfoItem.class,"6");
	private final String TXT_NEED_KEY		= TXT_WONT_KNOW +  Messages.get(WndInfoItem.class,"7");
	private final String TXT_INSIDE			= Messages.get(WndInfoItem.class,"8");
	private final String TXT_OWNER			= Messages.get(WndInfoItem.class,"9");
	private final String TXT_REMAINS		= Messages.get(WndInfoItem.class,"0");
	
	private static final float GAP	= 2;
	
	private static final int WIDTH = 120;
	
	public WndInfoItem( Heap heap ) {
		
		super();
		
		if (heap.type == Heap.Type.HEAP || heap.type == Heap.Type.FOR_SALE) {
			
			Item item = heap.peek();
			
			int color = TITLE_COLOR;
			if (item.levelKnown) {
				if (item.level() < 0) {
					color = ItemSlot.DEGRADED;				
				} else if (item.level() > 0) {
					color = item.isBroken() ? ItemSlot.WARNING : ItemSlot.UPGRADED;				
				}
			}
			fillFields( item.image(), item.glowing(), color, item.toString(), item.info() );
			
		} else {
			
			String title;
			String info;
			
			if (heap.type == Type.CHEST || heap.type == Type.MIMIC) {
				title = TXT_CHEST;
				info = TXT_WONT_KNOW;
			} else if (heap.type == Type.TOMB) {
				title = TXT_TOMB;
				info = TXT_OWNER;
			} else if (heap.type == Type.SKELETON) {
				title = TXT_SKELETON;
				info = TXT_REMAINS;
			} else if (heap.type == Type.CRYSTAL_CHEST) {
				title = TXT_CRYSTAL_CHEST;
				info = Utils.format( TXT_INSIDE, Utils.indefinite( heap.peek().name() ) );
			} else {
				title = TXT_LOCKED_CHEST;
				info = TXT_NEED_KEY;
			}
			
			fillFields( heap.image(), heap.glowing(), TITLE_COLOR, title, info );
			
		}
	}
	
	public WndInfoItem( Item item ) {
		
		super();
		
		int color = TITLE_COLOR;
		if (item.levelKnown) {
			if (item.level() < 0 || item.isBroken()) {
				color = ItemSlot.DEGRADED;				
			} else if (item.level() > 0) {
				color = ItemSlot.UPGRADED;				
			}
		}
		
		fillFields( item.image(), item.glowing(), color, item.toString(), item.info() );
	}
	
	private void fillFields( int image, ItemSprite.Glowing glowing, int titleColor, String title, String info ) {
		
		IconTitle titlebar = new IconTitle();
		titlebar.icon( new ItemSprite( image, glowing ) );
		titlebar.label( Utils.capitalize( title ), titleColor );
		titlebar.setRect( 0, 0, WIDTH, 0 );
		add( titlebar );
		
		RenderedTextMultiline txtInfo = PixelScene.renderMultiline( info, 6 );
		txtInfo.maxWidth (WIDTH);
		txtInfo.setPos(titlebar.left(),titlebar.bottom() + GAP);
		add( txtInfo );
		
		resize( WIDTH, (int)(txtInfo.top() + txtInfo.height()) );
	}
}
