package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;


public class FlappyBirdClone2 extends Game {
	SpriteBatch batch;
	Texture background;
	Texture main;
	//ShapeRenderer shapeRenderer;

	Texture instruction;
	Texture insBackground;

	Texture gameOver;

	Texture[] birds;
	int flapState = 0;
	float birdY = 0;
	float velocity = 0;
	Circle birdCircle;
	int score = 0;
	int scoringTube = 0;
	BitmapFont font,font2;

	FreeTypeFontGenerator fontGenerator,fontGenerator2;
	FreeTypeFontGenerator.FreeTypeFontParameter parameter,parameter2;

	String text;
	GlyphLayout layout;

	int gameState = 0;
	float gravity = 2;
	int initialCount = 0;

	Texture topTube;
	Texture bottomTube;
	float gap = 400;
	float maxTubeOffset;
	Random randomGenerator;
	float tubeVelocity = 4;
	int numberOfTubes = 4;
	float[] tubeX = new float[numberOfTubes];
	float[] tubeOffset = new float[numberOfTubes];
	float distanceBetweenTubes;
	Rectangle[] topTubeRectangles;
	Rectangle[] bottomTubeRectangles;

	Preferences preferences;
	int highScore;

	int instructionCount = 0;

	Music music;
	Music music2;
	int musicCount = 0;


	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("bg.png");
		gameOver = new Texture("gameover.png");
		main = new Texture("main.png");

		instruction = new Texture("ins.png");
		insBackground = new Texture("instructionbackground.png");

		//shapeRenderer = new ShapeRenderer();
		birdCircle = new Circle();

		fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("orbitron.ttf"));
		parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.borderColor = Color.BLACK;
		parameter.color = Color.WHITE;
		parameter.size = Gdx.graphics.getWidth()/13;
		parameter.borderWidth = 7.5f;
		font = fontGenerator.generateFont(parameter);

		fontGenerator2 = new FreeTypeFontGenerator(Gdx.files.internal("piedra.ttf"));
		parameter2 = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter2.borderColor = Color.BLACK;
		parameter2.color = Color.WHITE;
		parameter2.size = Gdx.graphics.getWidth()/10;
		parameter2.borderWidth = 10;
		font2 = fontGenerator2.generateFont(parameter2);


		birds = new Texture[2];
		birds[0] = new Texture("bird.png");
		birds[1] = new Texture("bird2.png");

		randomGenerator = new Random();
		topTubeRectangles = new Rectangle[numberOfTubes];
		bottomTubeRectangles = new Rectangle[numberOfTubes];

		topTube = new Texture("toptube.png");
		bottomTube = new Texture("bottomtube.png");

		maxTubeOffset = Gdx.graphics.getHeight() / 2 - gap / 2 - 550;
		distanceBetweenTubes = Gdx.graphics.getWidth() * 3 / 4;

		preferences  = Gdx.app.getPreferences("Game Preferences");
		highScore = preferences.getInteger("highscore",0);

		text = "HIGHSCORE: "+ highScore;
		layout = new GlyphLayout(font2,text);

		music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
		music.setLooping(true);
		music.setVolume(0.1f);
		music.play();

		music2 = Gdx.audio.newMusic(Gdx.files.internal("gameovermusic.mp3"));
		music2.setLooping(false);
		music2.setVolume(0.1f);

		startGame();
	}

	public void startGame() {

		birdY = Gdx.graphics.getHeight() / 2 - birds[0].getHeight() / 2;

		for (int i = 0; i < numberOfTubes; i++) {

			tubeOffset[i] = (randomGenerator.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - gap - 1100);
			tubeX[i] = Gdx.graphics.getWidth() / 2 - topTube.getWidth() / 2 + Gdx.graphics.getWidth() + i * distanceBetweenTubes;

			topTubeRectangles[i] = new Rectangle();
			bottomTubeRectangles[i] = new Rectangle();

		}

		score = 0;
		scoringTube = 0;
		velocity = 0;
	}

	@Override
	public void render () {

		batch.begin();

		if(gameState!=3) {
			batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		}

		if (gameState == 1) {

			if(initialCount == 0)
			{
				velocity = 0;
				birdY = Gdx.graphics.getHeight()/2;
				if(Gdx.input.justTouched())
				{
					initialCount++;
					velocity = -20;
					birdY -= velocity*1.5;
				}
			}
			else {

				if (tubeX[scoringTube] < Gdx.graphics.getWidth() / 2) {

				score++;

				if (scoringTube < numberOfTubes - 1) {

					scoringTube++;

				} else {

					scoringTube = 0;

				}

			}

				if (Gdx.input.justTouched()) {
					velocity = -20;
					birdY -= velocity;
				}

				for (int i = 0; i < numberOfTubes; i++) {

					if (tubeX[i] < -topTube.getWidth()) {

						tubeX[i] += numberOfTubes * distanceBetweenTubes;
						tubeOffset[i] = (randomGenerator.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - gap - 1100);

					} else {

						tubeX[i] = tubeX[i] - tubeVelocity;
					}

					batch.draw(topTube, tubeX[i], Gdx.graphics.getHeight() / 2 + gap / 2 + tubeOffset[i]);
					batch.draw(bottomTube, tubeX[i], Gdx.graphics.getHeight() / 2 - gap / 2 - bottomTube.getHeight() + tubeOffset[i]);

					topTubeRectangles[i] = new Rectangle(tubeX[i], Gdx.graphics.getHeight() / 2 + gap / 2 + tubeOffset[i], topTube.getWidth(), topTube.getHeight());
					bottomTubeRectangles[i] = new Rectangle(tubeX[i], Gdx.graphics.getHeight() / 2 - gap / 2 - bottomTube.getHeight() + tubeOffset[i], bottomTube.getWidth(), bottomTube.getHeight());
				}
			}

			if (birdY > 0 && birdY < Gdx.graphics.getHeight() - birds[0].getHeight()) {

				velocity = velocity + gravity;
				if(!Gdx.input.justTouched())
				{
					birdY -= velocity;
				}

			} else {

				gameState = 2;

			}

			if (flapState == 0) {
				flapState = 1;
			} else {
				flapState = 0;
			}

			birdCircle.set(Gdx.graphics.getWidth() / 2, birdY + birds[flapState].getHeight() / 2, birds[flapState].getWidth() / 2);

			for (int i = 0; i < numberOfTubes; i++) {

				//shapeRenderer.rect(tubeX[i], Gdx.graphics.getHeight() / 2 + gap / 2 + tubeOffset[i], topTube.getWidth(), topTube.getHeight());
				//shapeRenderer.rect(tubeX[i], Gdx.graphics.getHeight() / 2 - gap / 2 - bottomTube.getHeight() + tubeOffset[i], bottomTube.getWidth(), bottomTube.getHeight());

				if (Intersector.overlaps(birdCircle, topTubeRectangles[i]) || Intersector.overlaps(birdCircle, bottomTubeRectangles[i])) {
					gameState = 2;
				}

			}

			batch.draw(birds[flapState], Gdx.graphics.getWidth() / 2 - birds[flapState].getWidth() / 2, birdY);

			font.draw(batch, String.valueOf(score), 100, 200);

			if(score > highScore)
				highScore = score;
			text = "HIGHSCORE: "+ highScore;
			layout = new GlyphLayout(font2,text);
			font2.draw(batch, layout, (Gdx.graphics.getWidth() - layout.width)/2,Gdx.graphics.getHeight() * 4/5);

		} else if (gameState == 0) {

			if(musicCount == 0) {
				music.play();
				music2.stop();
				musicCount = 1;
			}
			batch.draw(main, (Gdx.graphics.getWidth() / 2) - ((main.getWidth() * 2) / 2), (Gdx.graphics.getHeight() / 2) - ((main.getHeight() * 2) / 2), main.getWidth() * 2, main.getHeight() * 2);


			text = "HIGHSCORE: "+ highScore;
			layout = new GlyphLayout(font2,text);
			font2.draw(batch, layout, (Gdx.graphics.getWidth() - layout.width)/2,Gdx.graphics.getHeight() * 4/5);

			if (Gdx.input.justTouched()) {

				if(instructionCount == 0)
				{
					gameState = 3;
					instructionCount = 1;
				}
				else
				{
					gameState = 1;
				}

			}

		} else if(gameState == 3) {

			batch.draw(insBackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			batch.draw(instruction, Gdx.graphics.getWidth()/2-(instruction.getWidth()*1.75f), Gdx.graphics.getHeight()/2 - (instruction.getHeight()*1.75f), instruction.getWidth()*3.5f, instruction.getHeight()*3.5f);

			if(Gdx.input.justTouched())
			{
				gameState = 1;
			}

		}
		else if (gameState == 2) {

			if(musicCount == 1) {
				music.stop();
				music2.play();
				musicCount = 0;
			}

			for(int i = 0; i < numberOfTubes; i++)
			{
				batch.draw(topTube, tubeX[i], (Gdx.graphics.getHeight() / 2) + (gap / 2) + tubeOffset[i]);
				batch.draw(bottomTube, tubeX[i], (Gdx.graphics.getHeight() / 2) - (gap / 2) - bottomTube.getHeight() + tubeOffset[i]);
			}

			batch.draw(birds[0], (Gdx.graphics.getWidth() / 2) - (birds[0].getWidth() / 2), birdY);

			font.draw(batch, String.valueOf(score), 100, 200);

			batch.draw(gameOver, Gdx.graphics.getWidth() / 2 - gameOver.getWidth() / 2, Gdx.graphics.getHeight() / 2 - gameOver.getHeight() / 2,gameOver.getWidth(),gameOver.getHeight());

			if(score >= highScore) {
				preferences.clear();
				preferences.putInteger("highscore", score);
				preferences.flush();
				highScore = score;
			}

			text = "HIGHSCORE: "+ highScore;
			layout = new GlyphLayout(font2,text);
			font2.draw(batch, layout, (Gdx.graphics.getWidth() - layout.width)/2,Gdx.graphics.getHeight() * 4/5);


			if (Gdx.input.justTouched()) {

				gameState = 0;
				initialCount = 0;
				startGame();

			}

		}

		//shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		//shapeRenderer.setColor(Color.RED);
		//shapeRenderer.circle(birdCircle.x, birdCircle.y, birdCircle.radius);

		batch.end();

		//shapeRenderer.end();

	}

	@Override
	public void dispose()
	{
		batch.dispose();
		background.dispose();
		main.dispose();
		gameOver.dispose();
		topTube.dispose();
		bottomTube.dispose();
		birds[0].dispose();
		birds[1].dispose();
		music.dispose();
	}


}