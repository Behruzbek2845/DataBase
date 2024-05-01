package com.example.database.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.database.databinding.ItemRvBinding
import com.example.database.models.Users

class RvAdapter(var rvAction: RvAction, val list: ArrayList<Users>): RecyclerView.Adapter<RvAdapter.VH>() {
    inner class VH(val itemContactBinding: ItemRvBinding) :
        RecyclerView.ViewHolder(itemContactBinding.root) {
        fun OnBind(user: Users) {
            itemContactBinding.txtName.text = user.name
            itemContactBinding.txtNumber.text = user.number
            itemContactBinding.menuPop.setOnClickListener {
                rvAction.moreClick(user, itemContactBinding.menuPop)
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemRvBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.OnBind(list[position])
    }

    interface RvAction{
        fun moreClick (user: Users, imageView: ImageView)
    }
}