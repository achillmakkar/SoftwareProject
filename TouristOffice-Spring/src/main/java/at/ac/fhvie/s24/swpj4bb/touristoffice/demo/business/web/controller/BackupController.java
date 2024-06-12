package at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

@RestController
public class BackupController {

    @GetMapping("/backup")
    public String backupDatabase() {
        try {
            // Datenbank-Verbindungsdetails
            String url = "jdbc:h2:file:~/hotels";
            String user = "sa";
            String password = "";

            // Verbindungsaufbau
            Connection conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();

            // Pfad zum Backup
            String backupPath = System.getProperty("user.home") + "/backup/hotels_backup.zip";

            // Backup-Befehl ausführen
            stmt.execute("BACKUP TO '" + backupPath + "'");

            // Verbindung schließen
            stmt.close();
            conn.close();

            return "Backup erfolgreich: " + backupPath;
        } catch (Exception e) {
            e.printStackTrace();
            return "Backup fehlgeschlagen: " + e.getMessage();
        }
    }
}
