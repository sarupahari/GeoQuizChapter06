package edu.wtamu.cis.cidm4385saru.geoquiz;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final String QUESTIONS_ANSWERED_KEY = "answeredQuestion";

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private int percentageScore = 0;
    private int totalScore = 0;
    private int counter = 0;

    private TextView mQuestionTextView;

    private Question[] mQuestionBank = new Question[]{
            new Question(R.string.question_australia, true),
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true),

    };

    private int mCurrentIndex = 0;
    private boolean[] mQuestionAnswered = new boolean[mQuestionBank.length];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);

        if(savedInstanceState != null){
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX,0);
            mQuestionAnswered = savedInstanceState.getBooleanArray(QUESTIONS_ANSWERED_KEY);

        }

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);

        // Challenge: Add a Listener to the TextView
        mQuestionTextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
            }
        });

        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {checkAnswer(true);
            }
        });

        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {checkAnswer(false);
            }
        });

        mNextButton = (Button) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                checkPercentage();
                mCurrentIndex =(mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
            }
        });
//        mPreviousButton = (Button) findViewById(R.id.previous_button);
//        mPreviousButton.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v){
//                if (mCurrentIndex == 0){
//                    mCurrentIndex = mQuestionBank.length - 1;
//                }else{
//                    mCurrentIndex = mCurrentIndex -1 % mQuestionBank.length;
//                }
//                updateQuestion();
//            }
//        });

        updateQuestion();
    }

    @Override
    public void onStart(){
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG,"onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX,mCurrentIndex);
        savedInstanceState.putBooleanArray(QUESTIONS_ANSWERED_KEY,mQuestionAnswered);
    }

    @Override
    public void onStop(){
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    private void updateQuestion(){
        mTrueButton.setEnabled(!mQuestionAnswered[mCurrentIndex]);
        mFalseButton.setEnabled(!mQuestionAnswered[mCurrentIndex]);
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue){
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        int messageResId = 0;

        if (userPressedTrue == answerIsTrue){
            messageResId = R.string.correct_toast;
            counter ++;
            totalScore ++;
        }else{
            messageResId = R.string.incorrect_toast;
            counter ++;
        }
        mQuestionAnswered[mCurrentIndex] = true;
        mFalseButton.setEnabled(false);
        mTrueButton.setEnabled(false);
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
                .show();
    }

    private void checkPercentage(){
        if(counter == mQuestionBank.length){
            percentageScore = (totalScore*100)/mQuestionBank.length;
            Toast.makeText(getApplicationContext(),"Percentage Score is"+ percentageScore,
                    Toast.LENGTH_LONG).show();
        }

    }
}