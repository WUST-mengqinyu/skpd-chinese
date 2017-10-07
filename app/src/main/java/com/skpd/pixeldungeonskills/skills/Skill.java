package com.skpd.pixeldungeonskills.skills;


import com.skpd.pixeldungeonskills.Dungeon;
import com.skpd.pixeldungeonskills.actors.hero.Hero;
import com.skpd.pixeldungeonskills.messages.Languages;
import com.skpd.pixeldungeonskills.messages.Messages;
import com.skpd.pixeldungeonskills.sprites.CharSprite;
import com.skpd.pixeldungeonskills.windows.WndStory;
import com.skpd.utils.Bundle;

import java.util.ArrayList;

import static com.skpd.pixeldungeonskills.messages.Messages.get;

/**
 * Created by Moussa on 20-Jan-17.
 */
public class Skill{
    {
        name = get(this, "");
        castText = Messages.get(this,"ct");
    }

    public static final String AC_ADVANCE = get(Skill.class,"1");
    public static final String AC_ACTIVATE = get(Skill.class,"2");
    public static final String AC_DEACTIVATE = get(Skill.class,"3");

    public static final String AC_SUMMON = get(Skill.class,"4");
    public static final String AC_CAST = get(Skill.class,"5");

    public static final String FAIL_ADVANCE = get(Skill.class,"6");
//Override
    public static final String SKILL_LEVEL = "LEVEL";

    public String tag = "";

    public static final int MAX_LEVEL = 3;

    public static final int STARTING_SKILL = 2;

    public static int availableSkill = STARTING_SKILL;

    public static final float TIME_TO_USE = 1f;

    private String a;
    public String name;
    public String castText = "";
    public int level = 0;
    public int tier = 1;
    public int mana = 0;
    public int image = 0;

    public boolean active = false;

    public boolean multiTargetActive = false;

    public boolean requestUpgrade()
    {
        if(availableSkill >= tier && level < MAX_LEVEL)
        {
            if(upgrade())
            {
                level++;
                availableSkill -= tier;
               // WndStory.showStory("You have gained a level in " + name);
                return true;
            }
        }
        else
        {
            WndStory.showStory(FAIL_ADVANCE);
        }

        return false;
    }

    protected boolean upgrade()
    {
        return false;
    }

    public void setLevel(int level)
    {
        this.level = level;
    }

    public float incomingDamageModifier()
    {
        return 1f;
    }

    public float damageModifier()
    {
        return 1f;
    }

    public int damageBonus(int hp)
    {
        return damageBonus(hp, false);
    }

    public int damageBonus(int hp, boolean castText)
    {
        return 0;
    }

    public int toHitBonus()
    {
        return 0;
    }

    public int healthRegenerationBonus()
    {
        return 0;
    }

    public int weaponLevelBonus()
    {
        return 0;
    }

    public int fletching() {return 0;}

    public int hunting() {return 0;}

    public boolean knocksBack()
    {
        return false;
    }

    public boolean AoEDamage()
    {
        return false;
    }

    public int manaRegenerationBonus() {return 0;}

    public int incomingDamageReduction(int damage) {return 0;}

    public int image() {
        return image;
    }

    public String info() {
        if (Messages.lang()== Languages.ENGLISH) {
            a = name + " is at level " + level + ".\n"
                    + (level < Skill.MAX_LEVEL ? "It costs " + upgradeCost() + " skill points to advance in " + name + "." : name + " is maxed out.")
                    + (level > 0 && mana > 0 ? "\nUsing " + name + " costs " + getManaCost() + " mana.\n" : "\n");
        }else {
            a = name + "技能现在" + level +"级。\n"
                    + (level<MAX_LEVEL ? "它需要" + upgradeCost() + "个技能点来升级。\n": name + "已经升至满级。\n")
                    + (level>0 &&mana>0 ? "使用" + name + "将会花费"+ getManaCost() +"点法力值\n": "\n" );
        }
        return get(this,"1") + a;
    }

    public ArrayList<String> actions( Hero hero ) {
        ArrayList<String> actions = new ArrayList<String>();
        return actions;
    }

    public void execute( Hero hero, String action ) {

    }

    public float getAlpha()
    {
        return 0.1f + level * 0.3f;
    }

    public int upgradeCost() {return tier;}

    protected int totalSpent()
    {
        return 0;
    }

    protected int nextUpgradeCost()
    {
        return 0;
    }

    protected boolean canUpgrade()
    {
        return false;
    }

    public int getManaCost()
    {
        return mana;
    }

    public void castTextYell()
    {
        if(castText != "")
            Dungeon.hero.sprite.showStatus(CharSprite.NEUTRAL, castText);
    }

    public float wandRechargeSpeedReduction()
    {
        return 1f;
    }

    public int summoningLimitBonus() {return 0;}


    public float wandDamageBonus()
    {
        return 1f;
    }

    public int lootBonus(int gold) { return 0;}

    public int stealthBonus(){return 0;}

    public boolean disableTrap() {return false;}

    public boolean venomousAttack() {return false;}

    public int venomBonus() {return 0;}

    public boolean instantKill() {return false;}

    public boolean dodgeChance(){return false;}

    public float toHitModifier(){return 1f;}

    public boolean cripple() {return false;}

    public int passThroughTargets(boolean shout)
    {
        return 0;
    }

    public boolean doubleShot()
    {
        return false;
    }

    public boolean doubleStab()
    {
        return false;
    }

    public boolean arrowToBomb()
    {
        return false;
    }

    public void mercSummon() {}

    public boolean goToSleep()
    {
        return false;
    }

    public void storeInBundle(Bundle bundle)
    {
        bundle.put( SKILL_LEVEL + " " + tag, level );
    }

    public void restoreInBundle(Bundle bundle)
    {
        level = bundle.getInt( SKILL_LEVEL + " " + tag);
    }
}
