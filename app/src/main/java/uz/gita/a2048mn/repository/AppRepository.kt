package uz.gita.a2048mn.repository

import uz.gita.a2048mn.local.LocalStorage
import kotlin.random.Random

class AppRepository private constructor() {

    private val localStorage: LocalStorage = LocalStorage.getInstance()

    companion object {
        private lateinit var appRepository: AppRepository

        fun init() {
            if (!(::appRepository.isInitialized))
                appRepository = AppRepository()
        }

        fun getInstance(): AppRepository {
            return appRepository
        }
    }

    private val NEW_ELEMENT = 2

    var matrix = arrayOf(
        arrayOf(0, 0, 0, 0),
        arrayOf(0, 0, 0, 0),
        arrayOf(0, 0, 0, 0),
        arrayOf(0, 0, 0, 0)
    )

    init {
        addNewElement()
        addNewElement()
    }

    private fun resume() {
        for (i in matrix.indices) {
            for (j in matrix.indices) {
                matrix[i][j] = localStorage.getInt("POSITIONS${i * 4 + j}")
            }
        }
    }

    fun resumeLast() {
        for (i in matrix.indices) {
            for (j in matrix.indices) {
                matrix[i][j] = localStorage.getInt("POSITIONSLAST${i * 4 + j}")
            }
        }
        localStorage.saveBooleanLAST(false)
        if (localStorage.getScore() != 0){
            localStorage.saveScore(localStorage.getScore() - localStorage.getLast())
        }
        localStorage.saveLast(0)
    }


    private fun addNewElement() {
        val emptyList = ArrayList<Pair<Int, Int>>()
        for (i in matrix.indices) {
            for (j in matrix[i].indices) {
                if (matrix[i][j] == 0) emptyList.add(Pair(i, j))
            }
        }

        if (emptyList.isNotEmpty()) {
            val randomPos = Random.nextInt(0, emptyList.size)
            matrix[emptyList[randomPos].first][emptyList[randomPos].second] = NEW_ELEMENT
        }

    }

    fun restartViews() {
        localStorage.saveScore(0)

        matrix = arrayOf(
            arrayOf(0, 0, 0, 0),
            arrayOf(0, 0, 0, 0),
            arrayOf(0, 0, 0, 0),
            arrayOf(0, 0, 0, 0)
        )

        addNewElement()
        addNewElement()
        saveInt()
        saveIntLast()
    }

    fun isGameOver(): Boolean {
        for (i in matrix.indices) {
            for (j in matrix[i].indices) {
                if (matrix[i][j] == 0) {
                    return false
                }
            }
        }

        for (i in matrix.indices) {
            for (j in matrix[i].indices) {
                if (i < matrix.size - 1 && matrix[i][j] == matrix[i + 1][j]) {
                    return false
                }
                if (j < matrix[i].size - 1 && matrix[i][j] == matrix[i][j + 1]) {
                    return false
                }
            }
        }

        return true
    }

    fun moveToLeft() {

        var logic = true
        var tekshirr = 0
        for (i in matrix.indices) {
            val amountss = ArrayList<Int>(4)
            val amountss2 = ArrayList<Int>(4)
            var bool = true
            var inc = 0
            for (j in matrix[i].indices) {
                amountss2.add(matrix[i][j])
                if (matrix[i][j] == 0) continue
                if (amountss.isEmpty()) amountss.add(matrix[i][j])
                else {
                    if (amountss.last() == matrix[i][j] && bool) {
                        amountss[amountss.size - 1] = amountss.last() * 2
                        bool = false
                    } else {
                        amountss.add(matrix[i][j])
                        bool = true
                    }
                }
            }

            for (l in amountss.indices) {
                if (amountss2[l] != amountss[l]) {
                    inc++
                }
            }

            if (inc == 0) tekshirr++

        }
        if (tekshirr == 4) {
            logic = false
        }


        if (!localStorage.getBooleanLAST()) {
            resumeLast()
            saveBooleanLast(true)
        } else if (!localStorage.getBoolean()) resume()

        if (logic){
            saveIntLast()
        }

        var tekshir = 0
        for (i in matrix.indices) {
            val amounts = ArrayList<Int>(4)
            val amounts2 = ArrayList<Int>(4)
            var bool = true
            var inc = 0
            for (j in matrix[i].indices) {
                amounts2.add(matrix[i][j])
                if (matrix[i][j] == 0) continue
                if (amounts.isEmpty()) amounts.add(matrix[i][j])
                else {
                    if (amounts.last() == matrix[i][j] && bool) {
                        amounts[amounts.size - 1] = amounts.last() * 2
                        localStorage.saveLast(amounts[amounts.size - 1])
                        localStorage.saveScore(localStorage.getScore() + amounts[amounts.size - 1])
                        if (localStorage.getScore() >= localStorage.getHigh()) {
                            localStorage.saveHigh(localStorage.getScore())
                        }
                        bool = false
                    } else {
                        amounts.add(matrix[i][j])
                        bool = true
                    }
                }
                matrix[i][j] = 0
            }

            for (l in amounts.indices) {
                if (amounts2[l] != amounts[l]) {
                    inc++
                }
            }

            for (k in amounts.indices) {
                matrix[i][k] = amounts[k]
            }

            if (inc == 0) tekshir++

        }
        if (tekshir != 4) {
            addNewElement()
            saveInt()
        }

    }

    fun moveToRight() {

        var logic = true

        var tekshirr = 0
        for (i in matrix.indices) {
            val amountss = ArrayList<Int>(4)
            val amountss2 = ArrayList<Int>(4)
            var bool = true
            var inc = 0
            for (j in matrix[i].size - 1 downTo 0) {
                amountss2.add(matrix[i][j])

                if (matrix[i][j] == 0) continue
                if (amountss.isEmpty()) amountss.add(matrix[i][j])
                else {
                    if (amountss.last() == matrix[i][j] && bool) {
                        amountss[amountss.size - 1] = amountss.last() * 2
                        bool = false
                    } else {
                        amountss.add(matrix[i][j])
                        bool = true
                    }
                }
            }

            for (l in amountss.indices) {
                if (amountss2[l] != amountss[l]) {
                    inc++
                }
            }

            if (inc == 0) tekshirr++

        }
        if (tekshirr == 4) {
            logic = false
        }


        if (!localStorage.getBooleanLAST()) {
            resumeLast()
            saveBooleanLast(true)
        } else if (!localStorage.getBoolean()) resume()

        if (logic){
            saveIntLast()
        }

        var tekshir = 0

        for (i in matrix.indices) {
            val amounts = ArrayList<Int>(4)
            val amounts2 = ArrayList<Int>(4)
            var bool = true
            var inc = 0
            for (j in matrix[i].size - 1 downTo 0) {
                amounts2.add(matrix[i][j])

                if (matrix[i][j] == 0) continue
                if (amounts.isEmpty()) amounts.add(matrix[i][j])
                else {
                    if (amounts.last() == matrix[i][j] && bool) {
                        amounts[amounts.size - 1] = amounts.last() * 2
                        localStorage.saveLast(amounts[amounts.size - 1])
                        localStorage.saveScore(localStorage.getScore() + amounts[amounts.size - 1])
                        if (localStorage.getScore() >= localStorage.getHigh()) {
                            localStorage.saveHigh(localStorage.getScore())
                        }
                        bool = false
                    } else {
                        amounts.add(matrix[i][j])
                        bool = true
                    }
                }
                matrix[i][j] = 0
            }

            for (l in amounts.indices) {
                if (amounts2[l] != amounts[l]) {
                    inc++
                }
            }

            for (k in amounts.indices) {
                matrix[i][matrix[i].size - k - 1] = amounts[k]
            }

            if (inc == 0) tekshir++

        }
        if (tekshir != 4) {
            addNewElement()
            saveInt()
        }
    }

    fun moveToUp() {

        var logic = true

        var tekshirr = 0
        for (i in matrix.indices) {
            val amountss = ArrayList<Int>()
            val amountss2 = ArrayList<Int>()
            var bool = true
            var inc = 0
            for (j in matrix[i].indices) {
                amountss2.add(matrix[j][i])
                if (matrix[j][i] == 0) continue
                if (amountss.isEmpty()) amountss.add(matrix[j][i])
                else {
                    if (amountss.last() == matrix[j][i] && bool) {
                        amountss[amountss.size - 1] = amountss.last() * 2
                        bool = false
                    } else {
                        bool = true
                        amountss.add(matrix[j][i])
                    }
                }
            }

            for (l in amountss.indices) {
                if (amountss2[l] != amountss[l]) {
                    inc++
                }
            }


            if (inc == 0) tekshirr++
        }
        if (tekshirr == 4) {
            logic = false
        }


        if (!localStorage.getBooleanLAST()) {
            resumeLast()
            saveBooleanLast(true)
        } else if (!localStorage.getBoolean()) resume()

        if (logic){
            saveIntLast()
        }

        var tekshir = 0
        for (i in matrix.indices) {
            val amounts = ArrayList<Int>()
            val amounts2 = ArrayList<Int>()
            var bool = true
            var inc = 0
            for (j in matrix[i].indices) {
                amounts2.add(matrix[j][i])
                if (matrix[j][i] == 0) continue
                if (amounts.isEmpty()) amounts.add(matrix[j][i])
                else {
                    if (amounts.last() == matrix[j][i] && bool) {
                        amounts[amounts.size - 1] = amounts.last() * 2
                        localStorage.saveLast(amounts[amounts.size - 1])
                        localStorage.saveScore(localStorage.getScore() + amounts[amounts.size - 1])
                        if (localStorage.getScore() >= localStorage.getHigh()) {
                            localStorage.saveHigh(localStorage.getScore())
                        }
                        bool = false
                    } else {
                        bool = true
                        amounts.add(matrix[j][i])
                    }
                }
                matrix[j][i] = 0
            }

            for (l in amounts.indices) {
                if (amounts2[l] != amounts[l]) {
                    inc++
                }
            }


            for (j in 0 until amounts.size) {
                matrix[j][i] = amounts[j]
            }

            if (inc == 0) tekshir++
        }
        if (tekshir != 4) {
            addNewElement()
            saveInt()
        }

    }

    fun moveToDown() {

        var logic = true

        var tekshirr = 0
        for (i in matrix.indices) {
            val amountss = ArrayList<Int>()
            val amountss2 = ArrayList<Int>()
            var bool = true
            var inc = 0
            for (j in matrix[i].size - 1 downTo 0) {
                amountss2.add(matrix[j][i])
                if (matrix[j][i] == 0) continue
                if (amountss.isEmpty()) amountss.add(matrix[j][i])
                else {
                    if (amountss.last() == matrix[j][i] && bool) {
                        amountss[amountss.size - 1] = amountss.last() * 2
                        bool = false
                    } else {
                        bool = true
                        amountss.add(matrix[j][i])
                    }
                }
            }

            for (l in amountss.indices) {
                if (amountss2[l] != amountss[l]) {
                    inc++
                }
            }

            if (inc == 0) tekshirr++
        }
        if (tekshirr == 4) {
            logic = false
        }


        if (!localStorage.getBooleanLAST()) {
            resumeLast()
            saveBooleanLast(true)
        } else if (!localStorage.getBoolean()) resume()

        if (logic){
            saveIntLast()
        }

        var tekshir = 0
        for (i in matrix.indices) {
            val amounts = ArrayList<Int>()
            val amounts2 = ArrayList<Int>()
            var bool = true
            var inc = 0
            for (j in matrix[i].size - 1 downTo 0) {
                amounts2.add(matrix[j][i])
                if (matrix[j][i] == 0) continue
                if (amounts.isEmpty()) amounts.add(matrix[j][i])
                else {
                    if (amounts.last() == matrix[j][i] && bool) {
                        amounts[amounts.size - 1] = amounts.last() * 2
                        localStorage.saveLast(amounts[amounts.size - 1])
                        localStorage.saveScore(localStorage.getScore() + amounts[amounts.size - 1])
                        if (localStorage.getScore() >= localStorage.getHigh()) {
                            localStorage.saveHigh(localStorage.getScore())
                        }
                        bool = false
                    } else {
                        bool = true
                        amounts.add(matrix[j][i])
                    }
                }
                matrix[j][i] = 0
            }

            for (l in amounts.indices) {
                if (amounts2[l] != amounts[l]) {
                    inc++
                }
            }

            for (j in amounts.size - 1 downTo 0) {
                matrix[3 - j][i] = amounts[j]
            }

            if (inc == 0) tekshir++
        }
        if (tekshir != 4) {
            addNewElement()
            saveInt()
        }

    }

    fun saveInt() {
        for (i in matrix.indices) {
            for (j in matrix.indices) {
                localStorage.saveInt("POSITIONS${i * 4 + j}", matrix[i][j])
            }
        }
    }

    fun saveIntLast() {
        for (i in matrix.indices) {
            for (j in matrix.indices) {
                localStorage.saveInt("POSITIONSLAST${i * 4 + j}", matrix[i][j])
            }
        }
    }

    fun getInt(s: String): Int {
        return localStorage.getInt(s)
    }

    fun saveBoolean(b: Boolean) {
        localStorage.saveBoolean(b)
    }

    fun getBoolean(): Boolean {
        return localStorage.getBoolean()
    }

    private fun saveBooleanLast(b: Boolean) {
        localStorage.saveBooleanLAST(b)
    }

    fun getBooleanLast(): Boolean {
        return localStorage.getBooleanLAST()
    }

    fun getScore(): Int {
        return localStorage.getScore()
    }

    fun getHigh(): Int {
        return localStorage.getHigh()
    }

}