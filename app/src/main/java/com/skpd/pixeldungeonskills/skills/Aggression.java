package com.skpd.pixeldungeonskills.skills;

/**
 * Created by Moussa on 20-Jan-17.
 */
public class Aggression extends PassiveSkillB2{


    {
        image = 9;
        tier = 2;
    }

    @Override
    protected boolean upgrade()
    {
        return true;
    }

    @Override
    public float damageModifier()
    {
        return 1f + 0.1f * level;
    }

}
