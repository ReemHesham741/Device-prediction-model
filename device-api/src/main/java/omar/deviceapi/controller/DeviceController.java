package omar.deviceapi.controller;

import omar.deviceapi.model.Device2;
import omar.deviceapi.service.DeviceService;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RestController
public class DeviceController {
    
    @Autowired
    private DeviceService deviceService;

    @GetMapping("/getAllDevices")
    public List<Device2> getAllDevices(){
        return deviceService.getAllDevices();
    }

    @GetMapping("/getDeviceByID/{id}")
    public Device2 getDeviceByID(@PathVariable Long id){
        return deviceService.getDeviceByID(id);
    }

    @PostMapping("/addDevice")
    public Device2 addDevice(@RequestBody Device2 device){
        return deviceService.addDevice(device);
    }

    @GetMapping("/saveDevices")
    public String saveDevices() {
        deviceService.saveDevicesFromFlaskApi();
        return "Devices saved successfully";
    }


}
