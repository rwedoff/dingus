package com.codeday.dingus;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

public class DingusDesktop
{
	public static void main(String[] args)
	{
		Utilities.packTextures();
		new LwjglApplication(new Dingus(), "Dingus", 1280, 720, true);
	}
}
