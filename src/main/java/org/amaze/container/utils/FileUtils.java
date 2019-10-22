package org.amaze.container.utils;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author : shunqxian
 * @date : 2019-10-08
 */
public class FileUtils {
    public static URL toURL(File file) {
        try {
            return file.toURI().toURL();
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }
}
