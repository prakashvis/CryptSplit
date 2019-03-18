package com.mini.process;

import com.mini.crypt.Crypto;
import com.mini.dao.FilesDBdao;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

//Splits the uploaded file, encrypts and store them in the server
//Generates map file and add it to Database

public class Splitter {

    File file;
    String fileName;
    int user_id;
    String ip;
    String encKey;
    public static int fixed = 10485760;
    static byte nl[] = System.getProperty("line.separator").getBytes();

    //"172.22.107.131"
    
    Splitter(File file, String fileName, int user_id, String ip, String encKey) {
        this.file = file;
        this.fileName = fileName;
        this.user_id = user_id;
        this.ip=ip;
        this.encKey = encKey;
        
    }

    public void splitFile() throws IOException {
        
        byte buffer[] = new byte[fixed];
        
        FileOutputStream mos;
        
        String chunkPath = ip + File.separator + "chunks";
        String mapPath = ip + File.separator + "Maps";
        File pathv = new File(chunkPath);
        
        if(!pathv.exists()) pathv.mkdirs();
        pathv = new File(mapPath);
        
        if(!pathv.exists()) pathv.mkdirs();
        
        try (FileInputStream fis = new FileInputStream(file)) {
            
            int i = 1;            
            File mapFile = new File(mapPath + File.separator + fileName.substring(0, fileName.lastIndexOf('.')) + ".map");
            mapFile.createNewFile();
            mos = new FileOutputStream(mapFile, true);
            
            System.out.println("Password is " + encKey);

            Crypto crypto = new Crypto(encKey);
            crypto.encryptInit();
            
            mapInit(mos, crypto);
            
            while (fis.read(buffer) != -1) {

                String name = uniqueName(chunkPath);
                String chunk = chunkPath + File.separator + name;
                File f = new File(chunk);
                
                f.createNewFile();
                try (FileOutputStream fos = new FileOutputStream(f)) {
                    crypto.encryptFile(buffer, fos);
                    
                    mos.write((i + "@" + name).getBytes());
                    mos.write(nl);
                    
                    i++;
                    buffer = new byte[fixed];
                }

            }   
            FilesDBdao.updateDB(mapFile.getName(), mapFile.getName(), user_id, file.length());
        }
        mos.close();
    }
    
    
    static String getAlphaNumericString(int n){ 
  
        int lowerLimit = 97;    
        Random random = new Random(); 
        StringBuilder r = new StringBuilder(n); 
  
        for (int i = 0; i < n; i++) { 
  
            int nextRandomChar = lowerLimit + (int)(random.nextFloat() * 26);   
            r.append((char)nextRandomChar); 
        }
  
        return r.toString(); 
    }
    
    static String uniqueName(String path){
                
        String chunkName = getAlphaNumericString(20);        
        String newpath = path + File.separator + chunkName;
        
        while((new File(newpath)).exists()){
            
            chunkName = getAlphaNumericString(20);
            newpath = path + File.separator + chunkName;
        }
        
        return chunkName;
    }
    
    void mapInit(FileOutputStream mos, Crypto crypto) throws IOException{
        
        mos.write(fileName.getBytes());
        mos.write(nl);
        mos.write(crypto.getSalt());
        mos.write(nl);
        mos.write(crypto.getIv());
        mos.write(nl);
    }
    
}
