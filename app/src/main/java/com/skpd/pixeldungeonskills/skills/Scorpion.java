package com.skpd.pixeldungeonskills.skills;

/**
 * Created by Moussa on 20-Jan-17.
 */
public class Scorpion extends PassiveSkillB2{


    {
        image = 58;
        tier = 2;
    }

    @Override
    public int venomBonus() {return level * 2;}

    @Override
    protected boolean upgrade()
    {
        return true;
    }

}
