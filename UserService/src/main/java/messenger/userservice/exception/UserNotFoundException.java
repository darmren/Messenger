package messenger.userservice.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String username) {
        super("Пользователь с username " + username +  " не найден");
    }
}
