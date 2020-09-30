package com.formation.kartina_spring.repositories;

import com.formation.kartina_spring.enums.RoleUtilisateur;
import com.formation.kartina_spring.models.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserTypeRepository extends JpaRepository<UserType, Long> {
    UserType findByUserEnum(RoleUtilisateur roleUtilisateur);
}
