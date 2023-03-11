package com.example.leaderit.repository;

import com.example.leaderit.entity.IotDevice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Repository
public interface IotDeviceRepository extends JpaRepository<IotDevice, Long>, JpaSpecificationExecutor<IotDevice> {
    Optional<IotDevice> findBySerialNumber(String serialNumber);

    default Page<IotDevice> getAllDevicesFiltered(List<String> deviceType, LocalDateTime startDateAdded, LocalDateTime endDateAdded, Pageable pageable) {
        Specification<IotDevice> spec = Specification.where(null);

        if (deviceType != null && !deviceType.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) -> root.get("deviceType").in(deviceType));
        }

        if (startDateAdded != null) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("dateAdded"), startDateAdded));
        }

        if (endDateAdded != null) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("dateAdded"), endDateAdded));
        }

        return findAll(spec, pageable);
    }

}
