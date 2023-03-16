package Maswillaeng.MSLback.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class AwsS3Service {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;


    public  Map<String,String> AwsUploadImage(MultipartFile imageFile) throws IOException {
        byte[] imageData = imageFile.getBytes();
        UUID uuid = UUID.randomUUID();
        String uploadUrl = "";
        String savedFileName = uuid.toString() + "_" + imageFile.getOriginalFilename();
        try {
            uploadUrl = s3uploadImage(savedFileName, imageFile);
        }catch (Exception e){
            throw  new FileUploadException("업로드 실패");
        }
        Map<String,String> imagePath = new HashMap<>();
        imagePath.put("img",uploadUrl);

        return imagePath;
    }

    public String s3uploadImage(String savedFileName,MultipartFile imageFile) throws IOException {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(imageFile.getSize());

        amazonS3Client.putObject(new PutObjectRequest(bucket, savedFileName,imageFile.getInputStream(),metadata));

              return amazonS3Client.getUrl(bucket,savedFileName).toString();
    }

}
