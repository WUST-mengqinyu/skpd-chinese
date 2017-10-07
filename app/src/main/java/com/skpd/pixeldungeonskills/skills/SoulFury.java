package com.skpd.pixeldungeonskills.skills;


import com.skpd.pixeldungeonskills.actors.hero.Hero;
import com.skpd.pixeldungeonskills.messages.Messages;

import java.util.ArrayList;

/**
 * Created by Moussa on 20-Jan-17.
 */
public class SoulFury extends ActiveSkill3{


    {
        tier = 3;
        image = 123;
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

        return actions;
    }

    @Override
    public void execute( Hero hero, String action ) {
        if(action == Skill.AC_CAST && hero.MP >= getManaCost())
        {

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


    @Override
    public String info()
    {
        return Messages.get(this,"1");
    }

}
