package valami.gonzales.example.com.breakoutball12;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by Gonzales on 2015.05.22..
 */
@ParseClassName("HighScores")
public class HighScores extends ParseObject {

    public int getPlace(){
        return getInt("Place");
    }

    public void setPlace(int place){
        put("Place", place);
    }

    public String getMap(){
        return getString("Map");
    }

    public void setMap(String map){
        put("Map", map);
    }

    public String getName(){
        return getString("Name");
    }

    public void setName(String name){
        put("Name", name);
    }

    public int getScore(){
        return getInt("Score");
    }

    public void setScore(int score){
        put("Score", score);
    }

    @Override
    public String toString(){
        return getString("Name") + " " + getInt("Score") + " points";
    }


}
