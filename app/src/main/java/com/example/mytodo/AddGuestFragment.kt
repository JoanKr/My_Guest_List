package com.example.mytodo

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mytodo.data.Category
import com.example.mytodo.data.Guest
import com.example.mytodo.data.Guests
import com.example.mytodo.databinding.FragmentAddGuestBinding
import java.util.UUID

class AddGuestFragment : Fragment() {
    private lateinit var binding: FragmentAddGuestBinding
    private val args: AddGuestFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddGuestBinding.inflate(inflater, container, false)

        args.guestToEdit?.let {
            val guest = Guests.getGuest(it)
            guest?.let { g ->
                binding.guestNameInput.setText(g.name)
                binding.guestAgeInput.setText(g.age.toString())
                binding.guestAddressInput.setText(g.address)
                when (g.category) {
                    Category.FRIEND -> binding.categoryGroup.check(R.id.friend_radioButton)
                    Category.FAMILY -> binding.categoryGroup.check(R.id.family_radioButton)
                    Category.COMPANION -> binding.categoryGroup.check(R.id.companion_radioButton)
                }
            }
        }

        binding.saveButton.setOnClickListener {
            saveGuest(args.guestToEdit)
        }
        return binding.root
    }

    private fun saveGuest(guestId: String?) {
        val name = binding.guestNameInput.text.toString()
        val age = binding.guestAgeInput.text.toString().toIntOrNull() ?: 0
        val address = binding.guestAddressInput.text.toString()
        val category = when (binding.categoryGroup.checkedRadioButtonId) {
            R.id.friend_radioButton -> Category.FRIEND
            R.id.family_radioButton -> Category.FAMILY
            R.id.companion_radioButton -> Category.COMPANION
            else -> Category.FRIEND
        }

        if (name.isEmpty() || age <= 0 || address.isEmpty()) {
            Toast.makeText(context, "Please fill out all fields correctly", Toast.LENGTH_SHORT).show()
            return
        }

        val guest = Guest(
            id = guestId ?: UUID.randomUUID().toString(),
            name = name,
            category = category,
            address = address,
            age = age
        )

        if (guestId == null) {
            Guests.addGuest(guest)
        } else {
            Guests.updateGuest(guestId, guest)
        }

        val inputMethodManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(binding.root.windowToken, 0)

        findNavController().popBackStack()
        Toast.makeText(context, getString(R.string.guest_added_successfully), Toast.LENGTH_SHORT).show()
    }
}
