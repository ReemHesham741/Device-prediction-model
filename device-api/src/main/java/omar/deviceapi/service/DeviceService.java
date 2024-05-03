package omar.deviceapi.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import omar.deviceapi.model.Device2;
import omar.deviceapi.repository.DeviceRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;

import java.util.List;



@Service
public class DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Transactional
    public List<Device2> getAllDevices(){
        return deviceRepository.findAll();
    }

    @Transactional
    public Device2 getDeviceByID(Long id){
        return deviceRepository.findById(id).orElse(null);
    }

    @Transactional
    public Device2 addDevice(Device2 device){
        return deviceRepository.save(device);
    }

    public void saveDevicesFromFlaskApi() {
        String flaskApiUrl = "http://localhost:5000/predict"; 
        ParameterizedTypeReference<List<Device2>> responseType = new ParameterizedTypeReference<List<Device2>>() {};
        List<Device2> devices = restTemplate.exchange(flaskApiUrl, HttpMethod.GET, null, responseType).getBody();
        if (devices != null) {
            for (Device2 device : devices) {
                deviceRepository.save(device);
            }
        }
    }
}

