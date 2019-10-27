package com.swivel.codechallenge

import android.app.Application
import com.swivel.codechallenge.di.ComponentInjector

class CodeChallengeApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        ComponentInjector.init()
    }
}