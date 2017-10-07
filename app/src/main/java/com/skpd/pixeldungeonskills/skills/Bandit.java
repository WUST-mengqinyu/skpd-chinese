package com.skpd.pixeldungeonskills.skills;



/**
 * Created by Moussa on 20-Jan-17.
 */
public class Bandit extends PassiveSkillA1{


    {
        image = 49;
        tier = 1;
    }

    @Override
    public int lootBonus(int gold)
    {
        return (int) (gold * 0.1f * level);
    }

    @Override
    protected boolean upgrade()
    {
        return true;
    }

}
