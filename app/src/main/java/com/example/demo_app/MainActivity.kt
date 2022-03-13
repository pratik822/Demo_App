package com.example.demo_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.demo_app.BusinessesModule.Businesse
import com.google.gson.Gson
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    lateinit var rv:RecyclerView;
    lateinit var seekbar:SeekBar;
    lateinit var tv_radius_start:TextView;
    lateinit var no_record_found:TextView;
    var original_distance=100;
    lateinit var swipeRefreshLayout:SwipeRefreshLayout;
    var distance:String="100 M";
    var list:List<Businesse>? = null;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rv=findViewById(R.id.rv);
        tv_radius_start=findViewById(R.id.tv_radius_start);
        swipeRefreshLayout=findViewById(R.id.swipeRefreshLayout);
        seekbar=findViewById(R.id.seekbar)
        no_record_found=findViewById(R.id.no_record_found);

        swipeRefreshLayout.setOnRefreshListener(object:SwipeRefreshLayout.OnRefreshListener{
            override fun onRefresh() {
                GlobalScope.launch(Dispatchers.Main + CoroutineExceptionHandler{_,ex-> print("error"+ex)}) {
                    var result= NetworkModule.getInstance().getLocation("NYC","restaurants","distance",original_distance)
                    list=result.body()?.businesses;

                }

                rv.apply {
                    layoutManager=LinearLayoutManager(this@MainActivity);

                    if(list!=null){
                        val adapter=Rv_Adapter(context,list)
                        this.adapter=adapter;
                        if(swipeRefreshLayout.isRefreshing==true){
                            swipeRefreshLayout.isRefreshing=false;
                        }
                        no_record_found.visibility=View.GONE
                    }else{
                        no_record_found.visibility=View.VISIBLE
                    }

                }



            }

        })

        seekbar.setOnSeekBarChangeListener(object:SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                original_distance=p1;
                if(p1>1000){
                    distance=(p1/1000).toString()+" KM";
                }else{
                    distance=p1.toString()+" M";
                }
                tv_radius_start.setText(distance)

                GlobalScope.launch(Dispatchers.Main + CoroutineExceptionHandler{_,ex-> print("error"+ex)}) {
                    var result=NetworkModule.getInstance().getLocation("NYC","restaurants","distance",original_distance)
                    println("getresp"+Gson().toJson(result.body()))
                    list=result.body()?.businesses;

                }

                rv.apply {
                    layoutManager=LinearLayoutManager(this@MainActivity);

                    if(list!=null){
                        val adapter=Rv_Adapter(context,list)
                        this.adapter=adapter;
                        no_record_found.visibility=View.GONE
                    }else{
                        no_record_found.visibility=View.VISIBLE
                    }

                }

            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }

        });

    }

}