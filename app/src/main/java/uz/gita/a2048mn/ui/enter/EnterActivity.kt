package uz.gita.a2048mn.ui.enter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatButton
import uz.gita.a2048mn.R
import uz.gita.a2048mn.ui.info.InfoActivity
import uz.gita.a2048mn.ui.main.MainActivity

class EnterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter)

        findViewById<AppCompatButton>(R.id.playBtn).setOnClickListener{
            startActivity(Intent(this,MainActivity::class.java))
        }

        findViewById<AppCompatButton>(R.id.infoBtn).setOnClickListener{
            startActivity(Intent(this,InfoActivity::class.java))
        }

        findViewById<AppCompatButton>(R.id.exitBtn).setOnClickListener{
            finish()
        }
    }
}