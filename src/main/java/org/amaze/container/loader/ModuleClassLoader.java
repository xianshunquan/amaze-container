/* 
 * Copyright (C), 2015-2016, 深圳天道计然金融服务有限公司
 * File Name: @(#)ModuleClassLoader.java
 * Encoding UTF-8
 * Author: shunquan.xian@mljr.com
 * Version: 1.0
 * Date: 2019年8月31日
 */
package org.amaze.container.loader;

import java.net.URL;
import java.net.URLClassLoader;

/** 
 * 功能描述
 * 
 * <p>
 * <a href="ModuleClassLoader.java"><i>View Source</i></a>
 * 
 * @author shunquan.xian@mljr.com
 * @version 1.0
 * @since 1.0 
*/
public class ModuleClassLoader extends URLClassLoader{
    /**
     * @param urls
     */
    public ModuleClassLoader(URL[] urls) {
        super(urls);
    }
    /**
     * @param urls
     */
    public ModuleClassLoader(URL[] urls, ClassLoader loader) {
        super(urls,loader);
    }
    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        return loadClass(name, false);
    }
    @Override
    protected Class<?> loadClass(String name, boolean resolve)
            throws ClassNotFoundException {
        return super.loadClass(name, resolve);
    }
}
