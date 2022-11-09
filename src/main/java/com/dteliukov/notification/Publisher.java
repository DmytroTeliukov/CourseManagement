package com.dteliukov.notification;

public interface Publisher<T> {
    void addSubscriber(T subscriber);
    void removeSubscriber(T subscriber);
    void notifySubscribers(String notification);
}
