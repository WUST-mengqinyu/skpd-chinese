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

import com.skpd.noosa.Image;
import com.skpd.noosa.ui.Component;
import com.skpd.pixeldungeonskills.items.Item;
import com.skpd.pixeldungeonskills.scenes.PixelScene;
import com.skpd.pixeldungeonskills.sprites.ItemSprite;
import com.skpd.pixeldungeonskills.ui.HealthBar;
import com.skpd.pixeldungeonskills.ui.RenderedTextMultiline;
import com.skpd.pixeldungeonskills.ui.Window;
import com.skpd.pixeldungeonskills.utils.Utils;

public class IconTitle extends Component {

	private static final float FONT_SIZE = 9;
	
	private static final float GAP = 2;
	
	protected Image imIcon;
	protected RenderedTextMultiline tfLabel;
	protected HealthBar health;
	
	private float healthLvl = Float.NaN;
	
	public IconTitle() {
		super();
	}
	
	public IconTitle( Item item ) {
		this( 
			new ItemSprite( item.image(), item.glowing() ), 
			Utils.capitalize( item.toString() ) );
	}
	
	public IconTitle( Image icon, String label ) {
		super();
		
		icon( icon );
		label( label );
	}
	
	@Override
	protected void createChildren() {
		imIcon = new Image();
		add( imIcon );
		
		tfLabel = PixelScene.renderMultiline( (int)FONT_SIZE );
		tfLabel.hardlight( Window.TITLE_COLOR );
		add( tfLabel );
		
		health = new HealthBar();
		add( health );
	}
	
	@Override
	protected void layout() {
		
		health.visible = !Float.isNaN( healthLvl );

		imIcon.x = x + (Math.max(0, 8 - imIcon.width()/2));
		imIcon.y = y + (Math.max(0, 8 - imIcon.height()/2));
		PixelScene.align(imIcon);

		int imWidth = (int)Math.max(imIcon.width(), 16);
		int imHeight = (int)Math.max(imIcon.height(), 16);

		tfLabel.maxWidth((int)(width - (imWidth + GAP)));
		tfLabel.setPos(x + imWidth + GAP, imHeight > tfLabel.height() ?
				y +(imHeight - tfLabel.height()) / 2 :
				y);
		PixelScene.align(tfLabel);

		if (health.visible) {
			health.setRect( tfLabel.left(), tfLabel.bottom(), tfLabel.maxWidth(), 0 );
			height = Math.max( imHeight, health.bottom() );
		} else {
			height = Math.max( imHeight,  tfLabel.height() );
		}
	}
	
	public void icon( Image icon ) {
		remove( imIcon );
		add( imIcon = icon );
	}
	
	public void label( String label ) {
		tfLabel.text( label );
	}
	
	public void label( String label, int color ) {
		tfLabel.text( label );
		tfLabel.hardlight( color );
	}
	
	public void color( int color ) {
		tfLabel.hardlight( color );
	}
	
	public void health( float value ) {
		health.level( healthLvl = value );
		layout();
	}
}
