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

import com.skpd.noosa.Camera;
import com.skpd.noosa.audio.Sample;
import com.skpd.pixeldungeonskills.Assets;
import com.skpd.pixeldungeonskills.Dungeon;
import com.skpd.pixeldungeonskills.PixelDungeon;
import com.skpd.pixeldungeonskills.actors.hero.Legend;
import com.skpd.pixeldungeonskills.messages.Messages;
import com.skpd.pixeldungeonskills.scenes.PixelScene;
import com.skpd.pixeldungeonskills.ui.CheckBox;
import com.skpd.pixeldungeonskills.ui.RedButton;
import com.skpd.pixeldungeonskills.ui.Toolbar;
import com.skpd.pixeldungeonskills.ui.Window;

public class WndSettings extends Window {
	
	private static final String TXT_ZOOM_IN			= "+";
	private static final String TXT_ZOOM_OUT		= "-";
	private static final String TXT_ZOOM_DEFAULT	= Messages.get(WndSettings.class,"1");

	private static final String TXT_SCALE_UP		= Messages.get(WndSettings.class,"2");
	private static final String TXT_IMMERSIVE		= Messages.get(WndSettings.class,"3");
	
	private static final String TXT_MUSIC	= Messages.get(WndSettings.class,"4");
	
	private static final String TXT_SOUND	= Messages.get(WndSettings.class,"5");
	
	private static final String TXT_BRIGHTNESS	= Messages.get(WndSettings.class,"6");
	
	private static final String TXT_QUICKSLOT	= Messages.get(WndSettings.class,"7");
	
	private static final String TXT_SWITCH_PORT	= Messages.get(WndSettings.class,"8");
	private static final String TXT_SWITCH_LAND	= Messages.get(WndSettings.class,"9");
	
	private static final int WIDTH		= 112;
	private static final int BTN_HEIGHT	= 20;
	private static final int GAP 		= 2;
	
	private RedButton btnZoomOut;
	private RedButton btnZoomIn;
	
	public WndSettings( boolean inGame ) {
		super();
		
		CheckBox btnImmersive = null;
		
		if (inGame) {
			int w = BTN_HEIGHT;
			
			btnZoomOut = new RedButton( TXT_ZOOM_OUT ) {
				@Override
				protected void onClick() {
					zoom( Camera.main.zoom - 1 );
				}
			};
			add( btnZoomOut.setRect( 0, 0, w, BTN_HEIGHT) );
			
			btnZoomIn = new RedButton( TXT_ZOOM_IN ) {
				@Override
				protected void onClick() {
					zoom( Camera.main.zoom + 1 );
				}
			};
			add( btnZoomIn.setRect( WIDTH - w, 0, w, BTN_HEIGHT) );
			
			add( new RedButton( TXT_ZOOM_DEFAULT ) {
				@Override
				protected void onClick() {
					zoom( PixelScene.defaultZoom );
				}
			}.setRect( btnZoomOut.right(), 0, WIDTH - btnZoomIn.width() - btnZoomOut.width(), BTN_HEIGHT ) );
			
			updateEnabled();
			
		} else {
			
			CheckBox btnScaleUp = new CheckBox( TXT_SCALE_UP ) {
				@Override
				protected void onClick() {
					super.onClick();
					PixelDungeon.scaleUp( checked() );
				}
			};
			btnScaleUp.setRect( 0, 0, WIDTH, BTN_HEIGHT );
			btnScaleUp.checked( PixelDungeon.scaleUp() );
			add( btnScaleUp );
			
			btnImmersive = new CheckBox( TXT_IMMERSIVE ) {
				@Override
				protected void onClick() {
					super.onClick();
					PixelDungeon.immerse( checked() );
				}
			};
			btnImmersive.setRect( 0, btnScaleUp.bottom() + GAP, WIDTH, BTN_HEIGHT );
			btnImmersive.checked( PixelDungeon.immersed() );
			btnImmersive.enable( android.os.Build.VERSION.SDK_INT >= 19 );
			add( btnImmersive );
			
		}
		
		CheckBox btnMusic = new CheckBox( TXT_MUSIC ) {
			@Override
			protected void onClick() {
				super.onClick();
				PixelDungeon.music( checked() );
			}
		};
		btnMusic.setRect( 0, (btnImmersive != null ? btnImmersive.bottom() : BTN_HEIGHT) + GAP, WIDTH, BTN_HEIGHT );
		btnMusic.checked( PixelDungeon.music() );
		add( btnMusic );
		
		CheckBox btnSound = new CheckBox( TXT_SOUND ) {
			@Override
			protected void onClick() {
				super.onClick();
				PixelDungeon.soundFx( checked() );
				Sample.INSTANCE.play( Assets.SND_CLICK );
			}
		};
		btnSound.setRect( 0, btnMusic.bottom() + GAP, WIDTH, BTN_HEIGHT );
		btnSound.checked( PixelDungeon.soundFx() );
		add( btnSound );

        if(Dungeon.hero == null || !(Dungeon.hero instanceof Legend)) {
            if (inGame) {

                CheckBox btnDeg = new CheckBox(Messages.get(this,"0")) {
                    @Override
                    protected void onClick() {
                        super.onClick();
                        PixelDungeon.itemDeg(checked());
                        //Sample.INSTANCE.play( Assets.SND_CLICK );
                    }
                };
                btnDeg.setRect(0, btnSound.bottom() + GAP, WIDTH, BTN_HEIGHT);
                btnDeg.checked(PixelDungeon.itemDeg());
                add(btnDeg);

                CheckBox btnBrightness = new CheckBox(TXT_BRIGHTNESS) {
                    @Override
                    protected void onClick() {
                        super.onClick();
                        PixelDungeon.brightness(checked());
                    }
                };
                btnBrightness.setRect(0, btnDeg.bottom() + GAP, WIDTH, BTN_HEIGHT);
                btnBrightness.checked(PixelDungeon.brightness());
                add(btnBrightness);

                CheckBox btnQuickslot = new CheckBox(TXT_QUICKSLOT) {
                    @Override
                    protected void onClick() {
                        super.onClick();
                        Toolbar.secondQuickslot(checked());
                    }
                };
                btnQuickslot.setRect(0, btnBrightness.bottom() + GAP, WIDTH, BTN_HEIGHT);
                btnQuickslot.checked(Toolbar.secondQuickslot());
                add(btnQuickslot);

                resize(WIDTH, (int) btnQuickslot.bottom());

            } else {

                RedButton btnOrientation = new RedButton(orientationText()) {
                    @Override
                    protected void onClick() {
                        PixelDungeon.landscape(!PixelDungeon.landscape());
                    }
                };
                btnOrientation.setRect(0, btnSound.bottom() + GAP, WIDTH, BTN_HEIGHT);
                add(btnOrientation);

                resize(WIDTH, (int) btnOrientation.bottom());

            }
        }
        else
        {
            resize(WIDTH, (int) btnSound.bottom());
            if (!inGame) {
                RedButton btnOrientation = new RedButton(orientationText()) {
                    @Override
                    protected void onClick() {
                        PixelDungeon.landscape(!PixelDungeon.landscape());
                    }
                };
                btnOrientation.setRect(0, btnSound.bottom() + GAP, WIDTH, BTN_HEIGHT);
                add(btnOrientation);


                resize(WIDTH, (int) btnOrientation.bottom());
            }
        }
	}
	
	private void zoom( float value ) {

		Camera.main.zoom( value );
		PixelDungeon.zoom( (int)(value - PixelScene.defaultZoom) );

		updateEnabled();
	}
	
	private void updateEnabled() {
		float zoom = Camera.main.zoom;
		btnZoomIn.enable( zoom < PixelScene.maxZoom );
		btnZoomOut.enable( zoom > PixelScene.minZoom );
	}
	
	private String orientationText() {
		return PixelDungeon.landscape() ? TXT_SWITCH_PORT : TXT_SWITCH_LAND;
	}
}
