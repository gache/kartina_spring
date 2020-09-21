package com.formation.kartina_spring.services;

import com.formation.kartina_spring.enums.RoleUtilisateur;
import com.formation.kartina_spring.models.UserType;
import com.formation.kartina_spring.repositories.UserTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserTypeService {
    private UserTypeRepository userTypeRepository;

    @Autowired
    public UserTypeService(UserTypeRepository userTypeRepository) {
        this.userTypeRepository = userTypeRepository;
    }

    public void save(UserType userType){
        userTypeRepository.save(userType);
    }

    public List<UserType> findAll(){
        return userTypeRepository.findAll();
    }

    public UserType findByRole(RoleUtilisateur roleUtilisateur){
        return userTypeRepository.findByUserEnum(roleUtilisateur);
    }
}
