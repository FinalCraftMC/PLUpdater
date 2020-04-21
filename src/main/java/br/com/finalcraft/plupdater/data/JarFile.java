package br.com.finalcraft.plupdater.data;

import java.io.File;

public abstract class JarFile {

    public File file;

    public File getFile() {
        return file;
    }

    public JarFile(File file) {
        this.file = file;
    }

    public boolean deleteFile(){
        return file.delete();
    }

    public boolean moveToFolder(File newFolder){
        return file.renameTo(new File(newFolder,file.getName()));
    }

}
