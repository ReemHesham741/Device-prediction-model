package omar.deviceapi.controller;

import omar.deviceapi.service.CSVService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/csv")
public class CSVController {

    @Autowired
    private CSVService csvService;

    @GetMapping("/process")
    public ResponseEntity<?> processCSVFile() {
        try {
            csvService.saveDevicesFromCSV();
            return ResponseEntity.ok("Devices added successfully from CSV file");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}
