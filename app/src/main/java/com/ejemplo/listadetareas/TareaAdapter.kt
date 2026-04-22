package com.ejemplo.listadetareas

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ejemplo.listadetareas.databinding.ItemTareaBinding

class TareaAdapter(
    private val tareas: MutableList<Tarea>,
    private val onEliminar: (Int) -> Unit
) : RecyclerView.Adapter<TareaAdapter.TareaViewHolder>() {

    inner class TareaViewHolder(val binding: ItemTareaBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TareaViewHolder {
        val binding = ItemTareaBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return TareaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TareaViewHolder, position: Int) {
        val tarea = tareas[position]

        // ✅ TODO 1: título
        holder.binding.tvTitulo.text = tarea.titulo

        // ✅ TODO 2: estado del checkbox
        holder.binding.cbCompletada.isChecked = tarea.completada

        actualizarEstiloTexto(holder, tarea.completada)

        //Limpiar listener anterior para evitar bugs de reciclaje
        holder.binding.cbCompletada.setOnCheckedChangeListener(null)

        // ✅ TODO 3: listener checkbox
        holder.binding.cbCompletada.setOnCheckedChangeListener { _, isChecked ->
            tarea.completada = isChecked
            actualizarEstiloTexto(holder, isChecked)
        }

        // ✅ TODO 4: eliminar
        holder.binding.btnEliminar.setOnClickListener {
            onEliminar(position)
        }
    }

    override fun getItemCount(): Int = tareas.size

    private fun actualizarEstiloTexto(holder: TareaViewHolder, completada: Boolean) {
        if (completada) {
            holder.binding.tvTitulo.paintFlags =
                holder.binding.tvTitulo.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            holder.binding.tvTitulo.paintFlags =
                holder.binding.tvTitulo.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }
    }
}