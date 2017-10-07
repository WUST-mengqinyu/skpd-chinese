package com.skpd.pixeldungeonskills.skills;


import com.skpd.pixeldungeonskills.Dungeon;
import com.skpd.pixeldungeonskills.actors.hero.Hero;
import com.skpd.pixeldungeonskills.actors.hero.Legend;
import com.skpd.pixeldungeonskills.items.wands.WandOfMagicCasting;

import java.util.ArrayList;

/**
 * Created by Moussa on 20-Jan-17.
 */
public class SoulSpark extends ActiveSkill3{


    {
        tier = 3;
        image = 122;
        mana = 3;
    }


    @Override
    public float getAlpha()
    {
        return 1f;
    }

    @Override
    public ArrayList<String> actions( Hero hero ) {
        ArrayList<String> actions = new ArrayList<String>();
        if(level > 0 && hero.MP >= getManaCost())
            actions.add(AC_CAST);
        return actions;
    }

    @Override
    public void execute( Hero hero, String action ) {
        if(action == Skill.AC_CAST && hero.MP >= getManaCost())
        {
            //hero.MP -= getManaCost();
            //castTextYell();
            Legend.haxWand.castSpell(WandOfMagicCasting.CAST_TYPES.SOUL_SPARK);
            Dungeon.hero.heroSkills.lastUsed = this;
        }
    }

    @Override
    public int getManaCost()
    {
        return (int)Math.ceil(mana * (1 + 0.25 * level));
    }

    @Override
    protected boolean upgrade()
    {
        return true;
    }

}
