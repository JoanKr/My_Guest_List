package com.example.mytodo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mytodo.data.Category
import com.example.mytodo.data.Guest
import com.example.mytodo.databinding.FragmentGuestItemBinding

class MyGuestRecyclerViewAdapter(
    private var guests: List<Guest>,
    private val eventListener: GuestListListener
) : RecyclerView.Adapter<MyGuestRecyclerViewAdapter.ViewHolder>() {


    fun removeGuestAtPosition(position: Int) {
        guests = guests.toMutableList().apply {
            removeAt(position)
        }
        notifyItemRemoved(position)
    }

    fun updateGuests(newGuests: List<Guest>) {
        guests = newGuests
        notifyDataSetChanged()  // Powiadom adapter o zmianie danych, aby mógł odświeżyć widok
    }

    interface GuestListListener {
        fun onGuestClick(guestId: String)
        fun onGuestLongClick(guestId: String)
    }

    class ViewHolder(val binding: FragmentGuestItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(guest: Guest, eventListener: GuestListListener) {
            binding.guestName.text = guest.name
            binding.guestCategory.text = guest.category.name
            binding.guestAddress.text = guest.address
            binding.guestAge.text = "${guest.age} years old"

            // Przypisanie ikony na podstawie kategorii
            val iconRes = when (guest.category) {
                Category.FRIEND -> R.drawable.gwiazda
                Category.FAMILY -> R.drawable.serce
                Category.COMPANION -> R.drawable.circle_drawable_green
            }
            binding.categoryIcon.setImageResource(iconRes)  // Ustawienie ikony

            itemView.setOnClickListener { eventListener.onGuestClick(guest.id) }
            itemView.setOnLongClickListener {
                eventListener.onGuestLongClick(guest.id)
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FragmentGuestItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(guests[position], eventListener)
    }

    override fun getItemCount() = guests.size
}
