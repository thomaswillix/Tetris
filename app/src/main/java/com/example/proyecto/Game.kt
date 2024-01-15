package com.example.proyectomviles

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.TextView
import kotlin.random.Random
import kotlin.random.nextInt

class Game : AppCompatActivity() {
    //Grid Elements
    private val tVListGrande = arrayListOf<TextView>()
    private val tVListPequenia = arrayListOf<TextView>()
    private lateinit var aux: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game)

        // Usuario
        val nom = intent.getStringExtra("userName")
        val user = findViewById<TextView>(R.id.username)
        user.text = "$nom"
        // Botón de Pausa
        /*val pause = findViewById<Button>(R.id.pause)
        pause.setOnClickListener(){
            stop = 1;
        }*/
        //Texto auxiliar
        aux = findViewById(R.id.text1)
        //Comienzo del juego
        initTextViewLists()
        nextShape()
    }
    //Inicialización de las listas contenedoras de los elementos de los GridLayouts
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
    var stop = 0
    var lines = 0
    var once = true

    // Revisar método
    private fun loseALine(){
        val lastIndex = 149
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
                    tVListGrande[i+10].text=""
                    // Se suman 100 puntos por línea completada.
                    points+=100
                    lines = 1 // Valor por defeco
                    when (startIndex) {
                        in 10..140 -> {
                            // Calcular el número de líneas en función del índice inicial
                            lines = ((startIndex - 1) / 10) + 2
                        }
                    }
                }
            }
        }
        Handler().postDelayed({
            for (lineCheck in 2..15) {
                val startIndex = (lineCheck - 2) * 10 + 1
                val endIndex = startIndex + 9

                if (lines == lineCheck && endIndex < tVListGrande.size) {
                    if ((startIndex..endIndex).all { tVListGrande[it].text == "" }) {
                        tVListGrande[startIndex].text = tVListGrande[startIndex -10].text
                        tVListGrande[startIndex].background = tVListGrande[startIndex -10].background
                        tVListGrande[startIndex -10].setBackgroundResource(R.drawable.block)
                        tVListGrande[startIndex -10].text = ""
                    }
                }
                lines ++
            }
        }, 500)
        val pointsText = findViewById<TextView>(R.id.points)
        pointsText.text = "Points: $points"
    }
    //All good
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
                tVListPequenia[5].setBackgroundResource(R.drawable.orange);tVListPequenia[6].setBackgroundResource(R.drawable.orange);
                tVListPequenia[7].setBackgroundResource(R.drawable.orange);tVListPequenia[8].setBackgroundResource(R.drawable.orange)
            }
            //Inverted S
            2 -> {
                tVListPequenia[3].setBackgroundResource(R.drawable.yellow);tVListPequenia[4].setBackgroundResource(R.drawable.yellow);
                tVListPequenia[7].setBackgroundResource(R.drawable.yellow);tVListPequenia[8].setBackgroundResource(R.drawable.yellow);
            }
            //square
            3 -> {
                tVListPequenia[4].setBackgroundResource(R.drawable.green);tVListPequenia[5].setBackgroundResource(R.drawable.green);
                tVListPequenia[7].setBackgroundResource(R.drawable.green);tVListPequenia[8].setBackgroundResource(R.drawable.green);
            }
            //Inverted L
            4 -> {
                tVListPequenia[3].setBackgroundResource(R.drawable.purple);tVListPequenia[6].setBackgroundResource(R.drawable.purple);
                tVListPequenia[7].setBackgroundResource(R.drawable.purple);tVListPequenia[8].setBackgroundResource(R.drawable.purple);
            }
            //S
            5 -> {
                tVListPequenia[5].setBackgroundResource(R.drawable.pink); tVListPequenia[4].setBackgroundResource(R.drawable.pink);
                tVListPequenia[7].setBackgroundResource(R.drawable.pink);tVListPequenia[6].setBackgroundResource(R.drawable.pink)
            }
            // _-_
            6 -> {
                tVListPequenia[4].setBackgroundResource(R.drawable.blue); tVListPequenia[6].setBackgroundResource(R.drawable.blue);
                tVListPequenia[7].setBackgroundResource(R.drawable.blue);tVListPequenia[8].setBackgroundResource(R.drawable.blue)
            }
            // |
            7 -> {
                tVListPequenia[1].setBackgroundResource(R.drawable.red); tVListPequenia[4].setBackgroundResource(R.drawable.red);
                tVListPequenia[7].setBackgroundResource(R.drawable.red);tVListPequenia[10].setBackgroundResource(R.drawable.red)
            }
        }
        nextUp = random
        if (once) {
            /* tVListPequenia[0].setBackgroundResource(R.drawable.block); tVListPequenia[1].setBackgroundResource(R.drawable.block);
            tVListPequenia[2].setBackgroundResource(R.drawable.block); tVListPequenia[3].setBackgroundResource(R.drawable.block);
            tVListPequenia[4].setBackgroundResource(R.drawable.block); tVListPequenia[5].setBackgroundResource(R.drawable.block);
            tVListPequenia[6].setBackgroundResource(R.drawable.block); tVListPequenia[7].setBackgroundResource(R.drawable.block);
            tVListPequenia[8].setBackgroundResource(R.drawable.block); tVListPequenia[9].setBackgroundResource(R.drawable.block);
            tVListPequenia[10].setBackgroundResource(R.drawable.block); tVListPequenia[11].setBackgroundResource(R.drawable.block);*/
            for (i in 0 until tVListPequenia.size) {
                tVListPequenia[i].setBackgroundResource(R.drawable.block)
            }

            tVListPequenia[4].setBackgroundResource(R.drawable.green);tVListPequenia[5].setBackgroundResource(R.drawable.green);
            tVListPequenia[7].setBackgroundResource(R.drawable.green);tVListPequenia[8].setBackgroundResource(R.drawable.green);
            nextUp = 3
            once = false
        }
        if (!starting) {
            Handler().postDelayed({ shapes() }, 1500)
            starting = true
        }
    }

    private fun shapes(){
        if (tVListGrande[10].text == "0" || tVListGrande[11].text == "0"|| tVListGrande[12].text == "0"|| tVListGrande[13].text == "0"|| tVListGrande[14].text == "0"
            || tVListGrande[15].text == "0"|| tVListGrande[16].text == "0"|| tVListGrande[17].text == "0"|| tVListGrande[18].text == "0"|| tVListGrande[19].text == "0"){

             for (i in 0 until tVListPequenia.size) {
                tVListPequenia[i].setBackgroundResource(R.drawable.block)
            }
            tVListGrande[3].setBackgroundResource(R.drawable.red);tVListGrande[4].setBackgroundResource(R.drawable.red);tVListGrande[5].setBackgroundResource(R.drawable.red);tVListGrande[6].setBackgroundResource(R.drawable.red)
        }
        loseALine()
        if (stop == 0){
            a=0;b=0;z=0;x=0
            if (tVListGrande[23].text == "0" || tVListGrande[24].text == "0" || tVListGrande[25].text == "0" || tVListGrande[26].text == "0"){
                nextUp=7
            }
            array.removeAll(array);arrayCollectPreviousOne.removeAll(arrayCollectPreviousOne)
            shape_is = nextUp
            nextShape()
            when(shape_is){
                //L
                1 ->{tVListGrande[4].setBackgroundResource(R.drawable.orange); tVListGrande[5].setBackgroundResource(R.drawable.orange); tVListGrande[6].setBackgroundResource(R.drawable.orange);tVListGrande[14].setBackgroundResource(R.drawable.orange)}
                //Inverted S
                2 ->{tVListGrande[4].setBackgroundResource(R.drawable.yellow);tVListGrande[5].setBackgroundResource(R.drawable.yellow);tVListGrande[14].setBackgroundResource(R.drawable.yellow);tVListGrande[13].setBackgroundResource(R.drawable.yellow);}
                //square
                3 ->{tVListGrande[4].setBackgroundResource(R.drawable.green);tVListGrande[5].setBackgroundResource(R.drawable.green);tVListGrande[14].setBackgroundResource(R.drawable.green);tVListGrande[15].setBackgroundResource(R.drawable.green);}
                //Inverted L
                4 ->{tVListGrande[3].setBackgroundResource(R.drawable.purple);tVListGrande[4].setBackgroundResource(R.drawable.purple);tVListGrande[5].setBackgroundResource(R.drawable.purple);tVListGrande[15].setBackgroundResource(R.drawable.purple);}
                //S
                5 ->{tVListGrande[4].setBackgroundResource(R.drawable.pink); tVListGrande[5].setBackgroundResource(R.drawable.pink); tVListGrande[15].setBackgroundResource(R.drawable.pink);tVListGrande[16].setBackgroundResource(R.drawable.pink)}
                // _-_
                6 ->{tVListGrande[4].setBackgroundResource(R.drawable.blue); tVListGrande[13].setBackgroundResource(R.drawable.blue); tVListGrande[14].setBackgroundResource(R.drawable.blue);tVListGrande[15].setBackgroundResource(R.drawable.blue)}
                // |
                7 ->{tVListGrande[3].setBackgroundResource(R.drawable.red); tVListGrande[4].setBackgroundResource(R.drawable.red); tVListGrande[5].setBackgroundResource(R.drawable.red);tVListGrande[6].setBackgroundResource(R.drawable.red)}
            }
            controls()
        } else{}
    }
    val arrayCollectPreviousOne = arrayListOf<TextView>()
    val array = arrayListOf<TextView>()
    val downArray: ArrayList<TextView> get() = arrayListOf<TextView>().apply {
        addAll(tVListGrande)
        add(aux)
    }
    var a = 0
    var b = 0
    var z = 0
    private fun controls(){
        val right = findViewById<Button>(R.id.right)
        val left = findViewById<Button>(R.id.left)
        val spinRight = findViewById<Button>(R.id.spin)
        val down = findViewById<Button>(R.id.down)
        val numbers = listOf(num1, num2, num3, num4)

        right.setOnClickListener{
            if (a == 0) {
                val invalidValues = listOf(9, 19, 29, 39, 49, 59, 69, 79, 89, 99, 109, 119, 129, 139, 149)
                if (numbers.none { it in invalidValues } && numbers.all { downArray[it + 1].text == "" }) {
                    num1++
                    num2++
                    num3++
                    num4++
                }
            }
        }
        left.setOnClickListener {
            if (b == 0) {
                val invalidValues = listOf(0, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 110, 120, 130, 140)
                if (numbers.none { it in invalidValues } && numbers.all { downArray[it - 1].text == "" }) {
                    num1--
                    num2--
                    num3--
                    num4--
                }
            }
        }
        spinRight.setOnClickListener{
            val isRotationAllowed = isRotationPossible()
            if (isRotationAllowed) {
                rotatePiece()
            }
        }

        down.setOnClickListener {
            puedeBajar()
            while (x == 0) {
                val currentCells = listOf(downArray[num1], downArray[num2], downArray[num3], downArray[num4])
                arrayCollectPreviousOne.addAll(currentCells)

                num1 += 10
                num2 += 10
                num3 += 10
                num4 += 10
            }
        }
        if(a==0||b==0){
            num1+=10;num2+=10;num3+=10;num4+=10
        }
        arrayCollectPreviousOne.add(downArray[num1]);arrayCollectPreviousOne.add(downArray[num2]);arrayCollectPreviousOne.add(downArray[num3]);arrayCollectPreviousOne.add(downArray[num4])
        array.add(downArray[num1]);array.add(downArray[num2]);array.add(downArray[num3]);array.add(downArray[num4])
        landing();colors()
    }
    private fun puedeBajar(){
        val maxPosition = 149
        val checkCondition = listOf(num1, num2, num3, num4).any { it > maxPosition }
        val currentCells = listOf(downArray[num1], downArray[num2], downArray[num3], downArray[num4])

        if (currentCells.any { it.text == "1" } || checkCondition) {
            num1 -= 10
            num2 -= 10
            num3 -= 10
            num4 -= 10
            x = 1
            a = 1
            b = 1
            points += 5
            landing()
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
            3 -> {} // No cambia (es un cuadrado)
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
            0 -> {num1 += 1;num2 += 1;num3 += 10;num4 += 10;z = 1}
            1 -> {num1 += 9;num2 += 0;num3 -= 9;num4 -= 2;z = 2}
            2 -> {num1 -= 10;num2 -= 10;num3 -= 1;num4 -= 1;z = 3}
            else -> {num1 += 2;num2 += 9;num3 += 0;num4 -= 9;z = 0}
        }
    }

    private fun rotateInvertedS() {
        // Lógica de rotación para la pieza S invertida
        if(z==0){
            num1+=1;num2+=9;num3+=0;num4+=8;z=1
        } else{num1-=1;num2-=9;num3-=0;num4-=9;z=0}
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
            else -> {num1 += 0;num2 += 0;num3 += 0;num4 += 9;z = 0}
        }
    }
    private fun rotateLine() {
        // Lógica de rotación para la pieza |
        if(z==0){
            num1+=1;num2+=10;num3+=19;num4+=28;z=1}
        else{num1-=1;num2-=10;num3-=19;num4-=28;z=0}
    }

    var x = 0
    private fun list2(){
        if (downArray[num1-10].text == ""){ downArray[num1-10].text="1" } else if (downArray[num1-10].text=="0"){}
        if (downArray[num2-10].text == ""){ downArray[num2-10].text="1" } else if (downArray[num2-10].text=="0"){}
        if (downArray[num3-10].text == ""){ downArray[num3-10].text="1" } else if (downArray[num3-10].text=="0"){}
        if (downArray[num4-10].text == ""){ downArray[num4-10].text="1" } else if (downArray[num4-10].text=="0"){}
        shapes()
    }
    private fun R_L(){
        for (i in 0 until 150) {
            if (tVListGrande[i].text != "0") {
                tVListGrande[i].setBackgroundResource(R.drawable.block)
            }
        }
    }
    var points  = 0
    private fun landing(){
        if (num1==140 || num1==141 || num1==142 || num1==143 || num1==144 || num1==145 || num1==146 || num1==147 || num1==148 || num1==149 ||
            num2==140 || num2==141 || num2==142 || num2==143 || num2==144 || num2==145 || num2==146 || num2==147 || num2==148 || num2==149 ||
            num3==140 || num3==141 || num3==142 || num3==143 || num3==144 || num3==145 || num3==146 || num3==147 || num3==148 || num3==149 ||
            num4==140 || num4==141 || num4==142 || num4==143 || num4==144 || num4==145 || num4==146 || num4==147 || num4==148 || num4==149){

            array[0].text="0";array[1].text="0";array[2].text="0";array[3].text="0"
        }

        if (array[0].text=="1"||array[1].text=="1"||array[2].text=="1"||array[3].text=="1"){

            array[0].text="0";array[1].text="0";array[2].text="0";array[3].text="0"
        }
    }
    private fun colors(){
        R_L()
        a=0;b=0
        arrayCollectPreviousOne[0].setBackgroundResource(R.drawable.block);arrayCollectPreviousOne[1].setBackgroundResource(R.drawable.block)
        arrayCollectPreviousOne[2].setBackgroundResource(R.drawable.block);arrayCollectPreviousOne[3].setBackgroundResource(R.drawable.block);
        when(shape_is){
            1-> {array[0].setBackgroundResource(R.drawable.orange);array[1].setBackgroundResource(R.drawable.orange);array[2].setBackgroundResource(R.drawable.orange);array[3].setBackgroundResource(R.drawable.orange)}
            2-> {array[0].setBackgroundResource(R.drawable.yellow);array[1].setBackgroundResource(R.drawable.yellow);array[2].setBackgroundResource(R.drawable.yellow);array[3].setBackgroundResource(R.drawable.yellow)}
            3-> {array[0].setBackgroundResource(R.drawable.green);array[1].setBackgroundResource(R.drawable.green);array[2].setBackgroundResource(R.drawable.green);array[3].setBackgroundResource(R.drawable.green)}
            4-> {array[0].setBackgroundResource(R.drawable.purple);array[1].setBackgroundResource(R.drawable.purple);array[2].setBackgroundResource(R.drawable.purple);array[3].setBackgroundResource(R.drawable.purple)}
            5-> {array[0].setBackgroundResource(R.drawable.pink);array[1].setBackgroundResource(R.drawable.pink);array[2].setBackgroundResource(R.drawable.pink);array[3].setBackgroundResource(R.drawable.pink)}
            6-> {array[0].setBackgroundResource(R.drawable.blue);array[1].setBackgroundResource(R.drawable.blue);array[2].setBackgroundResource(R.drawable.blue);array[3].setBackgroundResource(R.drawable.blue)}
            7-> {array[0].setBackgroundResource(R.drawable.red);array[1].setBackgroundResource(R.drawable.red);array[2].setBackgroundResource(R.drawable.red);array[3].setBackgroundResource(R.drawable.red)}
        }
        if (array[0].text=="0"&&array[1].text=="0"||array[2].text=="0"||array[3].text=="0"){points +=15;list2()}
        else{array.removeAll(array);arrayCollectPreviousOne.removeAll(arrayCollectPreviousOne);Handler().postDelayed({controls()}, 600)}
    }
}package com.example.proyectomviles

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.TextView
import kotlin.random.Random
import kotlin.random.nextInt

class Game : AppCompatActivity() {
    //Grid Elements
    private val tVListGrande = arrayListOf<TextView>()
    private val tVListPequenia = arrayListOf<TextView>()
    private lateinit var aux: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game)

        // Usuario
        val nom = intent.getStringExtra("userName")
        val user = findViewById<TextView>(R.id.username)
        user.text = "$nom"
        // Botón de Pausa
        /*val pause = findViewById<Button>(R.id.pause)
        pause.setOnClickListener(){
            stop = 1;
        }*/
        //Texto auxiliar
        aux = findViewById(R.id.text1)
        //Comienzo del juego
        initTextViewLists()
        nextShape()
    }
    //Inicialización de las listas contenedoras de los elementos de los GridLayouts
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
    var stop = 0
    var lines = 0
    var once = false

    private fun loseALine(){
        val lastIndex = 140
        for (startIndex in 10..lastIndex step 10) {
            if ((startIndex until startIndex + 10).all { tVListGrande[it].text == "0" }) {
                for (i in startIndex until startIndex + 10) {
                    tVListGrande[i].setBackgroundResource(R.drawable.block)
                    tVListGrande[i+10].text=""
                    points+=100
                    var lines = 1 // Valor por defecto para cualquier otro caso
                    when (startIndex) {
                        in 10..140 -> {
                            // Calcular el número de líneas en función del índice inicial
                            lines = ((startIndex - 1) / 10) + 2
                        }
                    }
                }
            }
        }
        Handler().postDelayed({
            for (lineCheck in 2..15) {
                val startIndex = (lineCheck - 2) * 10 + 1
                val endIndex = startIndex + 9

                if (lines == lineCheck && endIndex < tVListGrande.size) {
                    if ((startIndex..endIndex).all { tVListGrande[it].text == "" }) {
                        tVListGrande[startIndex].text = tVListGrande[startIndex -10].text
                        tVListGrande[startIndex].background = tVListGrande[startIndex -10].background
                        tVListGrande[startIndex -10].setBackgroundResource(R.drawable.block)
                        tVListGrande[startIndex -10].text = ""
                    }
                }
                lines ++
            }
        }, 500)
        val pointsText = findViewById<TextView>(R.id.points)
        pointsText.text = "Points: $points"
    }

    private fun nextShape() {
        /* 1   2   3
        *  4   5   6
        *  7   8   9
        *  10  11  12*/
        /*tVListPequenia[0].setBackgroundResource(R.drawable.block); tVListPequenia[1].setBackgroundResource(R.drawable.block);
        tVListPequenia[2].setBackgroundResource(R.drawable.block); tVListPequenia[3].setBackgroundResource(R.drawable.block);
        tVListPequenia[4].setBackgroundResource(R.drawable.block); tVListPequenia[5].setBackgroundResource(R.drawable.block);
        tVListPequenia[6].setBackgroundResource(R.drawable.block); tVListPequenia[7].setBackgroundResource(R.drawable.block);
        tVListPequenia[8].setBackgroundResource(R.drawable.block); tVListPequenia[9].setBackgroundResource(R.drawable.block);
        tVListPequenia[10].setBackgroundResource(R.drawable.block); tVListPequenia[11].setBackgroundResource(R.drawable.block);*/
        for (i in 0 until tVListPequenia.size) {
            tVListPequenia[i].setBackgroundResource(R.drawable.block)
        }

        val random = Random.nextInt(1..7)
        when (random) {
            //L
            1 -> {
                tVListPequenia[5].setBackgroundResource(R.drawable.orange);tVListPequenia[6].setBackgroundResource(R.drawable.orange);
                tVListPequenia[7].setBackgroundResource(R.drawable.orange);tVListPequenia[8].setBackgroundResource(R.drawable.orange)
            }
            //Inverted S
            2 -> {
                tVListPequenia[3].setBackgroundResource(R.drawable.yellow);tVListPequenia[4].setBackgroundResource(R.drawable.yellow);
                tVListPequenia[7].setBackgroundResource(R.drawable.yellow);tVListPequenia[8].setBackgroundResource(R.drawable.yellow);
            }
            //square
            3 -> {
                tVListPequenia[4].setBackgroundResource(R.drawable.green);tVListPequenia[5].setBackgroundResource(R.drawable.green);
                tVListPequenia[7].setBackgroundResource(R.drawable.green);tVListPequenia[8].setBackgroundResource(R.drawable.green);
            }
            //Inverted L
            4 -> {
                tVListPequenia[3].setBackgroundResource(R.drawable.purple);tVListPequenia[6].setBackgroundResource(R.drawable.purple);
                tVListPequenia[7].setBackgroundResource(R.drawable.purple);tVListPequenia[8].setBackgroundResource(R.drawable.purple);
            }
            //S
            5 -> {
                tVListPequenia[5].setBackgroundResource(R.drawable.pink); tVListPequenia[4].setBackgroundResource(R.drawable.pink);
                tVListPequenia[7].setBackgroundResource(R.drawable.pink);tVListPequenia[6].setBackgroundResource(R.drawable.pink)
            }
            // _-_
            6 -> {
                tVListPequenia[4].setBackgroundResource(R.drawable.blue); tVListPequenia[6].setBackgroundResource(R.drawable.blue);
                tVListPequenia[7].setBackgroundResource(R.drawable.blue);tVListPequenia[8].setBackgroundResource(R.drawable.blue)
            }
            // |
            7 -> {
                tVListPequenia[1].setBackgroundResource(R.drawable.red); tVListPequenia[4].setBackgroundResource(R.drawable.red);
                tVListPequenia[7].setBackgroundResource(R.drawable.red);tVListPequenia[10].setBackgroundResource(R.drawable.red)
            }
        }
        nextUp = random
        if (!once) {
            /* tVListPequenia[0].setBackgroundResource(R.drawable.block); tVListPequenia[1].setBackgroundResource(R.drawable.block);
            tVListPequenia[2].setBackgroundResource(R.drawable.block); tVListPequenia[3].setBackgroundResource(R.drawable.block);
            tVListPequenia[4].setBackgroundResource(R.drawable.block); tVListPequenia[5].setBackgroundResource(R.drawable.block);
            tVListPequenia[6].setBackgroundResource(R.drawable.block); tVListPequenia[7].setBackgroundResource(R.drawable.block);
            tVListPequenia[8].setBackgroundResource(R.drawable.block); tVListPequenia[9].setBackgroundResource(R.drawable.block);
            tVListPequenia[10].setBackgroundResource(R.drawable.block); tVListPequenia[11].setBackgroundResource(R.drawable.block);*/
            for (i in 0 until tVListPequenia.size) {
                tVListPequenia[i].setBackgroundResource(R.drawable.block)
            }

            tVListPequenia[4].setBackgroundResource(R.drawable.green);tVListPequenia[5].setBackgroundResource(R.drawable.green);
            tVListPequenia[7].setBackgroundResource(R.drawable.green);tVListPequenia[8].setBackgroundResource(R.drawable.green);
            nextUp = 3
            once = true
        }
        if (!starting) {
            Handler().postDelayed({ shapes() }, 1500)
            starting = true
        }
    }

    private fun shapes(){
        if (tVListGrande[10].text == "0" || tVListGrande[11].text == "0"|| tVListGrande[12].text == "0"|| tVListGrande[13].text == "0"|| tVListGrande[14].text == "0"
            || tVListGrande[15].text == "0"|| tVListGrande[16].text == "0"|| tVListGrande[17].text == "0"|| tVListGrande[18].text == "0"|| tVListGrande[19].text == "0"){
            /*tVListPequenia[0].setBackgroundResource(R.drawable.block); tVListPequenia[1].setBackgroundResource(R.drawable.block); tVListPequenia[2].setBackgroundResource(R.drawable.block); tVListPequenia[3].setBackgroundResource(R.drawable.block);
            tVListPequenia[4].setBackgroundResource(R.drawable.block); tVListPequenia[5].setBackgroundResource(R.drawable.block); tVListPequenia[6].setBackgroundResource(R.drawable.block); tVListPequenia[7].setBackgroundResource(R.drawable.block);
            tVListPequenia[8].setBackgroundResource(R.drawable.block); tVListPequenia[9].setBackgroundResource(R.drawable.block); tVListPequenia[10].setBackgroundResource(R.drawable.block); tVListPequenia[11].setBackgroundResource(R.drawable.block);*/
            for (i in 0 until tVListPequenia.size) {
                tVListPequenia[i].setBackgroundResource(R.drawable.block)
            }
            tVListGrande[3].setBackgroundResource(R.drawable.red);tVListGrande[4].setBackgroundResource(R.drawable.red);tVListGrande[5].setBackgroundResource(R.drawable.red);tVListGrande[6].setBackgroundResource(R.drawable.red)
        }
        loseALine()
        if (stop == 0){
            a=0;b=0;z=0;x=0
            if (tVListGrande[23].text == "0" || tVListGrande[24].text == "0" || tVListGrande[25].text == "0" || tVListGrande[26].text == "0"){nextUp=7}
            array.removeAll(array);arrayCollectPreviousOne.removeAll(arrayCollectPreviousOne)
            shape_is = nextUp
            nextShape()
            when(shape_is){
                //L
                1 ->{tVListGrande[4].setBackgroundResource(R.drawable.orange); tVListGrande[5].setBackgroundResource(R.drawable.orange); tVListGrande[6].setBackgroundResource(R.drawable.orange);tVListGrande[14].setBackgroundResource(R.drawable.orange)}
                //Inverted S
                2 ->{tVListGrande[4].setBackgroundResource(R.drawable.yellow);tVListGrande[5].setBackgroundResource(R.drawable.yellow);tVListGrande[14].setBackgroundResource(R.drawable.yellow);tVListGrande[13].setBackgroundResource(R.drawable.yellow);}
                //square
                3 ->{tVListGrande[4].setBackgroundResource(R.drawable.green);tVListGrande[5].setBackgroundResource(R.drawable.green);tVListGrande[14].setBackgroundResource(R.drawable.green);tVListGrande[15].setBackgroundResource(R.drawable.green);}
                //Inverted L
                4 ->{tVListGrande[3].setBackgroundResource(R.drawable.purple);tVListGrande[4].setBackgroundResource(R.drawable.purple);tVListGrande[5].setBackgroundResource(R.drawable.purple);tVListGrande[15].setBackgroundResource(R.drawable.purple);}
                //S
                5 ->{tVListGrande[4].setBackgroundResource(R.drawable.pink); tVListGrande[5].setBackgroundResource(R.drawable.pink); tVListGrande[15].setBackgroundResource(R.drawable.pink);tVListGrande[16].setBackgroundResource(R.drawable.pink)}
                // _-_
                6 ->{tVListGrande[4].setBackgroundResource(R.drawable.blue); tVListGrande[13].setBackgroundResource(R.drawable.blue); tVListGrande[14].setBackgroundResource(R.drawable.blue);tVListGrande[15].setBackgroundResource(R.drawable.blue)}
                // |
                7 ->{tVListGrande[3].setBackgroundResource(R.drawable.red); tVListGrande[4].setBackgroundResource(R.drawable.red); tVListGrande[5].setBackgroundResource(R.drawable.red);tVListGrande[6].setBackgroundResource(R.drawable.red)}
            }
            list()
        } else{}
    }
    val arrayCollectPreviousOne = arrayListOf<TextView>()
    val array = arrayListOf<TextView>()
    val downArray: ArrayList<TextView> get() = arrayListOf<TextView>().apply {
        addAll(tVListGrande)
        add(aux)
    }
    var a = 0
    var b = 0
    var z = 0
    private fun list(){
        val right = findViewById<Button>(R.id.right)
        val left = findViewById<Button>(R.id.left)
        val spinRight = findViewById<Button>(R.id.spin)
        val down = findViewById<Button>(R.id.down)
        val numbers = listOf(num1, num2, num3, num4)

        right.setOnClickListener{
            if (a == 0) {
                val invalidValues = listOf(10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 110, 120, 130, 140, 150)
                if (numbers.none { it in invalidValues } && numbers.all { downArray[it + 1].text == "" }) {
                    num1++
                    num2++
                    num3++
                    num4++
                }
            }
        }
        left.setOnClickListener {
            if (b == 0) {
                val invalidValues = listOf(1, 11, 21, 31, 41, 51, 61, 71, 81, 91, 101, 111, 121, 131, 141)

                if (numbers.none { it in invalidValues } &&
                    numbers.all { downArray[it - 1].text == "" }
                ) {
                    num1--
                    num2--
                    num3--
                    num4--
                }
            }
        }
        spinRight.setOnClickListener{
            val isRotationAllowed = isRotationPossible()
            if (isRotationAllowed) {
                rotatePiece()
            }
        }

        down.setOnClickListener {
            puedeBajar()
            while (x == 0) {
                val currentCells = listOf(downArray[num1], downArray[num2], downArray[num3], downArray[num4])
                arrayCollectPreviousOne.addAll(currentCells)

                num1 += 10
                num2 += 10
                num3 += 10
                num4 += 10
            }
        }
        puedeBajar()
        if(a==0||b==0){
            num1+=10;num2+=10;num3+=10;num4+=10
        }
        arrayCollectPreviousOne.add(downArray[num1]);arrayCollectPreviousOne.add(downArray[num2]);arrayCollectPreviousOne.add(downArray[num3]);arrayCollectPreviousOne.add(downArray[num4])
        array.add(downArray[num1]);array.add(downArray[num2]);array.add(downArray[num3]);array.add(downArray[num4])
        landing();colors()
    }
    private fun puedeBajar(){
        val maxPosition = 150
        val checkCondition = listOf(num1, num2, num3, num4).any { it > maxPosition }
        val currentCells = listOf(downArray[num1], downArray[num2], downArray[num3], downArray[num4])

        if (currentCells.any { it.text == "1" } || checkCondition) {
            num1 -= 10
            num2 -= 10
            num3 -= 10
            num4 -= 10
            x = 1
            a = 1
            b = 1
            points += 5
            landing()
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
            3 -> {} // No cambia (es un cuadrado)
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
            0 -> {num1 += 1;num2 += 1;num3 += 10;num4 += 10;z = 1}
            1 -> {num1 += 9;num2 += 0;num3 -= 9;num4 -= 2;z = 2}
            2 -> {num1 -= 10;num2 -= 10;num3 -= 1;num4 -= 1;z = 3}
            else -> {num1 += 2;num2 += 9;num3 += 0;num4 -= 9;z = 0}
        }
    }

    private fun rotateInvertedS() {
        // Lógica de rotación para la pieza S invertida
        if(z==0){
            num1+=1;num2+=9;num3+=0;num4+=8;z=1
        } else{num1-=1;num2-=9;num3-=0;num4-=9;z=0}
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
            else -> {num1 += 0;num2 += 0;num3 += 0;num4 += 9;z = 0}
        }
    }
    private fun rotateLine() {
        // Lógica de rotación para la pieza |
        if(z==0){
            num1+=1;num2+=10;num3+=19;num4+=28;z=1}
        else{num1-=1;num2-=10;num3-=19;num4-=28;z=0}
    }

    var x = 0
    private fun list2(){
        if (downArray[num1-10].text == ""){ downArray[num1-10].text="1" } else if (downArray[num1-10].text=="0"){}
        if (downArray[num2-10].text == ""){ downArray[num2-10].text="1" } else if (downArray[num2-10].text=="0"){}
        if (downArray[num3-10].text == ""){ downArray[num3-10].text="1" } else if (downArray[num3-10].text=="0"){}
        if (downArray[num4-10].text == ""){ downArray[num4-10].text="1" } else if (downArray[num4-10].text=="0"){}
        shapes()
    }
    private fun R_L(){
        for (i in 0 until 150) {
            if (tVListGrande[i].text != "0") {
                tVListGrande[i].setBackgroundResource(R.drawable.block)
            }
        }
    }
    var points  = 0
    private fun landing(){
        if (num1==141 || num1==142 || num1==143 || num1==144 || num1==145 || num1==146 || num1==147 || num1==148 || num1==149 || num1==150 ||
            num2==141 || num2==142 || num2==143 || num2==144 || num2==145 || num2==146 || num2==147 || num2==148 || num2==149 || num2==150 ||
            num3==141 || num3==142 || num3==143 || num3==144 || num3==145 || num3==146 || num3==147 || num3==148 || num3==149 || num3==150 ||
            num4==141 || num4==142 || num4==143 || num4==144 || num4==145 || num4==146 || num4==147 || num4==148 || num4==149 || num4==150){

            array[0].text="0";array[1].text="0";array[2].text="0";array[3].text="0"
        }

        if (array[0].text=="1"||array[1].text=="1"||array[2].text=="1"||array[3].text=="1"){

            array[0].text="0";array[1].text="0";array[2].text="0";array[3].text="0"
        }
    }
    private fun colors(){
        R_L()
        a=0;b=0
        arrayCollectPreviousOne[0].setBackgroundResource(R.drawable.block);arrayCollectPreviousOne[1].setBackgroundResource(R.drawable.block)
        arrayCollectPreviousOne[2].setBackgroundResource(R.drawable.block);arrayCollectPreviousOne[3].setBackgroundResource(R.drawable.block);
        when(shape_is){
            1-> {array[0].setBackgroundResource(R.drawable.orange);array[1].setBackgroundResource(R.drawable.orange);array[2].setBackgroundResource(R.drawable.orange);array[3].setBackgroundResource(R.drawable.orange)}
            2-> {array[0].setBackgroundResource(R.drawable.yellow);array[1].setBackgroundResource(R.drawable.yellow);array[2].setBackgroundResource(R.drawable.yellow);array[3].setBackgroundResource(R.drawable.yellow)}
            3-> {array[0].setBackgroundResource(R.drawable.green);array[1].setBackgroundResource(R.drawable.green);array[2].setBackgroundResource(R.drawable.green);array[3].setBackgroundResource(R.drawable.green)}
            4-> {array[0].setBackgroundResource(R.drawable.purple);array[1].setBackgroundResource(R.drawable.purple);array[2].setBackgroundResource(R.drawable.purple);array[3].setBackgroundResource(R.drawable.purple)}
            5-> {array[0].setBackgroundResource(R.drawable.pink);array[1].setBackgroundResource(R.drawable.pink);array[2].setBackgroundResource(R.drawable.pink);array[3].setBackgroundResource(R.drawable.pink)}
            6-> {array[0].setBackgroundResource(R.drawable.blue);array[1].setBackgroundResource(R.drawable.blue);array[2].setBackgroundResource(R.drawable.blue);array[3].setBackgroundResource(R.drawable.blue)}
            7-> {array[0].setBackgroundResource(R.drawable.red);array[1].setBackgroundResource(R.drawable.red);array[2].setBackgroundResource(R.drawable.red);array[3].setBackgroundResource(R.drawable.red)}
        }
        if (array[0].text=="0"&&array[1].text=="0"||array[2].text=="0"||array[3].text=="0"){points +=15;list2()}
        else{array.removeAll(array);arrayCollectPreviousOne.removeAll(arrayCollectPreviousOne);Handler().postDelayed({list()}, 600)}
    }
}
