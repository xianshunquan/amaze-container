package org.amaze.container.loader;

import org.amaze.container.utils.FileUtils;

import java.io.File;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * @author : shunqxian
 * @date : 2019-10-04
 */
public class ClassLoaderFactory {
    public static ModuleClassLoader createModuleLoader(String moduleRoot, String module, ClassLoader parent) {
        String modulePath = moduleRoot.concat(module);
        return createLoader(modulePath, parent);
    }

    public static SharedClassLoader createSharedLoader(String path, ClassLoader parent) {
        if (parent == null) {
            parent = ClassLoader.getSystemClassLoader();
        }
        URL url = ClassLoaderFactory.class.getClassLoader().getResource(path);
        List<URL> urls = url != null ? findAllFileUrl(url) : null;
        if (urls == null || urls.isEmpty()) {
            return null;
        }
        return new SharedClassLoader(urls.toArray(new URL[0]), parent);
    }

    public static ModuleClassLoader createLoader(String path, ClassLoader parent) {
        if (parent == null) {
            parent = ClassLoader.getSystemClassLoader();
        }
        URL url;
        File dir = new File(path);
        if (dir.exists()) {
            url = FileUtils.toURL(dir);
        } else {
            url = ClassLoaderFactory.class.getClassLoader().getResource(path);
        }
        List<URL> urls = url != null ? findAllFileUrl(url) : new LinkedList<>();
        return new ModuleClassLoader(urls.toArray(new URL[0]), parent);
    }

    private static List<URL> findAllFileUrl(URL dir) {
        List<URL> urls = new LinkedList<>();
        Stack<File> stack = new Stack<File>();
        stack.push(new File(dir.getFile()));
        File cur = null;
        while (stack.size() > 0) {
            cur = stack.pop();
            if (cur.isDirectory()) {
                File[] subFiles = cur.listFiles();
                if (subFiles != null && subFiles.length > 0) {
                    for (int i = subFiles.length - 1; i >= 0; i--) {
                        stack.push(subFiles[i]);
                    }
                }
            } else {
                urls.add(FileUtils.toURL(cur));
            }
        }
        return urls;
    }
}
