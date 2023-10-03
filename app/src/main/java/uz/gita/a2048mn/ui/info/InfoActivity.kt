package uz.gita.a2048mn.ui.info

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatImageView
import uz.gita.a2048mn.R

class InfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        findViewById<AppCompatImageView>(R.id.iconBackIdInfo).setOnClickListener{
            finish()
        }
    }
}