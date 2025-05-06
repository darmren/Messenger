package messenger.userservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name="refresh_tokens")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique=true, nullable=false)
    private String token;

    @Column(unique=true, name="created_at")
    private Date createdAt;

    @Column(unique=true, nullable=false, name="expires_at")
    private Date expiresAt;

    @JoinColumn(unique = true, nullable = false)
    @ManyToOne//TODO правильно?
    private User user;
}
