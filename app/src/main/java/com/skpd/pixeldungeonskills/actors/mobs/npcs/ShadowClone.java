package com.skpd.pixeldungeonskills.actors.mobs.npcs;

import com.skpd.pixeldungeonskills.actors.Char;
import com.skpd.pixeldungeonskills.messages.Messages;

/**
 * Created by Moussa on 22-Jan-17.
 */
public class ShadowClone extends MirrorImage {
    {
        name = Messages.get(this,"1");
    }

    int lifeSpan = 3;

    @Override
    public int attackProc( Char enemy, int damage ) {
        int dmg = super.attackProc( enemy, damage );
        lifeSpan--;
        if(lifeSpan < 1) {
            destroy();
            sprite.die();
        }
        return dmg;
    }

    @Override
    public String description() {
        return Messages.get(this,"2");
    }

}
