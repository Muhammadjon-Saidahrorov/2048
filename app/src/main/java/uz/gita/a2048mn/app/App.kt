package uz.gita.a2048mn.app

import android.app.Application
import uz.gita.a2048mn.local.LocalStorage
import uz.gita.a2048mn.repository.AppRepository

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        LocalStorage.init(this)
        AppRepository.init()
    }
}