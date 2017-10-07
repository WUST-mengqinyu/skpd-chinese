package com.skpd.pixeldungeonskills.skills;

import com.skpd.pixeldungeonskills.Dungeon;

/**
 * Created by Moussa on 20-Jan-17.
 */
public class Spirituality extends PassiveSkillA1 {


    {
        image = 25;
        tier = 1;
    }

    @Override
    protected boolean upgrade() {
        Dungeon.hero.MP += 5;
        Dungeon.hero.MMP += 5;
        return true;
    }

}
