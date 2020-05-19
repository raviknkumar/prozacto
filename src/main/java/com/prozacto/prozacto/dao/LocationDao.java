package com.prozacto.prozacto.dao;

import com.prozacto.prozacto.Entity.Location.Location;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationDao extends JpaRepository<Location, Integer> {

}