package org.amaze.container.module;


import org.amaze.container.config.AmazeConfigure;
import org.amaze.container.config.AmazeProperties;
import org.amaze.container.exception.ModuleLoadException;
import org.apache.commons.lang3.StringUtils;

/**
 * @author : shunqxian
 * @date : 2019-10-22
 */
public class ModuleContext {
    /**
     * 模块名称
     */
    private String module;
    /**
     * 模块类加载器
     */
    private ClassLoader classLoader;
    /**
     * 启动类
     */
    private MainClass mainClass;

    private String[] args;

    protected synchronized MainClass findMainClass() {
        if (mainClass != null) {
            return mainClass;
        }
        try {
            String mainClassStr = AmazeProperties.getProperty(module.replace("-", ".").concat(".main"));
            if (StringUtils.isNotBlank(mainClassStr)) {
                Class clazz = Class.forName(mainClassStr, false, classLoader);
                return mainClass = new MainClass(clazz);
            }
            return mainClass = MainClass.load(AmazeConfigure.getModuleRoot(), module, classLoader);
        } catch (ClassNotFoundException ex) {
            throw new ModuleLoadException("main class not found", ex);
        }
    }

    public MainClass getMainClass() {
        return findMainClass();
    }

    public String getModule() {
        return module;
    }

    public ClassLoader getClassLoader() {
        return classLoader;
    }

    public ModuleContext setModule(String module) {
        this.module = module;
        return this;
    }

    public ModuleContext setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
        return this;
    }

    public String[] getArgs() {
        return args;
    }

    public ModuleContext setArgs(String[] args) {
        this.args = args;
        return this;
    }
}
