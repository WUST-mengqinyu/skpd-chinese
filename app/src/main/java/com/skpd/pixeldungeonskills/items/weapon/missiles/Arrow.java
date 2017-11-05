/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package com.skpd.pixeldungeonskills.items.weapon.missiles;

import com.skpd.noosa.audio.Sample;
import com.skpd.pixeldungeonskills.Assets;
import com.skpd.pixeldungeonskills.Dungeon;
import com.skpd.pixeldungeonskills.actors.Actor;
import com.skpd.pixeldungeonskills.actors.Char;
import com.skpd.pixeldungeonskills.actors.buffs.Buff;
import com.skpd.pixeldungeonskills.actors.buffs.Paralysis;
import com.skpd.pixeldungeonskills.actors.hero.Hero;
import com.skpd.pixeldungeonskills.effects.CellEmitter;
import com.skpd.pixeldungeonskills.effects.particles.BlastParticle;
import com.skpd.pixeldungeonskills.effects.particles.SmokeParticle;
import com.skpd.pixeldungeonskills.items.Item;
import com.skpd.pixeldungeonskills.levels.Level;
import com.skpd.pixeldungeonskills.levels.Terrain;
import com.skpd.pixeldungeonskills.mechanics.Ballistica;
import com.skpd.pixeldungeonskills.scenes.GameScene;
import com.skpd.pixeldungeonskills.sprites.ItemSpriteSheet;
import com.skpd.pixeldungeonskills.utils.GLog;
import com.skpd.utils.Random;

import java.util.ArrayList;

public class Arrow extends MissileWeapon {


	{
		image = ItemSpriteSheet.Arrow;

        stackable = true;
	}

	public Arrow() {
		this( 1 );
	}

	public Arrow(int number) {
		super();
		quantity = number;
	}

    @Override
    public void cast( final Hero user, int dst ) {

        if(Dungeon.hero.heroSkills.passiveB3.passThroughTargets(false) > 0)
        {
            castSPD(user, dst, Dungeon.hero.heroSkills.passiveB3.passThroughTargets(true));
        }
        else
            super.cast(user, dst);
    }

    @Override
    protected void onThrow( int cell ) {


        if(Dungeon.hero.heroSkills.passiveB3.multiTargetActive == false || Dungeon.hero.heroSkills.active3.active == true) { //  bombvoyage
            // Turn to bomb
            if(Dungeon.hero.heroSkills.active3.arrowToBomb() == true) {
            if (Level.pit[cell]) {
                super.onThrow(cell);
            } else {
                Sample.INSTANCE.play(Assets.SND_BLAST, 2);

                if (Dungeon.visible[cell]) {
                    CellEmitter.center(cell).burst(BlastParticle.FACTORY, 30);
                }

                boolean terrainAffected = false;
                for (int n : Level.NEIGHBOURS9) {
                    int c = cell + n;
                    if (c >= 0 && c < Level.LENGTH) {
                        if (Dungeon.visible[c]) {
                            CellEmitter.get(c).burst(SmokeParticle.FACTORY, 4);
                        }

                        if (Level.flamable[c]) {
                            Level.set(c, Terrain.EMBERS);
                            GameScene.updateMap(c);
                            terrainAffected = true;
                        }

                        Char ch = Actor.findChar(c);
                        if (ch != null) {
                            int dmg = Random.Int(1 + Dungeon.depth, 10 + Dungeon.depth * 2) - Random.Int(ch.dr());
                            if (dmg > 0) {
                                ch.damage(dmg, this);
                                if (ch.isAlive()) {
                                    Buff.prolong(ch, Paralysis.class, 2);
                                }
                            }
                        }
                    }
                }

                if (terrainAffected) {
                    Dungeon.observe();
                }
            }
        }
            // End turn to bomb
         else   super.onThrow(cell);
            return;
        }

        Ballistica.distance = Math.min( Ballistica.distance, Level.distance(Dungeon.hero.pos, cell) );

        ArrayList<Char> chars = new ArrayList<Char>();

        for (int i=1; i < Ballistica.distance + 1; i++) {

            int c = Ballistica.trace[i];

            Char ch;
            if ((ch = Actor.findChar(c)) != null) {
                chars.add( ch );
            }
        }

        GLog.i(chars.size() + " targets");
        boolean hitOne = false;
        for (Char ch : chars) {
            if (!curUser.shootThrough(ch, this)) {

            }
            else
                hitOne = true;
        }

        if(hitOne == false)
            miss( cell );

        Dungeon.hero.rangedWeapon = null;
    }

    public void arrowEffect(Char attacker, Char defender)
    {

    }
	
	@Override
	public Item random() {
        quantity = Random.Int(5, 20);
		return this;
	}
	
	@Override
	public int price() {
		return quantity * 5;
	}

    @Override
    public ArrayList<String> actions( Hero hero ) {
        ArrayList<String> actions = super.actions( hero );
        if(Dungeon.hero.belongings.bow != null) {
            if(actions.contains(AC_THROW) == false)
            actions.add(AC_THROW);
        }
        else
            actions.remove( AC_THROW );
        actions.remove(AC_EQUIP);

        return actions;
    }

    @Override
    public int min(){return 3;}


    @Override
    public int max(){return 5;}

}
