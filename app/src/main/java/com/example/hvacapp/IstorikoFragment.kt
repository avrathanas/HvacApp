package com.example.hvacapp

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hvacapp.databinding.FragmentIstorikoBinding
import com.example.hvacapp.databinding.DialogAddIstorikoBinding
import kotlinx.coroutines.launch
import android.text.Editable
import android.text.TextWatcher
import com.example.hvacapp.data.AppDatabase
import com.example.hvacapp.data.entities.Istoriko

class IstorikoFragment : Fragment(R.layout.fragment_istoriko) {
    private var _binding: FragmentIstorikoBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: IstorikoAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentIstorikoBinding.bind(view)

        val db = AppDatabase.getDatabase(requireContext())


        adapter = IstorikoAdapter(emptyList(),
            onDeleteClick = { entry ->
                lifecycleScope.launch { db.istorikoDao().delete(entry) }
            },
            onItemClick = { entry ->
                showDetailsDialog(entry)
            }
        )

        binding.rvIstoriko.layoutManager = LinearLayoutManager(requireContext())
        binding.rvIstoriko.adapter = adapter


        lifecycleScope.launch {
            db.istorikoDao().getAllIstoriko().collect { entries ->
                adapter.updateData(entries)
            }
        }


        binding.fabAddIstoriko.setOnClickListener {
            showAddIstorikoDialog()
        }


        binding.etSearchIstoriko.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString()
                lifecycleScope.launch {
                    db.istorikoDao().searchIstoriko("%$query%").collect { result ->
                        adapter.updateData(result)
                    }
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }


    private fun showAddIstorikoDialog() {
        val dialogBinding = DialogAddIstorikoBinding.inflate(layoutInflater)
        val dialog = androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setView(dialogBinding.root)
            .create()

        val db = AppDatabase.getDatabase(requireContext())

        dialogBinding.btnSavePer.setOnClickListener {
            val onoma = dialogBinding.etPerName.text.toString()
            val odo = ""
            val ergasia = dialogBinding.etPerErgasia.text.toString()
            val hmerominia = dialogBinding.etPerDate.text.toString()
            val sxolia = dialogBinding.etPerSxolia.text.toString()
            val sunergatis = dialogBinding.etPerSynergeio.text.toString()

            if (onoma.isNotEmpty()) {
                val neaErgasia = Istoriko(
                    onoma = onoma,
                    odos = odo,
                    ergasia = ergasia,
                    hmerominia = hmerominia,
                    sxolia = sxolia,
                    synergeio = "",
                    synergatis = sunergatis
                )

                lifecycleScope.launch {
                    db.istorikoDao().insertOrUpdate(neaErgasia)
                    dialog.dismiss()
                }
            } else {
                dialogBinding.etPerName.error = "Συμπληρώστε το όνομα"
            }
        }
        dialog.show()
    }


    private fun showDetailsDialog(entry: Istoriko) {
        androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setTitle("Λεπτομέρειες Εργασίας")
            .setMessage("Όνομα: ${entry.onoma}\nΟδός: ${entry.odos}\nΕργασία: ${entry.ergasia}\nΗμερομηνία: ${entry.hmerominia}\nΣυνεργάτης: ${entry.synergatis}\nΣχόλια: ${entry.sxolia}")
            .setPositiveButton("ΟΚ", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}