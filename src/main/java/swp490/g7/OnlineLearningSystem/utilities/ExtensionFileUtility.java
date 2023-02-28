package swp490.g7.OnlineLearningSystem.utilities;

import com.google.common.io.Files;

public class ExtensionFileUtility {

    public static String getExtensionByGuava(String filename) {
        return Files.getFileExtension(filename);
    }

    public static String generateImageUrl(String awsBucketName, String awsS3Region, String fileName) {
        String domain = String.join(".", awsBucketName, "s3", awsS3Region, "amazonaws", "com");
        return String.format("https://%s/%s", domain, fileName);
    }

}
