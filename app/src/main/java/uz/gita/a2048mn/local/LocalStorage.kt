package uz.gita.a2048mn.local

import android.content.Context
import android.content.SharedPreferences

class LocalStorage private constructor(context: Context) {
    private var preferences: SharedPreferences
    private var editor: SharedPreferences.Editor

    init {
        preferences = context.getSharedPreferences("2048", Context.MODE_PRIVATE)
        editor = preferences.edit()
    }


    companion object {
        private lateinit var localStorage: LocalStorage

        fun init(context: Context) {
            if (!(::localStorage.isInitialized)) localStorage = LocalStorage(context)
        }

        fun getInstance(): LocalStorage {
            return localStorage
        }
    }

    fun saveInt(s: String, num: Int) {
        editor.putInt(s, num).apply()
    }

    fun getInt(s: String): Int {
        return preferences.getInt(s, 0)
    }

    fun saveBoolean(b: Boolean) {
        editor.putBoolean("BOOLEAN", b)
    }

    fun getBoolean(): Boolean {
        return preferences.getBoolean("BOOLEAN", true)
    }

    fun saveBooleanLAST(b: Boolean) {
        editor.putBoolean("BOOLEANLAST", b)
    }

    fun getBooleanLAST(): Boolean {
        return preferences.getBoolean("BOOLEANLAST", true)
    }

    fun saveScore(score: Int){
        editor.putInt("SCORE",score).apply()
    }

    fun getScore() : Int{
        return preferences.getInt("SCORE",0)
    }

    fun saveHigh(score: Int){
        editor.putInt("SCOREHIGH",score).apply()
    }

    fun getHigh() : Int{
        return preferences.getInt("SCOREHIGH",0)
    }

    fun saveLast(score: Int){
        editor.putInt("SCORELAST",score).apply()
    }

    fun getLast() : Int{
        return preferences.getInt("SCORELAST",0)
    }
}