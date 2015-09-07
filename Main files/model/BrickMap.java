package valami.gonzales.example.com.breakoutball12.model;

import android.util.Log;
import android.widget.Toast;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import valami.gonzales.example.com.breakoutball12.MainActivity;

/**
 * Created by Gonzales on 2015.05.17..
 */

@ParseClassName("Map")
public class BrickMap extends ParseObject{
    public Brick[][] map;
    public String strMap;
    public BrickMap() {
        map = new Brick[5][10];
    }
    //10x5
    public void setMap(String loadedMap){
        int k = 0;
        for(int i = 0; i < 5; i++)
            for(int j = 0; j < 10; j++){
                char c = loadedMap.charAt(k);
                if(c == '1') {
                    map[i][j].isExist = true;
                }
                else
                    map[i][j].isExist = false;
                k++;
            }
    }

    public Brick[][] getMap(){
        return this.map;
    }

    public String getGameMap(){
        return getString("GameMap");
    }

    public void setGameMap(String gameMap){
       strMap = gameMap;
    }
}
