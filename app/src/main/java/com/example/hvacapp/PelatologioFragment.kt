package com.example.hvacapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hvacapp.data.AppDatabase
import com.example.hvacapp.databinding.FragmentPelatologioBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch



class PelatologioFragment : Fragment() {

    private var _binding: FragmentPelatologioBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: PelatologioAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPelatologioBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = AppDatabase.getDatabase(requireContext())


        binding.etSearch.addTextChangedListener(object : android.text.TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString()


                lifecycleScope.launch {

                    val flow = if (query.isEmpty()) {
                        db.pelatologioDao().getAllPelates()
                    } else {
                        db.pelatologioDao().searchPelates("%$query%")
                    }


                    flow.collect { result ->
                        adapter.updateData(result)
                    }
                }
            }

            override fun afterTextChanged(s: android.text.Editable?) {}
        })

        binding.etSearch.requestFocus()
        binding.etSearch.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                // Δίνουμε ένα ελάχιστο χρόνο (100ms) στο Android να ετοιμάσει το UI
                v.postDelayed({
                    val imm = requireContext().getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as android.view.inputmethod.InputMethodManager
                    imm.showSoftInput(v, android.view.inputmethod.InputMethodManager.SHOW_IMPLICIT)
                }, 100)
            }
        }


        binding.etSearch.setOnClickListener {
            val imm = requireContext().getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as android.view.inputmethod.InputMethodManager
            imm.showSoftInput(binding.etSearch, android.view.inputmethod.InputMethodManager.SHOW_IMPLICIT)
        }

        binding.fabAdd.setOnClickListener {
            showAddDialog()
        }


        adapter = PelatologioAdapter(emptyList()) { pelatis ->

            lifecycleScope.launch {
                db.pelatologioDao().delete(pelatis)
            }
        }

        binding.rvPelates.layoutManager = LinearLayoutManager(requireContext())
        binding.rvPelates.adapter = adapter


        lifecycleScope.launch {
            db.pelatologioDao().getAllPelates().collect { listaPelaton ->
                adapter.updateData(listaPelaton)
            }
        }
    }

    fun performSearch(query: String) {
        val db = AppDatabase.getDatabase(requireContext())

        lifecycleScope.launch {

            db.pelatologioDao().searchPelates("%$query%").collect { result ->
                adapter.updateData(result)
            }
        }
    }

    private fun showAddDialog() {
        val dialogBinding = com.example.hvacapp.databinding.DialogAddPelatisBinding.inflate(layoutInflater)
        val dialog = androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setView(dialogBinding.root)
            .create()

        val db = AppDatabase.getDatabase(requireContext())

        dialogBinding.btnSave.setOnClickListener {
            val onoma = dialogBinding.etName.text.toString()
            val dieuthinsi = dialogBinding.etAddress.text.toString()
            val thlefono = dialogBinding.etPhone.text.toString()
            val afm = dialogBinding.etAfm.text.toString()
            val doy = dialogBinding.etDoy.text.toString()

            if (onoma.isNotEmpty()) {
                val neosPelatis = com.example.hvacapp.data.entities.Pelatologio(
                    onoma = onoma,
                    dieuthinsi = dieuthinsi,
                    thlefono = thlefono,
                    afm = afm,
                    doy = doy
                )


                lifecycleScope.launch {
                    db.pelatologioDao().insertOrUpdate(neosPelatis)
                    //dialog.dismiss()
                }

                // Αποθήκευση Online (Firebase Firestore)
                val firestore = Firebase.firestore
                firestore.collection("pelates") // Δημιουργεί "φάκελο" pelates στο cloud
                    .document(thlefono) // Χρησιμοποιεί το τηλέφωνο ως μοναδικό κλειδί
                    .set(neosPelatis)
                    .addOnSuccessListener {
                        android.widget.Toast.makeText(requireContext(), "Συγχρονίστηκε Online!", android.widget.Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener { e ->
                        android.widget.Toast.makeText(requireContext(), "Σφάλμα Cloud: ${e.message}", android.widget.Toast.LENGTH_SHORT).show()
                    }

                dialog.dismiss()
            } else {
                dialogBinding.etName.error = "Το όνομα είναι υποχρεωτικό"
            }
        }

        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}