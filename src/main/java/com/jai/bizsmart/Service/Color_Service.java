package com.jai.bizsmart.Service;

import com.jai.bizsmart.Model.Color;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface Color_Service{
    List<Color> findAll();
    Page<Color> findAll(Pageable pageable);
    Optional<Color> findById(Integer id);
    Color save(Color color) throws ChangeSetPersister.NotFoundException;
    void update(Integer id, Color color) throws ChangeSetPersister.NotFoundException;
    void removeOrRever(Integer id) throws ChangeSetPersister.NotFoundException;
    Page<Color> search(String keyword, Pageable pageable);
}
