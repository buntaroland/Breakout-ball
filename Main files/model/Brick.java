package valami.gonzales.example.com.breakoutball12.model;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * Created by Gonzales on 2015.05.17..
 */
public class Brick extends Rectangle {

    public boolean isExist = true;

    public Brick(float pX, float pY, float pWidth, float pHeight, VertexBufferObjectManager pVertexBufferObjectManager) {
        super(pX, pY, pWidth, pHeight, pVertexBufferObjectManager);
    }

    protected void onManagedUpdate(final float pSecondsElapsed) {
//			if(this.collidesWith(pOtherShape))
    }
}
