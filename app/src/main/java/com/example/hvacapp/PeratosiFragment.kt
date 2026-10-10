package com.example.hvacapp

import android.os.Bundle
import android.text.Editable
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hvacapp.databinding.FragmentPeratosiBinding
import com.example.hvacapp.databinding.DialogAddPeratosiBinding
import com.example.hvacapp.data.AppDatabase
import com.example.hvacapp.data.entities.Peratosi
import kotlinx.coroutines.launch

class PeratosiFragment : Fragment(R.layout.fragment_peratosi) {
    private var _binding: FragmentPeratosiBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: PeratosiAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentPeratosiBinding.bind(view)
        val db = AppDatabase.getDatabase(requireContext())

        adapter = PeratosiAdapter(emptyList(),
            onDelete = { item -> lifecycleScope.launch { db.peratosiDao().delete(item) } },
            onClick = { item -> showDetails(item) }
        )

        binding.rvPeratosi.layoutManager = LinearLayoutManager(requireContext())
        binding.rvPeratosi.adapter = adapter

        lifecycleScope.launch {
            db.peratosiDao().getAllPeratosi().collect { adapter.update(it) }
        }

        binding.fabAddPeratosi.setOnClickListener { showAddDialog() }

        // Αναζήτηση
        binding.etSearchPeratosi.addTextChangedListener(object : android.text.TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                lifecycleScope.launch {
                    db.peratosiDao().searchPeratosi("%${s.toString()}%").collect { adapter.update(it) }
                }
            }
        })
    }

    private fun showAddDialog() {
        val d = DialogAddPeratosiBinding.inflate(layoutInflater)
        val dialog = androidx.appcompat.app.AlertDialog.Builder(requireContext()).setView(d.root).create()
        val db = AppDatabase.getDatabase(requireContext())

        d.btnSavePer.setOnClickListener {
            val n = Peratosi(
                onoma = d.etPerName.text.toString(),
                ergasia = d.etPerErgasia.text.toString(),
                synergeio = d.etPerSynergeio.text.toString(),
                kostos = d.etPerKostos.text.toString().toDoubleOrNull() ?: 0.0,
                pliromi = d.cbPliromi.isChecked,
                oloklirosi = d.cbOloklirosi.isChecked,
                hmerominia = d.etPerDate.text.toString(),
                sxolia = d.etPerSxolia.text.toString()
            )
            lifecycleScope.launch { db.peratosiDao().insertOrUpdate(n); dialog.dismiss() }
        }
        dialog.show()
    }

    private fun showDetails(p: Peratosi) {
        androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setTitle("Λεπτομέρειες Περάτωσης")
            .setMessage("Όνομα: ${p.onoma}\nΕργασία: ${p.ergasia}\nΣυνεργείο: ${p.synergeio}\nΚόστος: ${p.kostos}€\nΠληρωμή: ${if(p.pliromi) "ΝΑΙ" else "ΟΧΙ"}\nΟλοκλήρωση: ${if(p.oloklirosi) "ΝΑΙ" else "ΟΧΙ"}\nΗμερομηνία: ${p.hmerominia}\nΣχόλια: ${p.sxolia}")
            .setPositiveButton("OK", null).show()
    }

    override fun onDestroyView() { super.onDestroyView(); _binding = null }
}