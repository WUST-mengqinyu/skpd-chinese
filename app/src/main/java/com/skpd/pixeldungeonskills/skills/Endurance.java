package com.skpd.pixeldungeonskills.skills;

import com.skpd.pixeldungeonskills.Dungeon;

/**
 * Created by Moussa on 20-Jan-17.
 */
public class Endurance extends PassiveSkillA1{


    {
        image = 1;
    }

    @Override
    protected boolean upgrade()
    {
        Dungeon.hero.HT += 5;
        Dungeon.hero.HP += 5;
        return true;
    }
}
