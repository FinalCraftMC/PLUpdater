package br.com.finalcraft.plupdater.data;

import org.apache.logging.log4j.LogManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ModJar extends JarFile{

    private String modIDentifier;

    public String getModIDentifier() {
        return modIDentifier;
    }

    public ModJar(File bukkitPlugin) {
        super(bukkitPlugin);
        try {
            ZipFile zipFile = new ZipFile(bukkitPlugin);
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            //Plugin Data
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                if (isPluginYML(entry.getName())) {
                    InputStream stream = zipFile.getInputStream(entry);
                    BufferedReader buf = new BufferedReader(new InputStreamReader(stream));
                    String line = buf.readLine();
                    while(line != null){
                        if (line.startsWith("name:")){
                            modIDentifier = line.replace("name:","").trim();
                            break;
                        }
                        line = buf.readLine();
                    }
                }
            }
        } catch (Exception e) {
            LogManager.getLogger().warn("[PLUpdater] Unable to read content from \'" + bukkitPlugin.getAbsolutePath() + "\'.");
            e.printStackTrace();
        }
    }

    private boolean isPluginYML(String name) {
        String splitName = ((name.contains("/")) ? name.substring(name.lastIndexOf("/")).replace("/", "") : name); // Caso o zip esteja usando o "\" esse zip está quebrado, já que o padrão é o "/".
        return splitName.equals("plugin.yml");
    }

    @Override
    public String toString() {
        return "PluginJar{" +
                "pluginIdentifier='" + modIDentifier + '\'' +
                ", file=" + file +
                '}';
    }
}
