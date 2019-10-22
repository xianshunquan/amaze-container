/*
 * Copyright (C), 2015-2016, 深圳天道计然金融服务有限公司
 * File Name: @(#)AmazeConfigure.java
 * Encoding UTF-8
 * Author: shunquan.xian@mljr.com
 * Version: 1.0
 * Date: 2019年8月31日
 */
package org.amaze.container.config;

import org.amaze.container.exception.ModuleLoadException;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;

/**
 * 功能描述
 *
 * <p>
 * <a href="AmazeConfigure.java"><i>View Source</i></a>
 *
 * @author shunquan.xian@mljr.com
 * @version 1.0
 * @since 1.0
 */
public class AmazeConfigure {
    private final static String sharedLoader = "shared.loader";

    private final static String modulePath = "module.path";
    private final static String usedModule = "module.used";

    public static String getSharedLoader() {
        return AmazeProperties.getProperty(sharedLoader);
    }

    public static String getModuleRoot() {
        return AmazeProperties.getProperty(modulePath);
    }

    public static String[] getUsedModules() {
        String modules = AmazeProperties.getProperty(usedModule);
        if (StringUtils.isBlank(modules)) {
            return null;
        }
        return modules.split(",");
    }

    public static String[] getModules() {
        String moduleRoot = getModuleRoot();
        File moduleDir=null;
        if(!(moduleDir=new File(moduleRoot)).exists()){
            URL moduleRootUrl = AmazeProperties.class.getClassLoader().getResource(moduleRoot);
            moduleDir = new File(moduleRootUrl.getFile());
        }
        if(moduleDir==null){
            throw new ModuleLoadException(new FileNotFoundException(moduleRoot));
        }
        File[] files = moduleDir.listFiles();
        if (files == null || files.length <= 0) {
            return new String[0];
        }
        String[] fileNames = new String[files.length];
        for (int i = 0; i < files.length; i++) {
            fileNames[i] = files[i].getName();
        }
        return fileNames;
    }
}
