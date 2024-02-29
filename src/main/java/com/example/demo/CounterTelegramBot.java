package com.example.demo;

import com.example.demo.components.BotCommands;
import com.example.demo.components.Buttons;
import com.example.demo.config.BotConfig;
import com.example.demo.responce.BookListResponse;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Component

public class CounterTelegramBot extends TelegramLongPollingBot implements BotCommands {

    final BotConfig config;

    public CounterTelegramBot(BotConfig config) {
        this.config = config;
        try {
            this.execute(new SetMyCommands(LIST_OF_COMMANDS, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e){
            log.error(e.getMessage());
        }
    }


    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override

    public String getBotToken() {
        return config.getToken();
    }

//    @Override
//    public void onUpdateReceived(@NotNull Update update) {
//        if (update.hasMessage() && update.getMessage().hasText()) {
//            String messageText = update.getMessage().getText();
//            long chatId = update.getMessage().getChatId();
//            String memberName = update.getMessage().getFrom().getFirstName();
//
//            switch (messageText) {
//                case "/start":
//                    startBot(chatId, memberName);
//                    break;
//                default:
//                    log.info("Unexpected message");
//            }
//        }
//    }
    @Override
    public void onUpdateReceived(@NotNull Update update) {
        long chatId = 0;
        long userId = 0; //это нам понадобится позже
        String userName = null;
        String receivedMessage;


        if(update.hasMessage()&&update.getMessage().hasText()){
            String messageText = update.getMessage().getText();
            chatId = update.getMessage().getChatId();
            String memberName = update.getMessage().getFrom().getFirstName();
        }

        //если получено сообщение текстом
        if(update.hasMessage()) {
            chatId = update.getMessage().getChatId();
            userId = update.getMessage().getFrom().getId();
            userName = update.getMessage().getFrom().getFirstName();
            if (update.getMessage().hasText()) {
                receivedMessage = update.getMessage().getText();
                botAnswerUtils(receivedMessage, chatId, userName);
            }

//
            //если нажата одна из кнопок бота
        } else if (update.hasCallbackQuery()) {
            chatId = update.getCallbackQuery().getMessage().getChatId();
            userId = update.getCallbackQuery().getFrom().getId();
            userName = update.getCallbackQuery().getFrom().getFirstName();
            receivedMessage = update.getCallbackQuery().getData();
            botAnswerUtils(receivedMessage, chatId, userName);
        }
    }


    private void botAnswerUtils(String receivedMessage, long chatId, String userName) {
        switch (receivedMessage){
            case "/start":
                startBot(chatId, userName);
                break;
            case "/help":
                sendHelpText(chatId, HELP_TEXT);
                break;
            case "/all":
                getAllBook(chatId);
                break;
            default: log.info("Unexpected message");
        }
    }

    private void getAllBook(Long chatId){
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        ResponseEntity<BookListResponse> responseEntity = new RestTemplate().getForEntity(
                "http://localhost:2825/api/v1/book/all",BookListResponse.class);
        System.out.println(responseEntity.getBody().getData());
        message.setText(responseEntity.getBody().getData().toString());

        try {
            execute(message);
            log.info("Reply sent");
        } catch (TelegramApiException e){
            log.error(e.getMessage());
        }
    }
    private void startBot(long chatId, String userName) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Hi, " + userName + "! I'm a Telegram bot.'");
        message.setReplyMarkup(Buttons.inlineMarkup());



//        private void addBook(){
//            SendMessage message = new SendMessage();
//            message.setChatId(chatId);
//            ResponseEntity<BookListResponse> responseEntity = new RestTemplate().postForEntity("http://localhost:2825/api/v1/book/add",BookListResponse.class);
//            System.out.println(responseEntity.getBody().getData());
//            message.setText(responseEntity.getBody().getData().toString());
//            try {
//                execute(message);
//                log.info("Reply sent");
//            } catch (TelegramApiException e){
//                log.error(e.getMessage());
//            }
//        }



        try {
            execute(message);
            log.info("Reply sent");
        } catch (TelegramApiException e){
            log.error(e.getMessage());
        }
    }


    private void sendHelpText(long chatId, String textToSend){
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(textToSend);


        try {
            execute(message);
            log.info("Reply sent");
        } catch (TelegramApiException e){
            log.error(e.getMessage());
        }
    }


}