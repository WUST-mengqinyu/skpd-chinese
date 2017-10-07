package com.skpd.pixeldungeonskills.skills;

import com.skpd.utils.Random;

/**
 * Created by Moussa on 20-Jan-17.
 */
public class KneeShot extends PassiveSkillB2{


    {
        image = 82;
        tier = 2;
    }

    @Override
    public boolean cripple()
    {
        if(Random.Int(100) < 10 * level)
        {
            castTextYell();
            return true;
        }
        return false;
    }

    @Override
    protected boolean upgrade()
    {
        return true;
    }

}
