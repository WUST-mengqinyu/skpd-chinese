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

import com.skpd.input.Touchscreen;
import com.skpd.noosa.Image;
import com.skpd.noosa.RenderedText;
import com.skpd.noosa.TouchArea;
import com.skpd.noosa.ui.Component;
import com.skpd.pixeldungeonskills.Dungeon;
import com.skpd.pixeldungeonskills.PixelDungeon;
import com.skpd.pixeldungeonskills.actors.Actor;
import com.skpd.pixeldungeonskills.actors.hero.HeroClass;
import com.skpd.pixeldungeonskills.actors.mobs.npcs.HiredMerc;
import com.skpd.pixeldungeonskills.items.armor.ClothArmor;
import com.skpd.pixeldungeonskills.items.armor.LeatherArmor;
import com.skpd.pixeldungeonskills.items.food.ChargrilledMeat;
import com.skpd.pixeldungeonskills.items.potions.PotionOfHealing;
import com.skpd.pixeldungeonskills.items.wands.WandOfBlink;
import com.skpd.pixeldungeonskills.items.weapon.melee.Dagger;
import com.skpd.pixeldungeonskills.items.weapon.melee.Knuckles;
import com.skpd.pixeldungeonskills.items.weapon.melee.Mace;
import com.skpd.pixeldungeonskills.items.weapon.missiles.Bow;
import com.skpd.pixeldungeonskills.items.weapon.missiles.FrostBow;
import com.skpd.pixeldungeonskills.levels.Level;
import com.skpd.pixeldungeonskills.messages.Languages;
import com.skpd.pixeldungeonskills.messages.Messages;
import com.skpd.pixeldungeonskills.scenes.GameScene;
import com.skpd.pixeldungeonskills.scenes.PixelScene;
import com.skpd.pixeldungeonskills.skills.Negotiations;
import com.skpd.pixeldungeonskills.sprites.ItemSprite;
import com.skpd.pixeldungeonskills.sprites.MercSprite;
import com.skpd.pixeldungeonskills.sprites.SkillSprite;
import com.skpd.pixeldungeonskills.ui.Icons;
import com.skpd.pixeldungeonskills.ui.RedButton;
import com.skpd.pixeldungeonskills.ui.RenderedTextMultiline;
import com.skpd.pixeldungeonskills.ui.Window;
import com.skpd.pixeldungeonskills.utils.GLog;
import com.skpd.pixeldungeonskills.utils.Utils;

import java.util.ArrayList;

import static com.skpd.pixeldungeonskills.Dungeon.hero;
import static com.skpd.pixeldungeonskills.actors.mobs.npcs.HiredMerc.MERC_TYPES.Archer;
import static com.skpd.pixeldungeonskills.actors.mobs.npcs.HiredMerc.MERC_TYPES.ArcherMaiden;
import static com.skpd.pixeldungeonskills.actors.mobs.npcs.HiredMerc.MERC_TYPES.Brute;
import static com.skpd.pixeldungeonskills.actors.mobs.npcs.HiredMerc.MERC_TYPES.Thief;
import static com.skpd.pixeldungeonskills.actors.mobs.npcs.HiredMerc.MERC_TYPES.Wizard;

public class WndMercs extends WndTabbed {

	public static enum Mode {
		ALL,
		BRUTE,
		WIZARD,
		THIEF,
		ARCHER,
		ARCHERMAIDEN
	}

    float pos = 5;
    float GAP = 2;

    private static final int WIDTH_P	= 120;
    private static final int WIDTH_L	= 144;

    protected static final int TAB_WIDTH	= 25;

    private static final String TXT_TITLE =  Messages.get(Negotiations.class,"1");

    private static final String TXT_MERCENARIES_DETAIL = Messages.get(WndMercs.class,"1");

    private static final String TXT_NO_GOLD = Messages.get(WndMercs.class,"2");

    public static int maxHeight = 0;

    public static int brute1=100;
    public static int brute2=25;
    public static int wizard1=80;
    public static int wizard2=20;
    public static int thief1=75;
    public static int thief2=15;
    public static int archer1=90;

    private static String speed;
    private static String skill;
    private static String special;
    protected static String Sbrute=Messages.get(WndMercs.class,"5");
    protected static String Swizard=Messages.get(WndMercs.class,"6");
    protected static String Sthief=Messages.get(WndMercs.class,"8");
    protected static String Sarcher=Messages.get(WndMercs.class,"7");
    protected static String Sarcher2=Messages.get(WndMercs.class,"7");


    public WndMercs(final Mode mode) {
        super();

        int width = PixelDungeon.landscape() ? WIDTH_L : WIDTH_P;

        if (mode == Mode.ALL) {
            Component titlebar = new IconTitle(new SkillSprite(96), TXT_TITLE);
            titlebar.setRect(0, 0, width, 0);
            add(titlebar);

            resize(width, (int) titlebar.bottom());


            pos = (int) titlebar.bottom() + GAP * 2;


            RenderedTextMultiline info = PixelScene.renderMultiline(6);
            add(info);

            info.text(TXT_MERCENARIES_DETAIL);
            info.maxWidth(width);
            info.setPos(0, pos);

            pos = (int) info.top() + (int) info.height() + GAP * 2;

            if (maxHeight < pos)
                maxHeight = (int) pos;

            resize(width, maxHeight);
        } else {
            Component titlebar1 = new IconTitle(new SkillSprite(getImage(mode)), "Hire " + (mode == Mode.ARCHER || mode == Mode.ARCHERMAIDEN ? "An " : "A ") + getName(mode));
            Component titlebar2 = new IconTitle(new SkillSprite(getImage(mode)), "雇佣一个" + getName(mode));
            Component titlebar;

            if (Messages.lang() == Languages.ENGLISH) {
                titlebar = titlebar1;
            } else {
                titlebar = titlebar2;
            }

            titlebar.setRect(0, 0, width, 0);

            add(titlebar);

            resize(width, (int) titlebar.bottom());

            pos = (int) titlebar.bottom() + GAP * 2;

            RenderedTextMultiline info = PixelScene.renderMultiline(6);
            add(info);

            info.text(getMercDetails(mode));
            info.maxWidth(width);
            info.setPos(0, pos);

            pos = (int) info.top() + (int) info.height() + GAP * 2;

            switch (mode) {
                case BRUTE:
                    speed = Sbrute;
                    skill = Messages.get(this, "a1");
                    special = Messages.get(this, "b1");
                    break;
                case WIZARD:
                    speed = Swizard;
                    skill = Messages.get(this, "a2");
                    special = Messages.get(this, "b2");
                    break;
                case THIEF:
                    speed = Sthief;
                    skill = Messages.get(this, "a3");
                    special = Messages.get(this, "b3");
                    break;
                case ARCHER:
                    speed = Sarcher;
                    skill = Messages.get(this, "a4");
                    special = Messages.get(this, "b4");
                    break;
                case ARCHERMAIDEN:
                    speed = Sarcher2;
                    skill = Messages.get(this, "a5");
                    special = Messages.get(this, "b5");
                    break;
            }

            statSlot(Messages.get(this, "health"), getHealth(hero.lvl, mode));
            statSlot(Messages.get(this, "strength"), getStrength(hero.lvl, mode));
            statSlot(Messages.get(this, "speed"), speed);
            statSlot(Messages.get(this, "skill"), skill);
            statSlot(Messages.get(this, "special"), special);
            statSlot(Messages.get(this, "gold"), getGoldCost(mode));
            pos += GAP;


            RenderedText equipment = PixelScene.renderText(Utils.capitalize(Messages.get(this, "standard")), 9);
            equipment.hardlight(TITLE_COLOR);
            add(equipment);

            equipment.y = pos;

            pos = equipment.y + equipment.height() + GAP;


            if (mode == Mode.BRUTE) {
                final Image imageWeapon = new ItemSprite(new Mace());
                add(imageWeapon);

                imageWeapon.x = 0;
                imageWeapon.y = pos;

                TouchArea hotArea_imageWeapon = new TouchArea(imageWeapon) {
                    @Override
                    protected void onClick(Touchscreen.Touch touch) {
                    }
                };
                add(hotArea_imageWeapon);


                final Image imageArmor = new ItemSprite(new LeatherArmor());
                add(imageArmor);

                imageArmor.x = imageWeapon.x + imageWeapon.width() + GAP;
                imageArmor.y = pos;

                TouchArea hotArea_imageArmo = new TouchArea(imageArmor) {
                    @Override
                    protected void onClick(Touchscreen.Touch touch) {
                    }
                };
                add(hotArea_imageArmo);

                final Image image = new ItemSprite(new ChargrilledMeat());
                add(image);

                image.x = imageArmor.x + imageArmor.width() + GAP;
                image.y = pos + 2;

                TouchArea hotArea_image = new TouchArea(image) {
                    @Override
                    protected void onClick(Touchscreen.Touch touch) {
                    }
                };
                add(hotArea_image);

                final Image imageSkill = new SkillSprite(3);
                add(imageSkill);

                imageSkill.x = image.x + image.width() + GAP;
                imageSkill.y = pos;

                TouchArea hotArea_imageSkill = new TouchArea(imageSkill) {
                    @Override
                    protected void onClick(Touchscreen.Touch touch) {
                    }
                };
                add(hotArea_imageSkill);

                pos = image.y + image.height() + GAP * 3;
            } else if (mode == Mode.WIZARD) {
                final Image imageWeapon = new ItemSprite(new Knuckles());
                add(imageWeapon);

                imageWeapon.x = 0;
                imageWeapon.y = pos + 2;

                TouchArea hotArea_imageWeapon = new TouchArea(imageWeapon) {
                    @Override
                    protected void onClick(Touchscreen.Touch touch) {
                    }
                };
                add(hotArea_imageWeapon);


                final Image imageArmor = new ItemSprite(new ClothArmor());
                add(imageArmor);

                imageArmor.x = imageWeapon.x + imageWeapon.width() + GAP;
                imageArmor.y = pos + 2;

                TouchArea hotArea_imageArmo = new TouchArea(imageArmor) {
                    @Override
                    protected void onClick(Touchscreen.Touch touch) {
                    }
                };
                add(hotArea_imageArmo);

                final Image image = new ItemSprite(new PotionOfHealing());
                add(image);

                image.x = imageArmor.x + imageArmor.width() + GAP;
                image.y = pos + 2;

                TouchArea hotArea_image = new TouchArea(image) {
                    @Override
                    protected void onClick(Touchscreen.Touch touch) {
                    }
                };
                add(hotArea_image);

                final Image imageSkill = new SkillSprite(41);
                add(imageSkill);

                imageSkill.x = image.x + image.width() + GAP;
                imageSkill.y = pos;

                TouchArea hotArea_imageSkill = new TouchArea(imageSkill) {
                    @Override
                    protected void onClick(Touchscreen.Touch touch) {
                    }
                };
                add(hotArea_imageSkill);

                pos = image.y + image.height() + GAP * 3;
            } else if (mode == Mode.THIEF) {
                final Image imageWeapon = new ItemSprite(new Dagger());
                add(imageWeapon);

                imageWeapon.x = 0;
                imageWeapon.y = pos + 1;

                TouchArea hotArea_imageWeapon = new TouchArea(imageWeapon) {
                    @Override
                    protected void onClick(Touchscreen.Touch touch) {
                    }
                };
                add(hotArea_imageWeapon);


                final Image imageArmor = new ItemSprite(new ClothArmor());
                add(imageArmor);

                imageArmor.x = imageWeapon.x + imageWeapon.width() + GAP;
                imageArmor.y = pos + 2;

                TouchArea hotArea_imageArmo = new TouchArea(imageArmor) {
                    @Override
                    protected void onClick(Touchscreen.Touch touch) {
                    }
                };
                add(hotArea_imageArmo);

                final Image image = new ItemSprite(new PotionOfHealing());
                add(image);

                image.x = imageArmor.x + imageArmor.width() + GAP;
                image.y = pos + 2;

                TouchArea hotArea_image = new TouchArea(image) {
                    @Override
                    protected void onClick(Touchscreen.Touch touch) {
                    }
                };
                add(hotArea_image);

                final Image imageSkill = new SkillSprite(57);
                add(imageSkill);

                imageSkill.x = image.x + image.width() + GAP;
                imageSkill.y = pos;

                TouchArea hotArea_imageSkill = new TouchArea(imageSkill) {
                    @Override
                    protected void onClick(Touchscreen.Touch touch) {
                    }
                };
                add(hotArea_imageSkill);

                pos = image.y + image.height() + GAP * 3;
            } else if (mode == Mode.ARCHER) {
                final Image imageWeapon = new ItemSprite(new Bow());
                add(imageWeapon);

                imageWeapon.x = 0;
                imageWeapon.y = pos + 1;

                TouchArea hotArea_imageWeapon = new TouchArea(imageWeapon) {
                    @Override
                    protected void onClick(Touchscreen.Touch touch) {
                    }
                };
                add(hotArea_imageWeapon);


                final Image imageArmor = new ItemSprite(new ClothArmor());
                add(imageArmor);

                imageArmor.x = imageWeapon.x + imageWeapon.width() + GAP;
                imageArmor.y = pos + 2;

                TouchArea hotArea_imageArmo = new TouchArea(imageArmor) {
                    @Override
                    protected void onClick(Touchscreen.Touch touch) {
                    }
                };
                add(hotArea_imageArmo);

                final Image image = new ItemSprite(new PotionOfHealing());
                add(image);

                image.x = imageArmor.x + imageArmor.width() + GAP;
                image.y = pos + 2;

                TouchArea hotArea_image = new TouchArea(image) {
                    @Override
                    protected void onClick(Touchscreen.Touch touch) {
                    }
                };
                add(hotArea_image);

                final Image imageSkill = new SkillSprite(82);
                add(imageSkill);

                imageSkill.x = image.x + image.width();
                imageSkill.y = pos + 1;

                TouchArea hotArea_imageSkill = new TouchArea(imageSkill) {
                    @Override
                    protected void onClick(Touchscreen.Touch touch) {
                    }
                };
                add(hotArea_imageSkill);

                pos = image.y + image.height() + GAP * 3;
            } else if (mode == Mode.ARCHERMAIDEN) {
                final Image imageWeapon = new ItemSprite(new FrostBow());
                add(imageWeapon);

                imageWeapon.x = 0;
                imageWeapon.y = pos + 1;

                TouchArea hotArea_imageWeapon = new TouchArea(imageWeapon) {
                    @Override
                    protected void onClick(Touchscreen.Touch touch) {
                    }
                };
                add(hotArea_imageWeapon);


                final Image image = new ItemSprite(new PotionOfHealing());
                add(image);

                image.x = imageWeapon.x + imageWeapon.width() + GAP;
                image.y = pos + 2;

                TouchArea hotArea_image = new TouchArea(image) {
                    @Override
                    protected void onClick(Touchscreen.Touch touch) {
                    }
                };
                add(hotArea_image);

                final Image imageSkill = new SkillSprite(82);
                add(imageSkill);

                imageSkill.x = image.x + image.width();
                imageSkill.y = pos + 1;

                TouchArea hotArea_imageSkill = new TouchArea(imageSkill) {
                    @Override
                    protected void onClick(Touchscreen.Touch touch) {
                    }
                };
                add(hotArea_imageSkill);

                final Image imageSkillB = new SkillSprite(106);
                add(imageSkillB);

                imageSkillB.x = imageSkill.x + imageSkill.width() + GAP;
                imageSkillB.y = pos + 1;

                TouchArea hotArea_imageSkillB = new TouchArea(imageSkillB) {
                    @Override
                    protected void onClick(Touchscreen.Touch touch) {
                    }
                };
                add(hotArea_imageSkillB);

                pos = image.y + image.height() + GAP * 3;
            }

            String TITLE_A = Messages.get(this, "hire");
            String TITLE_NAME = Messages.format(TITLE_A, getName(mode));

            if(mode != Mode.ARCHERMAIDEN || HiredMerc.archerMaidenUnlocked == true) {
                RedButton btnHire = new RedButton(TITLE_NAME) {
                    @Override
                    protected void onClick() {
                        if (Dungeon.gold < getGoldCost(mode)) {
                            text(TXT_NO_GOLD);
                        } else {

                            //Dungeon.hero.checkMerc = true;
                            ArrayList<Integer> respawnPoints = new ArrayList<Integer>();
                            for (int i=0; i < Level.NEIGHBOURS8.length; i++) {
                                int p = Dungeon.hero.pos + Level.NEIGHBOURS8[i];
                                if (Actor.findChar(p) == null && (Level.passable[p] || Level.avoid[p])) {
                                    respawnPoints.add( p );
                                }
                            }
                            if(respawnPoints.size() > 0) {
                                Dungeon.gold -= getGoldCost(mode);
                                Dungeon.hero.hiredMerc = new HiredMerc(getMercType(mode));
                                Dungeon.hero.hiredMerc.spawn(Dungeon.hero.lvl);
                                Dungeon.hero.hiredMerc.HP = Dungeon.hero.hiredMerc.mercType.getHealth(Dungeon.hero.lvl);
                                Dungeon.hero.hiredMerc.mercType.setEquipment(Dungeon.hero.hiredMerc);
                                Dungeon.hero.hiredMerc.pos = respawnPoints.get(0);
                                GameScene.add( Dungeon.hero.hiredMerc );
                                ((MercSprite) Dungeon.hero.hiredMerc.sprite).updateArmor();
                                WandOfBlink.appear(Dungeon.hero.hiredMerc, respawnPoints.get(0));

                                Dungeon.hero.spend( 1 / Dungeon.hero.speed() );
                            }
                            hide();
                            //WndStory.showStory("Your mercenary will arrive shortly");
                        }

                    }
                };

                btnHire.setRect((width - 120) / 2 > 0 ? (width - 120) / 2 : 0, pos,
                        120, 20);
                add(btnHire);

                pos = btnHire.bottom() + GAP;
            }
            else
            {
                    RedButton btnHire = new RedButton(Messages.get(this,"unlock")) {
                    @Override
                    protected void onClick() {
                        Image tmp = new  SkillSprite(104);
                        parent.add(new previewInformation(tmp, "Archer Maiden", HiredMerc.MAIDEN_UNLOCK_BY));
                        HiredMerc.archerMaidenUnlocked =true;
                        hide();
                        GLog.h("解锁成功");
                    }
                };

                btnHire.setRect((width - 120) / 2 > 0 ? (width - 120) / 2 : 0, pos,
                        120, 20);
                add(btnHire);

                pos = btnHire.bottom() + GAP;
            }

            if(maxHeight < pos)
                maxHeight = (int)pos;

            resize(width,  maxHeight);
        }

        MercenaryTab tabAll = new MercenaryTab(Mode.ALL);
        tabAll.setSize(TAB_WIDTH, tabHeight());
        add(tabAll);
        tabAll.select(mode == Mode.ALL);

        if(hero.heroClass != HeroClass.WARRIOR) {
            MercenaryTab tabBrute = new MercenaryTab(Mode.BRUTE);
            tabBrute.setSize(TAB_WIDTH, tabHeight());
            add(tabBrute);
            tabBrute.select(mode == Mode.BRUTE);
        }

        if(hero.heroClass != HeroClass.MAGE) {
        MercenaryTab tabWizard = new MercenaryTab(Mode.WIZARD);
        tabWizard.setSize(TAB_WIDTH, tabHeight());
        add(tabWizard);
        tabWizard.select(mode == Mode.WIZARD);
        }

        if(hero.heroClass != HeroClass.ROGUE) {
        MercenaryTab tabThief = new MercenaryTab(Mode.THIEF);
        tabThief.setSize(TAB_WIDTH, tabHeight());
        add(tabThief);
        tabThief.select(mode == Mode.THIEF);
        }

        if(hero.heroClass != HeroClass.HUNTRESS) {
        MercenaryTab tabArcher = new MercenaryTab(Mode.ARCHER);
        tabArcher.setSize(TAB_WIDTH, tabHeight());
        add(tabArcher);
        tabArcher.select(mode == Mode.ARCHER);
        }

        MercenaryTab tabMaiden = new MercenaryTab(Mode.ARCHERMAIDEN);
        tabMaiden.setSize(TAB_WIDTH, tabHeight());
        add(tabMaiden);
        tabMaiden.select(mode == Mode.ARCHERMAIDEN);

    }

    private void statSlot( String label, String value ) {

        RenderedText txt = PixelScene.renderText( label, 6 );
        txt.y = pos;
        add( txt );

        txt = PixelScene.renderText( value, 6 );
        txt.x = PixelScene.align( 30 );
        txt.y = pos;
        PixelScene.align(txt);
        add( txt );

        pos += GAP + txt.baseLine();
    }

    private void statSlot( String label, int value ) {
        statSlot( label, Integer.toString( value ) );
    }

    private String getMercDetails(Mode mode)
    {
        switch(mode)
        {
            case BRUTE: return Messages.get(this,"c1");
            case WIZARD: return Messages.get(this,"c2");
            case THIEF: return Messages.get(this,"c3");
            case ARCHER: return Messages.get(this,"c4");
            case ARCHERMAIDEN: return Messages.get(this,"c5");
        }
        return null;
    }

    private int getImage(Mode mode)
    {
        switch (mode)
        {
            case BRUTE: return 0;
            case WIZARD: return 24;
            case THIEF: return 48;
            case ARCHER: return 72;
            case ARCHERMAIDEN: return 104;
        }

        return 0;
    }

    private String getName(Mode mode)
    {
        switch (mode)
        {
            case BRUTE: return Messages.get(Negotiations.class,"2");
            case WIZARD: return Messages.get(Negotiations.class,"4");
            case THIEF: return Messages.get(Negotiations.class,"3");
            case ARCHER: return Messages.get(Negotiations.class,"5");
            case ARCHERMAIDEN: return Messages.get(Negotiations.class,"6");
        }

        return "Brute";
    }

    private HiredMerc.MERC_TYPES getMercType(Mode mode)
    {
        switch (mode)
        {
            case BRUTE: return Brute;
            case WIZARD: return Wizard;
            case THIEF: return Thief;
            case ARCHER: return Archer;
            case ARCHERMAIDEN: return ArcherMaiden;
        }

        return Brute;
    }

    private int getGoldCost(Mode mode)
    {
        switch (mode)
        {
            case BRUTE: return brute1 + hero.lvl * brute2;
            case WIZARD: return wizard1 + hero.lvl * wizard2;
            case THIEF: return thief1 + hero.lvl * thief2;
            case ARCHER: return archer1 + hero.lvl * wizard2;
            case ARCHERMAIDEN: return archer1 + hero.lvl * brute2;
        }

        return 0;
    }

    public int getHealth(int level,Mode mode)
    {
        switch (mode)
        {
            case BRUTE: return 20 + level * 3;
            case WIZARD: return 10 + level;
            case THIEF: return 15 + level * 2;
            case ARCHER: return 15 + level * 2;
            case ARCHERMAIDEN: return 17 + level * 2;
        }

        return 1;
    }

    public int getStrength(int level,Mode mode)
    {
        switch (mode)
        {
            case BRUTE: return 13 + (int) (level / 3);
            case WIZARD: return 10 + (int) (level / 5);
            case THIEF: return 13 + (int) (level / 4);
            case ARCHER: return 11 + (int) (level / 4);
            case ARCHERMAIDEN: return 12 + (int) (level / 4);
        }
        return 0;
    }

    @Override
    protected void onClick( Tab tab ) {

        parent.add(new WndMercs(((MercenaryTab)tab).mode));
        hide();
    }

    private class MercenaryTab extends Tab {

        private Image icon;

        Mode mode = Mode.BRUTE;

        public MercenaryTab(Mode mode  ) {
            super();

            this.mode = mode;

            switch (mode)
            {
                case ALL:  icon = Icons.get( Icons.ALL_MERCS ); break;
                case ARCHER:  icon = Icons.get( Icons.ARCHER ); break;
                case BRUTE: icon = Icons.get( Icons.BRUTE ); break;
                case WIZARD: icon = Icons.get( Icons.WIZARD ); break;
                case THIEF: icon = Icons.get( Icons.THIEF ); break;
                case ARCHERMAIDEN: icon = Icons.get( Icons.ARCHER_MAIDEN ); break;

            }

            add( icon );
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
                frame.top += (y - icon.y) / icon.texture.height;
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
            titlebar.label( Utils.capitalize( title ), TITLE_COLOR );
            titlebar.setRect( 0, 0, 100, 0 );
            add( titlebar );

            RenderedTextMultiline txtInfo = PixelScene.renderMultiline( description, 6 );
            txtInfo.maxWidth (100);
            txtInfo.setPos(titlebar.left(),titlebar.bottom() + GAP);
            add( txtInfo );

            resize( 100, (int) txtInfo.top() + (int) txtInfo.height() + (int)GAP);
        }
    }

}