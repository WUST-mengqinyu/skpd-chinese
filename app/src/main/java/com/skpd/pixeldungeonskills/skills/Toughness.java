package com.skpd.pixeldungeonskills.skills;


/**
 * Created by Moussa on 20-Jan-17.
 */
public class Toughness extends PassiveSkillA3{


    {
        tier = 3;
        image = 3;
    }

    @Override
    protected boolean upgrade()
    {
        return true;
    }

    @Override
    public float incomingDamageModifier()
    {
        return 1f - level * 0.1f;
    }

}
