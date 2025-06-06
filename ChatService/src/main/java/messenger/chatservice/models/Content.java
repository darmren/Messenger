package messenger.chatservice.models;

import ch.qos.logback.core.status.InfoStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import messenger.chatservice.enums.ContentType;

import java.time.Instant;

@Entity
@Table(name = "contents")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Content {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "file_path", nullable = false)
    private String filePath;

    @Column(nullable = false)
    private Long size;

    @Column(name = "uploaded_at", nullable = false)
    private Instant uploadedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "content_type", nullable = false)
    private ContentType contentType;
}

