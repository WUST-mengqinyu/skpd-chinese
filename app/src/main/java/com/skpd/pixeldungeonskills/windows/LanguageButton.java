package com.skpd.pixeldungeonskills.windows;

import com.skpd.noosa.Image;
import com.skpd.noosa.audio.Sample;
import com.skpd.noosa.ui.Button;
import com.skpd.pixeldungeonskills.Assets;

/**
 * Created by badcw on 2017/8/18.
 */

public class LanguageButton extends Button {

    private Image image;

    public LanguageButton() {
        super();

        width = image.width;
        height = image.height;
    }

    @Override
    protected void createChildren() {
        super.createChildren();

        image=new Image( Assets.AMULET );
        add( image );
    }


    @Override
    protected void layout() {
        super.layout();

        image.x = x;
        image.y = y;
    }

    @Override
    protected void onTouchDown() {
        image.brightness( 1.5f );
        Sample.INSTANCE.play( Assets.SND_CLICK );
    }

    @Override
    protected void onTouchUp() {
        image.resetColor();
    }

    @Override
    protected void onClick() {
        parent.add(new WndLangs());
    }


}
