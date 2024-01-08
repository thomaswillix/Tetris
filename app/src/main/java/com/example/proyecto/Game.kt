package com.example.proyecto

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
    private lateinit var B4: TextView
    private lateinit var B5: TextView
    private lateinit var B6: TextView
    private lateinit var B7: TextView
    private lateinit var B8: TextView
    private lateinit var B9: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game)

        val nom = intent.getStringExtra("userName")
        val user = findViewById<TextView>(R.id.username)
        user.text = "$nom"
        val pause = findViewById<Button>(R.id.pause)
        aux = findViewById(R.id.text1)
        B4 = findViewById(R.id.B4)
        B5 = findViewById(R.id.B5)
        B6 = findViewById(R.id.B6)
        B7 = findViewById(R.id.B7)
        B8 = findViewById(R.id.B8)
        B9 = findViewById(R.id.B9)

        initTextViewLists()
        next_shape()
    }

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
    var starting = 0
    var num1 = 0
    var num2 = 0
    var num3 = 0
    var num4 = 0
    var shape_is = 0
    var stop = 0
    var lines = 0
    var once = 0

  /* val bt1 = findViewById<TextView>(R.id.bt1);val bt2 = findViewById<TextView>(R.id.bt2);val bt3 = findViewById<TextView>(R.id.bt3);val bt4 = findViewById<TextView>(R.id.bt4)
    val bt5 = findViewById<TextView>(R.id.bt5);val bt6 = findViewById<TextView>(R.id.bt6);val bt7 = findViewById<TextView>(R.id.bt7);val bt8 = findViewById<TextView>(R.id.bt8)
    val bt9 = findViewById<TextView>(R.id.bt9);val bt10 = findViewById<TextView>(R.id.bt10);val bt11 = findViewById<TextView>(R.id.bt11);val bt12 = findViewById<TextView>(R.id.bt12)
    val B1 = findViewById<TextView>(R.id.B1);val B2 = findViewById<TextView>(R.id.B2);val B3 = findViewById<TextView>(R.id.B3);val B4 = findViewById<TextView>(R.id.B4)
    val B5 = findViewById<TextView>(R.id.B5);val B6 = findViewById<TextView>(R.id.B6);val B7 = findViewById<TextView>(R.id.B7);val B8 = findViewById<TextView>(R.id.B8)
    val B9 = findViewById<TextView>(R.id.B9);val B10 = findViewById<TextView>(R.id.B10); val B11 = findViewById<TextView>(R.id.B11);val B12 = findViewById<TextView>(R.id.B12)
    val B13 = findViewById<TextView>(R.id.B13);val B14 = findViewById<TextView>(R.id.B14);val B15 = findViewById<TextView>(R.id.B15);val B16 = findViewById<TextView>(R.id.B16)
    val B17 = findViewById<TextView>(R.id.B17);val B18 = findViewById<TextView>(R.id.B18);val B19 = findViewById<TextView>(R.id.B19);val B20 = findViewById<TextView>(R.id.B20)
    val B21 = findViewById<TextView>(R.id.B21);val B22 = findViewById<TextView>(R.id.B22);val B23 = findViewById<TextView>(R.id.B23);val B24 = findViewById<TextView>(R.id.B24)
    val B25 = findViewById<TextView>(R.id.B25);val B26 = findViewById<TextView>(R.id.B26);val B27 = findViewById<TextView>(R.id.B27);val B28 = findViewById<TextView>(R.id.B28)
    val B29 = findViewById<TextView>(R.id.B29);val B30 = findViewById<TextView>(R.id.B30);val B31 = findViewById<TextView>(R.id.B31);val B32 = findViewById<TextView>(R.id.B32)
    val B33 = findViewById<TextView>(R.id.B33);val B34 = findViewById<TextView>(R.id.B34);val B35 = findViewById<TextView>(R.id.B35);val B36 = findViewById<TextView>(R.id.B36)
    val B37 = findViewById<TextView>(R.id.B37);val B38 = findViewById<TextView>(R.id.B38);val B39 = findViewById<TextView>(R.id.B39);val B40 = findViewById<TextView>(R.id.B40)
    val B41 = findViewById<TextView>(R.id.B41);val B42 = findViewById<TextView>(R.id.B42);val B43 = findViewById<TextView>(R.id.B43);val B44 = findViewById<TextView>(R.id.B44)
    val B45 = findViewById<TextView>(R.id.B45);val B46 = findViewById<TextView>(R.id.B46);val B47 = findViewById<TextView>(R.id.B47);val B48 = findViewById<TextView>(R.id.B48)
    val B49 = findViewById<TextView>(R.id.B49);val B50 = findViewById<TextView>(R.id.B50);val B51 = findViewById<TextView>(R.id.B51);val B52 = findViewById<TextView>(R.id.B52)
    val B53 = findViewById<TextView>(R.id.B53);val B54 = findViewById<TextView>(R.id.B54);val B55 = findViewById<TextView>(R.id.B55);val B56 = findViewById<TextView>(R.id.B56)
    val B57 = findViewById<TextView>(R.id.B57);val B58 = findViewById<TextView>(R.id.B58);val B59 = findViewById<TextView>(R.id.B59);val B60 = findViewById<TextView>(R.id.B60)
    val B61 = findViewById<TextView>(R.id.B61);val B62 = findViewById<TextView>(R.id.B62);val B63 = findViewById<TextView>(R.id.B63);val B64 = findViewById<TextView>(R.id.B64)
    val B65 = findViewById<TextView>(R.id.B65);val B66 = findViewById<TextView>(R.id.B66);val B67 = findViewById<TextView>(R.id.B67);val B68 = findViewById<TextView>(R.id.B68)
    val B69 = findViewById<TextView>(R.id.B69);val B70 = findViewById<TextView>(R.id.B70);val B71 = findViewById<TextView>(R.id.B71);val B72 = findViewById<TextView>(R.id.B72)
    val B73 = findViewById<TextView>(R.id.B73);val B74 = findViewById<TextView>(R.id.B74);val B75 = findViewById<TextView>(R.id.B75);val B76 = findViewById<TextView>(R.id.B76)
    val B77 = findViewById<TextView>(R.id.B77);val B78 = findViewById<TextView>(R.id.B78);val B79 = findViewById<TextView>(R.id.B79);val B80 = findViewById<TextView>(R.id.B80)
    val B81 = findViewById<TextView>(R.id.B81);val B82 = findViewById<TextView>(R.id.B82);val B83 = findViewById<TextView>(R.id.B83);val B84 = findViewById<TextView>(R.id.B84)
    val B85 = findViewById<TextView>(R.id.B85);val B86 = findViewById<TextView>(R.id.B86);val B87 = findViewById<TextView>(R.id.B87);val B88 = findViewById<TextView>(R.id.B88)
    val B89 = findViewById<TextView>(R.id.B89);val B90 = findViewById<TextView>(R.id.B90);val B91 = findViewById<TextView>(R.id.B91);val B92 = findViewById<TextView>(R.id.B92)
    val B93 = findViewById<TextView>(R.id.B93);val B94 = findViewById<TextView>(R.id.B94);val B95 = findViewById<TextView>(R.id.B95);val B96 = findViewById<TextView>(R.id.B96)
    val B97 = findViewById<TextView>(R.id.B97);val B98 = findViewById<TextView>(R.id.B98);val B99 = findViewById<TextView>(R.id.B99);val B100 = findViewById<TextView>(R.id.B100)
    val B101 = findViewById<TextView>(R.id.B101);val B102 = findViewById<TextView>(R.id.B102);val B103 = findViewById<TextView>(R.id.B103);val B104 = findViewById<TextView>(R.id.B104)
    val B105 = findViewById<TextView>(R.id.B105);val B106 = findViewById<TextView>(R.id.B106);val B107 = findViewById<TextView>(R.id.B107);val B108 = findViewById<TextView>(R.id.B108)
    val B109 = findViewById<TextView>(R.id.B109);val B110 = findViewById<TextView>(R.id.B110);val B111 = findViewById<TextView>(R.id.B111);val B112 = findViewById<TextView>(R.id.B112)
    val B113 = findViewById<TextView>(R.id.B113);val B114 = findViewById<TextView>(R.id.B114);val B115 = findViewById<TextView>(R.id.B115);val B116 = findViewById<TextView>(R.id.B116)
    val B117 = findViewById<TextView>(R.id.B117);val B118 = findViewById<TextView>(R.id.B118);val B119 = findViewById<TextView>(R.id.B119);val B120 = findViewById<TextView>(R.id.B120)
    val B121 = findViewById<TextView>(R.id.B121);val B122 = findViewById<TextView>(R.id.B122);val B123 = findViewById<TextView>(R.id.B123);val B124 = findViewById<TextView>(R.id.B124)
    val B125 = findViewById<TextView>(R.id.B125);val B126 = findViewById<TextView>(R.id.B126);val B127 = findViewById<TextView>(R.id.B127);val B128 = findViewById<TextView>(R.id.B128)
    val B129 = findViewById<TextView>(R.id.B129);val B130 = findViewById<TextView>(R.id.B130);val B131 = findViewById<TextView>(R.id.B131);val B132 = findViewById<TextView>(R.id.B132)
    val B133 = findViewById<TextView>(R.id.B133);val B134 = findViewById<TextView>(R.id.B134);val B135 = findViewById<TextView>(R.id.B135);val B136 = findViewById<TextView>(R.id.B136)
    val B137 = findViewById<TextView>(R.id.B137);val B138 = findViewById<TextView>(R.id.B138);val B139 = findViewById<TextView>(R.id.B139);val B140 = findViewById<TextView>(R.id.B140)
    val B141 = findViewById<TextView>(R.id.B141);val B142 = findViewById<TextView>(R.id.B142);val B143 = findViewById<TextView>(R.id.B143);val B144 = findViewById<TextView>(R.id.B144)
    val B145 = findViewById<TextView>(R.id.B145);val B146 = findViewById<TextView>(R.id.B146);val B147 = findViewById<TextView>(R.id.B147);val B148 = findViewById<TextView>(R.id.B148)
    val B149 = findViewById<TextView>(R.id.B149);val B150 = findViewById<TextView>(R.id.B150)*/
    private fun loseALine(){}

    private fun next_shape() {

        /* 1   2   3
        *  4   5   6
        *  7   8   9
        *  10  11  12*/
        tVListPequenia[0].setBackgroundResource(R.drawable.block); tVListPequenia[1].setBackgroundResource(R.drawable.block);
        tVListPequenia[2].setBackgroundResource(R.drawable.block); tVListPequenia[3].setBackgroundResource(R.drawable.block);
        tVListPequenia[4].setBackgroundResource(R.drawable.block); tVListPequenia[5].setBackgroundResource(R.drawable.block);
        tVListPequenia[6].setBackgroundResource(R.drawable.block); tVListPequenia[7].setBackgroundResource(R.drawable.block);
        tVListPequenia[8].setBackgroundResource(R.drawable.block); tVListPequenia[9].setBackgroundResource(R.drawable.block);
        tVListPequenia[10].setBackgroundResource(R.drawable.block); tVListPequenia[11].setBackgroundResource(R.drawable.block);

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
        if (once == 0) {
            tVListPequenia[0].setBackgroundResource(R.drawable.block); tVListPequenia[1].setBackgroundResource(R.drawable.block);
            tVListPequenia[2].setBackgroundResource(R.drawable.block); tVListPequenia[3].setBackgroundResource(R.drawable.block);
            tVListPequenia[4].setBackgroundResource(R.drawable.block); tVListPequenia[5].setBackgroundResource(R.drawable.block);
            tVListPequenia[6].setBackgroundResource(R.drawable.block); tVListPequenia[7].setBackgroundResource(R.drawable.block);
            tVListPequenia[8].setBackgroundResource(R.drawable.block); tVListPequenia[9].setBackgroundResource(R.drawable.block);
            tVListPequenia[10].setBackgroundResource(R.drawable.block); tVListPequenia[11].setBackgroundResource(R.drawable.block);
            tVListPequenia[4].setBackgroundResource(R.drawable.green);tVListPequenia[5].setBackgroundResource(R.drawable.green);
            tVListPequenia[7].setBackgroundResource(R.drawable.green);tVListPequenia[8].setBackgroundResource(R.drawable.green);
            if (starting == 0) {
                Handler().postDelayed({ shapes() }, 1500)
                starting = 1
            }
        }
    }

    private fun shapes(){
        if (tVListGrande[10].text == "0" || tVListGrande[11].text == "0"|| tVListGrande[12].text == "0"|| tVListGrande[13].text == "0"|| tVListGrande[14].text == "0"
            || tVListGrande[15].text == "0"|| tVListGrande[16].text == "0"|| tVListGrande[17].text == "0"|| tVListGrande[18].text == "0"|| tVListGrande[19].text == "0"){
            tVListPequenia[0].setBackgroundResource(R.drawable.block); tVListPequenia[1].setBackgroundResource(R.drawable.block); tVListPequenia[2].setBackgroundResource(R.drawable.block); tVListPequenia[3].setBackgroundResource(R.drawable.block);
            tVListPequenia[4].setBackgroundResource(R.drawable.block); tVListPequenia[5].setBackgroundResource(R.drawable.block); tVListPequenia[6].setBackgroundResource(R.drawable.block); tVListPequenia[7].setBackgroundResource(R.drawable.block);
            tVListPequenia[8].setBackgroundResource(R.drawable.block); tVListPequenia[9].setBackgroundResource(R.drawable.block); tVListPequenia[10].setBackgroundResource(R.drawable.block); tVListPequenia[11].setBackgroundResource(R.drawable.block);
            tVListGrande[3].setBackgroundResource(R.drawable.red);tVListGrande[4].setBackgroundResource(R.drawable.red);tVListGrande[5].setBackgroundResource(R.drawable.red);tVListGrande[6].setBackgroundResource(R.drawable.red)
        }
        loseALine()
        if (stop == 0){
            a=0;b=0;z=0;x=0
            if (tVListGrande[23].text == "0" || tVListGrande[24].text == "0" || tVListGrande[25].text == "0" || tVListGrande[26].text == "0"){nextUp=7}
            array.removeAll(array);arrayCollectPreviousOne.removeAll(arrayCollectPreviousOne)
            shape_is = nextUp
            next_shape()
            when(shape_is){
                //L
                1 ->{tVListGrande[5].setBackgroundResource(R.drawable.orange); tVListGrande[13].setBackgroundResource(R.drawable.orange); tVListGrande[14].setBackgroundResource(R.drawable.orange);tVListGrande[15].setBackgroundResource(R.drawable.orange)}
                //Inverted S
                2 ->{tVListGrande[4].setBackgroundResource(R.drawable.yellow);tVListGrande[5].setBackgroundResource(R.drawable.yellow);tVListGrande[15].setBackgroundResource(R.drawable.yellow);tVListGrande[16].setBackgroundResource(R.drawable.yellow);}
                //square
                3 ->{tVListGrande[4].setBackgroundResource(R.drawable.green);tVListGrande[5].setBackgroundResource(R.drawable.green);tVListGrande[14].setBackgroundResource(R.drawable.green);tVListGrande[15].setBackgroundResource(R.drawable.green);}
                //Inverted L
                4 ->{tVListGrande[4].setBackgroundResource(R.drawable.purple);tVListGrande[14].setBackgroundResource(R.drawable.purple);tVListGrande[15].setBackgroundResource(R.drawable.purple);tVListGrande[16].setBackgroundResource(R.drawable.purple);}
                //S
                5 ->{tVListGrande[4].setBackgroundResource(R.drawable.pink); tVListGrande[5].setBackgroundResource(R.drawable.pink); tVListGrande[13].setBackgroundResource(R.drawable.pink);tVListGrande[12].setBackgroundResource(R.drawable.pink)}
                // _-_
                6 ->{tVListGrande[5].setBackgroundResource(R.drawable.blue); tVListGrande[14].setBackgroundResource(R.drawable.blue); tVListGrande[15].setBackgroundResource(R.drawable.blue);tVListGrande[16].setBackgroundResource(R.drawable.blue)}
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
        right.setOnClickListener{
            if (a==0){ if(num1!=10 && num1!=20 && num1!=30 && num1!=40 && num1!=50 && num1!=60 && num1!=70 && num1!=80 && num1!=90 && num1!=100 && num1!=110 && num1!=120 && num1!=130 && num1!=140 && num1!=150 &&
                num2!=10 && num2!=20 && num2!=30 && num2!=40 && num2!=50 && num2!=60 && num2!=70 && num2!=80 && num2!=90 && num2!=100 && num2!=110 && num2!=120 && num2!=130 && num2!=140 && num2!=150 &&
                num3!=10 && num3!=20 && num3!=30 && num3!=40 && num3!=50 && num3!=60 && num3!=70 && num3!=80 && num3!=90 && num3!=100 && num3!=110 && num3!=120 && num3!=130 && num3!=140 && num3!=150 &&
                num4!=10 && num4!=20 && num4!=30 && num4!=40 && num4!=50 && num4!=60 && num4!=70 && num4!=80 && num4!=90 && num4!=100 && num4!=110 && num4!=120 && num4!=130 && num4!=140 && num4!=150
                &&downArray[num1+1].text=="" && downArray[num2+1].text==""&& downArray[num3+1].text==""&& downArray[num4+1].text==""){num1+=1;num2+=1;num3+=1;num4+=1}
            }
        }
        left.setOnClickListener {if (b == 0){
            if (num1!=1 && num1!=11 && num1!=21 && num1!=31 && num1!=41 && num1!=51 && num1!=61 && num1!=71 && num1!=81 && num1!=91 && num1!=101 && num1!=111 && num1!=121 && num1!=131 && num1!=141 &&
                num2!=1 && num2!=11 && num2!=21 && num2!=31 && num2!=41 && num2!=51 && num2!=61 && num2!=71 && num2!=81 && num2!=91 && num2!=101 && num2!=111 && num2!=121 && num2!=131 && num2!=141 &&
                num3!=1 && num3!=11 && num3!=21 && num3!=31 && num3!=41 && num3!=51 && num3!=61 && num3!=71 && num3!=81 && num3!=91 && num3!=101 && num3!=111 && num3!=121 && num3!=131 && num3!=141 &&
                num4!=1 && num4!=11 && num4!=21 && num4!=31 && num4!=41 && num4!=51 && num4!=61 && num4!=71 && num4!=81 && num4!=91 && num4!=101 && num4!=111 && num4!=121 && num4!=131 && num4!=141
                &&downArray[num1-1].text=="" && downArray[num2-1].text==""&& downArray[num3-1].text==""&& downArray[num4-1].text==""){num1-=1;num2-=1;num3-=1;num4-=1}
        } }
        spinRight.setOnClickListener{
            if(downArray[num1 + 1].text=="" && downArray[num2 + 1].text=="" && downArray[num3 + 1].text=="" && downArray[num4 + 1].text==""&&
                downArray[num1 - 1].text=="" && downArray[num2 - 1].text=="" && downArray[num3 - 1].text=="" && downArray[num4 - 1].text=="" &&
                downArray[num1 + 10].text=="" && downArray[num2 + 10].text=="" && downArray[num3 + 10].text=="" && downArray[num4 + 10].text=="" &&
                downArray[num1 - 10].text=="" && downArray[num2 - 10].text=="" && downArray[num3 - 10].text=="" && downArray[num4 - 10].text==""){
                when(shape_is){
                    //L
                    1 -> {
                        when (z) {
                            0 -> {num1+=1;num2+=1;num3+=10;num4+=10;z=1}
                            1 -> {num1+=9;num2+=0;num3-=9;num4-=2;z=2}
                            2 -> {num1-=10;num2-=10;num3-=1;num4-=1;z=3}
                            else -> {num1+=2;num2+=9;num3+=0;num4-=9;z=0}
                        }
                    }
                    //Inverted S
                    2 ->{if(z==0){num1+=1;num2+=9;num3+=0;num4+=8;z=1} else{num1-=1;num2-=9;num3-=0;num4-=9;z=0}}
                    //square
                    3 -> {} //doesn't change
                    //Inverted L
                    4 ->{
                        when (z) {
                            0 -> {num1+=0;num2-=9;num3-=1;num4+=8;z=1}
                            1 -> {num1+=10;num2+=10;num3+=2;num4+=2;z=2}
                            2 -> {num1-=8;num2+=1;num3+=9;num4+=0;z=3}
                            else -> {num1-=2;num2-=2;num3-=10;num4-=10;z=0}
                        }
                    }
                    //S
                    5 ->{if(z==0){num1+=0;num2+=9;num3+=2;num4+=11;z=1} else{num1-=0;num2-=9;num3-=2;num4-=11;z=0}}
                    // _-_
                    6 ->{
                        when (z) {
                            0 -> {num1+=0;num2+=1;num3+=1;num4+=9;z=1}
                            1 -> {num1+=9;num2+=0;num3+=0;num4+=0;z=2}
                            2 -> {num1-=9;num2-=1;num3-=1;num4+=0;z=3}
                            else -> {num1+=0;num2+=0;num3+=0;num4+=9;z=0}
                        }
                    }
                    // |
                    7 ->{if(z==0){num1+=1;num2+=10;num3+=19;num4+=28;z=1} else{num1-=1;num2-=10;num3-=19;num4-=28;z=0}}
                }
            }
        }
        down.setOnClickListener{
            while(x==0){
                arrayCollectPreviousOne.add(downArray[num1]);arrayCollectPreviousOne.add(downArray[num2]);arrayCollectPreviousOne.add(downArray[num3]);arrayCollectPreviousOne.add(downArray[num4])
                num1+=10;num2+=10;num3+=10;num4+=10
                if(downArray[num1].text=="1" || downArray[num2].text=="1" || downArray[num3].text=="1" || downArray[num4].text=="1" ||
                    num1==141 || num1==142 || num1==143 || num1==144 || num1==145 || num1==146 || num1==147 || num1==148 || num1==149 || num1==150 ||
                    num2==141 || num2==142 || num2==143 || num2==144 || num2==145 || num2==146 || num2==147 || num2==148 || num2==149 || num2==150 ||
                    num3==141 || num3==142 || num3==143 || num3==144 || num3==145 || num3==146 || num3==147 || num3==148 || num3==149 || num3==150 ||
                    num4==141 || num4==142 || num4==143 || num4==144 || num4==145 || num4==146 || num4==147 || num4==148 || num4==149 || num4==150){
                    num1-=10;num2-=10;num3-=10;num4-=10;x=1; points+=5
                }
            }
        }
        if(a==0||b==0){
            num1+=10;num2+=10;num3+=10;num4+=10
        }
        arrayCollectPreviousOne.add(downArray[num1]);arrayCollectPreviousOne.add(downArray[num2]);arrayCollectPreviousOne.add(downArray[num3]);arrayCollectPreviousOne.add(downArray[num4])
        array.add(downArray[num1]);array.add(downArray[num2]);array.add(downArray[num3]);array.add(downArray[num4])
        landing();colors()
    }
    var x = 0
    private fun list2(){}
    private fun R_L(){}
    var points  = 0
    private fun landing(){
        if (num1==141 || num1==142 || num1==143 || num1==144 || num1==145 || num1==146 || num1==147 || num1==148 || num1==149 || num1==150 ||
            num2==141 || num2==142 || num2==143 || num2==144 || num2==145 || num2==146 || num2==147 || num2==148 || num2==149 || num2==150 ||
            num3==141 || num3==142 || num3==143 || num3==144 || num3==145 || num3==146 || num3==147 || num3==148 || num3==149 || num3==150 ||
            num4==141 || num4==142 || num4==143 || num4==144 || num4==145 || num4==146 || num4==147 || num4==148 || num4==149 || num4==150){array[0].text="0";array[1].text="0";array[2].text="0";array[3].text="0"}
        if (array[0].text=="1"||array[1].text=="1"||array[2].text=="1"||array[3].text=="1"){array[0].text="0";array[1].text="0";array[2].text="0";array[3].text="0"}
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