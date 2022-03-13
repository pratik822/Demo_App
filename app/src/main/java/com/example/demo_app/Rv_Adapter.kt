package com.example.demo_app

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.demo_app.BusinessesModule.Businesse
import com.example.demo_app.BusinessesModule.businessesData

class Rv_Adapter(val context: Context, val list: List<Businesse>?): RecyclerView.Adapter<Rv_Adapter.Holder>() {

    init {
        val ctx:Context =this.context;
       val list: List<Businesse>? =this.list;

    }


    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv_name:TextView=itemView.findViewById(R.id.tv_name);
        val tv_address:TextView=itemView.findViewById(R.id.tv_address)
        val tv_rating:TextView=itemView.findViewById(R.id.tv_rating)
        val  iv_image:ImageView=itemView.findViewById(R.id.iv_imageView);

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.lay_list,null);

        return Holder(view);

    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.tv_name.text= list?.get(position)?.name
        holder.tv_address.text=list?.get(position)?.location?.address1?:""+" "+list?.get(position)?.location?.address2?:"";
        holder.tv_rating.text="3"

        Glide.with(context).load(list?.get(position)?.image_url).into(holder.iv_image);

    }

    override fun getItemCount(): Int {
        return list!!.size
    }
}