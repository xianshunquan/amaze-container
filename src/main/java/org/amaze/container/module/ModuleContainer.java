package org.amaze.container.module;


import org.amaze.container.config.AmazeConfigure;
import org.amaze.container.exception.ModuleException;
import org.amaze.container.exception.ModuleLoadException;
import org.amaze.container.loader.ClassLoaderFactory;
import org.amaze.container.loader.ModuleClassLoader;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author : shunqxian
 * @date : 2019-10-05
 */
public class ModuleContainer implements Service {

    protected ClassLoader sharedClassLoader;

    protected Map<String, ModuleService> moduleServiceMap = new ConcurrentHashMap<>(4);

    @Override
    public void start() {
        this.sharedClassLoader = ClassLoaderFactory.createSharedLoader(AmazeConfigure.getSharedLoader(), null);
    }

    @Override
    public synchronized void stop() {
        moduleServiceMap.entrySet().forEach((Map.Entry<String, ModuleService> entry) -> {
            try {
                entry.getValue().stop();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    public ClassLoader getShardClassLoader() {
        return this.sharedClassLoader;
    }

    public ModuleService loadModule(String module) {
        synchronized (module) {
            if (moduleServiceMap.get(module) != null) {
                throw new ModuleLoadException(String.format("module:%s has loaded", module));
            }
            ModuleClassLoader moduleClassLoader = ClassLoaderFactory.createModuleLoader(AmazeConfigure.getModuleRoot(), module, sharedClassLoader);
            ModuleContext moduleContext = new ModuleContext().setModule(module).setClassLoader(moduleClassLoader);
            ModuleService moduleService = new ModuleService(moduleContext);
            moduleServiceMap.put(module, moduleService);
            return moduleService;
        }
    }

    public void unloadModule(String module) {
        ModuleService moduleService = moduleServiceMap.get(module);
        if (moduleService == null) {
            throw new ModuleException(String.format("module:%s not found", module));
        }
        moduleService.stop();
    }

    public void startModule(String module, String[] args) {
        ModuleService moduleService = moduleServiceMap.get(module);
        if (moduleService == null) {
            moduleService = loadModule(module);
        }
        moduleService.getModuleContext().setArgs(args);
        moduleService.start();
    }

    public void stopModule(String module) {
        ModuleService moduleService = moduleServiceMap.get(module);
        if (moduleService == null) {
            throw new ModuleException(String.format("module:%s not found", module));
        }
        moduleService.stop();
    }
}
