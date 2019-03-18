
package com.mini.download;
/*
Author: prakash. u

*/
import com.mini.crypt.Crypto;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class ThreadProcess extends Thread {
    
    byte buffer[];
    FileOutputStream fos;
    String chunk;
    File file;
    String deckey;
    Crypto crypto;
    
    ThreadProcess(File file, String chunk, String deckey, Crypto crypto){
        
        this.file =file;
        this.chunk = chunk;
        this.deckey = deckey;
        this.crypto = crypto;
    }
    
    @Override
    public void run() {
        
        try {
            
            System.out.println("Looking for " + chunk);
            fos = new FileOutputStream(file, true);
            buffer = Files.readAllBytes(Paths.get(chunk));
            buffer = crypto.decryptFile(buffer);
            
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
    
    public void writeToFile() throws IOException{
        
        if(buffer != null)
            fos.write(buffer);
        fos.flush();
        fos.close();
    }
    
}
