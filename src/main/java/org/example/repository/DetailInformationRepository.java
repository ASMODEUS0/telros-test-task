package org.example.repository;

import org.example.entity.DetailInformation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetailInformationRepository extends CrudRepository<DetailInformation, Long> {
}
