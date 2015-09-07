package valami.gonzales.example.com.breakoutball12.parse_highscores;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.ArrayAdapter;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import valami.gonzales.example.com.breakoutball12.HighScores;
import valami.gonzales.example.com.breakoutball12.R;

/**
 * Created by Gonzales on 2015.05.22..
 */
public class ParseFragmentActivity extends FragmentActivity {
    ViewPager pager;
    ArrayList<String> maps;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parse_hs);

        getActionBar().setTitle("High Scores");

        pager = (ViewPager)findViewById(R.id.pager);
        final ViewPagerFragmentAdapter adapter = new ViewPagerFragmentAdapter(getSupportFragmentManager());
        maps = new ArrayList<String>();

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("HighScoreTypes");
        query.addAscendingOrder("Map");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                if(e == null){
                    for (ParseObject type : parseObjects) {
                        String hsMap = type.getString("Map");
                        maps.add(hsMap);
                    }

                    pager.setAdapter(adapter);
                }
            }
        });

    }


    private class ViewPagerFragmentAdapter extends FragmentPagerAdapter{

        public ViewPagerFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PageFragment.create(position+1, maps.get(position).toString());
        }

        @Override
        public int getCount() {
            return maps.size();
        }

        @Override
        public CharSequence getPageTitle(int position){
            return maps.get(position).toString();
        }
    }
}
