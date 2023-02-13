package com.example.autoreplybot;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.theokanning.openai.OpenAiService;
import com.theokanning.openai.completion.CompletionChoice;
import com.theokanning.openai.completion.CompletionRequest;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText eMsg;
    private TextView tRep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eMsg = findViewById(R.id.etSendMessage);
        tRep = findViewById(R.id.tvChatGPTReply);

        initButton();
    }

    private void initButton(){
        Button bt = findViewById(R.id.btn_try);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getApplicationContext(),
//                        "Test Text", Toast.LENGTH_LONG).show();

                /**
                 * This ain't good but for now will do
                 * TODO shift to AsyncTask
                 */
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("NETWORK_LOAD","Initializing ChatGPT...");
                        OpenAiService service = new OpenAiService(Globals.API_KEY);
                        CompletionRequest completionRequest = CompletionRequest.builder()
                                .prompt(eMsg.getText().toString())
                                .model("text-davinci-003")
                                .echo(false)
                                .build();
                        List<CompletionChoice> choices = service
                                .createCompletion(completionRequest).getChoices();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                String chatgptresponse = choices.get(0).getText();
                                Toast.makeText(getApplicationContext(),
                                        choices.get(0).getText().trim(),Toast.LENGTH_LONG).show();
                                Log.d("REPLY",chatgptresponse);
                                tRep.setText("ChatGPT Reply: " + chatgptresponse.trim());
                            }
                        });
                    }
                }).start();
            }
        });
    }
}