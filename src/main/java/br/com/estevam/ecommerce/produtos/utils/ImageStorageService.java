package br.com.estevam.ecommerce.produtos.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class ImageStorageService {

    private Long idProduto;

    @Value("${application.root.path}")
    private String rootPath;

    @Autowired
    private ServletContext servletContext;

    public void store(List<MultipartFile> files, Long id) {
        if (files != null)
            files.stream().forEach(file -> storeOne(file, id));
    }

    public InputStream retrieve(String nome, Long id) {

        Resource resource = new FileSystemResource(filePath(nome, id));

        try {
            return resource.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void storeOne(MultipartFile file, Long id) {
        try {
            Files.createDirectories(Paths.get(getRootPath() + "/" + id));
            file.transferTo(filePath(file.getOriginalFilename(), id));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getRootPath() {
        return rootPath;
    }
    private Path filePath(String nome, Long id) {
        String path = getRootPath() + "/" + id;
        return Paths.get(path).resolve(nome);
    }

}
