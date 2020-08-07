package br.com.finalcraft.plupdater;

import br.com.finalcraft.plupdater.data.PluginJar;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;

import java.io.File;
import java.util.*;

@IFMLLoadingPlugin.Name("PLUpdater")
@IFMLLoadingPlugin.MCVersion("1.7.10")
public class PLUpdater implements IFMLLoadingPlugin {

    private final File updatesFolder;
    private final File pluginsFolder;

    private File getOrCreateFolder(String folderName){
        File file = new File(folderName);
        if (!file.exists()) {
            LogManager.getLogger().warn("[PLUpdater] Creating " + folderName + " folder.");
            if (!file.mkdirs()) {
                LogManager.getLogger().warn("[PLUpdater] Unable to create the folder " + file.getAbsolutePath());
                throw new RuntimeException("WTF if this manito? I cant create a new Folder?!?");
            }
        }
        return file;
    }

    public PLUpdater() {
        LogManager.getLogger().warn(" ");
        LogManager.getLogger().warn("[PLUpdater] Looking for plugin Updates at ./PLUpdates/ folder!");
        LogManager.getLogger().warn(" ");

        this.updatesFolder  = getOrCreateFolder("PLUpdates");
        this.pluginsFolder  = getOrCreateFolder("plugins");
        this.modsFolder     = getOrCreateFolder("mods");

        if (updatesFolder.listFiles().length == 0){ //No Plugins to Update
            LogManager.getLogger().warn("[PLUpdater] No PLUpdates found!");
            return;
        }

        updateBukkitPlugins();
    }

    private void updateBukkitPlugins(){
        List<PluginJar> updates = getAllPluginJarsFrom(updatesFolder);
        List<PluginJar> installeds = getAllPluginJarsFrom(pluginsFolder);
        for (PluginJar update : updates) {
            boolean replaced = false;
            for (PluginJar installed : installeds) {
                if (update.getPluginIdentifier().equalsIgnoreCase(installed.getPluginIdentifier())){

                    if (!installed.deleteFile()){
                        throw new RuntimeException("Cant Delete the old plugin! Are you using Windows?");
                    }
                    if (!update.moveToFolder(pluginsFolder)){
                        throw new RuntimeException("Cant move the new plugin! Are you using Windows?");
                    }
                    LogManager.getLogger().warn("[PLUpdater] Replacing old plugin [" + installed.getFile().getName() + "] with " + update.getFile().getName());
                    replaced = true;
                    break;
                }
            }
            if (!replaced){
                LogManager.getLogger().warn("[PLUpdater] Moving [" + update.getFile().getName() + "] to plugins folder (No Updated Done)");
                if (!update.moveToFolder(pluginsFolder)){
                    throw new RuntimeException("Cant move the new plugin! Are you using Windows?");
                }
            }
        }
    }

    private List<PluginJar> getAllPluginJarsFrom(File directory){
        List<PluginJar> pluginJars = new ArrayList<>();
        for (File pluginFile : FileUtils.listFiles(directory, new String[]{"jar"}, false)) {
            try {
                PluginJar pluginJar = new PluginJar(pluginFile);
                if (pluginJar.getPluginIdentifier() != null){
                    pluginJars.add(pluginJar);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return pluginJars;
    }

    @Override
    public String[] getASMTransformerClass() {
        return new String[0];
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {

    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}
