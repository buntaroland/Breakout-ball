package valami.gonzales.example.com.breakoutball12.model;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * Created by Gonzales on 2015.05.17..
 */
public class Paddle extends Rectangle{
    public Paddle(float pX, float pY, float pWidth, float pHeight, VertexBufferObjectManager pVertexBufferObjectManager) {
        super(pX, pY, pWidth, pHeight, pVertexBufferObjectManager);
    }
}
