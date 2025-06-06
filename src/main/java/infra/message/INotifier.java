package infra.message;

import model.MessageType;

public interface INotifier {
    void sendMessage(String message, MessageType messageType);

}
