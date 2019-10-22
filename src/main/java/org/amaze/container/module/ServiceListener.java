package org.amaze.container.module;

/**
 * @author : shunqxian
 * @date : 2019-10-22
 */
public interface ServiceListener {
    public void onStart(Service service);

    public void onStop(Service service);
}
