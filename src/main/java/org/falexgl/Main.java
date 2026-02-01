package org.falexgl;

import org.falexgl.bot.FetchIPBot;
import org.falexgl.helpers.menu.MainMenu;
import org.falexgl.models.Credentials;
import org.falexgl.helpers.ip.IPHandler;
import org.falexgl.helpers.settings.SettingFileHelper;

public class Main {

    public static void main(String[] args) {
        SettingFileHelper.checkOrCreateSettingFile();
        MainMenu menu = new MainMenu();
        menu.initMenu();
    }
}