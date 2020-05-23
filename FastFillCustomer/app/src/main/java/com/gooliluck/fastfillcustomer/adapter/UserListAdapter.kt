package com.gooliluck.fastfillcustomer.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gooliluck.fastfillcustomer.R
import com.gooliluck.fastfillcustomer.data.model.User
import com.gooliluck.fastfillcustomer.databinding.ProfileCouponWalletTicketItemBinding

class UserListAdapter (val context: Context, private val listener : View.OnClickListener, private val editListener : View.OnClickListener) : ListAdapter<User, UserListAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object{
        private var DIFF_CALLBACK = object : DiffUtil.ItemCallback<User>(){
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                Log.e("UserListAdapter","areItemsTheSame =============================================")
                Log.e("UserListAdapter","oldItem is ${oldItem.id}")
                Log.e("UserListAdapter","newItem is ${newItem.id}")
                Log.e("UserListAdapter","areItemsTheSame *****************************************")
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: User,
                newItem: User
            ): Boolean {
                Log.e("UserListAdapter","areContentsTheSame =============================================")
                Log.e("UserListAdapter","oldItem is ${oldItem.name}")
                Log.e("UserListAdapter","newItem is ${newItem.name}")
                Log.e("UserListAdapter","areContentsTheSame *****************************************")
                return oldItem.name == newItem.name
                        && newItem.birthday == oldItem.birthday
                        && newItem.desc == oldItem.desc
                        && newItem.phoneNumber == oldItem.phoneNumber
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<ProfileCouponWalletTicketItemBinding>(
            LayoutInflater.from(this.context),
            R.layout.profile_coupon_wallet_ticket_item,
            parent,
            false
        )
        return ViewHolder(binding,binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val trade = getItem(position)
        holder.bindingData(trade)
        holder.vTicketClickableView.tag = position
        holder.imgBtnEdit.tag = position
        holder.vTicketClickableView.setOnClickListener(listener)
        holder.imgBtnEdit.setOnClickListener(editListener)
    }

    class ViewHolder(private var binding: ProfileCouponWalletTicketItemBinding, itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val vTicketClickableView = itemView.findViewById<ConstraintLayout>(R.id.cl_ticket_active)!!
        val imgBtnEdit = itemView.findViewById<ImageButton>(R.id.imgbtn_edit)!!
        fun bindingData(data : User){
            binding.user = data
        }
    }
}