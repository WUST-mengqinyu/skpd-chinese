package com.skpd.pixeldungeonskills.skills;

/**
 * Created by Moussa on 20-Jan-17.
 */
public class Sorcerer extends PassiveSkillB2{


    {
        image = 34;
        tier = 2;
    }

    @Override
    protected boolean upgrade()
    {
        return true;
    }


    @Override
    public float wandDamageBonus()
    {
        return 1f + 0.1f * level;
    }

}
