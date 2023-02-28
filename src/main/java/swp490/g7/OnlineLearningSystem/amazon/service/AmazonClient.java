package swp490.g7.OnlineLearningSystem.amazon.service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import swp490.g7.OnlineLearningSystem.errorhandling.ErrorTypes;
import swp490.g7.OnlineLearningSystem.errorhandling.OnlineLearningException;
import swp490.g7.OnlineLearningSystem.utilities.ExtensionFileUtility;

import javax.annotation.PostConstruct;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AmazonClient {
    private static final Logger logger = LogManager.getLogger(AmazonClient.class);

    private AmazonS3 s3client;

    @Value("${amazonProperties.endpointUrl}")
    private String endpointUrl;
    @Value("${amazonProperties.bucketName}")
    private String bucketName;
    @Value("${amazonProperties.accessKey}")
    private String accessKey;
    @Value("${amazonProperties.secretKey}")
    private String secretKey;
    @Value("${amazonProperties.region}")
    private String region;

    @PostConstruct
    private void initializeAmazon() {
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
        this.s3client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.AP_SOUTHEAST_1)
                .build();
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convertFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convertFile);
        fos.write(file.getBytes());
        fos.close();
        return convertFile;
    }

    public String generateMultipartFileName(MultipartFile multiPart) {
        return new Date().getDay() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
    }

    public String generateFileName(File file) {
        return new Date().getDay() + "-" + file.getName().replace(" ", "_");
    }

    /**
     * Upload file into AWS S3
     *
     * @param file
     * @return String
     */
    public String uploadFile(Object file) {
        String fileName = null;
        try {
            ObjectMetadata metadata = new ObjectMetadata();
            if (file instanceof File) {
                InputStream inputStream = new FileInputStream((File) file);
                fileName = generateFileName((File) file);
                metadata.setContentLength(((File) file).length());
                s3client.putObject(bucketName, fileName, inputStream, metadata);
            }
            if (file instanceof MultipartFile) {
                fileName = generateMultipartFileName((MultipartFile) file);
                metadata.setContentLength(((MultipartFile) file).getSize());
                s3client.putObject(bucketName, fileName, ((MultipartFile) file).getInputStream(), metadata);
            }
            return ExtensionFileUtility.generateImageUrl(bucketName, region, fileName);
        } catch (AmazonServiceException serviceException) {
            logger.info("AmazonServiceException: " + serviceException.getMessage());
            throw serviceException;
        } catch (AmazonClientException clientException) {
            logger.info("AmazonClientException Message: " + clientException.getMessage());
            throw clientException;
        } catch (IOException e) {
            logger.info("Convert file failed!");
            throw new OnlineLearningException(ErrorTypes.FILE_UPLOAD_FAILED);
        }
    }

    /**
     * Downloads file using amazon S3 client from S3 bucket
     *
     * @param fileName
     * @return ByteArrayOutputStream
     */
    public ByteArrayOutputStream objectToByArray(String fileName) {
        try {
            S3Object s3object = s3client.getObject(new GetObjectRequest(bucketName, fileName));
            InputStream is = s3object.getObjectContent();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            int len;
            byte[] buffer = new byte[4096];
            while ((len = is.read(buffer, 0, buffer.length)) != -1) {
                outputStream.write(buffer, 0, len);
            }
            return outputStream;
        } catch (IOException ioException) {
            logger.error("IOException: " + ioException.getMessage());
        } catch (AmazonServiceException serviceException) {
            logger.info("AmazonServiceException Message:    " + serviceException.getMessage());
            throw serviceException;
        } catch (AmazonClientException clientException) {
            logger.info("AmazonClientException Message: " + clientException.getMessage());
            throw clientException;
        }
        return null;
    }

    public ResponseEntity<byte[]> downloadFile(String fileName) {
        ByteArrayOutputStream downloadInputStream = objectToByArray(fileName);
        return ResponseEntity.ok()
                .contentType(contentType(fileName))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(downloadInputStream.toByteArray());
    }

    /**
     * Deletes file from AWS S3 bucket
     *
     * @param fileName
     * @return
     */
    public String deleteFile(final String fileName) {
        s3client.deleteObject(bucketName, fileName);
        return "Deleted File: " + fileName;
    }

    /**
     * Get all files from S3 bucket
     *
     * @return
     */
    public List<String> listFiles() {
        ListObjectsRequest listObjectsRequest =
                new ListObjectsRequest()
                        .withBucketName(bucketName);
        List<String> keys = new ArrayList<>();
        ObjectListing objects = s3client.listObjects(listObjectsRequest);
        while (true) {
            List<S3ObjectSummary> objectSummaries = objects.getObjectSummaries();
            if (objectSummaries.size() < 1) {
                break;
            }
            for (S3ObjectSummary item : objectSummaries) {
                if (!item.getKey().endsWith("/"))
                    keys.add(item.getKey());
            }
            objects = s3client.listNextBatchOfObjects(objects);
        }
        return keys;
    }

    public void multipleFileUpload(List<MultipartFile> files) {
        files.forEach(f -> uploadFile(f));
    }

    private MediaType contentType(String filename) {
        String[] fileArrSplit = filename.split("\\.");
        String fileExtension = fileArrSplit[fileArrSplit.length - 1];
        switch (fileExtension) {
            case "txt":
                return MediaType.TEXT_PLAIN;
            case "png":
                return MediaType.IMAGE_PNG;
            case "jpg":
                return MediaType.IMAGE_JPEG;
            default:
                return MediaType.APPLICATION_OCTET_STREAM;
        }
    }
}