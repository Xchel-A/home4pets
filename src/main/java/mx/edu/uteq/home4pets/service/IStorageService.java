package mx.edu.uteq.home4pets.service;

import org.springframework.web.multipart.MultipartFile;

public interface IStorageService {
    public boolean almacenarApache(MultipartFile ine, MultipartFile cmp, String ineName, String cmpName);
}
