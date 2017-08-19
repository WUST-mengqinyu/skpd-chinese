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
package com.skpd.pixeldungeonskills.scenes;

import android.content.Intent;
import android.net.Uri;

import com.skpd.input.Touchscreen.Touch;
import com.skpd.noosa.BitmapTextMultiline;
import com.skpd.noosa.Camera;
import com.skpd.noosa.Game;
import com.skpd.noosa.Image;
import com.skpd.noosa.TouchArea;
import com.skpd.pixeldungeonskills.PixelDungeon;
import com.skpd.pixeldungeonskills.effects.Flare;
import com.skpd.pixeldungeonskills.ui.Archs;
import com.skpd.pixeldungeonskills.ui.ExitButton;
import com.skpd.pixeldungeonskills.ui.Icons;
import com.skpd.pixeldungeonskills.ui.Window;

public class AboutScene extends PixelScene {

	private static final String LNK = "pixeldungeon.watabou.ru";
    private static final String LNK_SPD = "https://github.com/badcw/skpd-chinese";
    private static final String LNK_SPD_WIKI = "http://pixeldungeon.wikia.com";

    float GAP = 2;
    float pos = 0f;
	@Override
	public void create() {
		super.create();

        BitmapTextMultiline textfirst = createMultiline( "技巧的像素地牢汉化版", 8 );
        textfirst.hardlight( Window.TITLE_COLOR );
        textfirst.maxWidth = Math.min( Camera.main.width, 120 );
        textfirst.measure();
        add( textfirst );

        textfirst.x = align( (Camera.main.width - textfirst.width()) / 2 );
        textfirst.y = align( ((Camera.main.height / 2) - textfirst.height()) / 2 );

        pos =  textfirst.y + textfirst.height() + GAP;

        textfirst = createMultiline( "制作者：雷霆", 8 );
        textfirst.maxWidth = Math.min( Camera.main.width, 120 );
        textfirst.measure();
        add( textfirst );

        textfirst.x = align( (Camera.main.width - textfirst.width()) / 2 );
        textfirst.y = pos;

        pos =  textfirst.y + textfirst.height() + GAP;

        textfirst = createMultiline( "GitHub中提供源码", 8 );
        textfirst.maxWidth = Math.min( Camera.main.width, 120 );
        textfirst.measure();
        add( textfirst );

        textfirst.x = align( (Camera.main.width - textfirst.width()) / 2 );
        textfirst.y = pos;

        pos =  textfirst.y + textfirst.height() + GAP;

        BitmapTextMultiline link_SPD = createMultiline( LNK_SPD, 8 );
        link_SPD.maxWidth = Math.min( Camera.main.width, 120 );
        link_SPD.measure();
        link_SPD.hardlight( Window.TITLE_COLOR );
        add( link_SPD );



        TouchArea hotArea_SPD = new TouchArea( link_SPD ) {
            @Override
            protected void onClick( Touch touch ) {
                Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse( LNK_SPD ) );
                Game.instance.startActivity( intent );
            }
        };
        add( hotArea_SPD );

        link_SPD.x = align( (Camera.main.width - link_SPD.width()) / 2 );
        link_SPD.y = pos;

        pos =  link_SPD.y + link_SPD.height() + GAP;

        BitmapTextMultiline link_SPD_Wiki = createMultiline( LNK_SPD_WIKI, 8 );
        link_SPD_Wiki.maxWidth = Math.min( Camera.main.width, 120 );
        link_SPD_Wiki.measure();
        link_SPD_Wiki.hardlight( Window.TITLE_COLOR );
        add( link_SPD_Wiki );

        link_SPD_Wiki.x = align( (Camera.main.width - link_SPD_Wiki.width()) / 2 );
        link_SPD_Wiki.y = pos;

        pos =  link_SPD_Wiki.y + link_SPD_Wiki.height() + 4 * GAP;

        TouchArea hotArea_SPD_WIKI = new TouchArea( link_SPD_Wiki ) {
            @Override
            protected void onClick( Touch touch ) {
                Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse( LNK_SPD_WIKI ) );
                Game.instance.startActivity( intent );
            }
        };
        add( hotArea_SPD_WIKI );




        BitmapTextMultiline textOther = createMultiline( "Some ice art: Nels Dachel & Sarius", 8 );
        textOther.maxWidth = Math.min( Camera.main.width, 120 );
        textOther.measure();
        add( textOther );

        textOther.x = align( (Camera.main.width - textOther.width()) / 2 );
        textOther.y = pos;

        pos =  textOther.y + textOther.height() + 4 * GAP;

        textOther = createMultiline( "Alternative Sound Track: Jivz & YAPD", 8 );
        textOther.maxWidth = Math.min( Camera.main.width, 120 );
        textOther.measure();
        add( textOther );

        textOther.x = align( (Camera.main.width - textOther.width()) / 2 );
        textOther.y = pos;

        pos =  textOther.y + textOther.height() + 4 * GAP;

        BitmapTextMultiline text = createMultiline( "Based on Pixel Dungeon", 8 );
		text.maxWidth = Math.min( Camera.main.width, 120 );
		text.measure();
		add( text );

		text.x = align( (Camera.main.width - text.width()) / 2 );
		text.y = pos;

        pos =  text.y + text.height() + GAP;

        text = createMultiline( "Code & graphics: Watabou", 8 );
        text.maxWidth = Math.min( Camera.main.width, 120 );
        text.measure();
        add( text );

        text.x = align( (Camera.main.width - text.width()) / 2 );
        text.y = pos;

        pos =  text.y + text.height() + GAP;

        text = createMultiline( "Music: Cube_Code", 8 );
        text.maxWidth = Math.min( Camera.main.width, 120 );
        text.measure();
        add( text );

        text.x = align( (Camera.main.width - text.width()) / 2 );
        text.y = pos;

        pos =  text.y + text.height() + GAP;

		BitmapTextMultiline link = createMultiline( LNK, 8 );
		link.maxWidth = Math.min( Camera.main.width, 120 );
		link.measure();
		link.hardlight( Window.TITLE_COLOR );
		add( link );

		link.x = align( (Camera.main.width - link.width()) / 2 );
		link.y = pos;

		TouchArea hotArea = new TouchArea( link ) {
			@Override
			protected void onClick( Touch touch ) {
				Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse( "http://" + LNK ) );
				Game.instance.startActivity( intent );
			}
		};
		add( hotArea );

		Image wata = Icons.WATA.get();
		wata.x = align( (Camera.main.width - wata.width) / 2 );
		wata.y = text.y + text.height() + wata.height + 8;
		add( wata );

		new Flare( 7, 64 ).color( 0x112233, true ).show( wata, 0 ).angularSpeed = +20;

		Archs archs = new Archs();
		archs.setSize( Camera.main.width, Camera.main.height );
		addToBack( archs );

		ExitButton btnExit = new ExitButton();
		btnExit.setPos( Camera.main.width - btnExit.width(), 0 );
		add( btnExit );

		fadeIn();
	}
	
	@Override
	protected void onBackPressed() {
		PixelDungeon.switchNoFade( TitleScene.class );
	}
}
