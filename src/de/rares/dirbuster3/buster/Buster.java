package de.rares.dirbuster3.buster;

import com.sun.tools.javac.Main;
import de.rares.dirbuster3.DirBuster3;
import de.rares.dirbuster3.dir.Dir;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class Buster extends Thread {
    File list;
    Dir parent;
    String url;

    public Buster( URL main) {
        this.list = list;
        this.parent = DirBuster3.Maindir;

    }


    public boolean test(URL url){
        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            if(conn.getResponseCode() == 200){
                return true;
            }
            return false;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void run() {
        try {
           String str;
            while ((str = DirBuster3.getSavenext()) != null){
                String  dirnam = str;
                String localUrl = url + "/"+dirnam;
                if(test(new URL(localUrl))){
                    Dir dir = new Dir(dirnam);
                    parent.childs.add(dir);
                    new SubBuster(list,new Dir(dirnam)).run();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
