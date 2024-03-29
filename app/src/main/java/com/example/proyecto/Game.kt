package com.example.proyecto

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ContentValues
import android.content.Context
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kotlin.random.Random
import kotlin.random.nextInt
import kotlin.system.exitProcess

class Game : AppCompatActivity() {
    //Grid Elements
    private val tVListGrande = arrayListOf<TextView>()
    private val tVListPequenia = arrayListOf<TextView>()
    private lateinit var aux: TextView
    private var song: MediaPlayer? = null
    private val channelID = "channelID"
    private val channelName = "channelName"
    private val notificationId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game)
        song = MediaPlayer.create(applicationContext,R.raw.boss_music)
        song!!.start()
        // Usuario
        val nom = intent.getStringExtra("userName")
        val user = findViewById<TextView>(R.id.username)
        user.text = "$nom"

        //Botón de Pausa
        //val pause = findViewById<Button>(R.id.pause)
        /*pause.setOnClickListener(){
        }*/
        //Texto auxiliar
        aux = findViewById(R.id.text1)
        //Comienzo del juego
        initTextViewLists()
        nextShape()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            val importance = NotificationManager.IMPORTANCE_HIGH

            val channel = NotificationChannel(channelID, channelName, importance).apply {
                lightColor = Color.RED
                enableLights(true)
            }
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            manager.createNotificationChannel(channel)
        }
    }

    override fun onPause() {
        super.onPause()
        stopPlayer()
        exitProcess(0)
    }


    //Inicialización de las listas contenedoras de los elementos de los GridLayouts
    @SuppressLint("DiscouragedApi")
    private fun initTextViewLists() {
        // Inicializar tVListGrande
        for (i in 1..150) {
            val textViewId = resources.getIdentifier("B$i", "id", packageName)
            val textView = findViewById<TextView>(textViewId)
            tVListGrande.add(textView)
        }
        // Inicializar tVListPequenia
        for (i in 1..12) {
            val textViewId = resources.getIdentifier("bt$i", "id", packageName)
            val textView = findViewById<TextView>(textViewId)
            tVListPequenia.add(textView)
        }
    }
    // Variables
    var nextUp:Int = 0
    var starting = false
    var num1 = 0
    var num2 = 0
    var num3 = 0
    var num4 = 0
    var shape_is = 0
    var stop = false
    var lines = 0
    var once = true

    private fun stopPlayer() {
        try {
            if (song != null) {
                // Log.e("Trying to Stop "," Song ");
                song!!.stop()
                song!!.release()
                song!!.reset() // causes IllegalstateException
                song = null
            }
        } catch (e: Exception) {
            song = null
            e.printStackTrace()
        }
    }
    // Revisar método
    @SuppressLint("SetTextI18n")
    private fun loseALine(){
        val lastIndex = 140
        var lineArray = arrayListOf<Int>()
        /* For que va saltando de 10 en 10 con el índice de comienzo (startIndex) siendo 10 siguiendo
         por 20, 30 y así hasta que el final sea 149 (lastIndex) coincidiendo con el último número
         de elemento del GridLaout grande.. */
        for (startIndex in 10..lastIndex step 10) {
            /*Condición que comprueba que los elementos dentro del rango (10<=x<20) tengan su texto = "0",
            lo que significará que la línea se ha perdido al completarla.*/
            if ((startIndex until startIndex + 10).all { tVListGrande[it].text == "0" }) {
                // For para recorrer la línea completada
                for (i in startIndex until startIndex + 10) {
                    //Se cambian los backgrounds al bloque genérico
                    tVListGrande[i].setBackgroundResource(R.drawable.block)
                    //Se pone el texto vacío.
                    tVListGrande[i].text=""
                    // Se suman 100 puntos por línea completada.
                    points+=100
                    // Calcular el número de líneas en función del índice inicial
                    lines = (startIndex / 10) + 1
                    lineArray.add(lines)
                }
            }
        }

        Handler(Looper.getMainLooper()).postDelayed({
            for (i in lineArray){
                var nextL = true
                while(nextL){
                    when(lines){
                        15-> {changeLines(140); lines=14}
                        14-> {changeLines(130);lines=13}
                        13-> {changeLines(120);lines=12}
                        12-> {changeLines(110);lines=11}
                        11-> {changeLines(100);lines=10}
                        10-> {changeLines(90);lines=9}
                        9-> {changeLines(80);lines=8}
                        8-> {changeLines(70);lines=7}
                        7-> {changeLines(60);lines=6}
                        6-> {changeLines(50);lines=5}
                        5-> {changeLines(40);lines=4}
                        4-> {changeLines(30);lines=3}
                        3-> {lines=2}
                    }
                    if (lines == 2){
                        nextL = false
                    }
                }
            }
            lineArray.removeAll(lineArray)
        }, 500)
        val pointsText = findViewById<TextView>(R.id.points)
        pointsText.text = "Points: $points"
    }
    private fun changeLines(startIndex :Int){
        val endIndex = startIndex + 9
        if ((startIndex..endIndex).all { tVListGrande[it].text == ""}) {
            for (i in startIndex .. endIndex){
                tVListGrande[i].text = tVListGrande[i -10].text
                tVListGrande[i].background = tVListGrande[i -10].background
                tVListGrande[i -10].setBackgroundResource(R.drawable.block)
                tVListGrande[i -10].text = ""
            }
        }
    }

    private fun nextShape() {
        /* 1   2   3
        *  4   5   6
        *  7   8   9
        *  10  11  12*/
        for (i in 0 until tVListPequenia.size) {
            tVListPequenia[i].setBackgroundResource(R.drawable.block)
        }

        val random = Random.nextInt(1..7)
        when (random) {
            //L
            1 -> {
                tVListPequenia[5].setBackgroundResource(R.drawable.orange);tVListPequenia[6].setBackgroundResource(R.drawable.orange)
                tVListPequenia[7].setBackgroundResource(R.drawable.orange);tVListPequenia[8].setBackgroundResource(R.drawable.orange)
            }
            //Inverted S
            2 -> {
                tVListPequenia[3].setBackgroundResource(R.drawable.yellow);tVListPequenia[4].setBackgroundResource(R.drawable.yellow)
                tVListPequenia[7].setBackgroundResource(R.drawable.yellow);tVListPequenia[8].setBackgroundResource(R.drawable.yellow)
            }
            //square
            3 -> {
                tVListPequenia[4].setBackgroundResource(R.drawable.green);tVListPequenia[5].setBackgroundResource(R.drawable.green)
                tVListPequenia[7].setBackgroundResource(R.drawable.green);tVListPequenia[8].setBackgroundResource(R.drawable.green)
            }
            //Inverted L
            4 -> {
                tVListPequenia[3].setBackgroundResource(R.drawable.purple);tVListPequenia[6].setBackgroundResource(R.drawable.purple)
                tVListPequenia[7].setBackgroundResource(R.drawable.purple);tVListPequenia[8].setBackgroundResource(R.drawable.purple)
            }
            //S
            5 -> {
                tVListPequenia[5].setBackgroundResource(R.drawable.pink); tVListPequenia[4].setBackgroundResource(R.drawable.pink)
                tVListPequenia[7].setBackgroundResource(R.drawable.pink);tVListPequenia[6].setBackgroundResource(R.drawable.pink)
            }
            // _-_
            6 -> {
                tVListPequenia[4].setBackgroundResource(R.drawable.blue); tVListPequenia[6].setBackgroundResource(R.drawable.blue)
                tVListPequenia[7].setBackgroundResource(R.drawable.blue);tVListPequenia[8].setBackgroundResource(R.drawable.blue)
            }
            // |
            7 -> {
                tVListPequenia[1].setBackgroundResource(R.drawable.red); tVListPequenia[4].setBackgroundResource(R.drawable.red)
                tVListPequenia[7].setBackgroundResource(R.drawable.red);tVListPequenia[10].setBackgroundResource(R.drawable.red)
            }
        }
        nextUp = random
        if (once) {
            for (i in 0 until tVListPequenia.size) {
                tVListPequenia[i].setBackgroundResource(R.drawable.block)
            }
            tVListPequenia[4].setBackgroundResource(R.drawable.green)
            tVListPequenia[5].setBackgroundResource(R.drawable.green)
            tVListPequenia[7].setBackgroundResource(R.drawable.green)
            tVListPequenia[8].setBackgroundResource(R.drawable.green)
            nextUp = 3
            once = false
        }
        if (!starting) {
            Handler(Looper.getMainLooper()).postDelayed({
                shapes()
            }, 1500)
            starting = true
        }
    }

    private fun shapes(){
        if (tVListGrande[10].text == "0" || tVListGrande[11].text == "0"|| tVListGrande[12].text == "0"|| tVListGrande[13].text == "0"|| tVListGrande[14].text == "0"
            || tVListGrande[15].text == "0"|| tVListGrande[16].text == "0"|| tVListGrande[17].text == "0"|| tVListGrande[18].text == "0"|| tVListGrande[19].text == "0"){
            for (i in 0 until tVListPequenia.size) {
                tVListPequenia[i].setBackgroundResource(R.drawable.block)
            }
            stop = true
            gameOver()
        }
        loseALine()
        if (!stop){
            z=0;x=0
            if (tVListGrande[23].text == "0" || tVListGrande[24].text == "0"
                || tVListGrande[25].text == "0" || tVListGrande[26].text == "0"){
                nextUp=7
            }
            array.removeAll(array)
            arrayCollectPreviousOne.removeAll(arrayCollectPreviousOne)
            shape_is = nextUp
            nextShape()
            when(shape_is){
                //L
                1 ->{tVListGrande[5].setBackgroundResource(R.drawable.orange);tVListGrande[13].setBackgroundResource(R.drawable.orange); tVListGrande[14].setBackgroundResource(R.drawable.orange);tVListGrande[15].setBackgroundResource(R.drawable.orange)
                    num1=5;num2=13;num3=14;num4=15}
                //Inverted S
                2 ->{tVListGrande[4].setBackgroundResource(R.drawable.yellow);tVListGrande[5].setBackgroundResource(R.drawable.yellow);tVListGrande[15].setBackgroundResource(R.drawable.yellow);tVListGrande[16].setBackgroundResource(R.drawable.yellow)
                    num1=4;num2=5;num3=15;num4=16}
                //square
                3 ->{tVListGrande[4].setBackgroundResource(R.drawable.green);tVListGrande[5].setBackgroundResource(R.drawable.green);tVListGrande[14].setBackgroundResource(R.drawable.green);tVListGrande[15].setBackgroundResource(R.drawable.green)
                    num1=4;num2=5;num3=14;num4=15}
                //Inverted L
                4 ->{tVListGrande[4].setBackgroundResource(R.drawable.purple);tVListGrande[14].setBackgroundResource(R.drawable.purple);tVListGrande[15].setBackgroundResource(R.drawable.purple);tVListGrande[16].setBackgroundResource(R.drawable.purple)
                    num1=4;num2=14;num3=15;num4=16}
                //S
                5 ->{tVListGrande[4].setBackgroundResource(R.drawable.pink);tVListGrande[5].setBackgroundResource(R.drawable.pink); tVListGrande[13].setBackgroundResource(R.drawable.pink);tVListGrande[14].setBackgroundResource(R.drawable.pink)
                    num1=4;num2=5;num3=13;num4=14}
                // _-_
                6 ->{tVListGrande[4].setBackgroundResource(R.drawable.blue);tVListGrande[13].setBackgroundResource(R.drawable.blue); tVListGrande[14].setBackgroundResource(R.drawable.blue);tVListGrande[15].setBackgroundResource(R.drawable.blue)
                    num1=4;num2=13;num3=14;num4=15}
                // |
                7 ->{tVListGrande[3].setBackgroundResource(R.drawable.red);tVListGrande[4].setBackgroundResource(R.drawable.red); tVListGrande[5].setBackgroundResource(R.drawable.red);tVListGrande[6].setBackgroundResource(R.drawable.red)
                    num1=3;num2=4;num3=5;num4=6}
            }
            controls()
        }
    }

    private fun gameOver(){
        stopPlayer()
        tVListGrande[3].setBackgroundResource(R.drawable.game_over_block)
        tVListGrande[4].setBackgroundResource(R.drawable.game_over_block)
        tVListGrande[5].setBackgroundResource(R.drawable.game_over_block)
        tVListGrande[6].setBackgroundResource(R.drawable.game_over_block)
        val gameOver: MediaPlayer = MediaPlayer.create(applicationContext,R.raw.game_over_ut)
        gameOver.start()
        val gameOver2 : MediaPlayer = MediaPlayer.create(applicationContext,R.raw.game_over_ut2)
        gameOver2.start()
        if(!gameOver.isPlaying || !gameOver2.isPlaying){
            gameOver.release()
            gameOver2.release()
        }
        updateDB()
    }
    private fun updateDB(){
        val user = findViewById<TextView>(R.id.username)
        val admin = AdminSQL(this,"Scores", null, 1)
        val db= admin.writableDatabase
        val registro = ContentValues()
        val fila = db.query("Scores",null, " user ='${user.text.toString()}'", null, null, null, null)
        if(fila.moveToFirst()){
            if (fila.getInt(1)<points){
                registro.put("points", points)
                db.update("Scores", registro, " user='${user.text.toString()}'", null)
                notificationHS()
            }
        } else {
            registro.put("user", user.text.toString())
            registro.put("points", points)
            db.insert("Scores", null, registro)
        }
    }
    @SuppressLint("MissingPermission")
    private fun notificationHS(){
        createNotificationChannel()

        val notification =  NotificationCompat.Builder(this, channelID).also {
            it.setSmallIcon(R.drawable.notification)
            it.setContentTitle("NEW HIGH SCORE!")
            it.setContentText("Go check if you're on the LeaderBoard!!")
            it.priority = NotificationCompat.PRIORITY_HIGH
        }.build()

        val notificationManager = NotificationManagerCompat.from(this)

        notificationManager.notify(notificationId, notification)
    }
    private val arrayCollectPreviousOne = arrayListOf<TextView>()
    private val array = arrayListOf<TextView>()
    private val downArray: ArrayList<TextView> get() = arrayListOf<TextView>().apply {
        addAll(tVListGrande)
        add(aux)
    }
    private var z = 0
    private fun controls()  {
        val right = findViewById<Button>(R.id.right)
        val left = findViewById<Button>(R.id.left)
        val spinRight = findViewById<Button>(R.id.spin)
        val down = findViewById<Button>(R.id.down)
        val numbers = listOf(num1, num2, num3, num4)

        right.setOnClickListener{
            val invalidValues = listOf(9, 19, 29, 39, 49, 59, 69, 79, 89, 99, 109, 119, 129, 139, 149)
            if (numbers.none { it in invalidValues } && numbers.all { downArray[it + 1].text == "" }) {
                num1++
                num2++
                num3++
                num4++
            }

        }
        left.setOnClickListener {
            val invalidValues = listOf(0, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 110, 120, 130, 140)
            if (numbers.none { it in invalidValues } && numbers.all { downArray[it - 1].text == "" }) {
                num1--
                num2--
                num3--
                num4--
            }
        }
        spinRight.setOnClickListener{
            val isRotationAllowed = isRotationPossible()
            if (isRotationAllowed) {
                rotatePiece()
            }
        }

        down.setOnClickListener {
            while (x == 0) {
                num1 += 10
                num2 += 10
                num3 += 10
                num4 += 10
                canGoDown()
            }
        }
        num1+=10;num2+=10;num3+=10;num4+=10
        val numerosUpdated = intArrayOf(num1, num2, num3, num4)
        for (index in numerosUpdated) {
            arrayCollectPreviousOne.add(downArray[index])
            array.add(downArray[index])
        }
        landing();colors()
    }
    private fun canGoDown(){
        //Lista que comprende los valores máximos del GridLayout para que las piezas no bajen más que eso
        val invalidNumbers = (140..149).toList()
        //Condición que
        val cantGoDown = (num1 in invalidNumbers) || (num2 in invalidNumbers) || (num3 in invalidNumbers) || (num4 in invalidNumbers)
        val currentCells = listOf(downArray[num1], downArray[num2], downArray[num3], downArray[num4])
        if (currentCells.any { it.text == "1" } || cantGoDown) {
            num1 -= 10
            num2 -= 10
            num3 -= 10
            num4 -= 10
            x = 1
            points += 5
        }
    }

    // Función para verificar si las posiciones alrededor de la pieza están vacías
    private fun isRotationPossible(): Boolean {
        // Lógica para verificar si las posiciones están vacías
        val positionsToCheck = listOf(
            num1 + 1, num2 + 1, num3 + 1, num4 + 1,
            num1 - 1, num2 - 1, num3 - 1, num4 - 1,
            num1 + 10, num2 + 10, num3 + 10, num4 + 10,
            num1 - 10, num2 - 10, num3 - 10, num4 - 10
        )
        /* true si todas las posiciones están vacías, de lo contrario false */
        return positionsToCheck.all { index ->
            downArray.getOrNull(index)?.text == ""
        }
    }

    // Función para realizar la rotación según el tipo de pieza
    private fun rotatePiece() {
        when (shape_is) {
            1 -> rotateLShape()
            2 -> rotateInvertedS()
            3 -> {} // Doesn't change
            4 -> rotateInvertedL()
            5 -> rotateS()
            6 -> rotateUnderscore()
            7 -> rotateLine()
        }
    }

    //Funciones separadas para cada tipo de rotación
    private fun rotateLShape() {
        // Lógica de rotación para la pieza L
        when (z) {
            0 -> {num1 -= 1;num2 += 1;num3 += 10;num4 += 10;z = 1}
            1 -> {num1 += 9;num2 += 0;num3 -= 9;num4 -= 2;z = 2}
            2 -> {num1 -= 10;num2 -= 10;num3 -= 1;num4 += 1;z = 3}
            else -> {num1 += 2;num2 += 9;num3 += 0;num4 -= 9;z = 0}
        }
    }

    private fun rotateInvertedS() {
        // Lógica de rotación para la pieza S invertida
        if(z==0){
            num1+=1;num2+=9;num3+=0;num4+=8;z=1
        } else{num1-=1;num2-=9;num3-=0;num4-=8;z=0}
    }

    private fun rotateInvertedL() {
        // Lógica de rotación para la pieza L invertida
        when (z) {
            0 -> {num1 += 0;num2 -= 9;num3 -= 1;num4 += 8;z = 1}
            1 -> {num1 += 10;num2 += 10;num3 += 2;num4 += 2;z = 2}
            2 -> {num1 -= 8;num2 += 1;num3 += 9;num4 += 0;z = 3}
            else -> {num1 -= 2;num2 -= 2;num3 -= 10;num4 -= 10;z = 0}
        }
    }

    private fun rotateS() {
        // Lógica de rotación para la pieza S
        if(z==0){
            num1+=0;num2+=9;num3+=2;num4+=11;z=1}
        else{num1-=0;num2-=9;num3-=2;num4-=11;z=0}
    }

    private fun rotateUnderscore() {
        // Lógica de rotación para la pieza _-_
        when (z) {
            0 -> {num1 += 0;num2 += 1;num3 += 1;num4 += 9;z = 1}
            1 -> {num1 += 9;num2 += 0;num3 += 0;num4 += 0;z = 2}
            2 -> {num1 -= 9;num2 -= 1;num3 -= 1;num4 += 0;z = 3}
            else -> {num1 += 0;num2 += 0;num3 += 0;num4 -= 9;z = 0}
        }
    }

    private fun rotateLine() {
        // Lógica de rotación para la pieza |
        if(z==0){
            num1+=1;num2+=10;num3+=19;num4+=28;z=1}
        else{num1-=1;num2-=10;num3-=19;num4-=28;z=0}
    }

    private var x = 0
    private fun list2(){
        //Este código llama a la función updateText para cada número
        for (num in arrayOf(num1, num2, num3, num4)) {
            updateText(num)
        }
        shapes()
    }
    // Función que cambia el texto de cada num con un if
    private fun updateText(index: Int) {
        if (downArray[index - 10].text == "") {
            downArray[index - 10].text = "1"
        }
    }
    private fun R_L(){
        for (i in 0 until 150) {
            if (tVListGrande[i].text != "0") {
                tVListGrande[i].setBackgroundResource(R.drawable.block)
            }
        }
    }
    private var points  = 0
    private fun landing(){
        //Lista que comprende los valores máximos del GridLayout para que las piezas no bajen más que eso
        val validNumbers = (140..149).toList()
        //Variable booleana que comprueba si alguno de los nums está en los validNumbers
        val cantGoDown = (num1 in validNumbers) || (num2 in validNumbers) || (num3 in validNumbers) || (num4 in validNumbers)
        if (cantGoDown){
            array[0].text="0";array[1].text="0";array[2].text="0";array[3].text="0"
            //añadir aqui sonido
        }

        if (array[0].text=="1"||array[1].text=="1"||array[2].text=="1"||array[3].text=="1"){
            array[0].text="0";array[1].text="0";array[2].text="0";array[3].text="0"
        }
    }
    private fun colors(){
        R_L()
        arrayCollectPreviousOne[0].setBackgroundResource(R.drawable.block);arrayCollectPreviousOne[1].setBackgroundResource(R.drawable.block)
        arrayCollectPreviousOne[2].setBackgroundResource(R.drawable.block);arrayCollectPreviousOne[3].setBackgroundResource(R.drawable.block)
        when(shape_is){
            1-> {array[0].setBackgroundResource(R.drawable.orange);array[1].setBackgroundResource(R.drawable.orange);array[2].setBackgroundResource(R.drawable.orange);array[3].setBackgroundResource(R.drawable.orange)}
            2-> {array[0].setBackgroundResource(R.drawable.yellow);array[1].setBackgroundResource(R.drawable.yellow);array[2].setBackgroundResource(R.drawable.yellow);array[3].setBackgroundResource(R.drawable.yellow)}
            3-> {array[0].setBackgroundResource(R.drawable.green);array[1].setBackgroundResource(R.drawable.green);array[2].setBackgroundResource(R.drawable.green);array[3].setBackgroundResource(R.drawable.green)}
            4-> {array[0].setBackgroundResource(R.drawable.purple);array[1].setBackgroundResource(R.drawable.purple);array[2].setBackgroundResource(R.drawable.purple);array[3].setBackgroundResource(R.drawable.purple)}
            5-> {array[0].setBackgroundResource(R.drawable.pink);array[1].setBackgroundResource(R.drawable.pink);array[2].setBackgroundResource(R.drawable.pink);array[3].setBackgroundResource(R.drawable.pink)}
            6-> {array[0].setBackgroundResource(R.drawable.blue);array[1].setBackgroundResource(R.drawable.blue);array[2].setBackgroundResource(R.drawable.blue);array[3].setBackgroundResource(R.drawable.blue)}
            7-> {array[0].setBackgroundResource(R.drawable.red);array[1].setBackgroundResource(R.drawable.red);array[2].setBackgroundResource(R.drawable.red);array[3].setBackgroundResource(R.drawable.red)}
        }
        if (array[0].text=="0"&&array[1].text=="0"&&array[2].text=="0"&&array[3].text=="0"){points +=15;list2()}
        else{array.removeAll(array);arrayCollectPreviousOne.removeAll(arrayCollectPreviousOne)
            Handler(Looper.getMainLooper()).postDelayed({
                controls()}, 800)}
    }
}
