package com.example.REST_Server_With_JWT.util.responses;

import com.example.REST_Server_With_JWT.dto.MessageDTO;

import java.util.List;

// класс обертка для отправки списка сообщений в виде json файла
public class MessagesResponse {

    // список сообщений
    private List<MessageDTO> messages;

    public MessagesResponse(List<MessageDTO> messages) {
        this.messages = messages;
    }

    public List<MessageDTO> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageDTO> messages) {
        this.messages = messages;
    }
}
