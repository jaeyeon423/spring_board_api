package farm.board.service.file;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;

@Service
public class LocalFileService implements FileService{

    @Value("${upload.image.location}")
    private String location;

    @PostConstruct
    void postConstruct(){
        File dir = new File(location);
        if(!dir.exists()){
            dir.mkdir();
        }
    }

    @Override
    public void upload(MultipartFile file, String filename) {
        try {
            file.transferTo(new File(location + filename));
        } catch (IOException e) {
            System.out.println("e = " + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(String filename) {
        new File(location + filename).delete();
    }
}
