package com.abramon.gdxlibtest.desktop;

import com.abramon.gdxlibtest.Main;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
    public LwjglApplication la;

public static void main(String[] arg) {
    LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
    config.title = "Main menu";
    config.useGL30 = true;
    config.height = 1000;
    config.width = 1200;
    config.backgroundFPS = 60;
    //config.stencil = 8;
    //config.
    //config.fullscreen = true;
    new LwjglApplication(new Main(), config);
}
}



