package com.alibasoglu.androidplayground.ui.notifications

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.alibasoglu.androidplayground.R
import com.alibasoglu.androidplayground.databinding.FragmentNotificationsBinding
import com.alibasoglu.androidplayground.viewBinding

class NotificationsFragment : Fragment(R.layout.fragment_notifications) {

    private val binding by viewBinding(FragmentNotificationsBinding::bind)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        // _binding?.input2?.setErrorMessage("hatali sifre")

        with(binding) {
            halfCircleProgressBar.setProgress(50)
            progressBar2.setProgress(70)
            button.setOnClickListener {
                halfCircleProgressBar.setProgress(90)
                progressBar2.setProgress(10)
            }
        }
    }

}
