package com.example.mytodo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.mytodo.data.Guests
import com.example.mytodo.databinding.FragmentDisplayGuestBinding

class DisplayGuestFragment : Fragment() {
    private lateinit var binding: FragmentDisplayGuestBinding
    private val args: DisplayGuestFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDisplayGuestBinding.inflate(inflater, container, false)
        displayGuestDetails()
        return binding.root
    }

    private fun displayGuestDetails() {
        val guest = Guests.getGuest(args.guestId)

        binding.nameText.text = guest?.name ?: "Unknown"
        binding.categoryText.text = guest?.category?.name ?: "Unknown"
        binding.addressText.text = guest?.address ?: "No address"
        binding.ageText.text = if (guest != null) "${guest.age} years old" else "Age unknown"
    }
}
