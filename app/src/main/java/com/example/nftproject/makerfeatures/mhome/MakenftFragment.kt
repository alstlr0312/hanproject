package com.example.nftproject.makerfeatures.mhome

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.nftproject.R
import com.example.nftproject.databinding.FragmentMakenftBinding
import java.util.*

class MakenftFragment : Fragment() {
    private lateinit var binding: FragmentMakenftBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMakenftBinding.inflate(inflater, container, false)

        binding.setCalender.setOnClickListener {
            var calendar = Calendar.getInstance()
            var year = calendar.get(Calendar.YEAR)
            var month = calendar.get(Calendar.MONTH)
            var dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

            var listener = DatePickerDialog.OnDateSetListener { datePicker, i, i2, i3 ->
                binding.opendayText.setText("${i}-${i2 + 1}-${i3}")
            }

            var picker = context?.let { it1 -> DatePickerDialog(it1, listener, year, month, dayOfMonth) }

            if (picker != null) {
                picker.show()
            }
        }

        return binding.root
    }
}
