package org.example.metabox.trailer;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class TrailerService {

    private final Path videoLocation = Paths.get("D:/workspace/java_lec/project-metabox/upload");

    public Resource getVideoRes(String filename) {
        try {
            System.out.println(9);
            Path filePath = videoLocation.resolve(filename).normalize();
            System.out.println(10);
            Resource resource = new UrlResource(filePath.toUri());
            System.out.println(11);

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("파일을 찾을 수 없습니다: " + filename);
            }
        } catch (MalformedURLException ex) {
            throw new RuntimeException("파일을 불러오는 데 실패했습니다: " + filename, ex);
        }
    }
}
