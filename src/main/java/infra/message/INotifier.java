package infra.message;

import domain.model.MessageType;

public interface INotifier {
    void sendMessage(String message, MessageType messageType);

}
