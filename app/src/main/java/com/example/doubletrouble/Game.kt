package com.example.doubletrouble

import android.app.Activity
import android.graphics.*
import android.os.AsyncTask
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.util.DisplayMetrics
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_game.*
import kotlinx.android.synthetic.main.activity_game.view.*
import java.util.*
import kotlin.concurrent.schedule
import kotlin.properties.Delegates

class Game: AppCompatActivity(),SurfaceHolder.Callback{
    var text:TextView by Delegates.notNull<TextView>()
    var Playflg:Boolean = true
    var tapflg:Boolean = false
    var startbt:Button by Delegates.notNull<Button>()
    var mPaint: Paint = Paint()
    var countHolder:SurfaceHolder by Delegates.notNull<SurfaceHolder>()
    var playHolder:SurfaceHolder by Delegates.notNull<SurfaceHolder>()
    var dm:DisplayMetrics by Delegates.notNull<DisplayMetrics>()
    var mWidth:Float =0F
    var mHeight:Float =0F
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        var surfaceCount: SurfaceView = findViewById(R.id.surfaceView)
        var surfacePlay:SurfaceView =findViewById(R.id.surfaceView2)
        countHolder = surfaceCount.holder
        countHolder.addCallback(this)
        playHolder = surfacePlay.holder
        playHolder.addCallback(this)
        val size = Point().also {
            (this.getSystemService(WINDOW_SERVICE) as WindowManager).defaultDisplay.apply { getSize(it) }
        }
        mWidth = size.x.toFloat()
        mHeight =size.x.toFloat()
        startbt = findViewById(R.id.start)
        var name:String = "ユーザ名:"
            name = name + intent.getStringExtra("name1")
        textView2.setText(name)
        text = findViewById(R.id.count)
        startbt.setOnClickListener {
            finish()
        }


    }


    fun Play(){
       //var task:CountDown = CountDown(5)
        //task.execute(1)
        if (Playflg) {
            Log.d("TAG", "mWidth=" + mWidth.toString())

            if (tapflg != true) {
                Log.d("TAG", "playflg:false")
                Drawstr("タップしてください")
            } else {

            }
        }else{
            //score表示
        }






    }
    fun DrawStartCountDown(ti :Int){
        var count:Int = ti
        Timer().schedule(0, 1000, {
            Log.v("TAG", count.toString())
            var c: Canvas = countHolder.lockCanvas()
            c.drawColor(Color.WHITE)
            mPaint.setColor(Color.BLACK)
            var rect:Rect = Rect(50,50,50,50)
            c.drawRect(rect,mPaint)
            mPaint.setTextSize(60F)
            c.drawText(count.toString(),mWidth/2-30,60F,mPaint)
            countHolder.unlockCanvasAndPost(c)
            count--
            if (count ==-1 ) {
                this.cancel()
                tapflg = true
                Play()
            }
        })

    }
    fun DrawCountDown(ti :Int){
        var count:Int = ti
        Timer().schedule(0, 1000, {

            Log.v("TAG", count.toString())
            var c: Canvas = countHolder.lockCanvas()
            c.drawColor(Color.WHITE)
            mPaint.setColor(Color.BLACK)
            var rect:Rect = Rect(50,50,50,50)
            c.drawRect(rect,mPaint)
            mPaint.setTextSize(60F)
            if(count >=0)
                c.drawText(count.toString(),mWidth/2-30,60F,mPaint)
            countHolder.unlockCanvasAndPost(c)
            count--
            if (count ==-2 ) {
                this.cancel()
                Playflg =false
                Play()
            }
        })

    }
    fun Drawstr(str:String){
        var c: Canvas = countHolder.lockCanvas()
        c.drawColor(Color.WHITE)
        mPaint.setColor(Color.BLACK)
        var rect:Rect = Rect(50,50,50,50)
        mPaint.setTextSize(60F)
        c.drawRect(rect,mPaint)
        c.drawText(str,10F,60F,mPaint)
        countHolder.unlockCanvasAndPost(c)
    }


    override fun surfaceChanged(p0: SurfaceHolder?, p1: Int, p2: Int, p3: Int) {

    }

    override fun surfaceDestroyed(p0: SurfaceHolder?) {

    }

    override fun surfaceCreated(p0: SurfaceHolder?) {
        Play()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Log.d("TAG",event.toString())
        if (tapflg != true) {
            when (event!!.action) {
                MotionEvent.ACTION_DOWN -> DrawStartCountDown(5)
                MotionEvent.ACTION_OUTSIDE -> Log.d("TAG","画面外タップ")
            }
        }

        return super.onTouchEvent(event)
    }

}