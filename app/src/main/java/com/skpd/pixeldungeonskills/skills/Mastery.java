package com.skpd.pixeldungeonskills.skills;


/**
 * Created by Moussa on 20-Jan-17.
 */
public class Mastery extends PassiveSkillB3{


    {
        tier = 3;
        image = 11;
    }

    @Override
    protected boolean upgrade()
    {
        return true;
    }

    @Override
    public int  weaponLevelBonus()
    {
        return level;
    }

}
