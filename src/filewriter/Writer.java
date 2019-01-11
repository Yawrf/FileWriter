/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filewriter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author rewil
 */
public class Writer {
    
    private String filepath = "/Program Files/Yawrf/";
    
    public Writer(String folder) {
        filepath += folder + "/";
    }
    
    public boolean writeObject(Serializable object, String name) {
        
        try {
            File file = new File(filepath);
            file.mkdirs();
            file = new File(filepath+name+".txt");
            FileOutputStream f = new FileOutputStream(file);
            ObjectOutputStream o = new ObjectOutputStream(f);
            
            o.writeObject(object);
            
            o.close();
            f.close();
            
        } catch(FileNotFoundException e) {
            System.out.println("File Not Found - Write");
            return false;
        } catch(IOException e) {
            System.out.println("Error Initializing Stream - Write");
            return false;
        }
        
        return true;
    }
    
    /**
     * Do not include ".txt" at end of filename
     * @param name
     * @return 
     */
    public Object readObject(String name) {
        Object output = null;
        try {
            File file = new File(filepath+name+".txt");
            FileInputStream f = new FileInputStream(file);
            ObjectInputStream o = new ObjectInputStream(f);
            
            output = o.readObject();
            
            o.close();
            f.close();
            
        } catch(FileNotFoundException e) {
            System.out.println("File Not Found - Read");
            return null;
        } catch(IOException e) {
            System.out.println("Error Initializing Stream - Read");
            return null;
        } catch(ClassNotFoundException e) {
            System.out.println("Class Not Found - Read");
            return null;
        }
        
        return output;
    }
    
    public boolean deleteFile(String name) {
        File file = new File(filepath+name);
        return file.delete();
    }
    
    public ArrayList<String> listFiles() {
        File path = new File(filepath);
        ArrayList<String> output = new ArrayList<>();
        for(String s : path.list()) {
            String t = s.replace(".txt", "");
            output.add(t);
        }
        return output;
    }
    
}