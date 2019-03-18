/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mini.download;

import com.mini.crypt.Crypto;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 *
 * @author Prakash
 */
public class Merger {
    
    static ArrayList<String>mapList;
    
    public static String merge(String mapPath, String appPath, String deckey) throws FileNotFoundException, IOException{
        
        File mapFile = new File(mapPath);
        String downloadName;
        
        String fname = getNameFromMap(mapFile);
        byte[] salt = getSalt(mapFile);
        byte[] iv = getIv(mapFile);
        
        Crypto crypto = new Crypto(deckey);
        
        if(salt == null) System.out.println("Salt is null");
        
        if(iv == null) System.out.println("iv is null");
        
        crypto.decryptInit(salt, iv);
        
        String downloadPath = appPath + File.separator + "Merged";
        
        File mergLoc = new File(downloadPath);
        if(!mergLoc.exists()){
            mergLoc.mkdirs();
        }
        
        downloadName = fname;
        getMapList(mapFile, appPath);
        downloadPath += File.separator + downloadName;
        File file = new File(downloadPath);
        
        new PrintWriter(file).close();
        file.deleteOnExit();
        
        int size = mapList.size();
        ThreadProcess tp[] = new ThreadProcess[size];
        int i = 0;
                
        for(String s : mapList){
            tp[i] = new ThreadProcess(file, s, deckey, crypto);
            i++;
        }
        
        for (int k = 0; k < i; k += 8) {
            int t = k + 8;
            for (int j = k; j < t && j < i; j++) {
                tp[j].start();
            }

            for (int j = k; j < i && j < t; j++) {
                try {
                    tp[j].join();
                }
                catch (InterruptedException e) {
                }
            }

            for (int j = k; j < i && j < t; j++) {
                try {
                    tp[j].writeToFile();
                }
                catch (IOException e) {
                }
            }
        }
        
        return downloadName;
    }
    
    public static void getMapList(File map, String appPath) {

        if (map.exists()) {
            try {
                
                mapList = new ArrayList<>();
                try (FileInputStream fis = new FileInputStream(map);
                        BufferedReader br = new BufferedReader(new InputStreamReader(fis))) {
                    
                    String line;
                    for(int i = 0; i < 3; i++)
                        br.readLine();
                    while ((line = br.readLine()) != null){
                        line = appPath + "chunks" + File.separator + line.substring(line.indexOf('@') + 1);
                        mapList.add(line);
                    }
                }
            }
            catch (IOException e) {
                System.out.println(e);
            }
        }
    }   
    
    public static String getNameFromMap(File map) {
        
        if (map.exists()) {
            try (FileInputStream fis = new FileInputStream(map);
                    BufferedReader br = new BufferedReader(new InputStreamReader(fis))) {
                
                String name = br.readLine();
                return name;
            }
            catch (IOException e) {
                System.out.println(e);
            }
        }
        return null;
    }
    
    public static byte[] getSalt(File map){
        
        if (map.exists()) {
            try (FileInputStream fis = new FileInputStream(map);
                    BufferedReader br = new BufferedReader(new InputStreamReader(fis))) {
                
                br.readLine();
                byte[] salt = new byte[8];
                fis.read(salt);
                
                return salt;
            }
            catch (IOException e) {
                System.out.println(e);
            }
        }
        return null;
    }
    
    public static byte[] getIv(File map){
        
        if (map.exists()) {
            try (FileInputStream fis = new FileInputStream(map);
                    BufferedReader br = new BufferedReader(new InputStreamReader(fis))) {
                
                br.readLine();
                byte[] iv = new byte[16];
                fis.read(iv);
                
                return iv;
            }
            catch (IOException e) {
                System.out.println(e);
            }
        }
        return null;
    }
}
