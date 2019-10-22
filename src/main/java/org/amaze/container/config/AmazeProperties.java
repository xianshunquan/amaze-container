
package org.amaze.container.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AmazeProperties {
    private static Properties properties = new Properties();
    private final static String CONFIG_FILE = "amaze.properties";

    static {
        loadProperties();
    }

    public static String getProperty(String name) {
        return properties.getProperty(name);
    }

    /**
     * Load properties.
     */
    private static void loadProperties() {
        String configFile = System.getProperty("amaze.config", CONFIG_FILE);
        InputStream is = AmazeProperties.class.getClassLoader().getResourceAsStream(configFile);
        if (is == null) {
            System.out.println("fail to load " + configFile);
            return;
        }
        try {
            properties.load(is);
        } catch (Throwable t) {
            handleThrowable(t);
            t.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }

    private static void handleThrowable(Throwable t) {
        if (t instanceof ThreadDeath) {
            throw (ThreadDeath) t;
        }
        if (t instanceof VirtualMachineError) {
            throw (VirtualMachineError) t;
        }
    }
}
