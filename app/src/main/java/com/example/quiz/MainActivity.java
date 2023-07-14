package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView totalQuestionsTextView;
    TextView questionTextView;
    Button AnswerA, AnswerB, AnswerC, AnswerD;
    Button submitbtn;

    int score=0;
    int totalQuestion = QuestionAnswer.question.length;
    int currentQuestionIndex = 0;
    String selectedAnswer="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        totalQuestionsTextView = findViewById(R.id.total_questions);
        questionTextView = findViewById(R.id.question);
        AnswerA = findViewById(R.id.Answer_A);
        AnswerB = findViewById(R.id.Answer_B);
        AnswerC = findViewById(R.id.Answer_C);
        AnswerD = findViewById(R.id.Answer_D);
        submitbtn = findViewById(R.id.submit_btn);

        AnswerA.setOnClickListener(this);
        AnswerB.setOnClickListener(this);
        AnswerC.setOnClickListener(this);
        AnswerD.setOnClickListener(this);
        submitbtn.setOnClickListener(this);

        totalQuestionsTextView.setText("Total questions : "+totalQuestion);

        loadNewQuestion();

    }

    @Override
    public void onClick(View view) {

        AnswerA.setBackgroundColor(Color.WHITE);
        AnswerB.setBackgroundColor(Color.WHITE);
        AnswerC.setBackgroundColor(Color.WHITE);
        AnswerD.setBackgroundColor(Color.WHITE);

        Button clickedButton = (Button) view;
        if(clickedButton.getId()==R.id.submit_btn){
            if(selectedAnswer.equals(QuestionAnswer.correctAnswers[currentQuestionIndex])) {
                score++;
            }
            currentQuestionIndex++;
            loadNewQuestion();

        }else{
            selectedAnswer = clickedButton.getText().toString();
            clickedButton.setBackgroundColor(getResources().getColor(R.color.blue));
        }

    }

    void loadNewQuestion(){

        if(currentQuestionIndex == totalQuestion ){
            finishQuiz();
            return;
        }

        questionTextView.setText(QuestionAnswer.question[currentQuestionIndex]);
        AnswerA.setText(QuestionAnswer.choices[currentQuestionIndex][0]);
        AnswerB.setText(QuestionAnswer.choices[currentQuestionIndex][1]);
        AnswerC.setText(QuestionAnswer.choices[currentQuestionIndex][2]);
        AnswerD.setText(QuestionAnswer.choices[currentQuestionIndex][3]);

    }

    void finishQuiz(){
        String passStatus = "";
        if(score > totalQuestion*.6) {
            passStatus = "Passed";

        }else{
            passStatus = "Failed";
        }

        new AlertDialog.Builder(this)
                .setTitle(passStatus)
                .setMessage("Score is"+ score+"out of"+ totalQuestion )
                .setPositiveButton("Restart",((dialogInterface, i) -> restartQuiz() ))
                .setCancelable(false)
                .show();
    }

    void restartQuiz(){
        score = 0;
        currentQuestionIndex = 0;
        loadNewQuestion();
    }
}