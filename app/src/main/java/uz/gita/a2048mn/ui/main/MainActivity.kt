package uz.gita.a2048mn.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.LinearLayoutCompat
import uz.gita.a2048mn.R
import uz.gita.a2048mn.model.SideEnum
import uz.gita.a2048mn.repository.AppRepository
import uz.gita.a2048mn.utils.BackgroundUtil
import uz.gita.a2048mn.utils.MyTouchListener

class MainActivity : AppCompatActivity() {

    private val items: MutableList<TextView> = ArrayList(16)
    private lateinit var mainView: LinearLayoutCompat
    private val repository = AppRepository.getInstance()
    private val util = BackgroundUtil()
    private lateinit var home: ImageView
    private lateinit var replay: ImageView
    private lateinit var restartBtn: ImageView
    private lateinit var score: TextView
    private lateinit var high: TextView
    private var logic = true

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainView = findViewById(R.id.mainView)
        loadViews()


        if (repository.getBoolean()) describeMatrix()
        else if (!repository.getBooleanLast()) {
            repository.resumeLast()
            describeMatrix()
        } else describeMatrix2()
        click()

        val myTouchListener = MyTouchListener(this)
        myTouchListener.setMyMovementSideListener {
            when (it) {
                SideEnum.LEFT -> {
                    repository.moveToLeft()
                    describeMatrix()

                }
                SideEnum.RIGHT -> {
                    repository.moveToRight()
                    describeMatrix()
                }
                SideEnum.UP -> {
                    repository.moveToUp()
                    describeMatrix()
                }
                SideEnum.DOWN -> {
                    repository.moveToDown()
                    describeMatrix()
                }
            }
        }
        mainView.setOnTouchListener(myTouchListener)
    }

    private fun loadViews() {
        for (i in 0 until mainView.childCount) {
            val linear = mainView.getChildAt(i) as LinearLayoutCompat
            for (j in 0 until linear.childCount) {
                items.add(linear.getChildAt(j) as TextView)
            }
        }

        home = findViewById(R.id.home)
        replay = findViewById(R.id.replay)
        restartBtn = findViewById(R.id.restart)

        score = findViewById(R.id.scoreText)
        high = findViewById(R.id.highScoreText)
    }

    private fun click() {
        home.setOnClickListener {
            finish()
        }
        replay.setOnClickListener {
            repository.resumeLast()
            repository.saveIntLast()
            describeMatrix()
        }
        restartBtn.setOnClickListener {

            val view: View = LayoutInflater.from(this).inflate(R.layout.reset_dialog, null)
            val alertDialog: AlertDialog = AlertDialog.Builder(this)
                .setView(view)
                .setCancelable(false)
                .create()

            alertDialog.show()
            alertDialog.window?.setBackgroundDrawable(null)

            view.findViewById<AppCompatButton>(R.id.cencelBtnDg).setOnClickListener{
                alertDialog.dismiss()
            }

            view.findViewById<AppCompatButton>(R.id.restartBtnBg).setOnClickListener{
                repository.restartViews()
                describeMatrix()
                alertDialog.dismiss()
            }

        }
    }

    private fun describeMatrix() {

        val _matrix = repository.matrix
        score.text = repository.getScore().toString()
        high.text = repository.getHigh().toString()

        for (i in _matrix.indices) {
            for (j in _matrix[i].indices) {
                items[i * 4 + j].apply {
                    text = if (_matrix[i][j] == 0) ""
                    else _matrix[i][j].toString()

                    setBackgroundResource(util.colorByAmount(_matrix[i][j]))
                }
            }
        }

        gaveOver()
    }

    private fun describeMatrix2() {

        val _matrix = arrayOf(
            arrayOf(0, 0, 0, 0),
            arrayOf(0, 0, 0, 0),
            arrayOf(0, 0, 0, 0),
            arrayOf(0, 0, 0, 0)
        )

        score.text = repository.getScore().toString()
        high.text = repository.getHigh().toString()

        for (i in _matrix.indices) {
            for (j in _matrix.indices) {
                _matrix[i][j] = repository.getInt("POSITIONS${i * 4 + j}")
            }
        }

        for (i in _matrix.indices) {
            for (j in _matrix[i].indices) {
                items[i * 4 + j].apply {
                    text = if (_matrix[i][j] == 0) ""
                    else _matrix[i][j].toString()

                    setBackgroundResource(util.colorByAmount(_matrix[i][j]))
                }
            }
        }

        gaveOver()
    }

    private fun gaveOver(){
        if(repository.isGameOver() && logic){
            logic = false

            val view: View = LayoutInflater.from(this).inflate(R.layout.gameover_dialog, null)
            val alertDialog: AlertDialog = AlertDialog.Builder(this)
                .setView(view)
                .setCancelable(false)
                .create()

            alertDialog.show()
            alertDialog.window?.setBackgroundDrawable(null)

            view.findViewById<AppCompatButton>(R.id.menuDg).setOnClickListener{
                alertDialog.dismiss()
                repository.restartViews()
                finish()
            }

            view.findViewById<AppCompatButton>(R.id.restartDG).setOnClickListener{
                repository.restartViews()
                describeMatrix()
                logic = true
                alertDialog.dismiss()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        repository.saveBoolean(false)
        if (repository.getBooleanLast()) {
            repository.saveInt()
        }
    }
}