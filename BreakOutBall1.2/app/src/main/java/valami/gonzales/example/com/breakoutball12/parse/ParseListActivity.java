package valami.gonzales.example.com.breakoutball12.parse;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import valami.gonzales.example.com.breakoutball12.GameHolder;
import valami.gonzales.example.com.breakoutball12.MainActivity;
import valami.gonzales.example.com.breakoutball12.R;
import valami.gonzales.example.com.breakoutball12.model.BrickMap;

/**
 * Created by Gonzales on 2015.05.17..
 */
public class ParseListActivity extends Activity {

    public final int REQUEST_ACT2 = 2;

    List<BrickMap> brickMaps = new ArrayList<BrickMap>();
    public String selectedMap;
    public String selectedMapId;
    public String playerName;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_map);

        ParseQuery<BrickMap> query = new ParseQuery<BrickMap>("Map");

        query.findInBackground(new FindCallback<BrickMap>() {
            @Override
            public void done(List<BrickMap> list, ParseException e) {
                if(e == null){
                    for (BrickMap brickMap : list) {
                        BrickMap newBrickMap = new BrickMap();
                        newBrickMap.setGameMap(brickMap.getGameMap());
                        brickMaps.add(newBrickMap);
                    }
                }
                Log.w("map1", brickMaps.get(0).strMap);
                Log.w("map2", brickMaps.get(1).strMap);
                Log.w("map3", brickMaps.get(2).strMap);
                Log.w("map4", brickMaps.get(3).strMap);
                Log.w("map5", brickMaps.get(4).strMap);

            }
        });

        Intent prefIntent = new Intent(getApplicationContext(), PrefActivity.class);
        startActivityForResult(prefIntent, REQUEST_ACT2);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (2): {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
                selectedMapId = sharedPreferences.getString("PREF_LIST", "0");
                playerName = sharedPreferences.getString("PREF_NAME", "player");

                selectedMap = brickMaps.get(Integer.parseInt(selectedMapId)).strMap;
                Log.w("selectedMap", selectedMap);
                Intent i = new Intent();
                i.putExtra("selectedMap", selectedMap);
                i.putExtra("playerName", playerName);
                setResult(RESULT_OK, i);
                finish();
            }
        }
    }
}