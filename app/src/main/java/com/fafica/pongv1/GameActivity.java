package com.fafica.pongv1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.fafica.simplegameenginev1.SGActivity;
import com.fafica.simplegameenginev1.SGPreferences;

/**
 * Created by pedrodimoura on 21/09/16.
 */

public class GameActivity extends SGActivity {

    public static final String TAG = "PongV1";

    private GameView mGameView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        enableFullScreen();
        enableKeepScreenOn();

        this.mGameView = new GameView(GameActivity.this);
        setContentView(this.mGameView);

        SGPreferences sgPreferences = new SGPreferences(GameActivity.this);

        if (sgPreferences.getInt("first_time", -1) == -1) {
            sgPreferences.begin()
                    .putInt("first_time", 1)
                    .putInt("difficulty", 0)
                    .putInt("high_score", 15)
                    .end();

            Log.d(TAG, "Primeira Inicialização");
        }

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Nível de Dificuldade: ");
        stringBuilder.append(sgPreferences.getInt("difficulty", 0));
        Log.d(TAG, stringBuilder.toString());

        stringBuilder.setLength(0);
        stringBuilder.append("High Score: ");
        stringBuilder.append(sgPreferences.getInt("high_score", 0));
        Log.d(TAG, stringBuilder.toString());

    }

}
