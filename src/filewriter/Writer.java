/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filewriter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 *
 * @author rewil
 */
public class Writer {
    
    private String baseFilepath = "/Yawrf/";
    private String filepath = "";
    private ArrayList<String> extensions = new ArrayList<>();
    
    public Writer(String directory, String folder) {
        this(folder);
        changeDirectory(directory);
    }
    public Writer(String folder) {
        baseFilepath += folder + "/";
        buildFilepath();
        
        System.out.println("\n>> The FileWriter Library has recently been updated; previous saves made using this will no longer be usable with it.");
        System.out.println(  ">> To make previous files saved under this library retrievable, simply move the folder (usually /Yawrf/) out of Program Files and into the primary directory (usually C:/)\n");
    }
    
    
    /**
     * Do not include ".txt" at end of filename
     * @param object
     * @param name
     * @return 
     */
    public boolean writeObject(Serializable object, String name) {
        
        try {
            File file = new File(filepath);
            file.mkdirs();
            file = new File(filepath+name+".txt");
//            file.createNewFile();
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
            e.printStackTrace();
            return false;
        } catch(Exception e) {
            e.printStackTrace();
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
            e.printStackTrace();
            return null;
        } catch(IOException e) {
            System.out.println("Error Initializing Stream - Read");
            e.printStackTrace();
            return null;
        } catch(ClassNotFoundException e) {
            System.out.println("Class Not Found - Read");
            e.printStackTrace();
            return null;
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
        
        return output;
    }
    
    /**
     * Do not include ".txt" at end of filename
     * @param text
     * @param name
     * @return 
     */
    public boolean writeTextFile(String text, String name) {
        try {
            Files.createDirectories(Paths.get(filepath));
            
            java.io.FileWriter fw = new java.io.FileWriter(filepath+name+".txt");
            BufferedWriter bw = new BufferedWriter(fw);
            
            bw.write(text);
            bw.flush();
            
            bw.close();
        } catch (IOException e) {
            System.out.println("Error Initializing Stream - WriteText");
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    /**
     * Do not include ".txt" nat end of filename
     * @param name
     * @return 
     */
    public String readTextFile(String name) {
        String output = "";
        try {
            File file = new File(filepath + name + ".txt");
            FileInputStream f = new FileInputStream(file);
            
            for(String s : Files.readAllLines(file.toPath())) {
                output += s + '\n';
            }
            
            f.close();
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found - ReadText");
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            System.out.println("Error Initializing Stream - ReadText");
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
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
    
    /**
     * Changes filepath to '/Program Files/<dir>'
     * @param dir 
     */
    public void changeDirectory(String dir) {
        baseFilepath = baseFilepath.substring(0, baseFilepath.lastIndexOf('/'));
        baseFilepath = baseFilepath.substring(baseFilepath.lastIndexOf('/'));
        baseFilepath = "/" + dir + baseFilepath + "/";
        buildFilepath();
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