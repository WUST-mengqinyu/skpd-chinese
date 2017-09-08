package com.skpd.pixeldungeonskills;


import com.skpd.pixeldungeonskills.messages.Messages;
import com.skpd.pixeldungeonskills.ui.Window;
import com.skpd.pixeldungeonskills.windows.WndWelcome;

/**
 * Created by Moussa on 23-Jan-17.
 */
public class VersionNewsInfo {
    public static boolean useable=true;
    public static String message = Messages.get(VersionNewsInfo.class,"");

    public static Window getWelcomeWindow()
    {
        return (new WndWelcome(message));
    }
}
