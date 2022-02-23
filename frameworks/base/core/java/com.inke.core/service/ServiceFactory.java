package com.inke.core.service;

public interface ServiceFactory<T> {
    static <T> ServiceFactory<T> build(ServiceFactory<? extends T> factory) {

        return new ServiceFactory<T>() {

            T impl = null;

            @Override
            public synchronized T getImpl() {
                if (impl == null) {
                    impl = factory.getImpl();
                }
                return impl;
            }
        };
    }

    T getImpl();
}
