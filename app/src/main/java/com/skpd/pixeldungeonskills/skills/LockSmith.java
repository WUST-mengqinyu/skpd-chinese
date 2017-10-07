package com.skpd.pixeldungeonskills.skills;

import com.skpd.pixeldungeonskills.messages.Messages;
import com.skpd.utils.Random;

/**
 * Created by Moussa on 20-Jan-17.
 */
public class LockSmith extends PassiveSkillA3{


    {
        tier = 3;
        image = 51;
    }

    @Override
    public boolean disableTrap()
    {
        if(Random.Int(100) < 33 * level)
        {
            castText = Messages.get(this,"2");
            castTextYell();
            return true;
        }
        //// FIXME: 2017/10/6
        String a = Messages.get(this,"3");
        castText = Messages.format(a,name);
        castTextYell();
        return false;
    }

    @Override
    protected boolean upgrade()
    {
        return true;
    }

}
