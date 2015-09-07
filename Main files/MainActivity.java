package valami.gonzales.example.com.breakoutball12;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseObject;

import java.util.Observable;
import java.util.Observer;

import valami.gonzales.example.com.breakoutball12.game.Game;
import valami.gonzales.example.com.breakoutball12.R;
import valami.gonzales.example.com.breakoutball12.model.BrickMap;
import valami.gonzales.example.com.breakoutball12.parse.ParseListActivity;
import valami.gonzales.example.com.breakoutball12.parse.PrefActivity;
import valami.gonzales.example.com.breakoutball12.parse_highscores.ParseFragmentActivity;


public class MainActivity extends Activity implements Observer {

    private GameHolder gameHolder;
    private Button newGameButton;
    private Button highScoresButton;
    private Button settingsButton;
    private Button exitButton;

    public final int REQUEST_ACT1 = 1;
    public final int REQUEST_ACT2 = 2;
    public String selectedMap = "11111111111111111111111111111111111111111111111111";
    public String playerName = "Player";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        gameHolder = GameHolder.getInstance();
        gameHolder.addObserver(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        newGameButton = (Button)this.findViewById(R.id.newGameButton);

        newGameButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gameIntent = new Intent(v.getContext(), Game.class);
                gameIntent.putExtra("map", selectedMap);
                gameIntent.putExtra("name", playerName);
                gameHolder.setGameIntent(gameIntent);
                startActivity(gameIntent);
                gameHolder.setGameState(gameHolder.getInGameState());
            }
        });

        highScoresButton = (Button)this.findViewById(R.id.highScoresButton);

        highScoresButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent prefIntent = new Intent(getApplicationContext(), ParseFragmentActivity.class);
                startActivityForResult(prefIntent, REQUEST_ACT2);

            }
        });

        settingsButton = (Button)this.findViewById(R.id.settingsButton);

        settingsButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent prefIntent = new Intent(getApplicationContext(), ParseListActivity.class);
                startActivityForResult(prefIntent, REQUEST_ACT1);
            }
        });

        exitButton = (Button)this.findViewById(R.id.exitButton);

        exitButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (1) : {
                if (resultCode == Activity.RESULT_OK) {
                    selectedMap = data.getExtras().getString("selectedMap");
                    playerName = data.getExtras().getString("playerName");
                }
                break;
            }
        }
    }

    @Override
    public void update(Observable observable, Object data) {
        GameHolder gameHolder = (GameHolder)observable;
        gameHolder.getGameState();
    }
}
