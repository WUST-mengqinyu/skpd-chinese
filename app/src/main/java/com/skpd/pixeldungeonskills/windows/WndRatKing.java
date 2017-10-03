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

import android.graphics.RectF;

import com.skpd.noosa.Camera;
import com.skpd.noosa.Image;
import com.skpd.noosa.ui.Component;
import com.skpd.pixeldungeonskills.Difficulties;
import com.skpd.pixeldungeonskills.Dungeon;
import com.skpd.pixeldungeonskills.actors.buffs.Champ;
import com.skpd.pixeldungeonskills.messages.Messages;
import com.skpd.pixeldungeonskills.scenes.PixelScene;
import com.skpd.pixeldungeonskills.ui.CheckBox;
import com.skpd.pixeldungeonskills.ui.Icons;
import com.skpd.pixeldungeonskills.ui.RedButton;
import com.skpd.pixeldungeonskills.ui.RenderedTextMultiline;
import com.skpd.pixeldungeonskills.ui.Window;
import com.skpd.pixeldungeonskills.utils.Utils;

public class WndRatKing extends WndTabbed {

    public static enum Mode {
        NORMAL,
        CHAMPIONS,
        HERO
    }

    public Mode mode = Mode.NORMAL;

	private static final String TXT_PLUS = "+";
	private static final String TXT_MINUS = "-";
	private static final String TXT_CHAMPION_CHANCE = Messages.get(WndRatKing.class,"1");




	private static final String TXT_TRUE_KING_TITLE	= Messages.get(WndRatKing.class,"2");


	private static final int WIDTH		= 112;
	private static final int BTN_HEIGHT	= 20;
	private static final int GAP 		= 2;

	private RedButton btnZoomOut;
	private RedButton btnZoomIn;
    private RedButton btnChamps;

    private RedButton btMobHP;
    private RedButton btMobAtt;
    private RedButton btMobDef;
    private RedButton btDayNight;

    protected static final int TAB_WIDTH	= 25;

    public static int maxHeight = 0;

	public WndRatKing(Mode mode) {
		super();

        this.mode = mode;

        if(mode == Mode.NORMAL) {
            Component titlebar = new IconTitle(Icons.RAT_KING.get(), TXT_TRUE_KING_TITLE);
            titlebar.setRect(0, 0, WIDTH, 0);
            add(titlebar);

            String description = Messages.get(WndRatKing.class,"3");
            RenderedTextMultiline txtInfo = PixelScene.renderMultiline( description, 6 );
            txtInfo.maxWidth (WIDTH);
            txtInfo.setPos(titlebar.left(),titlebar.bottom() + GAP);
            add( txtInfo );

            if(maxHeight < (int) txtInfo.top() + (int)txtInfo.height() )
                maxHeight = (int) txtInfo.top() + (int)txtInfo.height();

            resize(WIDTH, maxHeight);
        }
        else if(mode == Mode.CHAMPIONS) {
            CheckBox btnImmersive = null;


            int w = BTN_HEIGHT;

            btnZoomOut = new RedButton(TXT_MINUS) {
                @Override
                protected void onClick() {
                    Dungeon.currentDifficulty.changeChampionOffset(-1);
                    updateEnabled();
                }
            };
            add(btnZoomOut.setRect(0, 0, w, BTN_HEIGHT));

            btnZoomIn = new RedButton(TXT_PLUS) {
                @Override
                protected void onClick() {
                    Dungeon.currentDifficulty.changeChampionOffset(1);
                    ;
                    updateEnabled();
                }
            };
            add(btnZoomIn.setRect(WIDTH - w, 0, w, BTN_HEIGHT));

            btnChamps = new RedButton((String.format(TXT_CHAMPION_CHANCE, (Dungeon.currentDifficulty.championChance() * 10 + "%")))) {
                @Override
                protected void onClick() {
                    if(Difficulties.canDisableChampions == false)
                        parent.add(new previewInformation(Icons.get(Icons.CHAMP_HALO), "Disable Champions",  "Please consider donating to unlock disabling specific types of champions."));
                }
            };

            add(btnChamps.setRect(btnZoomOut.right(), 0, WIDTH - btnZoomIn.width() - btnZoomOut.width(), BTN_HEIGHT));

            updateEnabled();


            CheckBox btnChief = new CheckBox("Disable Chief") {
                @Override
                protected void onClick() {
                    super.onClick();
                    if(!Dungeon.currentDifficulty.disableChampion(Champ.CHAMP_CHIEF, checked()))
                        checked(false);
                }
            };
            btnChief.setRect(0, (btnImmersive != null ? btnImmersive.bottom() : BTN_HEIGHT) + GAP, WIDTH, BTN_HEIGHT);
            btnChief.checked(Dungeon.currentDifficulty.disableChampion(Champ.CHAMP_CHIEF));
            add(btnChief);

            CheckBox btnVamp = new CheckBox("Disable Vampiric") {
                @Override
                protected void onClick() {
                    super.onClick();
                    if(!Dungeon.currentDifficulty.disableChampion(Champ.CHAMP_VAMPERIC, checked()))
                        checked(false);
                }
            };
            btnVamp.setRect(0, btnChief.bottom() + GAP, WIDTH, BTN_HEIGHT);
            btnVamp.checked(Dungeon.currentDifficulty.disableChampion(Champ.CHAMP_VAMPERIC));
            add(btnVamp);


            CheckBox btnCursed = new CheckBox("Disable Cursed") {
                @Override
                protected void onClick() {
                    super.onClick();
                    if(!Dungeon.currentDifficulty.disableChampion(Champ.CHAMP_CURSED, checked()))
                        checked(false);
                }
            };
            btnCursed.setRect(0, btnVamp.bottom() + GAP, WIDTH, BTN_HEIGHT);
            btnCursed.checked(Dungeon.currentDifficulty.disableChampion(Champ.CHAMP_CURSED));
            add(btnCursed);

            CheckBox btnFoul = new CheckBox("Disable Foul") {
                @Override
                protected void onClick() {
                    super.onClick();
                    if(!Dungeon.currentDifficulty.disableChampion(Champ.CHAMP_FOUL, checked()))
                        checked(false);
                }
            };

            btnFoul.setRect(0, btnCursed.bottom() + GAP, WIDTH, BTN_HEIGHT);
            btnFoul.checked(Dungeon.currentDifficulty.disableChampion(Champ.CHAMP_FOUL));
            add(btnFoul);

            if(Difficulties.canDisableChampions == false)
            {
                btnFoul.enable(false);
                btnChief.enable(false);
                btnVamp.enable(false);
                btnCursed.enable(false);
            }

            if(maxHeight < (int) btnFoul.bottom() )
                maxHeight = (int) btnFoul.bottom();

            resize(WIDTH, maxHeight);

        }
        else if(mode == Mode.HERO) {
            CheckBox btnImmersive = null;



            btnChamps = new RedButton((String.format("Mobs Information"))) {
                @Override
                protected void onClick() {

                }
            };

            add(btnChamps.setRect(0, 0, WIDTH, BTN_HEIGHT));


            int w = BTN_HEIGHT;

            add(new RedButton(TXT_MINUS) {
                @Override
                protected void onClick() {
                    Dungeon.currentDifficulty.changeHPOffset(-0.1f);
                    updateMobStats();
                }
            }.setRect(0, BTN_HEIGHT + GAP, w, BTN_HEIGHT));

            add(new RedButton(TXT_PLUS) {
                @Override
                protected void onClick() {
                    Dungeon.currentDifficulty.changeHPOffset(0.1f);
                    updateMobStats();
                }
            }.setRect(WIDTH - w, BTN_HEIGHT + GAP, w, BTN_HEIGHT));

            btMobHP = new RedButton("HP: 100" + "%") {
                @Override
                protected void onClick() {

                }
            };
            add(btMobHP.setRect(w, BTN_HEIGHT + GAP, WIDTH - 2 * w, BTN_HEIGHT));


            add(new RedButton(TXT_MINUS) {
                @Override
                protected void onClick() {
                    Dungeon.currentDifficulty.changeDamageOffset(-0.1f);
                    updateMobStats();
                }
            }.setRect(0,  btMobHP.bottom() + GAP, w, BTN_HEIGHT));

            add(new RedButton(TXT_PLUS) {
                @Override
                protected void onClick() {
                    Dungeon.currentDifficulty.changeDamageOffset(0.1f);
                    updateMobStats();
                }
            }.setRect(WIDTH - w,  btMobHP.bottom() + GAP, w, BTN_HEIGHT));

            btMobAtt = new RedButton("ATT: 100" + "%") {
                @Override
                protected void onClick() {

                }
            };
            add(btMobAtt.setRect(w, btMobHP.bottom()  + GAP, WIDTH - 2 * w, BTN_HEIGHT));

            add(new RedButton(TXT_MINUS) {
                @Override
                protected void onClick() {
                    Dungeon.currentDifficulty.changeDefenceOffset(0.1f);
                    updateMobStats();
                }
            }.setRect(0,  btMobAtt.bottom() + GAP, w, BTN_HEIGHT));

            add(new RedButton(TXT_PLUS) {
                @Override
                protected void onClick() {
                    Dungeon.currentDifficulty.changeDefenceOffset(-0.1f);
                    updateMobStats();
                }
            }.setRect(WIDTH - w,  btMobAtt.bottom() + GAP, w, BTN_HEIGHT));

            btMobDef = new RedButton("Def: 100" + "%") {
                @Override
                protected void onClick() {

                }
            };

            add(btMobDef.setRect(w, btMobAtt.bottom()  + GAP, WIDTH - 2 * w, BTN_HEIGHT));

            add(new RedButton(TXT_MINUS) {
                @Override
                protected void onClick() {
                    Dungeon.currentDifficulty.ToggleNight(false);
                    updateMobStats();
                }
            }.setRect(0,  btMobDef.bottom() + GAP, w, BTN_HEIGHT));

            add(new RedButton(TXT_PLUS) {
                @Override
                protected void onClick() {
                    Dungeon.currentDifficulty.ToggleNight(true);
                    updateMobStats();
                }
            }.setRect(WIDTH - w,  btMobDef.bottom() + GAP, w, BTN_HEIGHT));

            btDayNight = new RedButton("DAY NIGHT") {
                @Override
                protected void onClick() {

                }
            };
            add(btDayNight.setRect(w, btMobDef.bottom()  + GAP, WIDTH - 2 * w, BTN_HEIGHT));

            if(maxHeight < (int) btDayNight.bottom() )
                maxHeight = (int) btDayNight.bottom();

            resize(WIDTH, maxHeight);
            updateMobStats();

        }

        StatsControl tab = new StatsControl(Mode.NORMAL);
        tab.setSize(TAB_WIDTH, tabHeight());
        add(tab);
        tab.select(mode == Mode.NORMAL);

        StatsControl tabChampions = new StatsControl(Mode.CHAMPIONS);
        tabChampions.setSize(TAB_WIDTH, tabHeight());
        add(tabChampions);

        tabChampions.select(mode == Mode.CHAMPIONS);


        StatsControl tabHero = new StatsControl(Mode.HERO);
        tabHero.setSize(TAB_WIDTH, tabHeight());
        add(tabHero);

        tabHero.select(mode == Mode.HERO);
	}

    @Override
    protected void onClick( Tab tab ) {
        parent.add(new WndRatKing(((StatsControl)tab).mode));
        hide();
    }

	
	private void updateEnabled() {
		float zoom = Camera.main.zoom;
		btnZoomIn.enable( zoom < PixelScene.maxZoom );
		btnZoomOut.enable( zoom > PixelScene.minZoom );
        btnChamps.text(String.format(TXT_CHAMPION_CHANCE, (Dungeon.currentDifficulty.championChance() * 10 + "%")));
	}

    private void updateMobStats()
    {
        btMobHP.text(String.format("HP: %s", ((int)(Dungeon.currentDifficulty.mobHPModifier() * 100) + "%")));
        btMobAtt.text(String.format("ATT: %s", ((int)(Dungeon.currentDifficulty.damageModifier() * 100) + "%")));
        btMobDef.text(String.format("Def: %s", ((int)((2 - Dungeon.currentDifficulty.mobDefenceModifier()) * 100) + "%")));
        btDayNight.text(Dungeon.currentDifficulty.GetToggleNightDesc());
    }
	


    private class StatsControl extends Tab {

        private Image icon = null;
        Mode mode = Mode.NORMAL;

        public StatsControl(Mode mode) {
            super();
            this.mode = mode;
            icon = Icons.get(getIcon());
            add( icon );

        }

        public Icons getIcon()
        {
            switch (mode)
            {
                case CHAMPIONS: return Icons.CHAMP_HALO;
                case HERO: return Icons.MOB;
            }
           return Icons.RAT_KING;
        }

        @Override
        protected void select( boolean value ) {
            super.select( value );
            icon.am = selected ? 1.0f : 0.6f;
        }

        @Override
        protected void layout() {
            super.layout();

            icon.copy( icon );
            icon.x = x + (width - icon.width) / 2;
            icon.y = y + (height - icon.height) / 2 - 2 - (selected ? 0 : 1);
            if (!selected && icon.y < y + CUT) {
                RectF frame = icon.frame();
                frame.top += (y  - icon.y) / icon.texture.height;
                icon.frame( frame );
                icon.y = y;
            }
        }
    }

    private class previewInformation extends Window
    {
        public previewInformation(Image image, String title, String description)
        {

            IconTitle titlebar = new IconTitle();
            titlebar.icon( image);
            titlebar.label( Utils.capitalize(title), TITLE_COLOR );
            titlebar.setRect( 0, 0, WIDTH - GAP, 0 );
            add( titlebar );

            RenderedTextMultiline txtInfo = PixelScene.renderMultiline( description, 6 );
            txtInfo.maxWidth (WIDTH - GAP * 2);
            txtInfo.setPos(titlebar.left(),titlebar.bottom() + GAP);
            add( txtInfo );

            resize( 100, (int) txtInfo.top() + (int) txtInfo.height() + (int)GAP);
        }
    }
}
