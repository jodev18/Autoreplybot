package com.example.autoreplybot;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.theokanning.openai.OpenAiService;
import com.theokanning.openai.completion.CompletionChoice;
import com.theokanning.openai.completion.CompletionRequest;

import java.util.List;

public class SmsReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            Object[] pdus = (Object[]) extras.get("pdus");

            String smsrec = "";
            String smssender = "";

            for (Object pdu : pdus) {
                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdu);
                String messageBody = smsMessage.getMessageBody();
                String messageSender = smsMessage.getOriginatingAddress();
                Toast.makeText(context, "SMS:"
                        + messageBody, Toast.LENGTH_LONG).show();
                smsrec = messageBody;
                smssender = messageSender;
            }

            Log.d("NETWORK_LOAD","Initializing ChatGPT...");
            OpenAiService service = new OpenAiService(Globals.API_KEY);
            CompletionRequest completionRequest = CompletionRequest.builder()
                    .prompt("reply kindly and sweet to this:" + smsrec)
                    .model("text-davinci-003")
                    .maxTokens(160)
                    .echo(false)
                    .build();

            final String phonenum = smssender;

            /**
             * TODO change this into a more efficient way of managing background threads
             *
             * This is just for sample PoC. Hehe
             */
            new Thread(new Runnable() {
                @Override
                public void run() {
                    List<CompletionChoice> choices = service
                            .createCompletion(completionRequest).getChoices();

                    String chatgptresponse = choices.get(0).getText().trim();
                    Log.d("REPLY",chatgptresponse.trim());

                    SmsManager sm = SmsManager.getDefault();

                    if(chatgptresponse.length() > 160){
                        //Send a reply
                        sm.sendTextMessage(phonenum,null,chatgptresponse.substring(0,160),null,null);
                    }
                    else{
                        sm.sendTextMessage(phonenum,null,chatgptresponse.trim(),null,null);
                    }
                }
            }).start();

        }
    }
}