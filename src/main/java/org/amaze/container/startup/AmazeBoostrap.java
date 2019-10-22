package org.amaze.container.startup;

import org.amaze.container.config.AmazeConfigure;
import org.amaze.container.module.ModuleContainer;
import org.amaze.container.utils.CollectionUtils;

import java.util.Set;

/**
 * 功能描述
 *
 * <p>
 * <a href="AmazeBoostrap.java"><i>View Source</i></a>
 *
 * @author shunquan.xian@mljr.com
 * @version 1.0
 * @since 1.0
 */
public class AmazeBoostrap {
    public static void main(String[] args) {
        ModuleContainer moduleContainer = new ModuleContainer();
        moduleContainer.start();
        Set<String> usedModuleSet = CollectionUtils.toSet(AmazeConfigure.getUsedModules());
        for (String module : AmazeConfigure.getModules()) {
            if (usedModuleSet != null && !usedModuleSet.contains(module)) {
                continue;
            }
            new Thread(module) {
                @Override
                public void run() {
                    moduleContainer.startModule(module,args);
                }
            }.start();
        }
    }
}
