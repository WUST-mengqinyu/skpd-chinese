package com.skpd.pixeldungeonskills.skills;

/**
 * Created by Moussa on 20-Jan-17.
 */
public class FirmHand extends PassiveSkillB1{


    {
        image = 10;
        tier = 1;
    }

    @Override
    protected boolean upgrade()
    {
        return true;
    }


    @Override
    public int toHitBonus()
    {
        return  level * 2;
    }

}
