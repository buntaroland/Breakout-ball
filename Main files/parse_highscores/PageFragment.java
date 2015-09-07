package valami.gonzales.example.com.breakoutball12.parse_highscores;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

import valami.gonzales.example.com.breakoutball12.HighScores;
import valami.gonzales.example.com.breakoutball12.R;

/**
 * Created by Gonzales on 2015.05.22..
 */
public class PageFragment extends ListFragment {
    private CustomParseQueryAdapter mAdapter;
    public static final String ARG_PAGE = "ARG_PAGE";
    public String maptype;

    public static PageFragment create(int page, String type){
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        args.putString("Map", type);
        PageFragment fragment = new PageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        maptype = getArguments().getString("Map");
        this.setRetainInstance(true);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.page_fragment, container, false);
        mAdapter = new CustomParseQueryAdapter(getActivity());
        setListAdapter(mAdapter);
        mAdapter.loadObjects();
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
    }

    public class CustomParseQueryAdapter extends ParseQueryAdapter<HighScores>{
        public CustomParseQueryAdapter(Context context){
            super(context, new QueryFactory<HighScores>() {
                public ParseQuery<HighScores> create() {
                    ParseQuery query = new ParseQuery("HighScores");
                    query.whereEqualTo("Map", maptype);
                    return query;
                }
            });
        }

        @Override
        public View getItemView(HighScores highScore, View v, ViewGroup parent){
            if(v == null){
                v = View.inflate(getContext(), R.layout.highscores, null);
            }
            super.getItemView(highScore, v, parent);

            TextView playerStats = (TextView)v.findViewById(R.id.playerStats);
            int playerPlace = highScore.getPlace();
            String playerName = highScore.getName();
            int playerScore = highScore.getScore();
            playerStats.setText(playerPlace + ". " + playerName + ": " + playerScore + "s");

            return v;
        }
    }


}
