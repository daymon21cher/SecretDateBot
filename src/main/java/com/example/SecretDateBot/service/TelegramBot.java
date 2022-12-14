package com.example.SecretDateBot.service;

import com.example.SecretDateBot.config.BotConfig;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    final BotConfig config;

    public TelegramBot(BotConfig config){
        this.config = config;
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {

        if(update.hasMessage() && update.getMessage().hasText()){
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            switch (messageText){
                case "/start":
                        startCommandReceived(chatId, update.getMessage().getChat().getFirstName(), update.getMessage().getChat().getLastName());

                        break;
                default:
                    sendMessage(chatId, "Простите, пока не работает:(");
            }
        }

    }

    private void startCommandReceived(long chatId, String name, String lastName){
        String answer;

        if(lastName.equals("Sidorkin")){
            answer = lastName + " - лох.";
        }else {
            answer = "Привет, " + name + ", хорошего дня)";
        }
        sendMessage(chatId, answer);
    }

    private void sendMessage(long chatId, String textToSend){
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);
        try {
            execute(message);
        }
        catch (TelegramApiException e){

        }
    }
}
