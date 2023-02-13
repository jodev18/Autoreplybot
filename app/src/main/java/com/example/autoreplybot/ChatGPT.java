package com.example.autoreplybot;

import okhttp3.OkHttpClient;

public class ChatGPT {

    private static final String OPENAI_API_KEY = "your_api_key_here";
    private static final String MODEL = "text-davinci-003";
    private static final int MAX_TOKENS = 150;
    private static final double TEMPERATURE = 0.7;
    private static final int MAX_COMPLETIONS = 1;

//    private CompletionsApi completionsApi;
//
//    public ChatGPT() {
//        OkHttpClient httpClient = new OkHttpClient.Builder().build();
//        ApiClient apiClient = new ApiClient(httpClient);
//        apiClient.setApiKey(OPENAI_API_KEY);
//        completionsApi = new CompletionsApi(apiClient);
//    }
//
//    public String generateText(String prompt) {
//        CompletionRequest request = new CompletionRequest()
//                .model(MODEL)
//                .prompt(prompt)
//                .maxTokens(MAX_TOKENS)
//                .temperature(TEMPERATURE)
//                .n(MAX_COMPLETIONS)
//                .stop(null);
//        try {
//            CompletionResponse response = completionsApi.createCompletion(request);
//            return response.getChoices().get(0).getText();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
}
