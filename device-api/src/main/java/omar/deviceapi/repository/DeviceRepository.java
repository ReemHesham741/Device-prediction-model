package omar.deviceapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import omar.deviceapi.model.Device2;

@Repository
public interface DeviceRepository extends JpaRepository<Device2, Long>{

}
