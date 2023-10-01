package com.yachtrent.main.yacht.photo.services;

import com.yachtrent.main.yacht.photo.YachtPhoto;
import com.yachtrent.main.yacht.photo.YachtPhotoRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class YachtPhotoService {
    private final YachtPhotoRepository yachtPhotoRepository;

    public void saveAllYachtPhotoByYacht(List<YachtPhoto> photos){
        for (YachtPhoto yachtPhoto: photos) {
            yachtPhotoRepository.save(yachtPhoto);
        }
    }

    @Transactional
    public void removeAllPhotosByYachtId(long id){
        yachtPhotoRepository.deleteAllByYachtId(id);
    }
}
