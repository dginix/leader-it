package com.example.leaderit.repository;

import com.example.leaderit.dto.StatisticsResponse;
import com.example.leaderit.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>, JpaSpecificationExecutor<Event> {
    default Page<Event> findEventsBySerialNumber(String serialNumber, LocalDateTime startDateAdded, LocalDateTime endDateAdded, Pageable pageable) {
        Specification<Event> spec = Specification.where((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("iotDevice").get("serialNumber"), serialNumber));

        if (startDateAdded != null) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("dateCreated"), startDateAdded));
        }

        if (endDateAdded != null) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("dateCreated"), endDateAdded));
        }

        return findAll(spec, pageable);
    }

    @Query("SELECT NEW com.example.leaderit.dto.StatisticsResponse(e.iotDevice.deviceType, COUNT(e)) " +
            "FROM Event e " +
            "WHERE e.dateCreated BETWEEN :startDate AND :endDate " +
            "GROUP BY e.iotDevice.deviceType")
    List<StatisticsResponse> getStatistics(@Param("startDate") LocalDateTime startDate,
                                           @Param("endDate") LocalDateTime endDate);
}

