package com.example.mytodo

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mytodo.data.Guests
import com.example.mytodo.databinding.FragmentGuestListBinding

class GuestListFragment : Fragment() {
    private lateinit var binding: FragmentGuestListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGuestListBinding.inflate(inflater, container, false)
        setupRecyclerView()
        return binding.root
    }



    private fun setupRecyclerView() {
        with(binding.guestList) {
            layoutManager = LinearLayoutManager(context)
            adapter = MyGuestRecyclerViewAdapter(Guests.getAllGuests(), object : MyGuestRecyclerViewAdapter.GuestListListener {
                override fun onGuestClick(guestId: String) {
                    val action = GuestListFragmentDirections.actionGuestListFragmentToAddGuestFragment(guestId)
                    findNavController().navigate(action)
                }

                override fun onGuestLongClick(guestId: String) {
                    showDeleteDialog(guestId)
                }
            })
        }
    }

    private fun showDeleteDialog(guestId: String) {
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.confirm_delete_guest))
            .setMessage(getString(R.string.confirm))
            .setPositiveButton(getString(R.string.confirm)) { dialog, which ->
                Guests.removeGuest(guestId)
                binding.guestList.adapter?.notifyDataSetChanged()
                Toast.makeText(context, getString(R.string.guest_deleted_successfully), Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addGuestButton.setOnClickListener {
            val action = GuestListFragmentDirections.actionGuestListFragmentToAddGuestFragment(null)
            findNavController().navigate(action)
        }
    }
}
