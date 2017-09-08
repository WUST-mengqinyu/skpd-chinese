package com.skpd.pixeldungeonskills.skills;


/**
 * Created by Moussa on 20-Jan-17.
 */
public class Regeneration extends PassiveSkillA2{


    {
        name = "Regeneration";
        tier = 2;
        image = 2;
    }


    @Override
    public int healthRegenerationBonus()
    {
        return level;
    }

    @Override
    protected boolean upgrade()
    {
        return true;
    }

    @Override
    public String info()
    {
        return "Regenerate health faster.\n"
                + costUpgradeInfo();
    }

}
