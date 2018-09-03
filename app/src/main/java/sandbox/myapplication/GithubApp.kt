package sandbox.myapplication

import dagger.android.support.DaggerApplication
import sandbox.myapplication.common.di.DaggerAppComponent

class GithubApp:DaggerApplication(){
    override fun applicationInjector()=
            DaggerAppComponent.builder()
                    .application(this)
                    .build()
}