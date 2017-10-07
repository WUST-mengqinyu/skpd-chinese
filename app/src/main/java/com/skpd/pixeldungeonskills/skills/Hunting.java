package com.skpd.pixeldungeonskills.skills;

/**
 * Created by Moussa on 20-Jan-17.
 */
public class Hunting extends PassiveSkillA2{


    {
        image = 74;
        tier = 2;
    }

    @Override
    public int hunting() {return level;}

    @Override
    protected boolean upgrade()
    {
        return true;
    }

}
