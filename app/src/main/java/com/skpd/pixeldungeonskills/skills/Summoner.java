package com.skpd.pixeldungeonskills.skills;


/**
 * Created by Moussa on 20-Jan-17.
 */
public class Summoner extends PassiveSkillB3{


    {
        tier = 3;
        image = 35;
    }

    @Override
    public int summoningLimitBonus()
    {
        return level;
    }

    @Override
    protected boolean upgrade()
    {
        return true;
    }

}
