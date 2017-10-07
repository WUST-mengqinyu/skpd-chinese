package com.skpd.pixeldungeonskills.skills;


import com.skpd.pixeldungeonskills.messages.Messages;

/**
 * Created by Moussa on 20-Jan-17.
 */
public class BranchSkill extends Skill{

    public String a = Messages.get(this,"1");
    public String b;
    public String c = Messages.get(BranchSkill.class,"auto");

    @Override
    public float getAlpha()
    {
        return 1f;
    }

    public String newDesc() {

        if (canUpgrade()){
            b = Messages.get(BranchSkill.class, "2" );
            return a + Messages.format(c,totalSpent()) + Messages.format( b , nextUpgradeCost() );
        }else{
            b = Messages.get( BranchSkill.class, "3" );
            return a + Messages.format(c,totalSpent()) + b;
        }
    }

    @Override
    public String info() {
        return newDesc();
    }

}
