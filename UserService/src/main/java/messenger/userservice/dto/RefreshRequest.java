package messenger.userservice.dto;

import messenger.userservice.model.RefreshToken;

public record RefreshRequest(RefreshToken token) {
}
