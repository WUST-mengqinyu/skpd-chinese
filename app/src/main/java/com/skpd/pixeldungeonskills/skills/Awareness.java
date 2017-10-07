package com.skpd.pixeldungeonskills.skills;

import com.skpd.pixeldungeonskills.messages.Messages;
import com.skpd.utils.Random;

/**
 * Created by Moussa on 20-Jan-17.
 */
public class Awareness extends PassiveSkillA3{


    {
        image = 75;
        tier = 3;
    }

    @Override
    public boolean dodgeChance()
    {
        if(Random.Int(100) < 10 * level)
        {
            castText = Messages.get(this,"2");
            castTextYell();
            return true;
        }
        castText = Messages.get(this,"3");
        castTextYell();
        return false;
    }

    @Override
    protected boolean upgrade()
    {
        return true;
    }

}
