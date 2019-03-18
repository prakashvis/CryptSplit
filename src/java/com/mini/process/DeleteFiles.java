
package com.mini.process;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

//delete all the chunks from server when user wants to delete them
class DeleteFiles {

    static void deleteFilesFromServer(String fileLocation,String path) throws IOException {
        
        File file = new File(path+fileLocation);
        if(!file.exists()) return;
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while((line = br.readLine())!=null){
            String temp[] = line.split("@");
            File tempFile = new File(path +"chunks" + File.separator +temp[1]);
            System.out.print(path + "chunks" + File.separator + temp[1]);
            if(tempFile.exists())tempFile.delete();
        }
        br.close();
        file.delete();
    }
}
