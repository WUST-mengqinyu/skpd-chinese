package com.skpd.pixeldungeonskills.windows;

import com.skpd.noosa.audio.Sample;
import com.skpd.noosa.ui.Button;
import com.skpd.pixeldungeonskills.Assets;
import com.skpd.pixeldungeonskills.messages.Messages;
import com.skpd.pixeldungeonskills.ui.Icons;
import com.skpd.noosa.Image;

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

        image = Icons.get(Icons.EXIT);
        add( image );
        updateIcon();
    }

    private void updateIcon(){
        switch(Messages.lang().status()){
            case INCOMPLETE:
                image.tint(1, 0, 0, .5f);
                break;
            case UNREVIEWED:
                image.tint(1, .5f, 0, .5f);
                break;
        }
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
        updateIcon();
    }

    @Override
    protected void onClick() {
        parent.add(new WndLangs());
    }


}
