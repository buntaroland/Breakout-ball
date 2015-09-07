package valami.gonzales.example.com.breakoutball12.game;

import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.widget.Toast;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.entity.util.FPSLogger;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.Texture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.HorizontalAlign;

import java.util.Observable;
import java.util.Observer;
import java.util.Timer;

import valami.gonzales.example.com.breakoutball12.GameHolder;
import valami.gonzales.example.com.breakoutball12.MainActivity;
import valami.gonzales.example.com.breakoutball12.model.Ball;
import valami.gonzales.example.com.breakoutball12.model.Brick;
import valami.gonzales.example.com.breakoutball12.model.BrickMap;
import valami.gonzales.example.com.breakoutball12.model.Paddle;
import valami.gonzales.example.com.breakoutball12.parse.ParseListActivity;

/**
 * Created by Gonzales on 2015.05.17..
 */
public class Game extends SimpleBaseGameActivity implements IOnSceneTouchListener, Observer {
    private static int CAMERA_HEIGHT = 480;
    private static int CAMERA_WIDTH = 800;
    private Camera mCamera;

    private TiledTextureRegion ballTextureRegion;
    private TextureRegion bgTextureRegion;

    private Font mFont;
    private int lives = 5;
    private int time = 0;
    public String selectedMap;
    public String playerName;

    private BitmapTextureAtlas paddleTexture;
    private BitmapTextureAtlas ballTexture;
    private BitmapTextureAtlas backgroundTexture;
    //private BitmapTextureAtlas lifeTexture;
    private Paddle paddle;
    private GameHolder gameHolder;

    public static int getCAMERA_HEIGHT() {
        return CAMERA_HEIGHT;
    }
    public static int getCAMERA_WIDTH() {
        return CAMERA_WIDTH;
    }

   /* private final MainActivity mainActivity;

    public Game(MainActivity final mainActivity) {
        this.mainActivity = mainActivity;
    }*/

    @Override
    public EngineOptions onCreateEngineOptions() {
        //final Display defaultDisplay = getWindow().getWindowManager().getDefaultDisplay();
        //CAMERA_WIDTH = defaultDisplay.getWidth();
        //CAMERA_HEIGHT = defaultDisplay.getHeight();
        this.mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);

        return new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED/*PORTRAIT_FIXED*/, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), this.mCamera);
    }

    @Override
    protected void onCreateResources() {
        gameHolder = GameHolder.getInstance();
        gameHolder.addObserver(this);
        gameHolder.setGameActivity(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            selectedMap = extras.getString("map");
            playerName = extras.getString("name");
        }

        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

        this.ballTexture = new BitmapTextureAtlas(this.getTextureManager(), 64, 64, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        this.paddleTexture = new BitmapTextureAtlas(this.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        this.backgroundTexture = new BitmapTextureAtlas(this.getTextureManager(), 800, 480, TextureOptions.DEFAULT);

        this.ballTextureRegion =  BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.ballTexture, this, "ball.png", 0, 0, 1, 1);
        this.bgTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.backgroundTexture, this, "background1.png", 0, 0);

        this.ballTexture.load();
        this.paddleTexture.load();
        this.backgroundTexture.load();

        this.mFont = FontFactory.create(this.getFontManager(), this.getTextureManager(), 256, 256, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 32);
        this.mFont.load();



        //this.lifeTexture = new BitmapTextureAtlas(this.getTextureManager(), 800, 480, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        //font = FontFactory.createFromAsset(fontTextureAtlas,this,"times.ttf",45f,true,Color.WHITE);
    }

    @Override
    protected Scene onCreateScene() {
        this.mEngine.registerUpdateHandler(new FPSLogger());
        final Scene scene = new Scene();
        scene.setOnSceneTouchListener(this);

        final float centerX = (CAMERA_WIDTH - backgroundTexture.getWidth()) / 2;
        final float centerY = (CAMERA_HEIGHT - backgroundTexture.getHeight()) / 2;

        //background
        final SpriteBackground bg = new SpriteBackground(new Sprite(centerX, centerY, bgTextureRegion, this.getVertexBufferObjectManager()));

        //text shows remaining lives
        final Text livesText = new Text(650, 350, this.mFont, "lives: " + lives, new TextOptions(HorizontalAlign.CENTER), this.getVertexBufferObjectManager());
        final Text timeText = new Text(650, 300, this.mFont, "time: " + time, new TextOptions(HorizontalAlign.CENTER), this.getVertexBufferObjectManager());
        final Text nameText = new Text(650, 250, this.mFont, playerName, new TextOptions(HorizontalAlign.CENTER), this.getVertexBufferObjectManager());

        final Ball ball = new Ball(CAMERA_WIDTH/2, CAMERA_HEIGHT/2, this.ballTextureRegion, this.getVertexBufferObjectManager());
        ball.mPhysicsHandler.setVelocity(100.0f, 100.0f);

        paddle = new Paddle(CAMERA_WIDTH/2, CAMERA_HEIGHT-50, CAMERA_HEIGHT/8, CAMERA_WIDTH/34, this.getVertexBufferObjectManager());

        //final Brick[][] bricks = new Brick[5][10];
        BrickMap brickMap = new BrickMap();

        for (int a = 0; a < 5; a++)
            for (int b = 0; b < 10; b++)
                brickMap.map[a][b] = new Brick(10 + b * CAMERA_WIDTH / 10, 10 + a * CAMERA_HEIGHT / 10, CAMERA_HEIGHT / 16, CAMERA_WIDTH / 32, this.getVertexBufferObjectManager());

        brickMap.setMap(selectedMap);
        final Brick[][] bricks = brickMap.map;

        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 10; j++)
                if(bricks[i][j].isExist)
                    scene.attachChild(bricks[i][j]);

        scene.attachChild(livesText);
        scene.attachChild(timeText);
        scene.attachChild(nameText);
        scene.attachChild(ball);
        scene.attachChild(paddle);
        scene.registerTouchArea(paddle);
        scene.setOnSceneTouchListener(this);

        scene.registerUpdateHandler(new IUpdateHandler() {
            public void reset() { }

            public void onUpdate(final float pSecondsElapsed) {
                if(ball.collidesWith(paddle)) {
                    ball.bounceWithRectangle(paddle);
                }
                else if (ball.getY() >= Game.getCAMERA_HEIGHT() - 30) {
                    scene.setBackground(new Background(255f, 0f, 0f));
                    ball.mPhysicsHandler.setVelocity(ball.mPhysicsHandler.getVelocityX()/2, ball.mPhysicsHandler.getVelocityY()/2);
                    lives--;
                    livesText.setText("lives: " + lives);
                    if(lives == 0){
                        //Toast.makeText(Game.this, "Game Over", Toast.LENGTH_SHORT).show();
                        /*runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(mainActivity, "message", Toast.LENGTH_LONG).show();

                            }
                        });*/
                        finish();
                    }
                }
                else {
                    for (int i = 0; i < bricks.length; i++) {
                        for (int j = 0; j < bricks[0].length; j++) {
                            scene.setBackground(bg);
                            if(ball.collidesWith(bricks[i][j])  && bricks[i][j].isExist) {
                                //bricks[i][j].setPosition(CAMERA_HEIGHT+20, CAMERA_WIDTH+20);
                                ball.bounceWithRectangle(bricks[i][j]);
                                scene.detachChild(bricks[i][j]);
                                bricks[i][j].isExist = false;

                                boolean isWin = true;

                                for(int k = 0; k < 5; k++)
                                    for(int l = 0; l < 10; l++)
                                        if(bricks[k][l].isExist)
                                            isWin = false;
                                if(isWin)
                                    finish();

                            }
                        }
                    }
                }
            }
        });

        scene.registerUpdateHandler(new TimerHandler(1.0f, new ITimerCallback()
        {
            public void onTimePassed(final TimerHandler pTimerHandler)
            {
                pTimerHandler.reset();
                time++;
                timeText.setText("time: " + time);
            }
        }));

        return scene;
    }

    @Override
    public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
        paddle.setPosition(pSceneTouchEvent.getX() - paddle.getWidth() / 2, Game.getCAMERA_HEIGHT()-30);
        return true;
    }

    /*public void onPause() {
        super.onPause();
        gameHolder.setGameState(gameHolder.getPausedGameState());
    }*/

    @Override
    public void update(Observable observable, Object data) {

    }
}
