package messenger.chatservice.client;

import messenger.chatservice.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-service", url = "http://user-service:8080") //в докер
//@FeignClient(name = "user-service", url = "http://localhost:8081") // в IDEA
public interface UserClient {

    @GetMapping("/by-id/{id}")
    UserDto getUserById(@PathVariable("id") Long id);
}

