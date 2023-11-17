package com.upao.intentodecrimen.presentacion

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import java.util.Calendar

class FragmentoTimePicker : DialogFragment(), TimePickerDialog.OnTimeSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendario = Calendar.getInstance()
        val hora = calendario.get(Calendar.HOUR_OF_DAY)
        val minuto = calendario.get(Calendar.MINUTE)

        return TimePickerDialog(requireContext(), this, hora, minuto, DateFormat.is24HourFormat(requireContext()))
    }

    override fun onTimeSet(view: TimePicker?, hora: Int, minuto: Int) {
        // Convertir la hora y minuto seleccionados a un formato apropiado y enviar el resultado al FragmentoCrimen
        val horaSeleccionada = String.format("%02d:%02d", hora, minuto)
        setFragmentResult(CLAVE_HORA_SOLICITADA, Bundle().apply { putString(CLAVE_HORA_BUNDLE, horaSeleccionada) })
    }

    companion object {
        const val CLAVE_HORA_SOLICITADA = "hora solicitada"
        const val CLAVE_HORA_BUNDLE = "hora"
    }
}
