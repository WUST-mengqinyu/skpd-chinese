package com.skpd.pixeldungeonskills.skills;

import com.skpd.pixeldungeonskills.messages.Messages;

/**
 * Created by Moussa on 20-Jan-17.
 */
public class LegendActiveA extends BranchSkill{



    {
        image = 120;
        level = 0;
    }

    @Override
    public String info()
    {
        return Messages.get(this,"1");
    }
}
