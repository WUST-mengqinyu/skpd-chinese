package com.skpd.pixeldungeonskills.actors.mobs;

import com.skpd.pixeldungeonskills.actors.Char;
import com.skpd.pixeldungeonskills.sprites.CursePersonificationSprite;
import com.skpd.utils.Random;

/**
 * Created by Moussa on 29-Jan-17.
 */
public class EnslavedSouls extends Mob {

    {
        name = "enslaved spirit";
        spriteClass = CursePersonificationSprite.class;

        HP = HT = 1;
        defenseSkill = 1;

        EXP = 0;

        state = HUNTING;
    }

    @Override
    public int attackSkill( Char target ) {
        return 5;
    }

    @Override
    public int damageRoll() {
        return Random.NormalIntRange(10, 20);
    }

    @Override
    public int dr() {
        return 8;
    }

    @Override
    public void die( Object cause ) {
        super.die(cause);
    }
    @Override
    public String description() {
        return "wha..";

    }
}

