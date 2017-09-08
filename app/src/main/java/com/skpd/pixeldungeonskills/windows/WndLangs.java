package com.skpd.pixeldungeonskills.windows;

import com.skpd.noosa.ColorBlock;
import com.skpd.noosa.Game;
import com.skpd.noosa.RenderedText;
import com.skpd.pixeldungeonskills.PixelDungeon;
import com.skpd.pixeldungeonskills.messages.Languages;
import com.skpd.pixeldungeonskills.messages.Messages;
import com.skpd.pixeldungeonskills.scenes.PixelScene;
import com.skpd.pixeldungeonskills.scenes.TitleScene;
import com.skpd.pixeldungeonskills.ui.NewRedButton;
import com.skpd.pixeldungeonskills.ui.RenderedTextMultiline;
import com.skpd.pixeldungeonskills.ui.Window;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by badcw on 2017/8/18.
 */

public class WndLangs extends Window {
    private int WIDTH_P = 120;
    private int WIDTH_L = 171;

    private int MIN_HEIGHT = 35;

    private int BTN_WIDTH = 50;
    private int BTN_HEIGHT = 14;

    public WndLangs(){
        super();

        final ArrayList<Languages> langs = new ArrayList<>(Arrays.asList(Languages.values()));

        Languages nativeLang = Languages.CHINESE;
        langs.remove(nativeLang);
        //move the native language to the top.
        langs.add(0, nativeLang);

        final Languages currLang = Messages.lang();

        //language buttons layout
        int y = 0;
        for (int i = 0; i < langs.size(); i++){
            final int langIndex = i;
            NewRedButton btn = new NewRedButton(Messages.titleCase(langs.get(i).nativeName())){
                @Override
                protected void onClick() {
                    super.onClick();
                    Messages.setup(langs.get(langIndex));
                    PixelDungeon.switchNoFade(TitleScene.class, new Game.SceneChangeCallback() {
                        @Override
                        public void beforeCreate() {
                            PixelDungeon.language(langs.get(langIndex));
                            RenderedText.clearCache();
                        }
                        @Override
                        public void afterCreate() {
                            Game.scene().add(new WndLangs());
                        }
                    });
                }
            };

            btn.textColor(TITLE_COLOR);
            btn.setSize(BTN_WIDTH, BTN_HEIGHT);
            if (PixelDungeon.landscape() && i % 2 == 1){
                btn.setPos(BTN_WIDTH+1, y-15);
            } else {
                btn.setPos(0, y);
                y += 15;
            }

            add(btn);
        }
        y = Math.max(MIN_HEIGHT, y+1);
        resize(PixelDungeon.landscape() ? WIDTH_L : WIDTH_P, y);

        int textLeft = width - 65;
        int textWidth = width - textLeft;

        ColorBlock separator = new ColorBlock(1, y, 0xFF000000);
        separator.x = textLeft - 2.5f;
        add(separator);

        //language info layout.
        RenderedText title = PixelScene.renderText( Messages.titleCase(currLang.nativeName()) , 9 );
        title.x = textLeft + (textWidth - title.width())/2f;
        title.y = 0;
        title.hardlight(TITLE_COLOR);
        PixelScene.align(title);
        add(title);

        if (currLang == Languages.ENGLISH){

            RenderedTextMultiline info = PixelScene.renderMultiline(6);
            info.text("This is the source language, written by the developer.", width - textLeft);
            info.setPos(textLeft, title.height() + 2);
            add(info);

        } else {


            NewRedButton creditsBtn = new NewRedButton(Messages.titleCase(Messages.get(this, "credits"))){
                @Override
                protected void onClick() {
                    super.onClick();
                    String creds = "";
                    String[] translators = currLang.translators();


                    if (translators.length > 0){
                        creds += "_" + Messages.titleCase(Messages.get(WndLangs.class, "translators")) + "_";
                        //reviewers are also translators
                        for (String translator : translators){
                            creds += "\n-" + translator;
                        }
                    }

                    Window credits = new Window();

                    RenderedTextMultiline title = PixelScene.renderMultiline(9);
                    title.text(Messages.titleCase(Messages.get(WndLangs.class, "credits")) , 65);
                    title.hardlight(SHPX_COLOR);
                    title.setPos((65 - title.width())/2, 0);
                    credits.add(title);

                    RenderedTextMultiline text = PixelScene.renderMultiline(6);
                    text.text(creds, 80);
                    text.setPos(0, title.bottom() + 2);
                    credits.add(text);

                    credits.resize(80, (int)text.bottom());
                    parent.add(credits);
                }
            };
            creditsBtn.setSize(creditsBtn.reqWidth() + 2, 16);
            creditsBtn.setPos(textLeft + (textWidth - creditsBtn.width()) / 2f, y - 18);
            add(creditsBtn);

        }

    }

}
