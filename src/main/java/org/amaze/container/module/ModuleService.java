package org.amaze.container.module;

/**
 * @author : shunqxian
 * @date : 2019-10-05
 */
public class ModuleService implements Service {

    private ModuleContext moduleContext;

    public ModuleService(ModuleContext moduleContext) {
        this.moduleContext = moduleContext;
    }

    public ModuleContext getModuleContext() {
        return moduleContext;
    }

    @Override
    public void start() {
        System.out.printf("开始启动%s \r\n", moduleContext.getModule());
        ClassLoader oldClassLoader = Thread.currentThread().getContextClassLoader();
        try {
            Thread.currentThread().setContextClassLoader(moduleContext.getClassLoader());
            MainClass mainClass = moduleContext.findMainClass();
            if (moduleContext.getArgs() != null) {
                System.out.printf("模块%s启动参数如下\n", moduleContext.getModule());
                for (String arg : moduleContext.getArgs()) {
                    System.out.println(arg);
                }
            }
            mainClass.main(moduleContext.getArgs());
            System.out.printf("模块%s启动成功\n", moduleContext.getModule());
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.printf("模块%s启动出错\n", moduleContext.getModule());
        } finally {
            Thread.currentThread().setContextClassLoader(oldClassLoader);
        }
    }

    @Override
    public void stop() {
    }
}
