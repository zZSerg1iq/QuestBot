package com.zinoviev.sandbox.data.repository;

import com.zinoviev.sandbox.data.entity.quest.GeoPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeoPointRepository extends JpaRepository<GeoPoint, Long> {
    GeoPoint getGeoPointByNodeId(Long nodeId);
}
