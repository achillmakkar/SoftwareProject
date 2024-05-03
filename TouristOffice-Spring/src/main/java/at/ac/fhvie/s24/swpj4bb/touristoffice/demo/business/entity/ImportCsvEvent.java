package at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "import_events")
// Codeanfang_Achill_02.05.2024/03.05.2024_ImportCsvEvent
public class ImportCsvEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventId;

    @Column(nullable = false)
    private LocalDateTime timestamp;


    @Column(length = 50, nullable = false)
    private String status;


    @Column(length = 255, nullable = false)
    private String user;

    public ImportCsvEvent() {
        this.timestamp = LocalDateTime.now();
    }

    // Getter und Setter Methoden
    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
// Codeende_Achill_02.05.2024/03.05.2024_ImportCsvEvent
