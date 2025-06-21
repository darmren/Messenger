package messenger.chatservice.exception;

public class UserIdNotFoundException extends RuntimeException{
    public UserIdNotFoundException(String id) {
        super("Пользователь с id " + id + " не найден");
    }
}
