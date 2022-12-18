package com.se.sws;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Contact extends AppCompatActivity {
    private EditText mEditTextSubject;
    private EditText mEditTextMessage;
    private Button move3;
    /*
    Here i've added a button that by pressing him, it will bring the USER back
    to the Universities page
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        /*
        Here ive added two id finders which relates to the subject and the message
         */

        mEditTextSubject = findViewById(R.id.edit_text_subject);
        mEditTextMessage = findViewById(R.id.edit_text_message);


        /*
        Here is the button that by pressing him will send the mail (Will connect the USER to the
        gmail with automatic copy pasting the subject and the message to the original mail page)
         */
        Button buttonSend = findViewById(R.id.button_send);
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                A helper function which does whats writen in the function description below.
                 */
                sendMail();
            }
        });
        /*
        Here is the button that once pushing on him , it will bring the USER back to the
        Universities page.
         */
        move3 = findViewById(R.id.prevfromemail);
        //Moves you to the Information page
        move3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Here it bring back the USER to the Universities page
                 */
                Intent intent = new Intent(Contact.this ,Universities.class);
                startActivity(intent);
            }
        });

    }

    /*
    This function gets the String from the subject and the String from the Message, and then
    lines 75-78 are
     */
    private void sendMail() {
        String[] recipientList = {"StudentsWstandards@gmail.com"};
        String subject = mEditTextSubject.getText().toString();
        String message = mEditTextMessage.getText().toString();

        /*
        Here is to start our email client
         */
        Intent intent = new Intent(Intent.ACTION_SEND);
        /*
        Here it gets the email that was opened especially for our project and it pasts it on the
        send automatically.
         */
        intent.putExtra(Intent.EXTRA_EMAIL,recipientList);
        /*
        Here i pass the subject that was writen and was parsed to the String subject.
         */
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        /*
        Here i pass the message that was writen and was parsed to the String message.
         */
        intent.putExtra(Intent.EXTRA_TEXT, message);

        /*
        Here to open email client
         */
        intent.setType("message/rfc822");

        startActivity(Intent.createChooser(intent, "Choose an email client"));
    }





/*
By the help of the tutorial: https://www.youtube.com/watch?v=tZ2YEw6SoBU&t=520s&ab_channel=CodinginFlow
 */
}