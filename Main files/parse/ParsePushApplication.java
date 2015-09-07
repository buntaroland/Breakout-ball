package valami.gonzales.example.com.breakoutball12.parse;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

import valami.gonzales.example.com.breakoutball12.HighScores;
import valami.gonzales.example.com.breakoutball12.R;
import valami.gonzales.example.com.breakoutball12.model.BrickMap;

/**
 * Created by Gonzales on 2015.05.17..
 */
public class ParsePushApplication extends Application {

    @Override
    public void onCreate(){
        Parse.enableLocalDatastore(this);
        ParseObject.registerSubclass(BrickMap.class);
        ParseObject.registerSubclass(HighScores.class);
        Parse.initialize(this, getString(R.string.ParseAppID), getString(R.string.ParseClientID));
    }
}
