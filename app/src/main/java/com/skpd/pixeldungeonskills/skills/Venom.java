package com.skpd.pixeldungeonskills.skills;

import com.skpd.utils.Random;

/**
 * Created by Moussa on 20-Jan-17.
 */
public class Venom extends PassiveSkillB1{


    {
        image = 57;
        tier = 1;
    }

    public boolean venomousAttack()
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
