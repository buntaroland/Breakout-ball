package valami.gonzales.example.com.breakoutball12.gameStates;

import valami.gonzales.example.com.breakoutball12.GameHolder;
import valami.gonzales.example.com.breakoutball12.GameState;

/**
 * Created by Gonzales on 2015.05.17..
 */
public class NewGameState implements GameState {

    private GameHolder gameHolder;

    public NewGameState(GameHolder gameHolder) {
        this.gameHolder = gameHolder;
    }

    @Override
    public void startGame() {

    }

    @Override
    public void pauseGame() {

    }

    @Override
    public void resumeGame() {

    }

    @Override
    public void killGame() {

    }
}
