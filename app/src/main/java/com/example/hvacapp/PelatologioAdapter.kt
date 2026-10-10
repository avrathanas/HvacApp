package com.example.hvacapp

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.hvacapp.data.entities.Pelatologio
import com.example.hvacapp.databinding.ItemPelatisBinding

class PelatologioAdapter(
    private var pelates: List<Pelatologio>,
    private val onDeleteClick: (Pelatologio) -> Unit
) : RecyclerView.Adapter<PelatologioAdapter.PelatologioViewHolder>() {

    class PelatologioViewHolder(val binding: ItemPelatisBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PelatologioViewHolder {
        val binding = ItemPelatisBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PelatologioViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PelatologioViewHolder, position: Int) {
        val currentPelatis = pelates[position]
        holder.binding.tvItemName.text = currentPelatis.onoma
        holder.binding.tvItemPhone.text = currentPelatis.thlefono

        holder.itemView.setOnClickListener {
            this@PelatologioAdapter.showDetailsDialog(holder.itemView.context, currentPelatis)
        }


        holder.binding.btnDelete.setOnClickListener {
            onDeleteClick(currentPelatis)
        }
    }

    override fun getItemCount() = pelates.size


    fun updateData(newPelates: List<Pelatologio>) {
        pelates = newPelates
        notifyDataSetChanged()
    }

    private fun showDetailsDialog(context: Context, pelatis: Pelatologio) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Στοιχεία Πελάτη")
        builder.setMessage("Όνομα: ${pelatis.onoma}\n" +
                "Διεύθυνση: ${pelatis.dieuthinsi}\n" +
                "Τηλέφωνο: ${pelatis.thlefono}\n" +
                "ΑΦΜ: ${pelatis.afm}\n" +
                "ΔΟΥ: ${pelatis.doy}")
        builder.setPositiveButton("OK", null)
        builder.show()
    }
}