package com.team5.career_progression_app.service;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.chat.completions.ChatCompletionCreateParams;
import org.springframework.stereotype.Service;

@Service
public class OpenAIService {
    OpenAIClient client = OpenAIOkHttpClient.builder()
    // Configures using the OPENAI_API_KEY, OPENAI_ORG_ID, OPENAI_PROJECT_ID and OPENAI_BASE_URL environment variables
    .fromEnv()
    .apiKey("")
    .build();

    public String prompt(String baseMessage, String userRequest) {
        ChatCompletionCreateParams.Builder createParamsBuilder =
                ChatCompletionCreateParams.builder()
                        .model("gpt-4o-mini")
                        .maxCompletionTokens(8192);

        ChatCompletionCreateParams createParams = createParamsBuilder
                .addSystemMessage(baseMessage)
                .addUserMessage(userRequest)
                .build();

        StringBuilder prompt = new StringBuilder();

        client.chat().completions().create(createParams).choices().stream()
                .flatMap(choice -> choice.message().content().stream())
                .forEach(prompt::append);

        return prompt.toString();
    }
} 