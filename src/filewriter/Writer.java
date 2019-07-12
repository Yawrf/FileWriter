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
    
    private String baseFilepath = "/Program Files/Yawrf/";
    private String filepath = "";
    private ArrayList<String> extensions = new ArrayList<>();
    
    public Writer(String folder) {
        baseFilepath += folder + "/";
        buildFilepath();
    }
    
    
    /**
     * Do Not include ".txt" at end of filename
     * @param object
     * @param name
     * @return 
     */
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
    
    /**
     * Do not include ".txt" at end of filename
     * @param name
     * @return 
     */
    public boolean deleteFile(String name) {
        File file = new File(filepath+name+".txt");
        return file.delete();
    }
    
    public boolean deleteFolder(String name) {
        File[] contents = new File(filepath + name).listFiles();
        if(contents != null) {
            for(File f : contents) {
                deleteFolder(f);
            }
        }
        File f = new File(filepath+name);
        return f.delete();
    }
    
    private boolean deleteFolder(File f) {
        File[] contents = f.listFiles();
        if(contents != null) {
            for(File g : contents) {
                deleteFolder(g);
            }
        }
        return f.delete();
    }
    
    public boolean renameFolder(String folderName, String newName) {
        File f = new File(filepath+folderName);
        return f.renameTo(new File(filepath+newName));
    }
    
    /**
     * Adds a given String 's' to the end of the filepath
     * Automatically includes '/', do not include
     * 
     * Example: .../Yawrf to .../Yawrf/Example
     * @param s 
     */
    public void moveDownFolder(String s) {
        if(!s.equals("")) {
            extensions.add(s);
            buildFilepath();
        }
    }
    
    /**
     * Moves up one folder
     * 
     * Example: .../Yawrf/Example to .../Yawrf
     */
    public void moveUpFolder() {
        extensions.remove(extensions.size() - 1);
        buildFilepath();
    }
    
    public void buildFilepath() {
        filepath = baseFilepath;
        for(String s : extensions) {
            filepath += s + "/";
        }
    }
    
    public void resetFilepath() {
        extensions.clear();
        buildFilepath();
    }
    
    public String getFilepath() {
        return filepath;
    }
    
    public ArrayList<String> listFiles() {
        File path = new File(filepath);
        path.mkdirs();
        ArrayList<String> output = new ArrayList<>();
        for(String s : path.list()) {
            if(s.contains(".txt")) {
                String t = s.replace(".txt", "");
                output.add(t);
            }
        }
        return output;
    }
    
}