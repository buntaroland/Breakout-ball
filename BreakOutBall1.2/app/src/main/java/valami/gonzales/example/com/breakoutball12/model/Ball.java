package valami.gonzales.example.com.breakoutball12.model;

import org.andengine.engine.Engine;
import org.andengine.engine.handler.physics.PhysicsHandler;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import valami.gonzales.example.com.breakoutball12.game.Game;

/**
 * Created by Gonzales on 2015.05.17..
 */
public class Ball extends AnimatedSprite {
    public final PhysicsHandler mPhysicsHandler;

    float velocity = 300;
    int i = 0;

    public Ball(float positionX, float positionY, TiledTextureRegion positionTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager/*, Engine mEngine*/) {
        super(positionX, positionY, positionTextureRegion, pVertexBufferObjectManager);
        this.mPhysicsHandler = new PhysicsHandler(this);
        this.registerUpdateHandler(this.mPhysicsHandler);
        this.mPhysicsHandler.setVelocity(0, 0);
    }

    protected void onManagedUpdate(final float pSecondsElapsed) {
        if(this.mX < 0) {
            this.mPhysicsHandler.setVelocityX(velocity);
        }
        else if(this.mX + this.getWidth() > Game.getCAMERA_WIDTH()){
            this.mPhysicsHandler.setVelocityX(-velocity);
        }

        if(this.mY < 0) {
            this.mPhysicsHandler.setVelocityY(velocity);
        } else if(this.mY + this.getHeight() + 5 > Game.getCAMERA_HEIGHT()) {
            this.mPhysicsHandler.setVelocityY(-velocity);
        }

        super.onManagedUpdate(pSecondsElapsed);
    }

    public void bounceWithRectangle(Rectangle rectangle){
		float ballPositionX = this.getX();
		float ballPositionY = this.getY();
		float centerVertical = rectangle.getX();
		float westWall = rectangle.getX() - rectangle.getWidth()/2 - this.getWidth()/2;
		float eastWall = rectangle.getX() + rectangle.getWidth()/2 + this.getWidth()/2;
		float centerHorizontal = rectangle.getY();
		float northWall = rectangle.getY() - rectangle.getHeight()/2 - this.getHeight()/2;
		float southWall = rectangle.getY() + rectangle.getHeight()/2 + this.getHeight()/2;

        /*float tolerance = 3;
        if(ballPositionY + tolerance < rectangle.getY() + rectangle.getHeight() &&  //ball not above block
           ballPositionY + this.getHeight() > rectangle.getY() + tolerance) {        // ball not below block
            this.mPhysicsHandler.setVelocityX(-this.mPhysicsHandler.getVelocityX());

            // ball hit block left or right
            if (ballPositionX > rectangle.getX() + (rectangle.getWidth() / 2)) { // hit on right
                this.setX(ballPositionX + tolerance + 1);
            } else { // hit on left
                this.setX(ballPositionX - (tolerance + 1));
            }
        }
        else if (ballPositionX + this.getWidth() > rectangle.getX() + tolerance // ball not left of block
                && ballPositionX + tolerance < rectangle.getX() + rectangle.getWidth()) {	// ball not right of block
            // hit block on top or bottom
            this.mPhysicsHandler.setVelocityY(-this.mPhysicsHandler.getVelocityY());
            if (ballPositionY > rectangle.getY() + (rectangle.getHeight() / 2)) { // hit on top
                this.setY(ballPositionY + tolerance);
            } else { // hit on bottom
                this.setY(ballPositionY - (tolerance + 1));
            }
        }*/


        if(ballPositionX <= westWall + 3 || ballPositionX >= eastWall + 3) {
			this.mPhysicsHandler.setVelocityX(-this.mPhysicsHandler.getVelocityX());
		}
		else {
            this.mPhysicsHandler.setVelocityY(-this.mPhysicsHandler.getVelocityY());
        }
    }
}
