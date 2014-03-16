package com.codeday.dingus;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class ScoreScreen extends AbstractScreen
{	
	
	public ScoreScreen(Dingus game)
	{
		super(game);		
	}

	@Override
	public void show()
	{
		super.show();

		// retrieve the default table actor
		Table table = super.getTable();
		

		String textureFile = "images/pages-info.atlas";
		TextureAtlas atlas = new TextureAtlas(Gdx.files.internal(textureFile),
				Gdx.files.internal("images"));
		int tileWidth = atlas.findRegion("starBackground").getRegionWidth();
		int tileHeight = atlas.findRegion("starBackground").getRegionHeight();
		Image background;
		
		for (int x = 0; x * tileWidth < game.getWidth(); x++)
		{
			for (int y = 0; y * tileHeight < game.getHeight(); y++)
			{

				background = new Image(atlas.findRegion(
						"starBackground"));
				background.setPosition(x * tileWidth, y * tileHeight);
				table.addActor(background);
			}
		}
		
		// register the button "start game"
		Label highscoresText = new Label("High Scores:", getSkin());
		
		table.add(highscoresText).size(300, 60).uniform().spaceBottom(10);
		table.row();
		ArrayList<Integer> scores = game.getActionResolver().getScoreList();
		
		for(int i = 0; i < scores.size(); i++) {
			table.add(new Label((i + 1) + ". - " + scores.get(i), getSkin()))
				.size(300, 60).uniform().spaceBottom(10);
			table.row();
		}
		
		TextButton backToMenuButton = new TextButton("Back", getSkin());
		backToMenuButton.addListener(new ClickListener()
		{
			public void clicked(InputEvent event, float x, float y) 
			{
				game.setScreen(new MenuScreen(game));
		    }
		});
		table.add(backToMenuButton).size(300, 60).uniform().spaceBottom(10);
		table.row();
	}
	
	@Override
	public void pause()
	{
		
	}
}