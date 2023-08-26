package group.oneonetwo.hotelintelligencesystem.tools;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * @author 文
 * @description: 文件流工具类
 * @date 2022/4/19 16:16
 */
public class FileUtils {
    /**
     * MultipartFile 转 File
     * @param file
     * @throws Exception
     */
    public static File multipartFileToFile(MultipartFile file ) throws Exception {

        File toFile = null;
        if(file.equals("")||file.getSize()<=0){
            file = null;
        }else {
            InputStream ins = null;
            ins = file.getInputStream();
            toFile = new File(file.getOriginalFilename());
            inputStreamToFile(ins, toFile);
            ins.close();
        }
        return toFile;
    }

    /**
     * File 转 MultipartFile
     * @param file
     * @throws Exception
     */
    public static void fileToMultipartFile( File file ) throws Exception {

//        FileInputStream fileInput = new FileInputStream(file);
//        MultipartFile toMultipartFile = new MockMultipartFile("file",file.getName(),"text/plain", IOUtils.toByteArray(fileInput));
//        toMultipartFile.getInputStream();

    }


    public static void inputStreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
