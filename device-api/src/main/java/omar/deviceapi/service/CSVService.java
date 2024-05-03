package omar.deviceapi.service;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import omar.deviceapi.model.Device;
import omar.deviceapi.repository.DeviceRepository;

import java.io.FileReader;
import java.io.Reader;

@Service
public class CSVService {

    @Autowired
    private DeviceRepository deviceRepository;

    public void saveDevicesFromCSV() throws Exception {
        // Load the CSV file from the resources directory
        ClassPathResource resource = new ClassPathResource("test.csv");
        try (Reader reader = new FileReader(resource.getFile());
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {
            for (CSVRecord csvRecord : csvParser) {
                Device device = new Device();
                
                device.setId(Long.parseLong(csvRecord.get("id")));
                
                device.setBattery_power(Integer.parseInt(csvRecord.get("battery_power")));
                device.setBlue(Boolean.parseBoolean(csvRecord.get("blue")));
                // try{
                    device.setClock_speed(Double.parseDouble(csvRecord.get("clock_speed")));
                // } catch (NumberFormatException e) {
                //     System.err.println("Error parsing clock_speed: " + e.getMessage());
                // }
                device.setDual_sim(Boolean.parseBoolean(csvRecord.get("dual_sim")));
                device.setFc(Integer.parseInt(csvRecord.get("fc")));
                device.setFour_g(Boolean.parseBoolean(csvRecord.get("four_g")));
                device.setInt_memory(Integer.parseInt(csvRecord.get("int_memory")));
                device.setM_dep(Double.parseDouble(csvRecord.get("m_dep")));
                device.setMobile_wt(Integer.parseInt(csvRecord.get("mobile_wt")));
                device.setN_cores(Integer.parseInt(csvRecord.get("n_cores")));
                device.setPc(Integer.parseInt(csvRecord.get("pc")));
                device.setPx_height(Integer.parseInt(csvRecord.get("px_height")));
                device.setPx_width(Integer.parseInt(csvRecord.get("px_width")));
                device.setRam(Integer.parseInt(csvRecord.get("ram")));
                device.setSc_h(Integer.parseInt(csvRecord.get("sc_h")));
                device.setSc_w(Integer.parseInt(csvRecord.get("sc_w")));
                device.setTalk_time(Integer.parseInt(csvRecord.get("talk_time")));
                device.setThree_g(Boolean.parseBoolean(csvRecord.get("three_g")));
                device.setTouch_screen(Boolean.parseBoolean(csvRecord.get("touch_screen")));
                device.setWifi(Boolean.parseBoolean(csvRecord.get("wifi")));
                
                deviceRepository.save(device);
            }
        }
    }
}
