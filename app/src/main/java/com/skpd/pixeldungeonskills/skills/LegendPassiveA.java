package com.skpd.pixeldungeonskills.skills;

import com.skpd.pixeldungeonskills.messages.Messages;

/**
 * Created by Moussa on 20-Jan-17.
 */
public class LegendPassiveA extends BranchSkill{



    {
        image = 112;
        level = 0;
    }

    @Override
    public String info()
    {
        return Messages.get(this,"1");
    }
}
