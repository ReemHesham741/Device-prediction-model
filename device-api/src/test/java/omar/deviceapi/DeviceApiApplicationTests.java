package omar.deviceapi;

import omar.deviceapi.model.Device;
import omar.deviceapi.repository.DeviceRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
public class DeviceApiApplicationTests {

    @Autowired
    private DeviceRepository deviceRepository;

    @Test
    public void testOutput10SamplesFromDeviceTable() {
        // Fetch the first 10 devices from the database
        List<Device> devices = deviceRepository.findAll().stream().limit(10).collect(Collectors.toList());

        // Print out the devices
        devices.forEach(device -> System.out.println(device));
    }
}
