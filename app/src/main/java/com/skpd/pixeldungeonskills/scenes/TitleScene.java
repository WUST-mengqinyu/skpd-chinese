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


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.opengl.GLES20;
import android.text.InputType;
import android.widget.EditText;

import com.skpd.noosa.BitmapText;
import com.skpd.noosa.Camera;
import com.skpd.noosa.Game;
import com.skpd.noosa.Image;
import com.skpd.noosa.RenderedText;
import com.skpd.noosa.audio.Music;
import com.skpd.noosa.audio.Sample;
import com.skpd.noosa.ui.Button;
import com.skpd.pixeldungeonskills.Assets;
import com.skpd.pixeldungeonskills.Dungeon;
import com.skpd.pixeldungeonskills.PixelDungeon;
import com.skpd.pixeldungeonskills.VersionNewsInfo;
import com.skpd.pixeldungeonskills.effects.BannerSprites;
import com.skpd.pixeldungeonskills.effects.Fireball;
import com.skpd.pixeldungeonskills.messages.Messages;
import com.skpd.pixeldungeonskills.ui.Archs;
import com.skpd.pixeldungeonskills.ui.ExitButton;
import com.skpd.pixeldungeonskills.ui.PrefsButton;
import com.skpd.pixeldungeonskills.ui.RedButton;
import com.skpd.pixeldungeonskills.windows.LanguageButton;

import java.io.IOException;

import javax.microedition.khronos.opengles.GL10;

import static com.skpd.pixeldungeonskills.Dungeon.name;


public class TitleScene extends PixelScene {

	@Override
	public void create() {
		
		super.create();

		if(name == null){
			goStore();
		}

		Music.INSTANCE.play( Assets.THEME, true );
		Music.INSTANCE.volume( 1f );
		
		uiCamera.visible = false;
		
		int w = Camera.main.width;
		int h = Camera.main.height;
		
		Archs archs = new Archs();
		archs.setSize( w, h );
		add( archs );
		
		Image title = BannerSprites.get( BannerSprites.Type.PIXEL_DUNGEON );
		add( title );

		float height = title.height +
			(PixelDungeon.landscape() ? DashboardItem.SIZE : DashboardItem.SIZE * 2);

		float topRegion = Math.max(95f, h*0.45f);

		title.x = (w - title.width()) / 2;
		if (PixelDungeon.landscape())
			title.y = (topRegion - title.height()) / 2f;
		else
			title.y = 16 + (topRegion - title.height() - 16) / 2f;
		
		placeTorch( title.x + 18, title.y + 20 );
		placeTorch( title.x + title.width - 18, title.y + 20 );
		
		Image signs = new Image( BannerSprites.get( BannerSprites.Type.PIXEL_DUNGEON_SIGNS ) ) {
			private float time = 0;
			@Override
			public void update() {
				super.update();
				am = (float)Math.sin( -(time += Game.elapsed) );
			}
			@Override
			public void draw() {
				GLES20.glBlendFunc( GL10.GL_SRC_ALPHA, GL10.GL_ONE );
				super.draw();
				GLES20.glBlendFunc( GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA );
			}
		};
		signs.x = title.x;
		signs.y = title.y;
		add( signs );
		
		DashboardItem btnBadges = new DashboardItem( Messages.get(this, "badges"), 3 ) {
			@Override
			protected void onClick() {
				PixelDungeon.switchNoFade( BadgesScene.class );
			}
		};
		add( btnBadges );
		
		DashboardItem btnAbout = new DashboardItem( Messages.get(this, "about"), 1 ) {
			@Override
			protected void onClick() {
				PixelDungeon.switchNoFade( AboutScene.class );
			}
		};
		add( btnAbout );
		
		DashboardItem btnPlay = new DashboardItem( Messages.get(this, "play"), 0 ) {
			@Override
			protected void onClick() {
				PixelDungeon.switchNoFade( StartScene.class );
			}
		};
		add( btnPlay );
		
		DashboardItem btnHighscores = new DashboardItem( Messages.get(this, "rankings"), 2 ) {
			@Override
			protected void onClick() {
				PixelDungeon.switchNoFade( RankingsScene.class );
			}
		};
		add( btnHighscores );

		if (PixelDungeon.landscape()) {
			float y = (h + height) / 2 - DashboardItem.SIZE;
			btnHighscores	.setPos( w / 2 - btnHighscores.width(), topRegion );
			btnBadges		.setPos( w / 2, topRegion );
			btnPlay			.setPos( btnHighscores.left() - btnPlay.width(), topRegion );
			btnAbout		.setPos( btnBadges.right(), topRegion );
		} else {
			btnPlay.setPos( w / 2 - btnPlay.width(), topRegion );
			btnHighscores.setPos( w / 2, btnPlay.top() );
			btnBadges.setPos( w / 2 - btnBadges.width(), btnPlay.top() + DashboardItem.SIZE );
			btnAbout.setPos( w / 2, btnBadges.top() );		}

        BitmapText version = new BitmapText( "ForChinese", font1x );
        version.measure();
        version.hardlight( 0xFFFFFF );
        version.x = w - version.width();
        version.y = h - version.height() - 9;
        add( version );
//// FIXME: 2017/10/8
		RedButton test = new RedButton("更改你的账户") {
			@Override
			protected void onClick() {
				goStore();
			}
		};		
		test.setRect(0 , version.y-7, 66, 15);
		add(test);
		try {
			Dungeon.loadName("Name");
		} catch (IOException e) {

			e.printStackTrace();
		}

		RenderedText str = PixelScene.renderText( "id:"+Messages.capitalize(name),9);
		str.x= w/2;
		str.y= 0 ;
		add(str);

		RedButton logcat = new RedButton("logcat"){
			@Override
			protected void onClick(){
				Game.logcat();}
		};
		logcat.setRect(4,100,20,15);
		add(logcat);

        BitmapText versionPD = new BitmapText( Game.version , font1x );
        versionPD.measure();
        versionPD.hardlight( 0x666666 );
        versionPD.x = w - versionPD.width();
        versionPD.y = h - versionPD.height();
        add( versionPD );

		LanguageButton btnLang = new LanguageButton();
		btnLang.setPos(16, 1);
		add( btnLang );


		PrefsButton btnPrefs = new PrefsButton();
		btnPrefs.setPos( 0, 0 );
		add( btnPrefs );
		
		ExitButton btnExit = new ExitButton();
		btnExit.setPos( w - btnExit.width(), 0 );
		add( btnExit );

		if(VersionNewsInfo.useable) {
			add(VersionNewsInfo.getWelcomeWindow());
			VersionNewsInfo.useable=false;
		}
		fadeIn();
	}
	
	private void placeTorch( float x, float y ) {
		Fireball fb = new Fireball();
		fb.setPos( x, y );
		add( fb );
	}

	private static class DashboardItem extends Button {

		public static final float SIZE	= 48;

		private static final int IMAGE_SIZE	= 32;

		private Image image;
		private RenderedText label;

		public DashboardItem( String text, int index ) {
			super();

			image.frame( image.texture.uvRect( index * IMAGE_SIZE, 0, (index + 1) * IMAGE_SIZE, IMAGE_SIZE ) );
			this.label.text( text );

			setSize( SIZE, SIZE );
		}

		@Override
		protected void createChildren() {
			super.createChildren();

			image = new Image( Assets.DASHBOARD );
			add( image );

			label = renderText( 9 );
			add( label );
		}

		@Override
		protected void layout() {
			super.layout();

			image.x = x + (width - image.width()) / 2;
			image.y = y;
			align(image);

			label.x = x + (width - label.width()) / 2;
			label.y = image.y + image.height() +2;
			align(label);
		}

		@Override
		protected void onTouchDown() {
			image.brightness( 1.5f );
			Sample.INSTANCE.play( Assets.SND_CLICK, 1, 1, 0.8f );
		}

		@Override
		protected void onTouchUp() {
			image.resetColor();
		}
	}


	public static void goStore(){

		PixelDungeon.instance.runOnUiThread( new Runnable(){
			@Override
			public void run(){
				final EditText input = new EditText(PixelDungeon.instance);
				AlertDialog.Builder builder = new AlertDialog.Builder(PixelDungeon.instance);
				builder.setTitle("输入你的内测码（最多12字符）");

				// Set up the input

				input.setInputType(InputType.TYPE_CLASS_NUMBER);
				builder.setView(input);

				// Set up the buttons
				builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						//// FIXME: 2017/10/8
						int a1=50806387;
						int a2=1771999446;
						int a3=131460;
						int a4=1361791669;
						int a5=1050574395;
						int a6=1234567890;
						int a7=0;
						int a8=159357;
						int a9=0;
						int b1=65358979;
						int b2=123456;
						int b3=1260578152;
						int b4=123456789;
						int b5=698110;
						int b6=0;
						int b7=123017472;
						int b8=1185528245;
						int b9=0;
						int b0=449667466;
						int p0=952223482;
						String name = input.getText().toString();
						int num = Integer.parseInt(name);
						if ( num==a1|| num==a2|| num==a3|| num==a4|| num==a5|| num==a6||  num==a8||  num==b1|| num==b2|| num==b3|| num==b4|| num==b5||num==b7||num==b8||num==b9||num==b0) {
							if (num == a1){
								name = "雷霆";}
							if (num == a2){
								name ="翻身的咸鱼";}
							if (num == a3){
								name ="彦";}
							if (num == a4){
								name ="Midens";}
							if (num == a5){
								name ="魔术师ξ";}
							if (num == a6){
								name ="疯掉的村民";}
							if (num == a7){
								name =null;}
							if (num == a8){
								name ="Wildwolf";}
							if (num == a9){
								name =null;}
							if (num == b1){
								name ="Moonfair";}
							if (num == b2){
								name ="faye";}
							if (num == b3){
								name ="Morinaga";}
							if (num == b4){
								name ="随吾";}
							if (num == b5){
								name ="kiki";}
							if (num == b6){
								name =null;}
							if (num == b7){
								name ="灵枫";}
							if (num == b8){
								name ="912567";}
							if (num == b9){
								name =null;}
							if (num == b0) {
								name = "Kandfo";}
							Dungeon.name = name;
							Dungeon.changename = false;
							try {
								Dungeon.saveName();
							} catch (IOException e) {
								e.printStackTrace();
							}
							Game.resetScene();
						}
						else if (num == p0 ){
							name = "访客型账户";
							Dungeon.name= name;
							try {
								Dungeon.saveName();
							} catch (IOException e) {
								e.printStackTrace();
							}
							Game.resetScene();
						}
						else{
							dialog.cancel();
							retry();
						}
					}
				});
				builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});

				builder.show();
			}
		});
	}

	public static void retry(){
		AlertDialog.Builder retry = new AlertDialog.Builder(PixelDungeon.instance);
		retry.setTitle("内测码错误请重试");
		retry.setPositiveButton("重试", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				goStore();
				dialog.cancel();

			}
		});
		retry.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		retry.show();
	}

}
