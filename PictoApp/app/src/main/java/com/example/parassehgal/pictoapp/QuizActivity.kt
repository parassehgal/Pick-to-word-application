package com.example.parassehgal.pictoapp

import android.content.DialogInterface
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_quiz.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class QuizActivity : AppCompatActivity() {

    var score=0
    var counter=0
    var ansTrack=0
    var answerButtonArray=ArrayList<Button>()
    var answerCounter=0
    lateinit var buttonArray:Array<Button>
    var tempData= ArrayList<Char>()
    var imageArray= arrayOf(R.drawable.foot,R.drawable.ball,
                           R.drawable.key,R.drawable.board,
            R.drawable.bat,R.drawable.human,
            R.drawable.basket,R.drawable.tennisball,
            R.drawable.pine,R.drawable.apple,
            R.drawable.tooth,R.drawable.brush,
            R.drawable.time,R.drawable.table,
            R.drawable.cat,R.drawable.fish,
            R.drawable.hot,R.drawable.dog,
            R.drawable.hand,R.drawable.pump,
            R.drawable.man,R.drawable.book,
            R.drawable.rain,R.drawable.bow,
            R.drawable.ear,R.drawable.ring,
            R.drawable.check,R.drawable.book2,
            R.drawable.house,R.drawable.fly,
            R.drawable.sun,R.drawable.light,
            R.drawable.home,R.drawable.boat,
            R.drawable.moon,R.drawable.light)
    var answerArray= arrayOf("football","keyboard","batman","basketball",
                             "pineapple","toothbrush","timetable","catfish",
                             "hotdog","handpump","facebook","rainbow",
                             "earring","checkbook","housefly","sunlight",
                              "houseboat","moonlight")

    var selectedAnswerButton=HashMap<Int,Int>()
    var selectedIndex=HashMap<Int,Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        buttonArray= arrayOf(button1,button2,button3,button4,
                             button5,button6,button7,button8,
                             button9,button10,button11,button12,
                             button13,button14)

        loadTempData()
        loadImage()
        loadAnswerButton()
        loadHintButton()
    }

    fun loadTempData(){
        for(i in 'A'..'Z'){
            tempData.add(i)
        } }

    fun loadImage(){
        ImageView1.setImageResource(imageArray[counter])
        counter++
        ImageView2.setImageResource(imageArray[counter])
        counter++
    }

    fun loadHintButton(){
        var tempArray= arrayListOf<Char>()
        for(i in 0 until answerArray[answerCounter].length){
            tempArray.add(i,answerArray[answerCounter][i])
        }

        Collections.shuffle(tempData)
        for(i in tempArray.size..13){
            tempArray.add(tempData[i])
        }
        Collections.shuffle(tempArray)
        for(i in 0..tempArray.size-1){
           buttonArray[i].text=tempArray[i].toString()
        }
    }




    fun loadAnswerButton(){
        for(i in 1..answerArray[answerCounter].length){
            var button=Button(this)
            var layoutParams=WindowManager.LayoutParams()
            layoutParams.width=80
            layoutParams.height=100
           //layoutParams.horizontalWeight=1f
           // layoutParams.horizontalMargin=1f
            button.layoutParams=layoutParams
            button.setTextColor(Color.BLUE)
            button.setBackgroundResource(R.drawable.answer_button)
            button.id=i

            button.setOnClickListener{
               if(button.text.isNotBlank()){
                    clickAnswerButton(button)}

            }
            answerButtonArray.add(button)
            answerButton.addView(button)
        }
    }

    fun clickAnswerButton(view: View){
        var button=findViewById<Button>(view.id)
        button.text=""
        var index=selectedIndex.getValue(button.id)
        var button1=findViewById<Button>(selectedAnswerButton.getValue(answerButtonArray[index].id))
        button1.visibility=View.VISIBLE

    }

  fun clickHintButton(view: View){

      when{
          ansTrack<answerButtonArray.size->{
              var selectedbutton:Button=findViewById(view.id)


              for(i in 0 until answerButtonArray.size) {

                  if(answerButtonArray[i].text.isEmpty()){
                      selectedbutton.visibility=View.INVISIBLE
                  answerButtonArray[i].text = selectedbutton.text
                  selectedIndex.put(answerButtonArray[i].id,i)
                  selectedAnswerButton.put(answerButtonArray[i].id, view.id)
                      ansTrack=i+1
                  break
                  }
                  else{
                      ansTrack=answerButtonArray.size
                  }

              }
          }
          ansTrack==answerButtonArray.size->{
             var data=""
              for(i in 0 until answerButtonArray.size){
                  data=data.plus(answerButtonArray[i].text.toString())
              }
              if(data.equals(answerArray[answerCounter],ignoreCase = true))
              {
                  Toast.makeText(this@QuizActivity,"Correct",Toast.LENGTH_LONG).show()


                  loadImage()
                  score++
                  scoreTextView.text=score.toString()

                  answerCounter++
                  for(i in 0 until buttonArray.size){
                      buttonArray[i].visibility=View.VISIBLE
                  }
                  answerButton.removeAllViews()
                  loadHintButton()
                  loadAnswerButton()
              }
              else
              {
                  Toast.makeText(this@QuizActivity,"Try Again",Toast.LENGTH_LONG).show()
              }
              ansTrack=0
          }

      }

  }

    override fun onBackPressed() {
        var alertdialog=AlertDialog.Builder(this)
        alertdialog.setView(R.layout.abc_alert_dialog_material)
        alertdialog.setTitle("Exit")
        alertdialog.setMessage("Are you sure you want to exit?")

        alertdialog.setPositiveButton("Yes",DialogInterface.OnClickListener { dialogInterface, i ->
            finish()
        })
        alertdialog.setNegativeButton("No",DialogInterface.OnClickListener { dialogInterface, i ->

        })
        alertdialog.setNeutralButton("Cancel",DialogInterface.OnClickListener { dialogInterface, i ->

        })

        alertdialog.show()
    }
}
