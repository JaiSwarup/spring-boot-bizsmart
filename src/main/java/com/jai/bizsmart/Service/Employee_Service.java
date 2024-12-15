package com.jai.bizsmart.Service;

import com.jai.bizsmart.Model.Employee;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface Employee_Service {
    List<Employee> findAll();
    Page<Employee> findAll(Pageable pageable);
    Optional<Employee> findById(Integer id);
    Employee save(Employee employee) throws ChangeSetPersister.NotFoundException;
    void update(Integer id, Employee employee) throws ChangeSetPersister.NotFoundException;
    void removeOrRever(Integer id) throws ChangeSetPersister.NotFoundException;
    Employee login( String username, String password);
}
