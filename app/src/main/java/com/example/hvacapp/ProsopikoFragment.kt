package com.example.hvacapp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hvacapp.data.AppDatabase
import com.example.hvacapp.data.entities.Prosopiko
import com.example.hvacapp.databinding.FragmentProsopikoBinding
import com.example.hvacapp.databinding.DialogAddProsopikoBinding
import kotlinx.coroutines.launch

class ProsopikoFragment : Fragment(R.layout.fragment_prosopiko) {
    private var _binding: FragmentProsopikoBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ProsopikoAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentProsopikoBinding.bind(view)

        val db = AppDatabase.getDatabase(requireContext())


        adapter = ProsopikoAdapter(emptyList(),
            onDelete = { entry ->
                lifecycleScope.launch { db.prosopikoDao().delete(entry) }
            },
            onClick = { entry ->
                showDetailsDialog(entry)
            }
        )

        binding.rvProsopiko.layoutManager = LinearLayoutManager(requireContext())
        binding.rvProsopiko.adapter = adapter


        lifecycleScope.launch {
            db.prosopikoDao().getAllProsopiko().collect { entries ->
                adapter.update(entries)
            }
        }


        binding.fabAddProsopiko.setOnClickListener {
            showAddProsopikoDialog()
        }


        binding.etSearchProsopiko.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString()
                lifecycleScope.launch {
                    db.prosopikoDao().searchProsopiko("%$query%").collect { result ->
                        adapter.update(result)
                    }
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }


    private fun showAddProsopikoDialog() {
        val dialogBinding = DialogAddProsopikoBinding.inflate(layoutInflater)
        val dialog = androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setView(dialogBinding.root)
            .create()

        val db = AppDatabase.getDatabase(requireContext())

        dialogBinding.btnSaveStaff.setOnClickListener {
            val onoma = dialogBinding.etStaffName.text.toString()
            val ores = dialogBinding.etStaffOres.text.toString().toDoubleOrNull() ?: 0.0
            val yperories = dialogBinding.etStaffYperories.text.toString().toDoubleOrNull() ?: 0.0
            val savvato = dialogBinding.etStaffSavvato.text.toString().toIntOrNull() ?: 0
            val synolo = dialogBinding.etStaffSynolo.text.toString().toDoubleOrNull() ?: 0.0

            if (onoma.isNotEmpty()) {
                val neos = Prosopiko(
                    onoma = onoma,
                    ores = ores,
                    kostosYperoria = yperories,
                    savvato = savvato,
                    synolo = synolo
                )

                lifecycleScope.launch {
                    db.prosopikoDao().insertOrUpdate(neos)
                    dialog.dismiss()
                }
            } else {
                dialogBinding.etStaffName.error = "Συμπληρώστε το όνομα"
            }
        }
        dialog.show()
    }


    private fun showDetailsDialog(entry: Prosopiko) {
        androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setTitle("Στοιχεία Μισθοδοσίας")
            .setMessage("Όνομα: ${entry.onoma}\nΏρες: ${entry.ores}\nΚόστος Υπερωριών: ${entry.kostosYperoria}€\nΣάββατα: ${entry.savvato}\nΣύνολο: ${entry.synolo}€")
            .setPositiveButton("ΟΚ", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}