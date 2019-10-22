package org.amaze.container.module;

import org.amaze.container.exception.ModuleLoadException;
import org.amaze.container.utils.Assert;
import org.amaze.container.utils.ReflectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

/**
 * @author : shunqxian
 * @date : 2019-10-05
 */
public class MainClass {
    private Class clazz;
    private Method mainMethod;
    private final static String MAIN_NAME = "main";

    public MainClass(Class clazz) {
        Assert.notNull(clazz,"Class clazz require not null");
        this.clazz = clazz;
        this.mainMethod = ReflectionUtils.findMethod(clazz, MAIN_NAME, String[].class);
        Assert.notNull(this.mainMethod,String.format("class %s does not have main method with String[] parameter",clazz.getName()));
    }
    public static MainClass load(String moduleRoot,String module,ClassLoader loader){
        String modulePath = moduleRoot.concat(module);
        File moduleDir=new File(modulePath);
        if(!moduleDir.exists()){
            throw new ModuleLoadException(new FileNotFoundException(modulePath));
        }
        if(!moduleDir.isDirectory()){
            throw new ModuleLoadException(String.format("modulePath %s is not directory",modulePath));
        }
        for(File file:moduleDir.listFiles()){
            if(!file.getName().endsWith(".jar")){
                continue;
            }
            try {
                JarFile jarfile = new JarFile(file);
                Manifest manifest = jarfile.getManifest();
                String mainClazz=manifest.getMainAttributes().getValue("Main-Class");
                if(StringUtils.isNotBlank(mainClazz)){
                    return new MainClass(loader.loadClass(mainClazz));
                }
            }catch (IOException ex){
                ex.printStackTrace();
                throw new ModuleLoadException(ex);
            }catch (ClassNotFoundException ex){
                ex.printStackTrace();
                throw new ModuleLoadException(ex);
            }
        }
        return null;
    }
    public void main(String[] args) {
        try {
            this.mainMethod.invoke(this.clazz, new Object[]{args});
        }catch (Exception ex){
            throw new ModuleLoadException(String.format("class %s start error",clazz.getName()),ex);
        }
    }

}
