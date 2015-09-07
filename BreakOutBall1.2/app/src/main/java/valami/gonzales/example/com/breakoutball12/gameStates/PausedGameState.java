package valami.gonzales.example.com.breakoutball12.gameStates;

import valami.gonzales.example.com.breakoutball12.GameHolder;
import valami.gonzales.example.com.breakoutball12.GameState;

public class PausedGameState implements GameState {

	private GameHolder gameHolder;

	public PausedGameState(GameHolder gameHolder) {
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
