package org.falexgl;

import org.falexgl.cli.menu.MainMenu;
import org.falexgl.utils.log.AppLogger;
import org.falexgl.utils.settings.SettingFileHelper;

public class Main {

    public static void main(String[] args) {
        AppLogger.info("App initiated");
        SettingFileHelper.checkOrCreateSettingFile();
        MainMenu menu = MainMenu.getInstance();
        menu.initMenu();
    }
}