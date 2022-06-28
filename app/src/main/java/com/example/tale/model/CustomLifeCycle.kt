package com.example.tale.model

import androidx.lifecycle.*

class CustomLifeCycle : LifecycleOwner {

    private val lifecycle = LifecycleRegistry(this)

    private val observer = object : LifecycleObserver {
        @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
        fun onCreate() {
            lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
        fun onResume() {
            lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_START)
        fun onStart() {
            lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_START)
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        fun onPause() {
            lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
        fun onStop() {
            lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        fun onDestroy() {
            lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        }
    }

    fun attachLifecycleOwner(lifecycleOwner: LifecycleOwner) {
        lifecycle.currentState = lifecycleOwner.lifecycle.currentState
        lifecycleOwner.lifecycle.addObserver(observer)
    }

    fun detachLifecycleOwner(lifecycleOwner: LifecycleOwner) {
        lifecycleOwner.lifecycle.removeObserver(observer)
    }

    fun pauseCamera() {
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    }

    fun resumeCamera() {
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
    }

    override fun getLifecycle(): Lifecycle = lifecycle
}