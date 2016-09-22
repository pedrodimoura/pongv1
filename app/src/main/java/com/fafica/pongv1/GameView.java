package com.fafica.pongv1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;

import com.fafica.simplegameenginev1.SGImage;
import com.fafica.simplegameenginev1.SGImageFactory;
import com.fafica.simplegameenginev1.SGView;

/**
 * Created by pedrodimoura on 21/09/16.
 */

public class GameView extends SGView {

    private final static int BALL_SIZE = 16;
    private final static int DISTANCE_FROM_EDGE = 16;
    private final static int PADDLE_HEIGHT = 98;
    private final static int PADDLE_WIDTH = 23;
    private final static int BALL_SPEED = 120;
    private final static int OPPONENT_SPEED = 120;
    private final static int PLAYER_SPEED = 120;

    private RectF mBallDestination = new RectF();
    private RectF mOpponentDestination = new RectF();
    private RectF mPlayerDestination = new RectF();
    private Paint mTempPaint = new Paint();

    private boolean mBallMoveRight = true;
    private boolean mOpponentMoveDown = true;
    private boolean mPlayerMoveDown = true;

    private SGImage mBallImage;
    private SGImage mOpponentImage;
    private SGImage mPlayerImage;

    private Rect mTempImageSource = new Rect();

    public GameView(Context context) {
        super(context);
    }

    @Override
    protected void setup() {

        SGImageFactory sgImageFactory = getImageFactory();

        mBallImage = sgImageFactory.createImage(R.drawable.ball);
        mOpponentImage = sgImageFactory.createImage("opponent.png");
        mPlayerImage = sgImageFactory.createImage("player.png");

        Point viewDimensions = getDimensions();
        Point viewCenter = new Point(viewDimensions.x / 2, viewDimensions.y / 2);

        int halfBall = BALL_SIZE / 2;
        int halfPaddleHeight = PADDLE_HEIGHT / 2;

        mBallDestination.set(
                viewCenter.x - halfBall, // Esquerda
                viewCenter.y - halfBall, // Topo
                viewCenter.x + halfBall, // Direita
                viewCenter.y + halfBall); // Base

        mPlayerDestination.set(
                DISTANCE_FROM_EDGE, // Esquerda
                viewCenter.y - halfPaddleHeight, // Topo
                DISTANCE_FROM_EDGE + PADDLE_WIDTH, // Direita
                viewCenter.y + halfPaddleHeight); // Base

        mOpponentDestination.set(
                viewDimensions.x - (DISTANCE_FROM_EDGE + PADDLE_WIDTH), // Esquerda
                viewCenter.y - halfPaddleHeight, // Topo
                viewDimensions.x - DISTANCE_FROM_EDGE, // Direita
                viewCenter.y + halfPaddleHeight); // Base
    }

    @Override
    public void step(Canvas canvas, float ellapsedTimeInSeconds) {
        moveBall(ellapsedTimeInSeconds);
        moveOpponent(ellapsedTimeInSeconds);
        movePlayer(ellapsedTimeInSeconds);

        this.mTempPaint.setColor(Color.RED);

        this.mTempImageSource.set(0, 0, PADDLE_WIDTH, PADDLE_HEIGHT);

        if (mPlayerImage != null) {
            canvas.drawBitmap(mPlayerImage.getBitmap(), this.mTempImageSource, mPlayerDestination, mTempPaint);
        } else {
            canvas.drawRect(mPlayerDestination, mTempPaint);
        }

        if (mOpponentImage != null) {
            canvas.drawBitmap(mOpponentImage.getBitmap(), this.mTempImageSource, mOpponentDestination, mTempPaint);
        } else {
            canvas.drawRect(mOpponentDestination, mTempPaint);
        }

        mTempImageSource.set(0, 0, BALL_SIZE, BALL_SIZE);

        if (mBallImage != null) {
            canvas.drawBitmap(mBallImage.getBitmap(), this.mTempImageSource, mBallDestination, mTempPaint);
        } else {
            canvas.drawRect(mBallDestination, mTempPaint);
        }

//        canvas.drawRect(mPlayerDestination, mTempPaint);
//        canvas.drawRect(mBallDestination, mTempPaint);
//        canvas.drawRect(mOpponentDestination, mTempPaint);
    }

    public void moveBall(float ellapsedTimeInSeconds) {
        Point viewDimensions = getDimensions();

        if (mBallMoveRight) {
            mBallDestination.left += BALL_SPEED * ellapsedTimeInSeconds;
            mBallDestination.right += BALL_SPEED * ellapsedTimeInSeconds;

            if (mBallDestination.right >= viewDimensions.x) {
                mBallDestination.left = viewDimensions.x - BALL_SIZE;
                mBallDestination.right = viewDimensions.x;

                mBallMoveRight = false;
            }
        } else {
            mBallDestination.left -= BALL_SPEED * ellapsedTimeInSeconds;
            mBallDestination.right -= BALL_SPEED * ellapsedTimeInSeconds;

            if (mBallDestination.left < 0) {
                mBallDestination.left = 0;
                mBallDestination.right = BALL_SIZE;

                mBallMoveRight = true;
            }

        }
    }

    public void moveOpponent(float ellapsedTimeInSeconds) {
        Point viewDimensions = getDimensions();

        if (mOpponentMoveDown) {
            mOpponentDestination.top += OPPONENT_SPEED * ellapsedTimeInSeconds;
            mOpponentDestination.bottom += OPPONENT_SPEED * ellapsedTimeInSeconds;

            if (mOpponentDestination.bottom >= viewDimensions.y) {
                mOpponentDestination.top = viewDimensions.y - PADDLE_HEIGHT;
                mOpponentDestination.bottom = viewDimensions.y;

                mOpponentMoveDown = false;
            }
        } else {
            mOpponentDestination.top -= OPPONENT_SPEED * ellapsedTimeInSeconds;
            mOpponentDestination.bottom -= OPPONENT_SPEED * ellapsedTimeInSeconds;

            if (mOpponentDestination.top < 0) {
                mOpponentDestination.top = 0;
                mOpponentDestination.bottom = PADDLE_HEIGHT;

                mOpponentMoveDown = true;
            }

        }
    }

    public void movePlayer(float ellapsedTimeInSeconds) {
        Point viewDimensions = getDimensions();

        if (mPlayerMoveDown) {
            mPlayerDestination.top += PLAYER_SPEED * ellapsedTimeInSeconds;
            mPlayerDestination.bottom += PLAYER_SPEED * ellapsedTimeInSeconds;

            if (mPlayerDestination.bottom >= viewDimensions.y) {
                mPlayerDestination.top = viewDimensions.y - PADDLE_HEIGHT;
                mPlayerDestination.bottom = viewDimensions.y;

                mPlayerMoveDown = false;
            }
        } else {
            mPlayerDestination.top -= PLAYER_SPEED * ellapsedTimeInSeconds;
            mPlayerDestination.bottom -= PLAYER_SPEED * ellapsedTimeInSeconds;

            if (mPlayerDestination.top == 0) {
                mPlayerDestination.top = 0;
                mPlayerDestination.bottom = PADDLE_HEIGHT;

                mPlayerMoveDown = true;
            }
        }
    }

}
